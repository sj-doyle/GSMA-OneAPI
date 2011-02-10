<?php

require_once('Mail.php');
require_once('Mail/mimeDecode.php');

require_once($ONEAPICOMMON.'foundation/FormParameter.php');
require_once($ONEAPICOMMON.'foundation/FormParameters.php');
require_once($ONEAPICOMMON.'foundation/JSONRequest.php');
require_once($ONEAPICOMMON.'response/RequestError.php');
require_once($ONEAPICOMMON.'response/ServiceException.php');
require_once($ONEAPICOMMON.'response/PolicyException.php');
require_once($ONEAPICOMMON.'response/ResourceReference.php');
require_once($ONEAPICOMMON.'response/mms/Attachment.php');
require_once($ONEAPICOMMON.'response/mms/InboundMMSMessage.php');
require_once($ONEAPICOMMON.'response/mms/InboundMessage.php');
require_once($ONEAPICOMMON.'response/mms/InboundMessageList.php');
require_once($ONEAPICOMMON.'response/mms/InboundMessageNotification.php');
require_once($ONEAPICOMMON.'response/mms/RetrieveMMSMessageResponse.php');
require_once($ONEAPICOMMON.'response/mms/RetrieveMMSResponse.php');
require_once($ONEAPICOMMON.'response/mms/MMSMessageReceiptSubscriptionResponse.php');


	
class MMSRetrieve {
	protected $endpoint;
	protected $username;
	protected $password;
	
	public function __construct ($endpoint = null, $username = null, $password = null) {
		$this->endpoint = $endpoint;
		$this->username = $username; 
		$this->password = $password;		
	}
	
	public function retrieveMessages($registrationId, $maxBatchSize) {
		$mmsRetrieveMessagesResponse=null;
		if ($registrationId && $maxBatchSize>0) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'RetrieveMMS')) {
				$url=$this->endpoint->getRetrieveMMSEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/RetrieveMMSService/1/messaging/inbound/registrations/{registrationId}/messages/?maxBatchSize={maxBatchSize}';
			}
			
			$url=str_replace('{registrationId}', urlencode($registrationId), $url);
			$url=str_replace('{maxBatchSize}', urlencode($maxBatchSize), $url);
			
			$request=new JSONRequest($url, $this->username, $this->password, 'GET', null);
			$response=$request->execute();
		
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			error_log("MMSRetrieve::retrieveMessages response=".print_r($response,true));
			error_log("MMSRetrieve::retrieveMessages jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$mmsRetrieveMessagesResponse=new RetrieveMMSResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("MMSRetrieve::retrieveMessages mmsRetrieveMessagesResponse=".print_r($mmsRetrieveMessagesResponse,true));
		}
		
		return $mmsRetrieveMessagesResponse;
	}
	
	
	public function retrieveMessageContent($registrationId, $messageId, $resFormat) {
		$retrieveMMSResponse=null;
		if ($registrationId && $messageId) {
			$jsondata=null;
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'RetrieveMMSMessage')) {
				$url=$this->endpoint->getRetrieveMMSMessageEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/RetrieveMMSMessageService/1/messaging/inbound/registrations/{registrationId}/messages/{messageId}';
			}
			
			$url=str_replace('{registrationId}', urlencode($registrationId), $url);
			$url=str_replace('{messageId}', urlencode($messageId), $url);
			$url=str_replace('{resFormat}', urlencode($resFormat), $url);

			$request=new JSONRequest($url, $this->username, $this->password, 'GET');
			$response=$request->execute();

			$ri=$response->getResponseInfo();
			error_log("Content type=".$ri["content_type"]);
			error_log("MMSRetrieve::retrieveMessageContent response=".print_r($response,true));
			
			$params['include_bodies'] = true;
			$params['decode_bodies']  = true;
			$params['decode_headers'] = true;
			$params['crlf']           = "\r\n";

			$mimedata="Content-Type: ".$ri["content_type"]."\r\n\r\n".$response->getResponseBody();

			error_log("MMSRetrieve::retrieveMessageContent mimedata=".print_r($mimedata,true));
			
			$decoder = new Mail_mimeDecode($mimedata);
			$structure = $decoder->decode($params);
			
			error_log('MMSRetrieve::retrieveMessageContent mime decoded to\n'.print_r($structure,true));
			
			$attachments=array();
			

			foreach ($structure->parts as $part) {
				$contentType=null;
				$contentName=null;
				//error_log('Part = '.print_r($part,true));
				if (isset($part->headers)) {
					$headers=$part->headers;
					if (isset($headers['content-type'])) {
						$contentType=$headers['content-type'];
					}
				}
				error_log('Disposition='.$part->disposition);
				error_log('D_paramters='.$part->d_parameters);
				
				if (isset($part->d_parameters)) {
					$dparameters=$part->d_parameters;
					if (isset($dparameters['name'])) {
						$contentName=$dparameters['name'];
					}
				}
				$data=$part->body;
				if ($contentType=='application/json' && $contentName=='root-fields') {
					$rawJSON=$data;
					error_log("Found JSON data. ".$rawJSON);
				} else {
					error_log("Found attachment name=".$contentName." type=".$contentType);
					$attachment=new Attachment($contentName, $contentType, $data);
					$index=count($attachments);
					$attachments[$index]=$attachment;
				}
			}
			
			if (isset($rawJSON))  $jsondata=json_decode($rawJSON);
					
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
			
			$retrieveMMSResponse=new RetrieveMMSMessageResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata, $attachments);
			
			error_log("MMSRetrieve::retrieveMessageContent retrieveMMSResponse=".print_r($retrieveMMSResponse,true));
		}
			
		
		return $retrieveMMSResponse;
	}
	
	public function subscribeToReceiptNotifications($destinationAddress, $notifyURL, $criteria, $notificationFormat, $clientCorrelator, $callbackData) {
		$mmsMessageReceiptSubscriptionResponse=null;
		if ($destinationAddress && $notifyURL) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'MMSReceiptSubscriptions')) {
				$url=$this->endpoint->getMMSReceiptSubscriptionsEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/MMSReceiptService/1/messaging/inbound/subscriptions';
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
			
			error_log("MMSRetrieve::subscribeToReceiptNotifications response=".print_r($response,true));
			error_log("MMSRetrieve::subscribeToReceiptNotifications jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$mmsMessageReceiptSubscriptionResponse=new MMSMessageReceiptSubscriptionResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("MMSRetrieve::subscribeToReceiptNotifications mmsMessageReceiptSubscriptionResponse=".print_r($mmsMessageReceiptSubscriptionResponse,true));
		}
		
		return $mmsMessageReceiptSubscriptionResponse;
	}
	
	public function cancelReceiptNotifications($subscriptionId) {
		$responseCode=null;
		if ($subscriptionId) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'CancelMMSReceiptSubscription')) {
				$url=$this->endpoint->getCancelMMSReceiptSubscriptionEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/CancelMMSReceiptService/1/messaging/inbound/subscriptions/{subscriptionId}';
			}
			
			$url=str_replace('{subscriptionId}', urlencode($subscriptionId), $url);
			
			$request=new JSONRequest($url, $this->username, $this->password, 'DELETE', null);
			$response=$request->execute();
			$responseInfo=$response->getResponseInfo();
		
			error_log("MMSRetrieve::cancelReceiptNotifications httpResponseCode=".$responseInfo["http_code"]);
			
			$responseCode=$responseInfo["http_code"];
		}
		
		return $responseCode;
	}
	
	public static function convertInboundMessageNotification($contentType, $requestBody) {
		$inboundMessageNotification=null;
		if ($contentType && $contentType=="application/json") {
			$jsondata=json_decode($requestBody);
			if (property_exists($jsondata, 'inboundMessageNotification')) {				
				$inboundMessageNotification=new InboundMessageNotification($jsondata->inboundMessageNotification);
			}			
		}
		return $inboundMessageNotification;
	}
	
}

?>
