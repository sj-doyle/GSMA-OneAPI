package org.gsm.oneapi.server.mms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.responsebean.mms.DeliveryInfoList;
import org.gsm.oneapi.server.OneAPIServlet;
import org.gsm.oneapi.server.ValidationRule;

/**
 * Servlet implementing the OneAPI function for querying (sent) MMS delivery status
 */
public class QueryMMSDeliveryStatusServlet extends OneAPIServlet {
	private static final long serialVersionUID = -2045239201538398260L;
	
	public static final String DELIVERYIMPOSSIBLE="DeliveryImpossible";
	public static final String DELIVEREDTONETWORK="DeliveredToNetwork";
	public static final String DELIVEREDTOTERMINAL="DeliveredToTerminal";
	public static final String DELIVERYUNCERTAIN="DeliveryUncertain";
	public static final String MESSAGEWAITING="MessageWaiting";

	static Logger logger=Logger.getLogger(QueryMMSDeliveryStatusServlet.class);

	public void init() throws ServletException {
		logger.debug("QueryDeliveryStausServlet initialised");
    }
	
	private final String[] validationRules={"1", "messaging", "outbound", "*", "requests", "*", "deliveryInfos"};
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		dumpRequestDetails(request, logger);

		String[] requestParts=getRequestParts(request);
		
		if (validateRequest(request, response, requestParts, validationRules)) {
			
			/*
			 * Decode the service parameters - in this case it is an HTTP GET request 
			 */			
			String senderAddress=requestParts[3];
			String requestId=requestParts[5];
			
			logger.debug("senderAddress = "+senderAddress);
			logger.debug("requestId = "+requestId);
			
			ValidationRule[] rules={
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "senderAddress", senderAddress),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "requestId", requestId),
			};
			
			if (checkRequestParameters(response, rules)) {
				DeliveryInfoList deliveryInfoList=new DeliveryInfoList();
				
				String resourceURL=getRequestHostnameAndContext(request)+request.getServletPath()+"/1/messaging/outbound/"+urlEncode(senderAddress)+"/requests/"+urlEncode(requestId)+"/deliveryInfos"; 
				
				deliveryInfoList.setResourceURL(resourceURL);
				
				DeliveryInfoList.DeliveryInfo[] deliveryInfo=new DeliveryInfoList.DeliveryInfo[5];
				deliveryInfo[0]=new DeliveryInfoList.DeliveryInfo("tel:+12300001", DELIVERYIMPOSSIBLE);
				deliveryInfo[1]=new DeliveryInfoList.DeliveryInfo("tel:+12300002", DELIVEREDTONETWORK);
				deliveryInfo[2]=new DeliveryInfoList.DeliveryInfo("tel:+12300003", DELIVEREDTOTERMINAL);
				deliveryInfo[3]=new DeliveryInfoList.DeliveryInfo("tel:+12300004", DELIVERYUNCERTAIN);
				deliveryInfo[4]=new DeliveryInfoList.DeliveryInfo("tel:+12300005", MESSAGEWAITING);
				
				deliveryInfoList.setDeliveryInfo(deliveryInfo);
				
				ObjectMapper mapper=new ObjectMapper();			
				String jsonResponse="{\"deliveryInfoList\":"+mapper.writeValueAsString(deliveryInfoList)+"}";
	
				sendJSONResponse(response, jsonResponse, OK, null);
			}			
		}
	}


}
