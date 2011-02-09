package org.gsm.oneapi.server.mms;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.responsebean.RequestError;
import org.gsm.oneapi.responsebean.mms.InboundMMSMessage;
import org.gsm.oneapi.responsebean.mms.InboundMessage;
import org.gsm.oneapi.responsebean.mms.InboundMessageList;
import org.gsm.oneapi.server.OneAPIServlet;
import org.gsm.oneapi.server.ValidationRule;

/**
 * Servlet implementing the OneAPI function for retrieving received MMS message (headers)
 */
public class RetrieveMMSMessagesServlet extends OneAPIServlet {
	private static final long serialVersionUID = -4424298224881074100L;

	public static final String DELIVERYIMPOSSIBLE="DeliveryImpossible";
	public static final String DELIVEREDTONETWORK="DeliveredToNetwork";
	public static final String DELIVEREDTOTERMINAL="DeliveredToTerminal";
	public static final String DELIVERYUNCERTAIN="DeliveryUncertain";
	public static final String MESSAGEWAITING="MessageWaiting";
	

	static Logger logger=Logger.getLogger(RetrieveMMSMessagesServlet.class);

	public void init() throws ServletException {
		logger.debug("RetrieveSMSMessagesServlet initialised");
    }

	private final String[] validationRules={"1", "messaging", "inbound", "registrations", "*", "messages"};
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		
		dumpRequestDetails(request, logger);

		String[] requestParts=getRequestParts(request);
		
		if (validateRequest(request, response, requestParts, validationRules)) {
			
			/*
			 * Decode the service parameters - in this case it is an HTTP GET request 
			 */			
			String registrationId=requestParts[4];
			int maxBatchSize=parseInt(request.getParameter("maxBatchSize"));
			
			logger.debug("registrationId = "+registrationId);
			logger.debug("maxBatchSize = "+maxBatchSize);
			
			ValidationRule[] rules={
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "registrationId", registrationId),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL_INT_GE_ZERO, "maxBatchSize", Integer.valueOf(maxBatchSize)),
			};

			if (checkRequestParameters(response, rules)) {			
				InboundMessageList inboundMessageList=new InboundMessageList();
				
				String resourceURL=getRequestHostnameAndContext(request)+request.getServletPath()+"/1/messaging/inbound/registrations/"+urlEncode(registrationId)+"/messages"; 

				inboundMessageList.setResourceURL(resourceURL);
				
				java.util.Date message1DT=makeUTCDateTime(2009, Calendar.NOVEMBER, 19, 12, 0, 0);
				String messageId1="msg1";
				String resourceURL1=getRequestHostnameAndContext(request)+request.getServletPath()+"/1/messaging/inbound/registrations/"+urlEncode(registrationId)+"/messages/"+urlEncode(messageId1);
				InboundMessage message1=new InboundMessage(message1DT, "3456", messageId1, resourceURL1, "+447825123456");
				message1.setInboundMMSMessage(new InboundMMSMessage("Msg 1 subject", null));
				
				
				java.util.Date message2DT=makeUTCDateTime(2009, Calendar.NOVEMBER, 19, 14, 30, 25);
				String messageId2="msg2";
				String resourceURL2=getRequestHostnameAndContext(request)+request.getServletPath()+"/1/messaging/inbound/registrations/"+urlEncode(registrationId)+"/messages/"+urlEncode(messageId2);
				InboundMessage message2=new InboundMessage(message2DT, "3456", messageId2, resourceURL2, "+447825789123");
				message2.setInboundMMSMessage(new InboundMMSMessage("Msg 2 subject", null));
				
				if (maxBatchSize==1) {
					InboundMessage[] messages={message1};
					inboundMessageList.setNumberOfMessagesInThisBatch(1);
					inboundMessageList.setInboundMessage(messages);
				} else {
					InboundMessage[] messages={message1, message2};
					inboundMessageList.setNumberOfMessagesInThisBatch(2);
					inboundMessageList.setInboundMessage(messages);					
				}
				inboundMessageList.setTotalNumberOfPendingMessages(2);
				
				ObjectMapper mapper=new ObjectMapper();			
				String jsonResponse="{\"inboundMessageList\":"+mapper.writeValueAsString(inboundMessageList)+"}";
	
				sendJSONResponse(response, jsonResponse, OK, null);
			}			
		}
	}


}
