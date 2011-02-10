<%@ page import="org.gsm.oneapi.sms.SMSRetrieve"%>
<%@ page import="org.gsm.oneapi.responsebean.sms.InboundSMSMessageNotification"%>
<%@ page import="org.gsm.oneapi.responsebean.sms.InboundSMSMessage"%>
<%
	System.out.println("Have invoked SMS Message Receipt Notification callback");

	InboundSMSMessageNotification inboundSMSMessageNotification=SMSRetrieve.convertInboundSMSMessageNotification(request);

	if (inboundSMSMessageNotification!=null) {
		System.out.println("Received DeliveryInfoNotification");
		System.out.println("CallbackData="+inboundSMSMessageNotification.getCallbackData());
		InboundSMSMessage smsMessage=inboundSMSMessageNotification.getInboundSMSMessage();
		if (smsMessage!=null) {
			System.out.println("SMS Message:");
			System.out.println("dateTime           = "+smsMessage.getDateTime());
			System.out.println("destinationAddress = "+smsMessage.getDestinationAddress());
			System.out.println("messageId          = "+smsMessage.getMessageId());
			System.out.println("message            = "+smsMessage.getMessage());
			System.out.println("resourceURL        = "+smsMessage.getResourceURL());
			System.out.println("senderAddress      = "+smsMessage.getSenderAddress());
		}
	}
	response.setStatus(200);
%>