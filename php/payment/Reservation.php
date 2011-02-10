<?php

require_once($ONEAPICOMMON.'foundation/FormParameter.php');
require_once($ONEAPICOMMON.'foundation/FormParameters.php');
require_once($ONEAPICOMMON.'foundation/JSONRequest.php');
require_once($ONEAPICOMMON.'response/RequestError.php');
require_once($ONEAPICOMMON.'response/ServiceException.php');
require_once($ONEAPICOMMON.'response/PolicyException.php');
require_once($ONEAPICOMMON.'response/payment/ChargingInformation.php');
require_once($ONEAPICOMMON.'response/payment/PaymentAmount.php');
require_once($ONEAPICOMMON.'response/payment/AmountTransaction.php');
require_once($ONEAPICOMMON.'response/payment/AmountReservationTransaction.php');
require_once($ONEAPICOMMON.'response/payment/PaymentResponse.php');
	
class Reservation {
	
	protected $endpoint;
	protected $username;
	protected $password;
	
	public function __construct ($endpoint = null, $username = null, $password = null) {
		$this->endpoint = $endpoint;
		$this->username = $username; 
		$this->password = $password;		
	}
		
	public function reserveInitialAmount($endUserId, $referenceCode, $description, $currency, $amount, $code, $clientCorrelator, 
						$onBehalfOf, $purchaseCategoryCode, $channel, $taxAmount, $serviceId, $productId) {
		$paymentResponse=null;
		if ($endUserId && $referenceCode && $description && $currency && $amount) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'AmountReserve')) {
				$url=$this->endpoint->getAmountReserveEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/AmountReserveService/1/payment/{endUserId}/transactions/amountReservation';
			}
			
			$url=str_replace('{endUserId}', urlencode($endUserId), $url);
			
			$formParameters=new FormParameters();
			$formParameters->put("endUserId", $endUserId);
			$formParameters->put("transactionOperationStatus", "reserved");
			$formParameters->put("referenceCode", $referenceCode);
			$formParameters->put("description", $description);
			$formParameters->put("currency", $currency);
			$formParameters->put("amount", $amount);
			$formParameters->put("referenceSequence", 1);
			$formParameters->put("code", $code);
			
			$formParameters->put("clientCorrelator", $clientCorrelator);
			$formParameters->put("onBehalfOf", $onBehalfOf);
			$formParameters->put("purchaseCategoryCode", $purchaseCategoryCode);
			$formParameters->put("channel", $channel);
			$formParameters->put("taxAmount", $taxAmount);
			$formParameters->put("serviceId", $serviceId);
			$formParameters->put("productId", $productId);
			
			
			$request=new JSONRequest($url, $this->username, $this->password, 'POST', $formParameters->getParameterSet());
			$response=$request->execute();
		
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			error_log("Charge::charge response=".print_r($response,true));
			error_log("Charge::charge jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$paymentResponse=new PaymentResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("Reservation::reserveInitialAmount paymentResponse=".print_r($paymentResponse,true));
		}
				
		return $paymentResponse;
	}

	public function reserveAdditionalAmount($endUserId, $referenceCode, $description, $currency, $amount, $referenceSequence, $code, 
						$onBehalfOf, $purchaseCategoryCode, $channel, $taxAmount, $serviceId, $productId) {
		$paymentResponse=null;
		if ($endUserId && $referenceCode && $description && $currency && $amount) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'AmountReserveAdditional')) {
				$url=$this->endpoint->getAmountReserveAdditionalEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/AmountReserveAdditionalService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}';
			}
			
			$url=str_replace('{endUserId}', urlencode($endUserId), $url);
			$url=str_replace('{transactionId}', urlencode($referenceCode), $url);
			
			$formParameters=new FormParameters();
			$formParameters->put("endUserId", $endUserId);
			$formParameters->put("transactionOperationStatus", "reserved");
			$formParameters->put("referenceCode", $referenceCode);
			$formParameters->put("description", $description);
			$formParameters->put("currency", $currency);
			$formParameters->put("amount", $amount);
			$formParameters->put("referenceSequence", $referenceSequence);
			$formParameters->put("code", $code);
		
			$formParameters->put("onBehalfOf", $onBehalfOf);
			$formParameters->put("purchaseCategoryCode", $purchaseCategoryCode);
			$formParameters->put("channel", $channel);
			$formParameters->put("taxAmount", $taxAmount);
			$formParameters->put("serviceId", $serviceId);
			$formParameters->put("productId", $productId);
			
			
			$request=new JSONRequest($url, $this->username, $this->password, 'POST', $formParameters->getParameterSet());
			$response=$request->execute();
		
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			error_log("Charge::refund response=".print_r($response,true));
			error_log("Charge::refund jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$paymentResponse=new PaymentResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("Reservation::reserveAdditionalAmount paymentResponse=".print_r($paymentResponse,true));
		}
		
		return $paymentResponse;
	}

	public function chargeAmount($endUserId, $referenceCode, $description, $currency, $amount, $referenceSequence, $code, $callbackURL,
						$clientCorrelator, $onBehalfOf, $purchaseCategoryCode, $channel, $taxAmount, $serviceId, $productId) {
		$paymentResponse=null;
		if ($endUserId && $referenceCode && $description && $currency && $amount) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'AmountReservationCharge')) {
				$url=$this->endpoint->getAmountReservationChargeEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/AmountReserveChargeService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}';
			}
			
			$url=str_replace('{endUserId}', urlencode($endUserId), $url);
			$url=str_replace('{transactionId}', urlencode($referenceCode), $url);
			
			$formParameters=new FormParameters();
			$formParameters->put("endUserId", $endUserId);
			$formParameters->put("transactionOperationStatus", "charged");
			$formParameters->put("referenceCode", $referenceCode);
			$formParameters->put("description", $description);
			$formParameters->put("currency", $currency);
			$formParameters->put("amount", $amount);
			$formParameters->put("referenceSequence", $referenceSequence);
			$formParameters->put("code", $code);
			$formParameters->put("callbackURL", $callbackURL);
		
			$formParameters->put("clientCorrelator", $clientCorrelator);
			$formParameters->put("onBehalfOf", $onBehalfOf);
			$formParameters->put("purchaseCategoryCode", $purchaseCategoryCode);
			$formParameters->put("channel", $channel);
			$formParameters->put("taxAmount", $taxAmount);
			$formParameters->put("serviceId", $serviceId);
			$formParameters->put("productId", $productId);
			
			
			$request=new JSONRequest($url, $this->username, $this->password, 'POST', $formParameters->getParameterSet());
			$response=$request->execute();
		
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			error_log("Charge::refund response=".print_r($response,true));
			error_log("Charge::refund jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$paymentResponse=new PaymentResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("Reservation::chargeAmount paymentResponse=".print_r($paymentResponse,true));
		}
		
		return $paymentResponse;
	}

	public function releaseReservation($endUserId, $referenceCode, $referenceSequence) {
		$paymentResponse=null;
		if ($endUserId && $referenceCode && $referenceSequence) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'AmountReservationRelease')) {
				$url=$this->endpoint->getAmountReservationReleaseEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/AmountReserveReleaseService/1/payment/{endUserId}/transactions/amountReservation/{transactionId}';
			}
			
			$url=str_replace('{endUserId}', urlencode($endUserId), $url);
			$url=str_replace('{transactionId}', urlencode($referenceCode), $url);
			
			$formParameters=new FormParameters();
			$formParameters->put("endUserId", $endUserId);
			$formParameters->put("transactionOperationStatus", "released");
			$formParameters->put("referenceCode", $referenceCode);
			$formParameters->put("referenceSequence", $referenceSequence);
		
			$request=new JSONRequest($url, $this->username, $this->password, 'POST', $formParameters->getParameterSet());
			$response=$request->execute();
		
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			error_log("Charge::refund response=".print_r($response,true));
			error_log("Charge::refund jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$paymentResponse=new PaymentResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("Reservation::releaseReservation paymentResponse=".print_r($paymentResponse,true));
		}
		
		return $paymentResponse;
	}

	public static function convertAmountReservationTransaction($contentType, $requestBody) {
		$amountReservationTransaction=null;
		if ($contentType && $contentType=="application/json") {
			$jsondata=json_decode($requestBody);
			if (property_exists($jsondata, 'amountReservationTransaction')) {				
				$amountReservationTransaction=new AmountReservationTransaction($jsondata->amountReservationTransaction);
			}			
		}
		return $amountReservationTransaction;
	}
	
}

?>
