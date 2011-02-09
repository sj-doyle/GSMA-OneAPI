package org.gsm.oneapi.server.mms;

import java.io.IOException;
import java.util.Calendar;

import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.responsebean.RequestError;
import org.gsm.oneapi.server.OneAPIServlet;
import org.gsm.oneapi.server.ValidationRule;

import org.gsm.oneapi.responsebean.mms.InboundMessage;
import org.gsm.oneapi.responsebean.mms.InboundMMSMessage;

/**
 * Servlet implementing the OneAPI function for retrieving received the full MMS message contents
 */
public class RetrieveMMSMessageServlet extends OneAPIServlet {

	private static final long serialVersionUID = 2849235677506318772L;
	
//	public static final String DELIVERYIMPOSSIBLE="DeliveryImpossible";
//	public static final String DELIVEREDTONETWORK="DeliveredToNetwork";
//	public static final String DELIVEREDTOTERMINAL="DeliveredToTerminal";
//	public static final String DELIVERYUNCERTAIN="DeliveryUncertain";
//	public static final String MESSAGEWAITING="MessageWaiting";
	

	static Logger logger=Logger.getLogger(RetrieveMMSMessageServlet.class);

	public void init() throws ServletException {
		logger.debug("RetrieveSMSMessagesServlet initialised");
    }

	private final String[] validationRules={"1", "messaging", "inbound", "registrations", "*", "messages", "*"};
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		
		dumpRequestDetails(request, logger);

		String[] requestParts=getRequestParts(request);
		
		if (validateRequest(request, response, requestParts, validationRules)) {
			
			/*
			 * Decode the service parameters - in this case it is an HTTP GET request 
			 */			
			String registrationId=requestParts[4];
			String messageId=requestParts[6];
			String resFormat=request.getParameter("resFormat");
			
			logger.debug("registrationId = "+registrationId);
			logger.debug("messageId = "+messageId);
			logger.debug("resFormat = "+resFormat);
			
			ValidationRule[] rules={
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "registrationId", registrationId),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "messageId", messageId),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_JSON, "resFormat", resFormat),
			};

			if (checkRequestParameters(response, rules)) {			
				InboundMessage inboundMessage=new InboundMessage();
				java.util.Date message1DT=makeUTCDateTime(2010, Calendar.NOVEMBER, 19, 12, 0, 0);
				inboundMessage.setDateTimeAsDate(message1DT);
				inboundMessage.setDestinationAddress("6789");
				String messageId1="msg1";
				inboundMessage.setMessageId(messageId1);
				InboundMMSMessage inboundMMSMessage=new InboundMMSMessage();
				inboundMMSMessage.setSubject("Rock Festival 2010");
				inboundMMSMessage.setMessage("Making the somewhat shorter journey from Brighton, homegrown heroes Architects make their first festival appearance in this country in support of new album 'The Here And Now', which was released one week ago today. Never has an album been more aptly titled, with the band currently on the cover of Metal Hammer magazine while their latest single enjoys daytime play on national radio, and the album receives rave reviews across the rock media, earning it a top 75 chart position (57) in its first week. Frontman Sam Carter enthuses:");
				inboundMessage.setInboundMMSMessage(inboundMMSMessage);
				
				String resourceURL1=getRequestHostnameAndContext(request)+request.getServletPath()+"/1/messaging/inbound/registrations/"+urlEncode(registrationId)+"/messages/"+urlEncode(messageId1);
				inboundMessage.setResourceURL(resourceURL1);
				inboundMessage.setSenderAddress("tel:+1234567");

				ObjectMapper mapper=new ObjectMapper();			
				String jsonResponse="{\"inboundMessage\":"+mapper.writeValueAsString(inboundMessage)+"}";
				
				System.out.println("JSON section=\n"+jsonResponse);

				try {
					logger.debug("Constructing multipart");
			        MimeMultipart mp = new MimeMultipart("mixed");

			        logger.debug("Storing JSON response as first section");
			        InternetHeaders rootHdr = new InternetHeaders();
			        rootHdr.addHeader("Content-Type", "application/json");
			        rootHdr.addHeader("Content-Disposition", "form-data; name=\"root-fields\"");	
			        byte[] rootData = jsonResponse.getBytes();
					mp.addBodyPart(new MimeBodyPart(rootHdr, rootData));
					
					logger.debug("Send second section as text attachment");			        
			        InternetHeaders textPartHeader = new InternetHeaders();
			        textPartHeader.addHeader("Content-Type", "text/plain");
			        textPartHeader.addHeader("Content-Disposition", "form-data; name=\"textpart.txt\"");
			        byte[] textPartData = "Hello from OneAPI".getBytes();
			        mp.addBodyPart(new MimeBodyPart(textPartHeader, textPartData));
	
					logger.debug("Send third section as html attachment");			        
			        InternetHeaders htmlPartHeader = new InternetHeaders();
			        htmlPartHeader.addHeader("Content-Type", "text/html");
			        htmlPartHeader.addHeader("Content-Disposition", "form-data; name=\"htmlpart.htm\"");
			        byte[] htmlPartData = "<html><body><p><strong>Hello from OneAPI</strong></p></body></html>".getBytes();
			        mp.addBodyPart(new MimeBodyPart(htmlPartHeader, htmlPartData));
			        
			        logger.debug("Sending status="+OK);
					response.setStatus(OK);
					response.setContentType(mp.getContentType());
					response.setHeader("Location", resourceURL1);
					
					logger.debug("Sending content type="+mp.getContentType());

					logger.debug("Sending MIME content");
			        mp.writeTo(response.getOutputStream());
			        
				} catch (MessagingException e) {
					e.printStackTrace();
					sendError(response, BAD_REQUEST, RequestError.SERVICEEXCEPTION, "SVC0004", "Messaging exception ", e.getMessage());
				}
			} else {
				sendError(response, BAD_REQUEST, RequestError.SERVICEEXCEPTION, "SVC0003", "Retrieve MMS messages service - request is missing registrationId or messageId", null);			
			}			
		}
	}


}
