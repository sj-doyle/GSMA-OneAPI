<?php

	$ONEAPICOMMON = dirname(__FILE__)."/oneapi/";

	include ($ONEAPICOMMON.'mms/MMSRetrieve.php');   

	$requestBody=@file_get_contents('php://input');
	$contentType=$_SERVER['CONTENT_TYPE'];

	error_log('Content Type='.$contentType);

	$inboundMessageNotification=null;
	if (!is_null($requestBody)) {
		$inboundMessageNotification=MMSRetrieve::convertInboundMessageNotification($contentType, $requestBody);
	}

	if (!is_null($inboundMessageNotification)) {
		error_log('Have an MMS inboundMessageNotification object ');
		error_log('callbackData               = '.$inboundMessageNotification->getCallbackData());
		if (!is_null($inboundMessageNotification->getInboundMessage()) ) {
			$inboundMessage=$inboundMessageNotification->getInboundMessage();
			error_log('dateTime                   = '.$inboundMessage->getDateTime());
			error_log('destinationAddress         = '.$inboundMessage->getDestinationAddress());
			error_log('messageId                  = '.$inboundMessage->getMessageId());
			error_log('resourceURL                = '.$inboundMessage->getResourceURL());
			error_log('senderAddress              = '.$inboundMessage->getSenderAddress());
		}
	}	

?>