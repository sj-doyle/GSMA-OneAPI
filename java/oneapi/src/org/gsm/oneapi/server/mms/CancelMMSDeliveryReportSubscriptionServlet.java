package org.gsm.oneapi.server.mms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.gsm.oneapi.server.OneAPIServlet;
import org.gsm.oneapi.server.ValidationRule;

/**
 * Servlet implementing the OneAPI function for cancelling MMS delivery report subscriptions
 */
public class CancelMMSDeliveryReportSubscriptionServlet extends OneAPIServlet {
	private static final long serialVersionUID = -8377700391917892308L;

	static Logger logger=Logger.getLogger(CancelMMSDeliveryReportSubscriptionServlet.class);

	public void init() throws ServletException {
		logger.debug("CancelMMSDeliveryReportSubscriptionServlet initialised");
    }
	
	private final String[] validationRules={"1", "messaging", "outbound", "subscriptions", "*"};
	
	public void doDelete(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{		
		dumpRequestDetails(request, logger);

		String[] requestParts=getRequestParts(request);
		
		if (validateRequest(request, response, requestParts, validationRules)) {
			
			/*
			 * Decode the service parameters - in this case it is an HTTP GET request 
			 */			
			String subscriptionId=requestParts[4];
			
			ValidationRule[] rules={
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "subscriptionId", subscriptionId),
			};

			if (checkRequestParameters(response, rules)) {			

				logger.debug("subscriptionId = "+subscriptionId);			
				response.setStatus(NOCONTENT);
			}			
		}
	}


}
