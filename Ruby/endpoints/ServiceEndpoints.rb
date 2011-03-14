
class ServiceEndpoints

  def initialize
    @AmountCharge="http://localhost:8080/oneapiserver/AmountChargeService/1/payment/{endUserId}/transactions/amount"
		@AmountRefund="http://localhost:8080/oneapiserver/AmountRefundService/1/payment/{endUserId}/transactions/amount"
		@AmountReserve="http://localhost:8080/oneapiserver/AmountReserveService/1/payment/{endUserId}/transactions/amountReservation"
		@AmountReserveAdditional="http://localhost:8080/oneapiserver/AmountReserveAdditionalService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}"
		@AmountReservationCharge="http://localhost:8080/oneapiserver/AmountReserveChargeService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}"
		@AmountReservationRelease="http://localhost:8080/oneapiserver/AmountReserveReleaseService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}"		
		@Location="http://localhost:8080/oneapiserver/LocationService/1/location/queries/location"
		@SendSMS="http://localhost:8080/oneapiserver/SendSMSService/1/smsmessaging/outbound/{senderAddress}/requests"
		@QuerySMSDelivery="http://localhost:8080/oneapiserver/QuerySMSService/1/smsmessaging/outbound/{senderAddress}/requests/{requestId}/deliveryInfos"
		@SMSDeliverySubscriptions="http://localhost:8080/oneapiserver/SMSDeliveryService/1/smsmessaging/outbound/{senderAddress}/subscriptions"
		@CancelSMSDeliverySubscription="http://localhost:8080/oneapiserver/CancelSMSDeliveryService/1/smsmessaging/outbound/subscriptions/{subscriptionId}"
		@RetrieveSMS="http://localhost:8080/oneapiserver/RetrieveSMSService/1/smsmessaging/inbound/registrations/{registrationId}/messages/?maxBatchSize={maxBatchSize}"
		@SMSReceiptSubscriptions="http://localhost:8080/oneapiserver/SMSReceiptService/1/smsmessaging/inbound/subscriptions"
		@CancelSMSReceiptSubscription="http://localhost:8080/oneapiserver/CancelSMSReceiptService/1/smsmessaging/inbound/subscriptions/{subscriptionId}"	
		@SendMMS="http://localhost:8080/oneapiserver/SendMMSService/1/messaging/outbound/{senderAddress}/requests"
    @QueryMMSDelivery="http://localhost:8080/oneapiserver/QueryMMSService/1/messaging/outbound/{senderAddress}/requests/{requestId}/deliveryInfos"
		@MMSDeliverySubscriptions="http://localhost:8080/oneapiserver/MMSDeliveryService/1/messaging/outbound/{senderAddress}/subscriptions"
		@CancelMMSDeliverySubscription="http://localhost:8080/oneapiserver/CancelMMSDeliveryService/1/messaging/outbound/subscriptions/{subscriptionId}"
		@RetrieveMMS="http://localhost:8080/oneapiserver/RetrieveMMSService/1/messaging/inbound/registrations/{registrationId}/messages/?maxBatchSize={maxBatchSize}"
		@RetrieveMMSMessage="http://localhost:8080/oneapiserver/RetrieveMMSMessageService/1/messaging/inbound/registrations/{registrationId}/messages/{messageId}?resFormat={resFormat}"
		@MMSReceiptSubscriptions="http://localhost:8080/oneapiserver/MMSReceiptService/1/messaging/inbound/subscriptions"
		@CancelMMSReceiptSubscription="http://localhost:8080/oneapiserver/CancelMMSReceiptService/1/messaging/inbound/subscriptions/{subscriptionId}"
  end
	
	def getAmountChargeEndpoint
		@AmountCharge
	end
		
	def getAmountRefundEndpoint
		@AmountRefund
	end
		
	def getAmountReserveEndpoint
		@AmountReserve
	end
		
	def getAmountReserveAdditionalEndpoint
		@AmountReserveAdditional
  end
		
	def getAmountReservationChargeEndpoint
		@AmountReservationCharge
  end
		
	def getAmountReservationReleaseEndpoint
		@AmountReservationRelease
  end

	def getLocationEndpoint
		@Location;
  end
	
	def getSendSMSEndpoint
		@SendSMS
  end
		
	def getQuerySMSDeliveryEndpoint
		@QuerySMSDelivery
  end
		
	def getSMSDeliverySubscriptionsEndpoint
		@SMSDeliverySubscriptions
  end
		
	def getCancelSMSDeliverySubscriptionEndpoint
		@CancelSMSDeliverySubscription
  end
		
	def getRetrieveSMSEndpoint
		@RetrieveSMS
  end
		
	def getSMSReceiptSubscriptionsEndpoint 
		@SMSReceiptSubscriptions
  end
		
	def getCancelSMSReceiptSubscriptionEndpoint 
		@CancelSMSReceiptSubscription
  end

	def getSendMMSEndpoint
		@SendMMS
  end
		
	def getQueryMMSDeliveryEndpoint 
		@QueryMMSDelivery
  end
		
	def getMMSDeliverySubscriptionsEndpoint
		@MMSDeliverySubscriptions
  end
		
	def getCancelMMSDeliverySubscriptionEndpoint
		@CancelMMSDeliverySubscription
  end
		
	def getRetrieveMMSEndpoint
		@RetrieveMMS
  end
		
	def getRetrieveMMSMessageEndpoint
		@RetrieveMMSMessage
  end
		
	def getMMSReceiptSubscriptionsEndpoint
		@MMSReceiptSubscriptions
  end
		
	def getCancelMMSReceiptSubscriptionEndpoint
		@CancelMMSReceiptSubscription
  end
end

