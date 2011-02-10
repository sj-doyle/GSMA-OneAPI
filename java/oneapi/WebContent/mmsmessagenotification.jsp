<%@ page import="org.gsm.oneapi.mms.MMSRetrieve"%>
<%@ page import="org.gsm.oneapi.responsebean.mms.InboundMessageNotification"%>
<%@ page import="org.gsm.oneapi.responsebean.mms.InboundMessage"%>
<%
	System.out.println("Have invoked MMS Message Receipt Notification callback");

	InboundMessageNotification inboundMessageNotification=MMSRetrieve.convertInboundMessageNotification(request);

	if (inboundMessageNotification!=null) {
		System.out.println("Received DeliveryInfoNotification");
		System.out.println("CallbackData="+inboundMessageNotification.getCallbackData());
		InboundMessage mmsMessage=inboundMessageNotification.getInboundMessage();
		if (mmsMessage!=null) {
			System.out.println("MMS Message:");
			System.out.println("dateTime           = "+mmsMessage.getDateTime());
			System.out.println("destinationAddress = "+mmsMessage.getDestinationAddress());
			System.out.println("messageId          = "+mmsMessage.getMessageId());
			if (mmsMessage.getInboundMMSMessage()!=null) System.out.println("message            = "+mmsMessage.getInboundMMSMessage().getMessage());
			System.out.println("resourceURL        = "+mmsMessage.getResourceURL());
			System.out.println("senderAddress      = "+mmsMessage.getSenderAddress());
		}
	}
	response.setStatus(200);
%>