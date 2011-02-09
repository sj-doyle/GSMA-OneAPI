<?php

require_once($ONEAPICOMMON.'foundation/FormParameter.php');
require_once($ONEAPICOMMON.'foundation/FormParameters.php');
require_once($ONEAPICOMMON.'foundation/JSONRequest.php');
require_once($ONEAPICOMMON.'response/RequestError.php');
require_once($ONEAPICOMMON.'response/ServiceException.php');
require_once($ONEAPICOMMON.'response/PolicyException.php');
require_once($ONEAPICOMMON.'response/sms/InboundSMSMessage.php');
require_once($ONEAPICOMMON.'response/sms/InboundSMSMessageList.php');
require_once($ONEAPICOMMON.'response/sms/RetrieveSMSResponse.php');
require_once($ONEAPICOMMON.'response/ResourceReference.php');
require_once($ONEAPICOMMON.'response/sms/SMSMessageReceiptSubscriptionResponse.php');
	
class SMSRetrieve {
	protected $endpoint;
	protected $username;
	protected $password;
	
	public function __construct ($endpoint = null, $username = null, $password = null) {
		$this->endpoint = $endpoint;
		$this->username = $username; 
		$this->password = $password;		
	}
	
	public function retrieveMessages($registrationId, $maxBatchSize) {
		$smsRetrieveMessagesResponse=null;
		if ($registrationId && $maxBatchSize>0) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'RetrieveSMS')) {
				$url=$this->endpoint->getRetrieveSMSEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/RetrieveSMSService/1/smsmessaging/inbound/registrations/{registrationId}/messages/?maxBatchSize={maxBatchSize}';
			}
			
			$url=str_replace('{registrationId}', urlencode($registrationId), $url);
			$url=str_replace('{maxBatchSize}', urlencode($maxBatchSize), $url);
			
			$request=new JSONRequest($url, $this->username, $this->password, 'GET', null);
			$response=$request->execute();
		
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			error_log("SMSRetrieve::retrieveMessages response=".print_r($response,true));
			error_log("SMSRetrieve::retrieveMessages jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$smsRetrieveMessagesResponse=new RetrieveSMSResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("SMSRetrieve::retrieveMessages smsRetrieveMessagesResponse=".print_r($smsRetrieveMessagesResponse,true));
		}
		
		return $smsRetrieveMessagesResponse;
	}
	
	
	public function subscribeToReceiptNotifications($destinationAddress, $notifyURL, $criteria, $notificationFormat, $clientCorrelator, $callbackData) {
		$smsMessageReceiptSubscriptionResponse=null;
		if ($destinationAddress && $notifyURL) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'SMSReceiptSubscriptions')) {
				$url=$this->endpoint->getSMSReceiptSubscriptionsEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/SMSReceiptService/1/smsmessaging/inbound/subscriptions';
			}
			
			$formParameters=new FormParameters();
			$formParameters->put("destinationAddress", $destinationAddress);
			$formParameters->put("notifyURL", $notifyURL);
			$formParameters->put("criteria", $criteria);
			$formParameters->put("notificationFormat", $notificationFormat);
			$formParameters->put("clientCorrelator", $clientCorrelator);
			$formParameters->put("callbackData", $callbackData);			
			
			$request=new JSONRequest($url, $this->username, $this->password, 'POST', $formParameters->getParameterSet());
			$response=$request->execute();
		
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			error_log("SMSRetrieve::subscribeToReceiptNotifications response=".print_r($response,true));
			error_log("SMSRetrieve::subscribeToReceiptNotifications jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$smsMessageReceiptSubscriptionResponse=new SMSMessageReceiptSubscriptionResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("SMSRetrieve::subscribeToReceiptNotifications smsMessageReceiptSubscriptionResponse=".print_r($smsMessageReceiptSubscriptionResponse,true));
		}
		
		return $smsMessageReceiptSubscriptionResponse;
	}
	
	public function cancelReceiptNotifications($subscriptionId) {
		$responseCode=null;
		if ($subscriptionId) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'CancelSMSReceiptSubscription')) {
				$url=$this->endpoint->getCancelSMSReceiptSubscriptionEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/CancelSMSReceiptService/1/smsmessaging/inbound/subscriptions/{subscriptionId}';
			}
			
			$url=str_replace('{subscriptionId}', urlencode($subscriptionId), $url);
			
			$request=new JSONRequest($url, $this->username, $this->password, 'DELETE', null);
			$response=$request->execute();
			$responseInfo=$response->getResponseInfo();
		
			error_log("SMSRetrieve::cancelReceiptNotifications httpResponseCode=".$responseInfo["http_code"]);
			$responseCode=$responseInfo["http_code"];
		}
		
		return $responseCode;
	}

}
