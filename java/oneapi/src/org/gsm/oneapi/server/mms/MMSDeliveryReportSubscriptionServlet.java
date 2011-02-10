package org.gsm.oneapi.server.mms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.responsebean.mms.DeliveryReceiptSubscription;
import org.gsm.oneapi.server.OneAPIServlet;
import org.gsm.oneapi.server.ValidationRule;

/**
 * Servlet implementing the OneAPI function for creating an MMS delivery report subscription
 */
public class MMSDeliveryReportSubscriptionServlet extends OneAPIServlet {
	
	private static final long serialVersionUID = -7359556423074788912L;
	
	static Logger logger=Logger.getLogger(MMSDeliveryReportSubscriptionServlet.class);

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
			}
		}
		
	}

}
