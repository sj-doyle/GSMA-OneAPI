
class ServiceEndpoints:

	def __init__(self):
		self.AmountCharge="http://localhost:8080/oneapiserver/AmountChargeService/1/payment/{endUserId}/transactions/amount"
		self.AmountRefund="http://localhost:8080/oneapiserver/AmountRefundService/1/payment/{endUserId}/transactions/amount"
		self.AmountReserve="http://localhost:8080/oneapiserver/AmountReserveService/1/payment/{endUserId}/transactions/amountReservation"
		self.AmountReserveAdditional="http://localhost:8080/oneapiserver/AmountReserveAdditionalService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}"
		self.AmountReservationCharge="http://localhost:8080/oneapiserver/AmountReserveChargeService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}"
		self.AmountReservationRelease="http://localhost:8080/oneapiserver/AmountReserveReleaseService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}"		
		self.Location="http://localhost:8080/oneapiserver/LocationService/1/location/queries/location"
		self.SendSMS="http://localhost:8080/oneapiserver/SendSMSService/1/smsmessaging/outbound/{senderAddress}/requests"
		self.QuerySMSDelivery="http://localhost:8080/oneapiserver/QuerySMSService/1/smsmessaging/outbound/{senderAddress}/requests/{requestId}/deliveryInfos"
		self.SMSDeliverySubscriptions="http://localhost:8080/oneapiserver/SMSDeliveryService/1/smsmessaging/outbound/{senderAddress}/subscriptions"
		self.CancelSMSDeliverySubscription="http://localhost:8080/oneapiserver/CancelSMSDeliveryService/1/smsmessaging/outbound/subscriptions/{subscriptionId}"
		self.RetrieveSMS="http://localhost:8080/oneapiserver/RetrieveSMSService/1/smsmessaging/inbound/registrations/{registrationId}/messages/?maxBatchSize={maxBatchSize}"
		self.SMSReceiptSubscriptions="http://localhost:8080/oneapiserver/SMSReceiptService/1/smsmessaging/inbound/subscriptions"
		self.CancelSMSReceiptSubscription="http://localhost:8080/oneapiserver/CancelSMSReceiptService/1/smsmessaging/inbound/subscriptions/{subscriptionId}"	
		self.SendMMS="http://localhost:8080/oneapiserver/SendMMSService/1/messaging/outbound/{senderAddress}/requests"
		self.QueryMMSDelivery="http://localhost:8080/oneapiserver/QueryMMSService/1/messaging/outbound/{senderAddress}/requests/{requestId}/deliveryInfos"
		self.MMSDeliverySubscriptions="http://localhost:8080/oneapiserver/MMSDeliveryService/1/messaging/outbound/{senderAddress}/subscriptions"
		self.CancelMMSDeliverySubscription="http://localhost:8080/oneapiserver/CancelMMSDeliveryService/1/messaging/outbound/subscriptions/{subscriptionId}"
		self.RetrieveMMS="http://localhost:8080/oneapiserver/RetrieveMMSService/1/messaging/inbound/registrations/{registrationId}/messages/?maxBatchSize={maxBatchSize}"
		self.RetrieveMMSMessage="http://localhost:8080/oneapiserver/RetrieveMMSMessageService/1/messaging/inbound/registrations/{registrationId}/messages/{messageId}?resFormat={resFormat}"
		self.MMSReceiptSubscriptions="http://localhost:8080/oneapiserver/MMSReceiptService/1/messaging/inbound/subscriptions"
		self.CancelMMSReceiptSubscription="http://localhost:8080/oneapiserver/CancelMMSReceiptService/1/messaging/inbound/subscriptions/{subscriptionId}"
	
	def getAmountChargeEndpoint(self):
		return self.AmountCharge
		
	def getAmountRefundEndpoint(self):
		return self.AmountRefund
		
	def getAmountReserveEndpoint(self):
		return self.AmountReserve
		
	def getAmountReserveAdditionalEndpoint(self):
		return self.AmountReserveAdditional
		
	def getAmountReservationChargeEndpoint(self):
		return self.AmountReservationCharge
		
	def getAmountReservationReleaseEndpoint(self):
		return self.AmountReservationRelease

	def getLocationEndpoint(self):
		return self.Location;
	
	def getSendSMSEndpoint(self):
		return self.SendSMS
		
	def getQuerySMSDeliveryEndpoint(self):
		return self.QuerySMSDelivery
		
	def getSMSDeliverySubscriptionsEndpoint(self):
		return self.SMSDeliverySubscriptions
		
	def getCancelSMSDeliverySubscriptionEndpoint(self):
		return self.CancelSMSDeliverySubscription
		
	def getRetrieveSMSEndpoint(self):
		return self.RetrieveSMS
		
	def getSMSReceiptSubscriptionsEndpoint(self): 
		return self.SMSReceiptSubscriptions
		
	def getCancelSMSReceiptSubscriptionEndpoint(self): 
		return self.CancelSMSReceiptSubscription

	def getSendMMSEndpoint(self):
		return self.SendMMS
		
	def getQueryMMSDeliveryEndpoint(self): 
		return self.QueryMMSDelivery
		
	def getMMSDeliverySubscriptionsEndpoint(self):
		return self.MMSDeliverySubscriptions
		
	def getCancelMMSDeliverySubscriptionEndpoint(self):
		return self.CancelMMSDeliverySubscription
		
	def getRetrieveMMSEndpoint(self):
		return self.RetrieveMMS
		
	def getRetrieveMMSMessageEndpoint(self):
		return self.RetrieveMMSMessage
		
	def getMMSReceiptSubscriptionsEndpoint(self):
		return self.MMSReceiptSubscriptions
		
	def getCancelMMSReceiptSubscriptionEndpoint(self):
		return self.CancelMMSReceiptSubscription

