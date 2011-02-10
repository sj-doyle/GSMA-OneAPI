<%@ page import="org.gsm.oneapi.mms.MMSSend"%>
<%@ page import="org.gsm.oneapi.responsebean.mms.DeliveryInfoNotification"%>
<%
	System.out.println("Have invoked MMS Delivery Notification callback");

	DeliveryInfoNotification deliveryInfoNotification=MMSSend.convertDeliveryInfoNotification(request);

	if (deliveryInfoNotification!=null) {
		System.out.println("Received DeliveryInfoNotification");
		System.out.println("CallbackData="+deliveryInfoNotification.getCallbackData());
		System.out.println("Number of deliveryInfo elements="+deliveryInfoNotification.getDeliveryInfo()!=null?deliveryInfoNotification.getDeliveryInfo().length:0);
		if (deliveryInfoNotification.getDeliveryInfo()!=null) {
			for (int i=0; i<deliveryInfoNotification.getDeliveryInfo().length; i++) {
				DeliveryInfoNotification.DeliveryInfo deliveryInfo=deliveryInfoNotification.getDeliveryInfo()[i];
				if (deliveryInfo!=null) System.out.println("address="+deliveryInfo.getAddress()+" deliveryStatus="+deliveryInfo.getDeliveryStatus());
			}
		}
	}
	response.setStatus(200);
%>