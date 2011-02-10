<?php

require_once($ONEAPICOMMON.'foundation/FormParameter.php');
require_once($ONEAPICOMMON.'foundation/FormParameters.php');
require_once($ONEAPICOMMON.'foundation/JSONRequest.php');
require_once($ONEAPICOMMON.'response/RequestError.php');
require_once($ONEAPICOMMON.'response/ServiceException.php');
require_once($ONEAPICOMMON.'response/PolicyException.php');
require_once($ONEAPICOMMON.'response/ResourceReference.php');
require_once($ONEAPICOMMON.'response/sms/DeliveryInfo.php');
require_once($ONEAPICOMMON.'response/sms/DeliveryInfoList.php');
require_once($ONEAPICOMMON.'response/sms/DeliveryInfoNotification.php');
require_once($ONEAPICOMMON.'response/sms/CallbackReference.php');
require_once($ONEAPICOMMON.'response/sms/DeliveryReceiptSubscription.php');
require_once($ONEAPICOMMON.'response/sms/SMSSendResponse.php');
require_once($ONEAPICOMMON.'response/sms/SMSSendDeliveryStatusResponse.php');
require_once($ONEAPICOMMON.'response/sms/SMSDeliveryReceiptSubscriptionResponse.php');
	
class SMSSend {
	protected $endpoint;
	protected $username;
	protected $password;
	
	public function __construct ($endpoint = null, $username = null, $password = null) {
		$this->endpoint = $endpoint;
		$this->username = $username; 
		$this->password = $password;		
	}
	
	public function sendSMS($senderAddress, $addressList, $message, $clientCorrelator, $notifyURL, $senderName, $callbackData) {
		$sendSMSResponse=null;
		if ($senderAddress && $addressList && $message) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'SendSMS')) {
				$url=$this->endpoint->getSendSMSEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/SendSMSService/1/smsmessaging/outbound/{senderAddress}/requests';
			}
			
			$url=str_replace('{senderAddress}', urlencode($senderAddress), $url);
			
			$formParameters=new FormParameters();
			$formParameters->put("senderAddress", $senderAddress);
			for ($i=0; $i<count($addressList); $i++) $formParameters->put("address", $addressList[$i]); 			
			$formParameters->put("message", $message);
			$formParameters->put("clientCorrelator", $clientCorrelator);
			$formParameters->put("notifyURL", $notifyURL);
			$formParameters->put("senderName", $senderName);			
			$formParameters->put("callbackData", $callbackData);			
			
			$request=new JSONRequest($url, $this->username, $this->password, 'POST', $formParameters->getParameterSet());
			$response=$request->execute();
		
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			error_log("SMSSend::sendSMS response=".print_r($response,true));
			error_log("SMSSend::sendSMS jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$sendSMSResponse=new SMSSendResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("SMSSend::sendSMS sendSMSResponse=".print_r($sendSMSResponse,true));
		}
		
		return $sendSMSResponse;
	}
	
	public function queryDeliveryStatus($senderAddress, $requestId) {
		$smsSendDeliveryStatusResponse=null;
		if ($senderAddress && $requestId) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'QuerySMSDelivery')) {
				$url=$this->endpoint->getQuerySMSDeliveryEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/QuerySMSService/1/smsmessaging/outbound/{senderAddress}/requests/{requestId}/deliveryInfos';
			}
			
			$url=str_replace('{senderAddress}', urlencode($senderAddress), $url);
			$url=str_replace('{requestId}', urlencode($requestId), $url);
			
			$request=new JSONRequest($url, $this->username, $this->password, 'GET', null);
			$response=$request->execute();
		
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			error_log("SMSSend::queryDeliveryStatus response=".print_r($response,true));
			error_log("SMSSend::queryDeliveryStatus jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$smsSendDeliveryStatusResponse=new SMSSendDeliveryStatusResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("SMSSend::queryDeliveryStatus smsSendDeliveryStatusResponse=".print_r($smsSendDeliveryStatusResponse,true));
		}
				
		return $smsSendDeliveryStatusResponse;
	}
	
	public function subscribeToDeliveryNotifications($senderAddress, $clientCorrelator, $notifyURL, $callbackData) {
		$smsDeliveryReceiptSubscriptionResponse=null;
		if ($senderAddress && $notifyURL) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'SMSDeliverySubscriptions')) {
				$url=$this->endpoint->getSMSDeliverySubscriptionsEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/SMSDeliveryService/1/smsmessaging/outbound/{senderAddress}/subscriptions';
			}
			
			$url=str_replace('{senderAddress}', urlencode($senderAddress), $url);
			
			$formParameters=new FormParameters();
			$formParameters->put("clientCorrelator", $clientCorrelator);
			$formParameters->put("notifyURL", $notifyURL);
			$formParameters->put("callbackData", $callbackData);			
			
			$request=new JSONRequest($url, $this->username, $this->password, 'POST', $formParameters->getParameterSet());
			$response=$request->execute();
		
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			error_log("SMSSend::subscribeToDeliveryNotifications response=".print_r($response,true));
			error_log("SMSSend::subscribeToDeliveryNotifications jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$smsDeliveryReceiptSubscriptionResponse=new SMSDeliveryReceiptSubscriptionResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("SMSSend::subscribeToDeliveryNotifications smsDeliveryReceiptSubscriptionResponse=".print_r($smsDeliveryReceiptSubscriptionResponse,true));
		}
		
		return $smsDeliveryReceiptSubscriptionResponse;
	}
	
	public function cancelDeliveryNotifications($subscriptionId) {
		$responseCode=null;
		if ($subscriptionId) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'CancelSMSDeliverySubscription')) {
				$url=$this->endpoint->getCancelSMSDeliverySubscriptionEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/CancelSMSDeliveryService/1/smsmessaging/outbound/subscriptions/{subscriptionId}';
			}
			
			$url=str_replace('{subscriptionId}', urlencode($subscriptionId), $url);
			
			$request=new JSONRequest($url, $this->username, $this->password, 'DELETE', null);
			$response=$request->execute();
			$responseInfo=$response->getResponseInfo();
		
			error_log("SMSSend::cancelDeliveryNotifications httpResponseCode=".$responseInfo["http_code"]);
			$responseCode=$responseInfo["http_code"];
		}
		
		return $responseCode;
	}

	public static function convertDeliveryInfoNotification($contentType, $requestBody) {
		$deliveryInfoNotification=null;
		if ($contentType && $contentType=="application/json") {
			$jsondata=json_decode($requestBody);
			if (property_exists($jsondata, 'deliveryInfoNotification')) {				
				$deliveryInfoNotification=new DeliveryInfoNotification($jsondata->deliveryInfoNotification);
			}			
		}
		return $deliveryInfoNotification;
	}

}

?>
