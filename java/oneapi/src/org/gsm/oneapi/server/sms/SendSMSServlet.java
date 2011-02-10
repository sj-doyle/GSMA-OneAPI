package org.gsm.oneapi.server.sms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.responsebean.ResourceReference;
import org.gsm.oneapi.responsebean.mms.DeliveryInfoNotification;
import org.gsm.oneapi.server.OneAPIServlet;
import org.gsm.oneapi.server.ValidationRule;
import org.gsm.oneapi.server.mms.SendMMSServlet;

/**
 * Servlet implementing the OneAPI function for sending an SMS message
 */
public class SendSMSServlet extends OneAPIServlet implements Runnable {
	private static final long serialVersionUID = 6553586905656923326L;

	static Logger logger=Logger.getLogger(SendSMSServlet.class);

	// Used when the servlet is created
	public SendSMSServlet() {
		
	}
	
	private String callbackData=null;
	private String notifyURL=null;
	private String[] addresses=null;

	// Used when want to emulate sending a notification
	public SendSMSServlet(String callbackData, String notifyURL, String[] addresses) {
		this.callbackData=callbackData;
		this.notifyURL=notifyURL;		
		this.addresses=addresses;
	}
	
	public void init() throws ServletException {
		logger.debug("SendSMSServlet initialised");
    }
	
	private final String[] validationRules={"1", "smsmessaging", "outbound", "*", "requests"};

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		
		dumpRequestDetails(request, logger);
		
		String[] requestParts=getRequestParts(request);
		
		if (validateRequest(request, response, requestParts, validationRules)) {

			logger.debug("SendSMS - url appears correctly formatted");
			/*
			 * Decode the service parameters - in this case it is an HTTP POST request 
			 */
			String[] addresses=request.getParameterValues("address");	// Note there can be multiple addresses specified
			String senderAddress=nullOrTrimmed(request.getParameter("senderAddress"));
			String message=nullOrTrimmed(request.getParameter("message"));
			String clientCorrelator=nullOrTrimmed(request.getParameter("clientCorrelator"));
			String notifyURL=nullOrTrimmed(request.getParameter("notifyURL"));
			String callbackData=nullOrTrimmed(request.getParameter("callbackData"));
			String senderName=nullOrTrimmed(request.getParameter("senderName"));

			logger.debug("senderAddress = "+senderAddress);
			logger.debug("message = "+message);
			logger.debug("clientCorrelator = "+clientCorrelator);
			logger.debug("notifyURL = "+notifyURL);
			logger.debug("senderName = "+senderName);			
			logger.debug("callbackData = "+callbackData);
			
			if (addresses!=null) for (String add:addresses) logger.debug("address = "+add);		
			
			String resourceURL=null;

			ValidationRule[] rules={
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "senderAddress", senderAddress),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "address", addresses),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "message", message),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "clientCorrelator", clientCorrelator),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "senderName", senderName),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL_URL, "notifyURL", notifyURL),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "callbackData", callbackData),					
			};

			if (checkRequestParameters(response, rules)) {			
				resourceURL=getRequestHostnameAndContext(request)+request.getServletPath()+"/1/smsmessaging/outbound/"+urlEncode(senderAddress)+"/requests/"+urlEncode(clientCorrelator);
				
				ResourceReference resourceReference=new ResourceReference();
				resourceReference.setResourceURL(resourceURL);
				
				ObjectMapper mapper=new ObjectMapper();

				String jsonResponse="{\"resourceReference\":"+mapper.writeValueAsString(resourceReference)+"}";
				
				logger.debug("Sending response. ResourceURL="+resourceURL);
				
				sendJSONResponse(response, jsonResponse, CREATED, resourceURL);
				
				if (notifyURL!=null) {
					SendSMSServlet t=new SendSMSServlet(callbackData, notifyURL, addresses);					
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
