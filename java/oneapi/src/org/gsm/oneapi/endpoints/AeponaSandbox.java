package org.gsm.oneapi.endpoints;

import org.apache.log4j.Logger;

public class AeponaSandbox extends ServiceEndpoints {
	static Logger logger=Logger.getLogger(AeponaSandbox.class);
	
	public static final AeponaSandbox INSTANCE=new AeponaSandbox();
	
	ServiceEndpoints endpoints=null;
	
	public AeponaSandbox () {
		endpoints=new ServiceEndpoints();
		
		endpoints.AmountCharge="https://oneapi.aepona.com/PaymentService/OneAPI_REST_v2_0/sandbox/2_1/payment/{endUserId}/transactions/amount";
		endpoints.AmountRefund="https://oneapi.aepona.com/PaymentService/OneAPI_REST_v2_0/sandbox/2_1/payment/{endUserId}/transactions/amount";
		endpoints.AmountReserve="https://oneapi.aepona.com/PaymentService/OneAPI_REST_v2_0/sandbox/2_1/payment/{endUserId}/transactions/amountReservation";
		endpoints.AmountReserveAdditional="https://oneapi.aepona.com/PaymentService/OneAPI_REST_v2_0/sandbox/2_1/payment/{endUserId}/transactions/amountReservation/${transactionId}";
		endpoints.AmountReservationCharge="https://oneapi.aepona.com/PaymentService/OneAPI_REST_v2_0/sandbox/2_1/payment/{endUserId}/transactions/amountReservation/${transactionId}";
		endpoints.AmountReservationRelease="https://oneapi.aepona.com/PaymentService/OneAPI_REST_v2_0/sandbox/2_1/payment/{endUserId}/transactions/amountReservation/${transactionId}";
		
		endpoints.Location="https://oneapi.aepona.com/TerminalLocationService/OneAPI_REST_v2_0/sandbox/2_0/location/queries/location";
		
		endpoints.SendSMS="https://oneapi.aepona.com/SendSmsService/OneAPI_REST_v2_0/sandbox/2_0/smsmessaging/outbound/{senderAddress}/requests";
		endpoints.QuerySMSDelivery="https://oneapi.aepona.com/SendSmsService/OneAPI_REST_v2_0/sandbox/2_0/smsmessaging/outbound/{senderAddress}/requests/{requestId}/deliveryInfos";
		endpoints.RetrieveSMS="https://oneapi.aepona.com/ReceiveSmsService/OneAPI_REST_v2_0/sandbox/2_0/smsmessaging/inbound/registrations/{registrationId}/messages/?maxBatchSize={maxBatchSize}";

	}
	
	public ServiceEndpoints getEndpoints() {
		return INSTANCE.endpoints;
	}

}
