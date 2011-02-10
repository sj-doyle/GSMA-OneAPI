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
import org.gsm.oneapi.responsebean.mms.DeliveryReceiptSubscription;
import org.gsm.oneapi.responsebean.mms.DeliveryInfoNotification;
import org.gsm.oneapi.server.OneAPIServlet;
import org.gsm.oneapi.server.ValidationRule;

/**
 * Servlet implementing the OneAPI function for creating an MMS delivery report subscription
 */
public class MMSDeliveryReportSubscriptionServlet extends OneAPIServlet implements Runnable {
	
	private static final long serialVersionUID = -7359556423074788912L;
	
	static Logger logger=Logger.getLogger(MMSDeliveryReportSubscriptionServlet.class);

	// Used when the servlet is created
	public MMSDeliveryReportSubscriptionServlet() {
		
	}
	
	private String callbackData=null;
	private String notifyURL=null;

	// Used when want to emulate sending a notification
	public MMSDeliveryReportSubscriptionServlet(String callbackData, String notifyURL) {
		this.callbackData=callbackData;
		this.notifyURL=notifyURL;		
	}
	

	public void init() throws ServletException {
		logger.debug("MMSDeliveryReportSubscriptionServlet initialised");
    }
	
	private final String[] validationRules={"1", "messaging", "outbound", "*", "subscriptions"};

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		dumpRequestDetails(request, logger);
		
		String[] requestParts=getRequestParts(request);
		
		if (validateRequest(request, response, requestParts, validationRules)) {

			logger.debug("MMS Delivery Report Subscriptions - url appears correctly formatted");
			
			String senderAddress=requestParts[3];

			/*
			 * Decode the service parameters - in this case it is an HTTP POST request 
			 */
			String clientCorrelator=nullOrTrimmed(request.getParameter("clientCorrelator"));
			String notifyURL=nullOrTrimmed(request.getParameter("notifyURL"));
			String callbackData=nullOrTrimmed(request.getParameter("callbackData"));

			logger.debug("senderAddress = "+senderAddress);
			logger.debug("clientCorrelator = "+clientCorrelator);
			logger.debug("notifyURL = "+notifyURL);
			logger.debug("callbackData = "+callbackData);			
			
			String resourceURL=null;
			
			ValidationRule[] rules={
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "senderAddress", senderAddress),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_URL, "notifyURL", notifyURL),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "clientCorrelator", clientCorrelator),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "callbackData", callbackData),					
			};

			
			if (checkRequestParameters(response, rules)) {
				resourceURL=getRequestHostnameAndContext(request)+request.getServletPath()+"/1/messaging/outbound/subscriptions/sub789";
				
				DeliveryReceiptSubscription receiptSubscription=new DeliveryReceiptSubscription();
				receiptSubscription.setResourceURL(resourceURL);
				
				DeliveryReceiptSubscription.CallbackReference callbackReference=new DeliveryReceiptSubscription.CallbackReference();
				callbackReference.setNotifyURL(notifyURL);
				callbackReference.setCallbackData(callbackData);
				
				receiptSubscription.setCallbackReference(callbackReference);
				
				ObjectMapper mapper=new ObjectMapper();

				String jsonResponse="{\"deliveryReceiptSubscription\":"+mapper.writeValueAsString(receiptSubscription)+"}";
				
				logger.debug("Sending response. ResourceURL="+resourceURL);
				
				sendJSONResponse(response, jsonResponse, CREATED, resourceURL);
				
				if (notifyURL!=null) {
					MMSDeliveryReportSubscriptionServlet t=new MMSDeliveryReportSubscriptionServlet(callbackData, notifyURL);					
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
			DeliveryInfoNotification.DeliveryInfo deliveryInfo1=new DeliveryInfoNotification.DeliveryInfo("tel:1350000001", "DeliveredToTerminal");
			DeliveryInfoNotification.DeliveryInfo deliveryInfo2=new DeliveryInfoNotification.DeliveryInfo("tel:1350000999", "DeliveredToTerminal");
			deliveryInfoNotification.setDeliveryInfo(new DeliveryInfoNotification.DeliveryInfo[] {deliveryInfo1, deliveryInfo2});
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
