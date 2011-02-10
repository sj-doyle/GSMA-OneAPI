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
	
class Charge {
	
	protected $endpoint;
	protected $username;
	protected $password;
	
	public function __construct ($endpoint = null, $username = null, $password = null) {
		$this->endpoint = $endpoint;
		$this->username = $username; 
		$this->password = $password;		
	}
	
	public function charge($endUserId, $referenceCode, $description, $currency, $amount, $code, $callbackURL, $clientCorrelator, 
						$onBehalfOf, $purchaseCategoryCode, $channel, $taxAmount, $serviceId, $productId) {
		$paymentResponse=null;
		if ($endUserId && $referenceCode && $description && $currency && $amount) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'AmountCharge')) {
				$url=$this->endpoint->getAmountChargeEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/AmountChargeService/1/payment/{endUserId}/transactions/amount';
			}
			
			$url=str_replace('{endUserId}', urlencode($endUserId), $url);
			
			$formParameters=new FormParameters();
			$formParameters->put("endUserId", $endUserId);
			$formParameters->put("transactionOperationStatus", "charged");
			$formParameters->put("referenceCode", $referenceCode);
			$formParameters->put("description", $description);
			$formParameters->put("currency", $currency);
			$formParameters->put("amount", $amount);
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
			
			error_log("Charge::charge response=".print_r($response,true));
			error_log("Charge::charge jsondata=".print_r($jsondata,true));
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$paymentResponse=new PaymentResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
			
			error_log("Charge::charge paymentResponse=".print_r($paymentResponse,true));
		}
		
		return $paymentResponse;
	}

	public function refund($endUserId, $referenceCode, $description, $currency, $amount, $code, $clientCorrelator, $originalServerReferenceCode,
						$onBehalfOf, $purchaseCategoryCode, $channel, $taxAmount, $serviceId, $productId) {
		$paymentResponse=null;
		if ($endUserId && $referenceCode && $description && $currency && $amount) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'AmountRefund')) {
				$url=$this->endpoint->getAmountRefundEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/AmountRefundService/1/payment/{endUserId}/transactions/amount';
			}
			
			$url=str_replace('{endUserId}', urlencode($endUserId), $url);
			
			$formParameters=new FormParameters();
			$formParameters->put("endUserId", $endUserId);
			$formParameters->put("transactionOperationStatus", "refunded");
			$formParameters->put("referenceCode", $referenceCode);
			$formParameters->put("description", $description);
			$formParameters->put("currency", $currency);
			$formParameters->put("amount", $amount);
			$formParameters->put("code", $code);
			$formParameters->put("clientCorrelator", $clientCorrelator);
			$formParameters->put("originalServerReferenceCode", $originalServerReferenceCode);
		
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
			
			error_log("Charge::refund paymentResponse=".print_r($paymentResponse,true));
		}
		
		return $paymentResponse;
	}
	
	public static function convertAmountTransaction($contentType, $requestBody) {
		$amountTransaction=null;
		if ($contentType && $contentType=="application/json") {
			$jsondata=json_decode($requestBody);
			if (property_exists($jsondata, 'amountTransaction')) {				
				$amountTransaction=new AmountTransaction($jsondata->amountTransaction);
			}			
		}
		return $amountTransaction;
	}
	
}

?>
