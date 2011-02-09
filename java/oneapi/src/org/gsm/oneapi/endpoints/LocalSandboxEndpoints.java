package org.gsm.oneapi.endpoints;

import org.apache.log4j.Logger;

public class LocalSandboxEndpoints {

	static Logger logger=Logger.getLogger(LocalSandboxEndpoints.class);
	
//	public static final LocalSandboxEndpoints INSTANCE=new LocalSandboxEndpoints();
//	
	ServiceEndpoints endpoints=null;
	
	public LocalSandboxEndpoints () {
		endpoints=new ServiceEndpoints();		
		reset();
	}
	
	public ServiceEndpoints getServiceEndpoints() { return endpoints; }
	
//	public static final LocalSandboxEndpoints getInstance() {
//		INSTANCE.reset();
//		return INSTANCE;
//	}
//	
//	
	public void reset(String prefix) {
		endpoints.AmountCharge=prefix+"AmountChargeService/1/payment/{endUserId}/transactions/amount";
		endpoints.AmountRefund=prefix+"AmountRefundService/1/payment/{endUserId}/transactions/amount";
		endpoints.AmountReserve=prefix+"AmountReserveService/1/payment/{endUserId}/transactions/amountReservation";
		endpoints.AmountReserveAdditional=prefix+"AmountReserveAdditionalService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}";
		endpoints.AmountReservationCharge=prefix+"AmountReserveChargeService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}";
		endpoints.AmountReservationRelease=prefix+"AmountReserveReleaseService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}";
		
		endpoints.Location=prefix+"LocationService/1/location/queries/location";
		
		endpoints.SendSMS=prefix+"SendSMSService/1/smsmessaging/outbound/{senderAddress}/requests";
		endpoints.QuerySMSDelivery=prefix+"QuerySMSService/1/smsmessaging/outbound/{senderAddress}/requests/{requestId}/deliveryInfos";
		endpoints.SMSDeliverySubscriptions=prefix+"SMSDeliveryService/1/smsmessaging/outbound/{senderAddress}/subscriptions";
		endpoints.CancelSMSDeliverySubscription=prefix+"CancelSMSDeliveryService/1/smsmessaging/outbound/subscriptions/{subscriptionId}";
		endpoints.RetrieveSMS=prefix+"RetrieveSMSService/1/smsmessaging/inbound/registrations/{registrationId}/messages/?maxBatchSize={maxBatchSize}";
		endpoints.SMSReceiptSubscriptions=prefix+"SMSReceiptService/1/smsmessaging/inbound/subscriptions";
		endpoints.CancelSMSReceiptSubscription=prefix+"CancelSMSReceiptService/1/smsmessaging/inbound/subscriptions/{subscriptionId}";		

		endpoints.SendMMS=prefix+"SendMMSService/1/messaging/outbound/{senderAddress}/requests";
		endpoints.QueryMMSDelivery=prefix+"QueryMMSService/1/messaging/outbound/{senderAddress}/requests/{requestId}/deliveryInfos";
		endpoints.MMSDeliverySubscriptions=prefix+"MMSDeliveryService/1/messaging/outbound/{senderAddress}/subscriptions";
		endpoints.CancelMMSDeliverySubscription=prefix+"CancelMMSDeliveryService/1/messaging/outbound/subscriptions/{subscriptionId}";
		endpoints.RetrieveMMS=prefix+"RetrieveMMSService/1/messaging/inbound/registrations/{registrationId}/messages/?maxBatchSize={maxBatchSize}";
		endpoints.RetrieveMMSMessage=prefix+"RetrieveMMSMessageService/1/messaging/inbound/registrations/{registrationId}/messages/{messageId}?resFormat={resFormat}";
		endpoints.MMSReceiptSubscriptions=prefix+"MMSReceiptService/1/messaging/inbound/subscriptions";
		endpoints.CancelMMSReceiptSubscription=prefix+"CancelMMSReceiptService/1/messaging/inbound/subscriptions/{subscriptionId}";		

	}
	
	public void reset() {
		reset("http://localhost:8080/oneapiserver/");
	}
	public void setAmountCharge(String AmountCharge) { endpoints.AmountCharge=AmountCharge; }
	public void setAmountRefund(String AmountRefund) { endpoints.AmountRefund=AmountRefund; }
	public void setAmountReserve(String AmountReserve) { endpoints.AmountReserve=AmountReserve; }
	public void setAmountReserveAdditional(String AmountReserveAdditional) { endpoints.AmountReserveAdditional=AmountReserveAdditional; }
	public void setAmountReservationCharge(String AmountReservationCharge) { endpoints.AmountReservationCharge=AmountReservationCharge; }
	public void setAmountReservationRelease(String AmountReservationRelease) { endpoints.AmountReservationRelease=AmountReservationRelease; }

	public void setLocation(String Location) { endpoints.Location=Location; }

	public void setSendSMS(String SendSMS) { endpoints.SendSMS=SendSMS; }
	public void setQuerySMSDelivery(String QuerySMSDelivery) { endpoints.QuerySMSDelivery=QuerySMSDelivery; }
	public void setSMSDeliverySubscriptions(String SMSDeliverySubscriptions) { endpoints.SMSDeliverySubscriptions=SMSDeliverySubscriptions; }
	public void setCancelSMSDeliverySubscription(String CancelSMSDeliverySubscription) { endpoints.CancelSMSDeliverySubscription=CancelSMSDeliverySubscription; }
	public void setRetrieveSMS(String RetrieveSMS) { endpoints.RetrieveSMS=RetrieveSMS; }
	public void setSMSReceiptSubscriptions(String SMSReceiptSubscriptions) { endpoints.SMSReceiptSubscriptions=SMSReceiptSubscriptions; }
	public void setCancelSMSReceiptSubscription(String CancelSMSReceiptSubscription) { endpoints.CancelSMSReceiptSubscription=CancelSMSReceiptSubscription; }

	public void setSendMMS(String SendMMS) { endpoints.SendMMS=SendMMS; }
	public void setQueryMMSDelivery(String QueryMMSDelivery) { endpoints.QueryMMSDelivery=QueryMMSDelivery; }
	public void setMMSDeliverySubscriptions(String MMSDeliverySubscriptions) { endpoints.MMSDeliverySubscriptions=MMSDeliverySubscriptions; }
	public void setCancelMMSDeliverySubscription(String CancelMMSDeliverySubscription) { endpoints.CancelMMSDeliverySubscription=CancelMMSDeliverySubscription; }
	public void setRetrieveMMS(String RetrieveMMS) { endpoints.RetrieveMMS=RetrieveMMS; }
	public void setRetrieveMMSMessage(String RetrieveMMSMessage) { endpoints.RetrieveMMSMessage=RetrieveMMSMessage; }
	public void setMMSReceiptSubscriptions(String MMSReceiptSubscriptions) { endpoints.MMSReceiptSubscriptions=MMSReceiptSubscriptions; }
	public void setCancelMMSReceiptSubscription(String CancelMMSReceiptSubscription) { endpoints.CancelMMSReceiptSubscription=CancelMMSReceiptSubscription; }

//
//	public static ServiceEndpoints getEndpoints() {
//		logger.debug("Loading Local Sandbox endpoint details");
//		return INSTANCE.endpoints;
//	}

}
