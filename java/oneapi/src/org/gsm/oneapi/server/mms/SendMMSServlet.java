package org.gsm.oneapi.server.mms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.responsebean.ResourceReference;
import org.gsm.oneapi.responsebean.mms.DeliveryInfoNotification;
import org.gsm.oneapi.server.OneAPIServlet;
import org.gsm.oneapi.server.ValidationRule;

/**
 * Servlet implementing the OneAPI function for sending an MMS message
 */
public class SendMMSServlet extends OneAPIServlet implements Runnable {
	private static final long serialVersionUID = -626608869216499030L;
	
	static Logger logger=Logger.getLogger(SendMMSServlet.class);

	// Used when the servlet is created
	public SendMMSServlet() {
		
	}
	
	private String callbackData=null;
	private String notifyURL=null;
	private String[] addresses=null;

	// Used when want to emulate sending a notification
	public SendMMSServlet(String callbackData, String notifyURL, String[] addresses) {
		this.callbackData=callbackData;
		this.notifyURL=notifyURL;		
		this.addresses=addresses;
	}
	

	public void init() throws ServletException {
		logger.debug("SendMMSServlet initialised");
    }
	
	private final String[] validationRules={"1", "messaging", "outbound", "*", "requests"};

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{

		/*
		 * The initial processing of the content is performed using the Apache Commons FileUpload package
		 */
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(10*1024*1024);  // Set factory constraints - 10 meg individual attachment
		ServletFileUpload upload = new ServletFileUpload(factory); // Create a new file upload handler 
		upload.setSizeMax(50*1024*1024); // Set overall request size constraint
		
		/*
		 * The form parameter block and attachments are saved when encountered
		 */
		ArrayList<byte[]> attachments=new ArrayList<byte[]>();
		ArrayList<String> contentTypes=new ArrayList<String>();
		ArrayList<String> fileNames=new ArrayList<String>();
		String parameterBlock=null;

		// Use Apache Commons FileUpload to parse the HTTP request
		try {
			List<FileItem> items = (List<FileItem>) upload.parseRequest(request);
			
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = iter.next();

			    if (item.isFormField()) {
			    	String name = item.getFieldName();
			        String value = item.getString();
			        String type = item.getContentType();
			        logger.debug("PARAMETER...Field Name="+name+" type="+type);
			        if (name.equals("root-fields") && type.equals("application/x-www-form-urlencoded")) {
			        	parameterBlock=value;
			        } else {
				        attachments.add(item.get());
				        contentTypes.add(type);
				        fileNames.add(name);
			        }
			    } else {
			    	String fieldName = item.getFieldName();
			        String fileName = item.getName();
			        String fileType = item.getContentType();
			        logger.debug("FILE...Field Name="+fieldName+" fileName="+fileName+" fileType="+fileType);
			        attachments.add(item.get());
			        contentTypes.add(fileType);
			        fileNames.add(fileName);
			    }
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		logger.debug("Parameter block contains:\n"+parameterBlock);
		
		dumpRequestDetails(request, logger);
		
		String[] requestParts=getRequestParts(request);

		if (validateRequest(request, response, requestParts, validationRules)) {

			String contentType=request.getContentType();
			
			logger.debug("Request content type="+contentType);
			
			ArrayList<String> addresses=new ArrayList<String> ();
			String senderAddress=null;
			String message=null;
			String clientCorrelator=null;
			String notifyURL=null;
			String callbackData=null;
			String senderName=null;
			
			if (parameterBlock!=null) {
				String[] split=parameterBlock.split("\\&");
				logger.debug("ParameterBlock split into "+split.length+" elements");
				for (int i=0; i<split.length; i++) {
					String line=split[i].trim();
					logger.debug("Line ["+i+"] = "+line);
					if (line.indexOf("=")!=-1) {
						String[] kvp=line.split("\\=",2);
						if (kvp!=null && kvp.length==2 && kvp[0]!=null && kvp[1]!=null) {
							// Have key=value 
							String key=URLDecoder.decode(kvp[0].trim(), "UTF-8");
							String value=URLDecoder.decode(kvp[1].trim(), "UTF-8");
							logger.debug("Key = ["+key+"] Value = ["+value+"]");
							if (key!=null) {
								if (key.equals("senderAddress")) {
									senderAddress=value;
								} else if (key.equals("message")) {
									message=value;
								} else if (key.equals("clientCorrelator")) {
									clientCorrelator=value;
								} else if (key.equals("notifyURL")) {
									notifyURL=value;
								} else if (key.equals("callbackData")) {
									callbackData=value;
								} else if (key.equals("senderName")) {
									senderName=value;
								} else if (key.equals("address")) {
									addresses.add(value);
								}
								
							}
						}
					}
				}
			}
			
			logger.debug("SendMMS - url appears correctly formatted");
			/*
			 * Decode the service parameters - in this case it is an HTTP POST request 
			 */

			logger.debug("senderAddress = "+senderAddress);
			logger.debug("message = "+message);
			logger.debug("clientCorrelator = "+clientCorrelator);
			logger.debug("notifyURL = "+notifyURL);
			logger.debug("senderName = "+senderName);			
			logger.debug("callbackData = "+callbackData);

			if (addresses!=null) for (String add:addresses) logger.debug("address = "+add);		
			
			if (attachments.size()>0) {
				for (int i=0; i<attachments.size(); i++) {
					logger.debug("Attachment ["+i+"] name="+fileNames.get(i)+" has size="+(attachments.get(i)!=null?attachments.get(i).length:0)+" type="+contentTypes.get(i));
				}
			}

			ValidationRule[] rules={
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "senderAddress", senderAddress),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "address", addresses),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "message", message),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "senderName", senderName),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "clientCorrelator", clientCorrelator),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL_URL, "notifyURL", notifyURL),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "callbackData", callbackData),					
			};

			if (checkRequestParameters(response, rules)) {			
				String resourceURL=getRequestHostnameAndContext(request)+request.getServletPath()+"/1/messaging/outbound/"+urlEncode(senderAddress)+"/requests/"+urlEncode(clientCorrelator);
				
				ResourceReference resourceReference=new ResourceReference();
				resourceReference.setResourceURL(resourceURL);
				
				ObjectMapper mapper=new ObjectMapper();

				String jsonResponse="{\"resourceReference\":"+mapper.writeValueAsString(resourceReference)+"}";
				
				logger.debug("Sending response. ResourceURL="+resourceURL);
				
				sendJSONResponse(response, jsonResponse, CREATED, resourceURL);
				
				if (notifyURL!=null) {
					SendMMSServlet t=new SendMMSServlet(callbackData, notifyURL, addresses.toArray(new String[]{}));					
					new Thread(t).start();
				}

			}
		}
		
	}

	public void run() {
		logger.debug("Notifier Thread :: Sleeping now...");
		try {
			Thread.sleep(10000L);
		} catch (Exception e) {}
		logger.debug("Notifier Thread ::  Awoken");
		try {
			logger.debug("Notifier Thread :: Creating connection to "+notifyURL);
			HttpURLConnection con = (HttpURLConnection) new URL(notifyURL).openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			
			DeliveryInfoNotification deliveryInfoNotification=new DeliveryInfoNotification();
			
			DeliveryInfoNotification.DeliveryInfo[] deliveryInfo=new DeliveryInfoNotification.DeliveryInfo[addresses.length];
			for (int i=0; i<addresses.length; i++) {
				if (addresses[i]!=null && addresses[i].trim().length()>0) deliveryInfo[i]=new DeliveryInfoNotification.DeliveryInfo(addresses[i], "DeliveredToTerminal");
			}
			deliveryInfoNotification.setDeliveryInfo(deliveryInfo);
			deliveryInfoNotification.setCallbackData(callbackData);			
			
			ObjectMapper mapper=new ObjectMapper();			
			String jsonResponse="{\"deliveryInfoNotification\":"+mapper.writeValueAsString(deliveryInfoNotification)+"}";

			logger.debug("Notifier Thread :: Sending JSON data: "+jsonResponse);
			OutputStream output=con.getOutputStream();
			byte[] ba=jsonResponse.getBytes();
			output.write (ba);
			output.flush();
			output.close();
			logger.debug("Notifier Thread :: Finished output");
			logger.debug("Notifier Thread :: Reading response");
			
			InputStream in=con.getInputStream();
			logger.debug("Notifier Thread :: Response code: "+con.getResponseCode());
			if (in!=null) {
				int c;
				StringBuffer rbuf=new StringBuffer();
				while ((c=in.read())!=-1) {
					rbuf.append((char) c);
				}
				logger.debug("Notifier Thread :: Read: "+rbuf.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
