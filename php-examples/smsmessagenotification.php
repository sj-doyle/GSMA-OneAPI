<?php

	$ONEAPICOMMON = dirname(__FILE__)."/oneapi/";

	include ($ONEAPICOMMON.'sms/SMSRetrieve.php');   

	$requestBody=@file_get_contents('php://input');
	$contentType=$_SERVER['CONTENT_TYPE'];

	error_log('Content Type='.$contentType);

	$inboundSMSMessageNotification=null;
	if (!is_null($requestBody)) {
		$inboundSMSMessageNotification=SMSRetrieve::convertInboundSMSMessageNotification($contentType, $requestBody);
	}

	if (!is_null($inboundSMSMessageNotification)) {
		error_log('Have an SMS inboundSMSMessageNotification object ');
		error_log('callbackData               = '.$inboundSMSMessageNotification->getCallbackData());
		if (!is_null($inboundSMSMessageNotification->getInboundSMSMessage()) ) {
			$inboundSMSMessage=$inboundSMSMessageNotification->getInboundSMSMessage();
			error_log('dateTime                   = '.$inboundSMSMessage->getDateTime());
			error_log('destinationAddress         = '.$inboundSMSMessage->getDestinationAddress());
			error_log('messageId                  = '.$inboundSMSMessage->getMessageId());
			error_log('message                    = '.$inboundSMSMessage->getMessage());
			error_log('resourceURL                = '.$inboundSMSMessage->getResourceURL());
			error_log('senderAddress              = '.$inboundSMSMessage->getSenderAddress());
		}
	}	

?>
