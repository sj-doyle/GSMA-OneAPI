package org.gsm.oneapi.server.mms;

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
import org.gsm.oneapi.responsebean.mms.InboundMMSMessage;
import org.gsm.oneapi.responsebean.mms.InboundMessage;
import org.gsm.oneapi.responsebean.mms.InboundMessageNotification;
import org.gsm.oneapi.server.OneAPIServlet;
import org.gsm.oneapi.server.ValidationRule;

/**
 * Servlet implementing the OneAPI function for creating an MMS message receipt subscription
 */
public class MMSReceiptReportSubscriptionServlet extends OneAPIServlet implements Runnable {
	private static final long serialVersionUID = 8016610130231905846L;

	static Logger logger=Logger.getLogger(MMSReceiptReportSubscriptionServlet.class);

	// Used when the servlet is created
	public MMSReceiptReportSubscriptionServlet() {
		
	}
	
	private String callbackData=null;
	private String notifyURL=null;

	// Used when want to emulate sending a notification
	public MMSReceiptReportSubscriptionServlet(String callbackData, String notifyURL) {
		this.callbackData=callbackData;
		this.notifyURL=notifyURL;		
	}
	


	public void init() throws ServletException {
		logger.debug("MMSReceiptReportSubscriptionServlet initialised");
    }
	
	private final String[] validationRules={"1", "messaging", "inbound", "subscriptions"};

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		
		dumpRequestDetails(request, logger);
		
		String[] requestParts=getRequestParts(request);
		
		if (validateRequest(request, response, requestParts, validationRules)) {

			logger.debug("MMS Receive Report Subscriptions - url appears correctly formatted");
			
			/*
			 * Decode the service parameters - in this case it is an HTTP POST request 
			 */
			String destinationAddress=nullOrTrimmed(request.getParameter("destinationAddress"));
			String notifyURL=nullOrTrimmed(request.getParameter("notifyURL"));
			String criteria=nullOrTrimmed(request.getParameter("criteria"));
			String notificationFormat=nullOrTrimmed(request.getParameter("notificationFormat"));
			String clientCorrelator=nullOrTrimmed(request.getParameter("clientCorrelator"));
			String callbackData=nullOrTrimmed(request.getParameter("callbackData"));

			logger.debug("destinationAddress = "+destinationAddress);
			logger.debug("notifyURL = "+notifyURL);
			logger.debug("criteria = "+criteria);
			logger.debug("notificationFormat = "+notificationFormat);
			logger.debug("clientCorrelator = "+clientCorrelator);
			logger.debug("callbackData = "+callbackData);			
			
			ValidationRule[] rules={
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "destinationAddress", destinationAddress),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_URL, "notifyURL", notifyURL),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "criteria", criteria),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL_JSON, "notificationFormat", notificationFormat),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "clientCorrelator", clientCorrelator),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "callbackData", callbackData),					
			};
			
			if (checkRequestParameters(response, rules)) {
				String resourceURL=getRequestHostnameAndContext(request)+request.getServletPath()+"/1/messaging/inbound/subscriptions/sub6789";
				
				ResourceReference resourceReference=new ResourceReference();
				resourceReference.setResourceURL(resourceURL);
								
				ObjectMapper mapper=new ObjectMapper();

				String jsonResponse="{\"resourceReference\":"+mapper.writeValueAsString(resourceReference)+"}";
				
				logger.debug("Sending response. ResourceURL="+resourceURL);
				
				sendJSONResponse(response, jsonResponse, CREATED, resourceURL);
				
				if (notifyURL!=null) {
					MMSReceiptReportSubscriptionServlet t=new MMSReceiptReportSubscriptionServlet(callbackData, notifyURL);					
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
			
			InboundMessageNotification inboundMessageNotification=new InboundMessageNotification();
			
			InboundMessage inboundMessage=new InboundMessage();
			inboundMessage.setDateTimeAsDate(new java.util.Date());
			inboundMessage.setDestinationAddress("3456");
			inboundMessage.setMessageId("mes1234");
			inboundMessage.setSenderAddress("+447825123456");
			
			InboundMMSMessage inboundMMSMessage=new InboundMMSMessage();
			inboundMMSMessage.setMessage("Vote for Mega Boy Band");
			inboundMessage.setInboundMMSMessage(inboundMMSMessage);
			
			inboundMessageNotification.setInboundMessage(inboundMessage);
			inboundMessageNotification.setCallbackData(callbackData);
			
			ObjectMapper mapper=new ObjectMapper();			
			String jsonResponse="{\"inboundMessageNotification\":"+mapper.writeValueAsString(inboundMessageNotification)+"}";

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
