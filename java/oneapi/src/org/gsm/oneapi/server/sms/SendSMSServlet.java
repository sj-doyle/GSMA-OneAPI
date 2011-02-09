package org.gsm.oneapi.server.sms;

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
 * Servlet implementing the OneAPI function for sending an SMS message
 */
public class SendSMSServlet extends OneAPIServlet {
	private static final long serialVersionUID = 6553586905656923326L;

	static Logger logger=Logger.getLogger(SendSMSServlet.class);

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
			}
		}
		
	}

}
