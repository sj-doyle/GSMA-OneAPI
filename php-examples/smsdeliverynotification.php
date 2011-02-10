<?php

	$ONEAPICOMMON = dirname(__FILE__)."/oneapi/";

	include ($ONEAPICOMMON.'sms/SMSSend.php');   

	$requestBody=@file_get_contents('php://input');
	$contentType=$_SERVER['CONTENT_TYPE'];

	error_log('Content Type='.$contentType);

	$deliveryInfoNotification=null;
	if (!is_null($requestBody)) {
		$deliveryInfoNotification=SMSSend::convertDeliveryInfoNotification($contentType, $requestBody);
	}

	if (!is_null($deliveryInfoNotification)) {
		error_log('Have an SMS deliveryInfoNotification object ');
		error_log('callbackData               = '.$deliveryInfoNotification->getCallbackData());
		if (!is_null($deliveryInfoNotification->getDeliveryInfo()) && is_array($deliveryInfoNotification->getDeliveryInfo())) {
			$deliveryInfo=$deliveryInfoNotification->getDeliveryInfo();
			
			for ($i=0; $i<count($deliveryInfo); $i++) {
				error_log('address                    = '.$deliveryInfo[$i]->getAddress());
				error_log('deliveryStatus             = '.$deliveryInfo[$i]->getDeliveryStatus());
			}
		}
	}	

?>