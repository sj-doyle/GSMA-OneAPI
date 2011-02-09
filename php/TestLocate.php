<?php

	error_log('');
	error_log('');
	error_log('');
	error_log('');
	error_log('');
	error_log('including OneAPIConfig.php');
	
	require_once('OneAPIConfig.php');
	
	error_log('Common directory is '.$ONEAPICOMMON);
	
	error_log('including endpoints');
	require_once($ONEAPICOMMON.'endpoints/ServiceEndpoints.php');
	
	error_log('including Locate.php');	

	require_once($ONEAPICOMMON.'location/Locate.php');
	
	$endpoints=new ServiceEndpoints();
	
	error_log('Creating a locate object');
	
	$locator = new Locate(&$endpoints, 'fred.bloggs', '1234');
	
	$locationResponse=$locator->locateTerminal('tel:12345', 500);

	error_log(print_r($locator, true));	
?>

Testing Locate Call