<?php

	$ONEAPICOMMON = dirname(__FILE__)."/oneapi/";

	include ($ONEAPICOMMON.'payment/Reservation.php');   

	$requestBody=@file_get_contents('php://input');
	$contentType=$_SERVER['CONTENT_TYPE'];

	error_log('Content Type='.$contentType);

	$amountReservationTransaction=null;
	if (!is_null($requestBody)) {
		$amountReservationTransaction=Reservation::convertAmountReservationTransaction($contentType, $requestBody);
	}

	if (!is_null($amountReservationTransaction)) {
		error_log('Have an amountReservationTransaction object ');
		error_log('clientCorrelator           = '.$amountReservationTransaction->getClientCorrelator());
		error_log('endUserId                  = '.$amountReservationTransaction->getEndUserId());
		error_log('referenceCode              = '.$amountReservationTransaction->getReferenceCode());
		error_log('referenceSequence          = '.$amountReservationTransaction->getReferenceSequence());
		error_log('serverReferenceCode        = '.$amountReservationTransaction->getServerReferenceCode());
		error_log('resourceURL                = '.$amountReservationTransaction->getResourceURL());
		error_log('transactionOperationStatus = '.$amountReservationTransaction->getTransactionOperationStatus());
	}
	
?>
