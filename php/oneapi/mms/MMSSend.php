<?php

require_once('Mail.php');
require_once('Mail/Mime.php');

require_once($ONEAPICOMMON.'foundation/FormParameter.php');
require_once($ONEAPICOMMON.'foundation/FormParameters.php');
require_once($ONEAPICOMMON.'foundation/JSONRequest.php');
require_once($ONEAPICOMMON.'response/RequestError.php');
require_once($ONEAPICOMMON.'response/ServiceException.php');
require_once($ONEAPICOMMON.'response/PolicyException.php');
require_once($ONEAPICOMMON.'response/ResourceReference.php');
require_once($ONEAPICOMMON.'response/mms/DeliveryInfo.php');
require_once($ONEAPICOMMON.'response/mms/DeliveryInfoList.php');
require_once($ONEAPICOMMON.'response/mms/CallbackReference.php');
require_once($ONEAPICOMMON.'response/mms/DeliveryReceiptSubscription.php');
require_once($ONEAPICOMMON.'response/mms/MMSSendResponse.php');
require_once($ONEAPICOMMON.'response/mms/MMSSendDeliveryStatusResponse.php');
require_once($ONEAPICOMMON.'response/mms/MMSDeliveryReceiptSubscriptionResponse.php');
	
class MMSSend {
	protected $endpoint;
	protected $username;
	protected $password;
	
	public function __construct ($endpoint = null, $username = null, $password = null) {
		$this->endpoint = $endpoint;
		$this->username = $username; 
		$this->password = $password;		
	}
	
	public function sendMMS($senderAddress, $addressList, $message, $attachments, $senderName, $clientCorrelator, $notifyURL, $callbackData) {
		$sendMMSResponse=null;
		if ($senderAddress && $addressList && ($message || $attachments)) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'SendSMS')) {
				$url=$this->endpoint->getSendMMSEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/SendMMSService/1/messaging/outbound/{senderAddress}/requests';
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
			
			$params=array();		
			$params['content_type'] = 'multipart/mixed';
			$requestBody = new Mail_mimePart('', $params);

			$params['content_type'] = 'application/x-www-form-urlencoded';
			//$params['encoding']     = '8bit';
			$params['disposition']    = 'form-data; name="root-fields"';
			$text = $requestBody->addSubPart($formParameters->encodeParameters(), $params);
			
			error_log('Processing attachments');
			if ($attachments && is_array($attachments) && count($attachments)>0) {
				for ($i=0; $i<count($attachments); $i++) {
					error_log('Processing file '.$i);
					$file=$attachments[$i];
					if ($file && is_array($file) && isset($file['error']) && $file['error']==UPLOAD_ERR_OK) {
						error_log('Have attachment name='.$file['name'].' type='.$file['type'].' size='.$file['size']);
						$params['content_type'] = $file['type'];
						$params['encoding']     = 'base64';
						$params['disposition']  = 'form-data; name="'.$file['name'].'"';
						$contents=file_get_contents($file['tmp_name']);
						$attach =& $requestBody->addSubPart($contents, $params);
					} 
				}
			}
			
			$content=$requestBody->encode();	
			$contentType=$content['headers']['Content-Type'];
			$data=$content['body'];
			
			error_log('Generated...\n'.print_r($content,true));																																																
			
			$request=new JSONRequest($url, $this->username, $this->password, 'POST', null, $contentType, $data);
			$response=$request->execute();
		
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			error_log("MMSSend::sendMMS response=".print_r($response,true));
			error_log("MMSSend::sendMMS jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$sendMMSResponse=new MMSSendResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("MMSSend::sendMMS sendMMSResponse=".print_r($sendMMSResponse,true));
		}
		
		return $sendMMSResponse;
	}

	public function queryDeliveryStatus($senderAddress, $requestId) {
		$mmsSendDeliveryStatusResponse=null;
		if ($senderAddress && $requestId) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'QuerySMSDelivery')) {
				$url=$this->endpoint->getQueryMMSDeliveryEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/QueryMMSService/1/messaging/outbound/{senderAddress}/requests/{requestId}/deliveryInfos';
			}
			
			$url=str_replace('{senderAddress}', urlencode($senderAddress), $url);
			$url=str_replace('{requestId}', urlencode($requestId), $url);
			
			$request=new JSONRequest($url, $this->username, $this->password, 'GET', null);
			$response=$request->execute();
		
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			error_log("MMSSend::queryDeliveryStatus response=".print_r($response,true));
			error_log("MMSSend::queryDeliveryStatus jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$mmsSendDeliveryStatusResponse=new MMSSendDeliveryStatusResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("MMSSend::queryDeliveryStatus mmsSendDeliveryStatusResponse=".print_r($mmsSendDeliveryStatusResponse,true));
		}
		
		return $mmsSendDeliveryStatusResponse;
	}
	
	public function subscribeToDeliveryNotifications($senderAddress, $clientCorrelator, $notifyURL, $callbackData) {
		$mmsDeliveryReceiptSubscriptionResponse=null;
		if ($senderAddress && $notifyURL) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'MMSDeliverySubscriptions')) {
				$url=$this->endpoint->getMMSDeliverySubscriptionsEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/MMSDeliveryService/1/messaging/outbound/{senderAddress}/subscriptions';
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
			
			error_log("MMSSend::subscribeToDeliveryNotifications response=".print_r($response,true));
			error_log("MMSSend::subscribeToDeliveryNotifications jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$mmsDeliveryReceiptSubscriptionResponse=new MMSDeliveryReceiptSubscriptionResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("MMSSend::subscribeToDeliveryNotifications mmsDeliveryReceiptSubscriptionResponse=".print_r($mmsDeliveryReceiptSubscriptionResponse,true));
		}
		
		return $mmsDeliveryReceiptSubscriptionResponse;
	}
	
	public function cancelDeliveryNotifications($subscriptionId) {
		$responseCode=null;
		if ($subscriptionId) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'CancelMMSDeliverySubscription')) {
				$url=$this->endpoint->getCancelMMSDeliverySubscriptionEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/CancelMMSDeliveryService/1/messaging/outbound/subscriptions/{subscriptionId}';
			}
			
			$url=str_replace('{subscriptionId}', urlencode($subscriptionId), $url);
			
			$request=new JSONRequest($url, $this->username, $this->password, 'DELETE', null);
			$response=$request->execute();
			$responseInfo=$response->getResponseInfo();

			error_log("MMSSend::cancelDeliveryNotifications httpResponseCode=".$responseInfo["http_code"]);
			$responseCode=$responseInfo["http_code"];

		}		
		
		return $responseCode;
	}
	
}

?>
