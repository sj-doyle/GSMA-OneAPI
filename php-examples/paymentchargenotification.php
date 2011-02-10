<?php

	$ONEAPICOMMON = dirname(__FILE__)."/oneapi/";

	include ($ONEAPICOMMON.'payment/Charge.php');   

	$requestBody=@file_get_contents('php://input');
	$contentType=$_SERVER['CONTENT_TYPE'];

	error_log('Content Type='.$contentType);

	$amountTransaction=null;
	if (!is_null($requestBody)) {
		$amountTransaction=Charge::convertAmountTransaction($contentType, $requestBody);
	}

	if (!is_null($amountTransaction)) {
		error_log('Have an amountTransaction object ');
		error_log('clientCorrelator           = '.$amountTransaction->getClientCorrelator());
		error_log('endUserId                  = '.$amountTransaction->getEndUserId());
		error_log('referenceCode              = '.$amountTransaction->getReferenceCode());
		error_log('serverReferenceCode        = '.$amountTransaction->getServerReferenceCode());
		error_log('resourceURL                = '.$amountTransaction->getResourceURL());
		error_log('transactionOperationStatus = '.$amountTransaction->getTransactionOperationStatus());
	}
	

?>