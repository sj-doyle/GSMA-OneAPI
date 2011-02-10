package org.gsm.oneapi.server.mms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.responsebean.ResourceReference;
import org.gsm.oneapi.server.OneAPIServlet;
import org.gsm.oneapi.server.ValidationRule;

/**
 * Servlet implementing the OneAPI function for creating an MMS message receipt subscription
 */
public class MMSReceiptReportSubscriptionServlet extends OneAPIServlet {
	private static final long serialVersionUID = 8016610130231905846L;

	static Logger logger=Logger.getLogger(MMSReceiptReportSubscriptionServlet.class);

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
			}
		}
		
	}

}
