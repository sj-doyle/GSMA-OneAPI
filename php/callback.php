<?php
	error_log('callback.php has been invoked');
	
	$requestBody=@file_get_contents('php://input');
	
	error_log('RequestBody = '.$requestBody);	
	
?>