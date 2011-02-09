<?php

require_once($ONEAPICOMMON.'foundation/JSONRequest.php');
require_once($ONEAPICOMMON.'response/RequestError.php');
require_once($ONEAPICOMMON.'response/ServiceException.php');
require_once($ONEAPICOMMON.'response/PolicyException.php');
require_once($ONEAPICOMMON.'response/location/ErrorInformation.php');
require_once($ONEAPICOMMON.'response/location/TerminalLocationList.php');
require_once($ONEAPICOMMON.'response/location/CurrentLocation.php');
require_once($ONEAPICOMMON.'response/location/TerminalLocation.php');
require_once($ONEAPICOMMON.'response/location/LocationResponse.php');


	
class Locate {
	protected $endpoint;
	protected $username;
	protected $password;
	
	public function __construct ($endpoint = null, $username = null, $password = null) {
		$this->endpoint = $endpoint;
		$this->username = $username; 
		$this->password = $password;		
		error_log('Username = '.$username);	
		error_log('Password = '.$password);	
	}
	
	public function locateTerminal($endUserId = null, $requestedAccuracy = 1000) {
		$locationResponse=null;
		if ($endUserId && $requestedAccuracy>0) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'Location')) {
				$url=$this->endpoint->getLocationEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/LocationService/1/location/queries/location';
			}
			$url=$url.'?requestedAccuracy='.urlencode($requestedAccuracy).'&address='.urlencode($endUserId);
			
			$request=new JSONRequest($url, $this->username, $this->password, 'GET');
			$response=$request->execute();
		
			error_log('Response body = '.print_r($response, true));
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$locationResponse=new LocationResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
		}
		
		return $locationResponse;
	}

	public function locateMultipleTerminals($endUserId = null, $requestedAccuracy = 1000) {
		$locationResponse=null;
		if (isset($endUserId) && is_array($endUserId) && count($endUserId)>0 && $requestedAccuracy>0) {
			if (!is_null($this->endpoint) && property_exists($this->endpoint, 'Location')) {
				$url=$this->endpoint->getLocationEndpoint();
			} else {
				$url='http://localhost:8080/oneapiserver/LocationService/1/location/queries/location';
			}
			$url=$url.'?requestedAccuracy='.urlencode($requestedAccuracy);
			for ($i=0; $i<count($endUserId); $i++) {
				$url=$url.'&address='.urlencode($endUserId[$i]);
			}			
			error_log('Request is '.$url);
			$request=new JSONRequest($url, $this->username, $this->password, 'GET');
			$response=$request->execute();
		
			if ($response->getResponseBody()) {
				$jsondata=json_decode($response->getResponseBody());
			}
			
			$responseInfo=$response->getResponseInfo();
			$location=$response->getLocation();
	
			$locationResponse=new LocationResponse($responseInfo["http_code"], $responseInfo["content_type"], $location, $jsondata);
		}
		
		return $locationResponse;
	}

}
	
?>