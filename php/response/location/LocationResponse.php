<?php 

class LocationResponse {

	protected $httpResponseCode=0;
	protected $contentType=null;
	protected $requestError=null;
	
	public function getHTTPResponseCode() { return $this->httpResponseCode; }
	public function getContentType() { return $this->contentType; }
	public function getRequestError() { return $this->requestError; }
	
	public function setHTTPResponseCode($httpResponseCode) { $this->httpResponseCode=(int) $httpResponseCode; }
	public function setContentType($contentType) { $this->contentType=(string) $contentType; }
	public function setRequestError($requestError) { $this->requestError=$requestError; }
	
	protected $location=null;
	public function getLocation() { return $this->location; }
	public function setLocation($location) { $this->location=(string) $location; }
	
	protected $terminalLocationList=null;
	public function getTerminalLocationList() { return $this->terminalLocationList; }
	public function setTerminalLocationList($terminalLocationList) { $this->terminalLocationList=$terminalLocationList ; }
	
	public function __construct($httpResponseCode=0, $contentType=null, $location=null, $jsondata = null) {
		$this->httpResponseCode=$httpResponseCode;
		$this->contentType=$contentType;
		$this->location=$location;
		
		error_log('LocationResponse->httpResponseCode = '.$httpResponseCode);
		error_log('LocationResponse->contentType = '.$contentType);
		error_log('LocationResponse->httpResponseCode = '.print_r($jsondata,true));
		
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'terminalLocationList')) {
				error_log('LocationResponse::have terminal location list');
				
				$terminalLocationList=new TerminalLocationList($jsondata->terminalLocationList);
				
				$this->terminalLocationList = $terminalLocationList;	
			} 
			if (property_exists($jsondata, 'requestError')) {
				error_log('LocationResponse::have request error');
			}
		}
		
	}

	public function toString() {
		$buffer='httpResponseCode='.$this->httpResponseCode.', contentType='.$this->contentType.', location='.$this->location;
		$buffer=$buffer.', requestError=';
		if (!is_null($this->requestError)) {
			$buffer=$buffer.'{'.$this->requestError->toString().'}';
		}
		$buffer=$buffer.', terminalLocationList=';
		if (!is_null($this->terminalLocationList)) {
			$buffer=$buffer.'{'.$this->terminalLocationList->toString().'}';
		}
		return $buffer;
	}
		
}

?>