<?php

class ServiceEndpoints {
	protected $AmountCharge="http://localhost:8080/oneapiserver/AmountChargeService/1/payment/{endUserId}/transactions/amount";
	protected $AmountRefund="http://localhost:8080/oneapiserver/AmountRefundService/1/payment/{endUserId}/transactions/amount";
	protected $AmountReserve="http://localhost:8080/oneapiserver/AmountReserveService/1/payment/{endUserId}/transactions/amountReservation";
	protected $AmountReserveAdditional="http://localhost:8080/oneapiserver/AmountReserveAdditionalService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}";
	protected $AmountReservationCharge="http://localhost:8080/oneapiserver/AmountReserveChargeService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}";
	protected $AmountReservationRelease="http://localhost:8080/oneapiserver/AmountReserveReleaseService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}";
	protected $Location="http://localhost:8080/oneapiserver/LocationService/1/location/queries/location";
	
	protected $SendSMS="http://localhost:8080/oneapiserver/SendSMSService/1/smsmessaging/outbound/{senderAddress}/requests";
	protected $QuerySMSDelivery="http://localhost:8080/oneapiserver/QuerySMSService/1/smsmessaging/outbound/{senderAddress}/requests/{requestId}/deliveryInfos";
	protected $SMSDeliverySubscriptions="http://localhost:8080/oneapiserver/SMSDeliveryService/1/smsmessaging/outbound/{senderAddress}/subscriptions";
	protected $CancelSMSDeliverySubscription="http://localhost:8080/oneapiserver/CancelSMSDeliveryService/1/smsmessaging/outbound/subscriptions/{subscriptionId}";
	protected $RetrieveSMS="http://localhost:8080/oneapiserver/RetrieveSMSService/1/smsmessaging/inbound/registrations/{registrationId}/messages/?maxBatchSize={maxBatchSize}";
	protected $SMSReceiptSubscriptions="http://localhost:8080/oneapiserver/SMSReceiptService/1/smsmessaging/inbound/subscriptions";
	protected $CancelSMSReceiptSubscription="http://localhost:8080/oneapiserver/CancelSMSReceiptService/1/smsmessaging/inbound/subscriptions/{subscriptionId}";
	
	protected $SendMMS="http://localhost:8080/oneapiserver/SendMMSService/1/messaging/outbound/{senderAddress}/requests";
	protected $QueryMMSDelivery="http://localhost:8080/oneapiserver/QueryMMSService/1/messaging/outbound/{senderAddress}/requests/{requestId}/deliveryInfos";
	protected $MMSDeliverySubscriptions="http://localhost:8080/oneapiserver/MMSDeliveryService/1/messaging/outbound/{senderAddress}/subscriptions";
	protected $CancelMMSDeliverySubscription="http://localhost:8080/oneapiserver/CancelMMSDeliveryService/1/messaging/outbound/subscriptions/{subscriptionId}";
	protected $RetrieveMMS="http://localhost:8080/oneapiserver/RetrieveMMSService/1/messaging/inbound/registrations/{registrationId}/messages/?maxBatchSize={maxBatchSize}";
	protected $RetrieveMMSMessage="http://localhost:8080/oneapiserver/RetrieveMMSMessageService/1/messaging/inbound/registrations/{registrationId}/messages/{messageId}?resFormat={resFormat}";
	protected $MMSReceiptSubscriptions="http://localhost:8080/oneapiserver/MMSReceiptService/1/messaging/inbound/subscriptions";
	protected $CancelMMSReceiptSubscription="http://localhost:8080/oneapiserver/CancelMMSReceiptService/1/messaging/inbound/subscriptions/{subscriptionId}";
	
	public function getAmountChargeEndpoint() { return $this->AmountCharge; }
	public function getAmountRefundEndpoint() { return $this->AmountRefund; }
	public function getAmountReserveEndpoint() { return $this->AmountReserve; }
	public function getAmountReserveAdditionalEndpoint() { return $this->AmountReserveAdditional; }
	public function getAmountReservationChargeEndpoint() { return $this->AmountReservationCharge; }
	public function getAmountReservationReleaseEndpoint() { return $this->AmountReservationRelease; }

	public function getLocationEndpoint() { return $this->Location; }
	
	public function getSendSMSEndpoint() { return $this->SendSMS; }
	public function getQuerySMSDeliveryEndpoint() { return $this->QuerySMSDelivery; }
	public function getSMSDeliverySubscriptionsEndpoint() { return $this->SMSDeliverySubscriptions; }
	public function getCancelSMSDeliverySubscriptionEndpoint() { return $this->CancelSMSDeliverySubscription; }
	public function getRetrieveSMSEndpoint() { return $this->RetrieveSMS; }
	public function getSMSReceiptSubscriptionsEndpoint() { return $this->SMSReceiptSubscriptions; }
	public function getCancelSMSReceiptSubscriptionEndpoint() { return $this->CancelSMSReceiptSubscription; }

	public function getSendMMSEndpoint() { return $this->SendMMS; }
	public function getQueryMMSDeliveryEndpoint() { return $this->QueryMMSDelivery; }
	public function getMMSDeliverySubscriptionsEndpoint() { return $this->MMSDeliverySubscriptions; }
	public function getCancelMMSDeliverySubscriptionEndpoint() { return $this->CancelMMSDeliverySubscription; }
	public function getRetrieveMMSEndpoint() { return $this->RetrieveMMS; }
	public function getRetrieveMMSMessageEndpoint() { return $this->RetrieveMMSMessage; }
	public function getMMSReceiptSubscriptionsEndpoint() { return $this->MMSReceiptSubscriptions; }
	public function getCancelMMSReceiptSubscriptionEndpoint() { return $this->CancelMMSReceiptSubscription; }

	public function setAmountChargeEndpoint($revised) { $this->AmountCharge=$revised; }
	public function setAmountRefundEndpoint($revised) { $this->AmountRefund=$revised; }
	public function setAmountReserveEndpoint($revised) { $this->AmountReserve=$revised; }
	public function setAmountReserveAdditionalEndpoint($revised) { $this->AmountReserveAdditional=$revised; }
	public function setAmountReservationChargeEndpoint($revised) { $this->AmountReservationCharge=$revised; }
	public function setAmountReservationReleaseEndpoint($revised) { $this->AmountReservationRelease=$revised; }

	public function setLocationEndpoint($revised) { $this->Location=$revised; }
	
	public function setSendSMSEndpoint($revised) { $this->SendSMS=$revised; }
	public function setQuerySMSDeliveryEndpoint($revised) { $this->QuerySMSDelivery=$revised; }
	public function setSMSDeliverySubscriptionsEndpoint($revised) { $this->SMSDeliverySubscriptions=$revised; }
	public function setCancelSMSDeliverySubscriptionEndpoint($revised) { $this->CancelSMSDeliverySubscription=$revised; }
	public function setRetrieveSMSEndpoint($revised) { $this->RetrieveSMS=$revised; }
	public function setSMSReceiptSubscriptionsEndpoint($revised) { $this->SMSReceiptSubscriptions=$revised; }
	public function setCancelSMSReceiptSubscriptionEndpoint($revised) { $this->CancelSMSReceiptSubscription=$revised; }

	public function setSendMMSEndpoint($revised) { $this->SendMMS=$revised; }
	public function setQueryMMSDeliveryEndpoint($revised) { $this->QueryMMSDelivery=$revised; }
	public function setMMSDeliverySubscriptionsEndpoint($revised) { $this->MMSDeliverySubscriptions=$revised; }
	public function setCancelMMSDeliverySubscriptionEndpoint($revised) { $this->CancelMMSDeliverySubscription=$revised; }
	public function setRetrieveMMSEndpoint($revised) { $this->RetrieveMMS=$revised; }
	public function setRetrieveMMSMessageEndpoint($revised) { $this->RetrieveMMSMessage=$revised; }
	public function setMMSReceiptSubscriptionsEndpoint($revised) { $this->MMSReceiptSubscriptions=$revised; }
	public function setCancelMMSReceiptSubscriptionEndpoint($revised) { $this->CancelMMSReceiptSubscription=$revised; }

	
	public function __construct() {}

}

?>