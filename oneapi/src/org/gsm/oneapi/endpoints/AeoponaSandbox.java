package org.gsm.oneapi.endpoints;

import org.apache.log4j.Logger;

public class AeoponaSandbox extends ServiceEndpoints {
	static Logger logger=Logger.getLogger(AeoponaSandbox.class);
	
	public static final AeoponaSandbox INSTANCE=new AeoponaSandbox();
	
	ServiceEndpoints endpoints=null;
	
	public AeoponaSandbox () {
		endpoints=new ServiceEndpoints();
		
		endpoints.AmountCharge="https://oneapi.aepona.com/AmountChargingService/OneAPI_REST_v1_0/sandbox/1/payment/{endUserId}/transactions/amount";
		endpoints.AmountRefund="https://oneapi.aepona.com/AmountChargingService/OneAPI_REST_v1_0/sandbox/1/payment/{endUserId}/transactions/amount";
		endpoints.AmountReserve="https://oneapi.aepona.com/ReserveAmountService/OneAPI_REST_v1_0/sandbox/1/payment/{endUserId}/transactions/amountReservation";
		endpoints.AmountReserveAdditional="https://oneapi.aepona.com/ReserveAmountService/OneAPI_REST_v1_0/sandbox/1/payment/{endUserId}/transactions/amountReservation/${transactionId}";
		endpoints.AmountReservationCharge="https://oneapi.aepona.com/ReserveAmountService/OneAPI_REST_v1_0/sandbox/1/payment/{endUserId}/transactions/amountReservation/${transactionId}";
		endpoints.AmountReservationRelease="https://oneapi.aepona.com/ReserveAmountService/OneAPI_REST_v1_0/sandbox/1/payment/{endUserId}/transactions/amountReservation/${transactionId}";
		
		endpoints.Location="https://oneapi.aepona.com/TerminalLocationService/OneAPI_REST_v1_0/sandbox/1/location/queries/location";
		
		endpoints.SendSMS="https://oneapi.aepona.com/SendSmsService/OneAPI_REST_v1_0/sandbox/1/smsmessaging/outbound/{senderAddress}/requests";
		endpoints.QuerySMSDelivery="https://oneapi.aepona.com/SendSmsService/OneAPI_REST_v1_0/sandbox/1/smsmessaging/outbound/{senderAddress}/requests/{requestId}/deliveryInfos";
		endpoints.SMSDeliverySubscriptions="https://oneapi.aepona.com/SmsNotificationManagerService/OneAPI_REST_v1_0/sandbox/1/smsmessaging/outbound/{senderAddress}/subscriptions";
		endpoints.CancelSMSDeliverySubscription="https://oneapi.aepona.com/SmsNotificationManagerService/OneAPI_REST_v1_0/sandbox/1/smsmessaging/outbound/subscriptions/{subscriptionId}";
		endpoints.RetrieveSMS="https://oneapi.aepona.com/ReceiveSmsService/OneAPI_REST_v1_0/sandbox/1/smsmessaging/inbound/registrations/{registrationId}/messages/?maxBatchSize={maxBatchSize}";
		endpoints.SMSReceiptSubscriptions="https://oneapi.aepona.com/SmsNotificationManagerService/OneAPI_REST_v1_0/sandbox/1/smsmessaging/inbound/subscriptions";
		endpoints.CancelSMSReceiptSubscription="https://oneapi.aepona.com/SmsNotificationManagerService/OneAPI_REST_v1_0/sandbox/1/smsmessaging/inbound/subscriptions/{subscriptionId}";

	}
	
	public ServiceEndpoints getEndpoints() {
		return INSTANCE.endpoints;
	}

}
