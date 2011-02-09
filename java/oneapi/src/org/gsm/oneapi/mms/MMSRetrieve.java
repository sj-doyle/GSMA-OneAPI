package org.gsm.oneapi.mms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.endpoints.ServiceEndpoints;
import org.gsm.oneapi.foundation.CommandLineOptions;
import org.gsm.oneapi.foundation.FormParameters;
import org.gsm.oneapi.foundation.JSONRequest;
import org.gsm.oneapi.responsebean.RequestError;
import org.gsm.oneapi.responsebean.mms.InboundMessage;
import org.gsm.oneapi.responsebean.mms.InboundMessageWrapper;
import org.gsm.oneapi.responsebean.mms.MMSMessageReceiptSubscriptionResponse;
import org.gsm.oneapi.responsebean.mms.RetrieveMMSMessageResponse;
import org.gsm.oneapi.responsebean.mms.RetrieveMMSResponse;
import org.gsm.oneapi.responsebean.sms.RetrieveSMSResponse;
import org.gsm.oneapi.responsebean.sms.SMSMessageReceiptSubscriptionResponse;
import org.gsm.oneapi.server.OneAPIServlet;

public class MMSRetrieve {
	static Logger logger=Logger.getLogger(MMSRetrieve.class);
	
	ServiceEndpoints endPoints=null;
	String authorisationHeader=null;
	
	public static boolean dumpRequestAndResponse=false;

	private static JSONRequest<RetrieveMMSResponse> retrieveMMSprocessor=new JSONRequest<RetrieveMMSResponse>(new RetrieveMMSResponse());
	private static JSONRequest<MMSMessageReceiptSubscriptionResponse> mmsMessageReceiptSubscriptionProcessor=new JSONRequest<MMSMessageReceiptSubscriptionResponse>(new MMSMessageReceiptSubscriptionResponse());

	/**
	 Creates a new instance of the Receive MMS API main interface. Requires endPoints to define the URL targets of the various Receive MMS network calls and authorisationHeader containing the username/password used for HTTP Basic authorisation with the OneAPI server.  
	                          
	@param  endPoints  contains a set of service/ call specific endpoints 	
	@param  authorisationHeader Base 64 encoded username/ password
	     
	@see org.gsm.oneapi.endpoints.ServiceEndpoints
	@see org.gsm.oneapi.foundation.JSONRequest#getAuthorisationHeader(String, String)
	 */	
	public MMSRetrieve(ServiceEndpoints endPoints, String authorisationHeader) {
		this.endPoints=endPoints;
		this.authorisationHeader=authorisationHeader;
	}
	
	/**
	 Can be used to update the service endpoints  
	                          
	@param  endPoints  contains a set of service/ call specific endpoints 	

	@see org.gsm.oneapi.endpoints.ServiceEndpoints
	 */	
	public void setEndpoints(ServiceEndpoints endPoints) {
		this.endPoints=endPoints;		
	}
	
	/**
	 Can be used to update the service authorisation header  
	                          
	@param  authorisationHeader Base 64 encoded username/ password

	@see org.gsm.oneapi.foundation.JSONRequest#getAuthorisationHeader(String, String)
	 */	
	public void setAuthorisationHeader(String authorisationHeader) {
		this.authorisationHeader=authorisationHeader;
	}
	
	/**
	 Retrieve a list of MMS messages sent to your Web application  
	                          
	@param registrationId (mandatory) is agreed with your network operator for receiving messages
	@param maxBatchSize (mandatory) is the maximum number of messages to retrieve in this request
	     
	@see RetrieveMMSResponse
	*/
	public RetrieveMMSResponse retrieveMessages(String registrationId, int maxBatchSize) {
		RetrieveMMSResponse response=new RetrieveMMSResponse();
		
		if (registrationId!=null && maxBatchSize>=0) {
			
        	int responseCode=0;
            String contentType = null;

            try {
				logger.debug("endpoint="+endPoints.getRetrieveMMSEndpoint());
				String endpoint=endPoints.getRetrieveMMSEndpoint().replaceAll("\\{registrationId\\}", URLEncoder.encode(registrationId, "utf-8")).replaceAll("\\{maxBatchSize\\}", String.valueOf(maxBatchSize));
				
				if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, null);
				
				HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
	
	        	responseCode=con.getResponseCode();
	            contentType = con.getContentType();
	            
	            response.setHTTPResponseCode(responseCode);
	            response.setContentType(contentType);
		        
	            response=retrieveMMSprocessor.getResponse(con, OneAPIServlet.OK);
			} catch (Exception e) {
				logger.error("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
				e.printStackTrace();
				response.setHTTPResponseCode(responseCode);
				response.setContentType(contentType);
				
				response.setRequestError(new RequestError(RequestError.SERVICEEXCEPTION, "SVCJAVA", e.getMessage(), e.getClass().getName()));
			}
		}
		return response;
	}


	/**
	 Retrieve the full contents of an MMS message (including attachments) sent to your Web application	   
	                          
	@param registrationId (mandatory) is agreed with your network operator for receiving messages
	@param messageId (mandatory) is the messageId obtained from the {link retrieveMessages} function
	@param resFormat (mandatory) specifies the response format required. The only permitted value for verions 1 of OneAPI is 'JSON' ensuring a JSON Content-Type
	     
	@see RetrieveMMSMessageResponse
	*/
	@SuppressWarnings("deprecation")
	public RetrieveMMSMessageResponse retrieveMessageContent(String registrationId, String messageId, String resFormat) {
		RetrieveMMSMessageResponse response=new RetrieveMMSMessageResponse();
		
		if (registrationId!=null && messageId!=null) {
			
        	int responseCode=0;
            String contentType = null;

            try {
				logger.debug("endpoint="+endPoints.getRetrieveMMSMessageEndpoint());
				String endpoint=endPoints.getRetrieveMMSMessageEndpoint().replaceAll("\\{registrationId\\}", URLEncoder.encode(registrationId, "utf-8")).replaceAll("\\{messageId\\}", messageId).replaceAll("\\{resFormat\\}", resFormat);
				
				if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, null);
				
				HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
	
	        	responseCode=con.getResponseCode();
	            contentType = con.getContentType();
	            String location=con.getHeaderField("Location");
	            
	            logger.debug("responseCode="+responseCode+" contentType="+contentType);
	            
	            response.setHTTPResponseCode(responseCode);
	            response.setContentType(contentType);
	            response.setLocation(location);
	            
	            logger.debug("getting input stream");
	            
	            InputStream input=con.getInputStream();
	            
	            int boundaryIndex = contentType.indexOf("boundary=");
	            String bound=contentType.substring(boundaryIndex + 9).replaceAll("\\\"","");
	            System.out.println(bound);
	            byte[] boundary = (contentType.substring(boundaryIndex + 9).replaceAll("\\\"","")).getBytes();

	            /*
	             * Note this API has been marked as deprecated though 
	             */
	            logger.debug("Parsing...");
	            MultipartStream multipartStream =  new MultipartStream(input, boundary);
	            
	            ArrayList<RetrieveMMSMessageResponse.Attachment> attachments=new ArrayList<RetrieveMMSMessageResponse.Attachment>();
	            
	            boolean nextPart = multipartStream.skipPreamble();
	            while(nextPart) {
	              String headerSection = multipartStream.readHeaders();
	              
	              String partContentType=null;
	              String partContentName=null;
	              
	              if (headerSection!=null) {
	            	  String[] headers=headerSection.split("\n");
	            	  for (String header:headers) {
	            		  header=header.trim();
	            		  if (header.startsWith("Content-Type:")) {
	            			  String value=header.substring(header.indexOf(":")+1).trim();
	            			  if (value.indexOf(";")!= -1) {
	            				  value=value.substring(0, value.indexOf(";"));
	            			  }
	            			  partContentType=value;
	            		  }
	            		  if (header.startsWith("Content-Disposition:")) {
	            			  String value=header.substring(header.indexOf(":")+1).trim();
	            			  if (value.indexOf(";")!= -1) {
	            				  value=value.substring(0, value.indexOf(";"));
	            			  }
	            			  logger.debug("got content disposition="+value);	   
	            			  
	            			  if (header.indexOf("name=")!=-1) {
	            				  String name=header.substring(header.indexOf("name=")+5).replaceAll("\\\"", "");
	            				  logger.debug("Parsed name="+name);
	            				  partContentName=name;
	            			  }
	            		  }
	            	  }
	              }
	              
//	              System.out.println("Headers: [" + headers+"]");
	              ByteArrayOutputStream data = new ByteArrayOutputStream();
	              multipartStream.readBodyData(data);
	              
	              if (partContentType!=null && partContentType.equals("application/json") && partContentName!=null && partContentName.equals("root-fields")) {
	            	  logger.debug("Have the JSON response part:"+(new String(data.toByteArray())));
	            	  
	            	  ObjectMapper mapper=new ObjectMapper();
	            	  ByteArrayInputStream bais = new ByteArrayInputStream(data.toByteArray());
	            	  InboundMessageWrapper inboundMessageWrapper=mapper.readValue(bais, InboundMessageWrapper.class);
	            	  if (inboundMessageWrapper!=null && inboundMessageWrapper.getInboundMessage()!=null) response.setInboundMessage(inboundMessageWrapper.getInboundMessage());	            	  
	              } else {
		              RetrieveMMSMessageResponse.Attachment attachment=new RetrieveMMSMessageResponse.Attachment();
		              attachment.setAttachmentContentType(partContentType);
		              attachment.setAttachmentName(partContentName);
		              attachment.setAttachmentData(data.toByteArray());
		              attachments.add(attachment);
	              }

	              nextPart = multipartStream.readBoundary();
	            }
	            
	            response.setAttachments(attachments);
	            
//	            logger.debug("starting conversion to mime message");
//	            Properties props = new Properties(); 
//	            Session session = Session.getDefaultInstance(props, null); 
//	            MimeMessage message = new MimeMessage(session, input);
	            
//	            logger.debug("getting content");
//	            Object content=message.get
//	            MimeMultipart content=(MimeMultipart) message.getContent();
//	            
//	            
//	            int sections=content.getCount();
//	            logger.debug("Content count="+sections);
	            
		        
			} catch (Exception e) {
				logger.error("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
				e.printStackTrace();
				response.setHTTPResponseCode(responseCode);
				response.setContentType(contentType);
				
				response.setRequestError(new RequestError(RequestError.SERVICEEXCEPTION, "SVCJAVA", e.getMessage(), e.getClass().getName()));
			}
		}
		return response;
	}

	/**
	 Start subscribing to notifications of MMS messages sent to your application 
	                          
	@param destinationAddress (mandatory) is the address/ MSISDN, or code agreed with the operator, to which people may send an MMS to your application
	@param notifyURL (mandatory) is the URL to which you would like a notification of message receipts sent
	@param criteria (optional) is case-insensitve text to match against the first word of the message, ignoring any leading whitespace. This allows you to reuse a short code among various applications, each of which can register their own subscription with different criteria
	@param notificationFormat (optional) is the content type that notifications will be sent in Ð for OneAPI v1.0 only JSON is supported
	@param clientCorrelator (optional) uniquely identifies this create subscription request. If there is a communication failure during the request, using the same clientCorrelator when retrying the request allows the operator to avoid creating a duplicate subscription
	@param callbackData (optional) is a function name or other data that you would like included when the POST is sent to your application

	@see MMSMessageReceiptSubscriptionResponse
	*/
	public MMSMessageReceiptSubscriptionResponse subscribeToReceiptNotifications(String destinationAddress, String notifyURL, String criteria, String notificationFormat, String clientCorrelator, String callbackData) {
		MMSMessageReceiptSubscriptionResponse response=new MMSMessageReceiptSubscriptionResponse();

		if (destinationAddress!=null && notifyURL!=null) {
			FormParameters formParameters=new FormParameters();
			formParameters.put("destinationAddress", destinationAddress);
			formParameters.put("notifyURL", notifyURL);
			formParameters.put("criteria", criteria);
			formParameters.put("notificationFormat", notificationFormat);
			formParameters.put("clientCorrelator", clientCorrelator);
			formParameters.put("callbackData", callbackData);
			
	    	int responseCode=0;
	        String contentType = null;
			
			try {
				String endpoint=endPoints.getMMSReceiptSubscriptionsEndpoint();
				
				if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, formParameters); 
	
				HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
		        con.setDoOutput(true);
		        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
		        
		        String requestBody=JSONRequest.formEncodeParams(formParameters);
		        out.write(requestBody);
		        out.close();
	
	        	responseCode=con.getResponseCode();
	            contentType = con.getContentType();
		        
	            response=mmsMessageReceiptSubscriptionProcessor.getResponse(con, OneAPIServlet.CREATED);
			} catch (Exception e) {
				response.setHTTPResponseCode(responseCode);
				response.setContentType(contentType);
				
				response.setRequestError(new RequestError(RequestError.SERVICEEXCEPTION, "SVCJAVA", e.getMessage(), e.getClass().getName()));
				
				logger.error("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
			}
		}
		return response;
	}
	
	/**
	 Stop subscribing to message receipt notifications for all your received SMS  
	                          
	@param subscriptionId (mandatory) contains the subscriptionId of a previously created SMS message receipt subscription

	*/
	public int cancelReceiptNotifications(String subscriptionId)  {
		int responseCode=0;
		
		if (subscriptionId!=null) {
			
			try {
				logger.debug("endpoint="+endPoints.getCancelMMSReceiptSubscriptionEndpoint());
				String endpoint=endPoints.getCancelMMSReceiptSubscriptionEndpoint().replaceAll("\\{subscriptionId\\}", URLEncoder.encode(subscriptionId, "utf-8"));
				
				if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, null);
				
				HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
				
				con.setRequestMethod("DELETE");
	
	        	responseCode=con.getResponseCode();
			} catch (Exception e) {
				logger.error("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		return responseCode;
	}



}
