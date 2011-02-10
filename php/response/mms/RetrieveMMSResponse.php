<?php

class RetrieveMMSResponse {	

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
			
	protected $inboundMessageList=null;
	public function getInboundMessageList() { return $this->inboundMessageList; }
	public function setInboundMessageList($inboundMessageList) { $this->inboundMessageList=$inboundMessageList; }

	public function __construct($httpResponseCode=0, $contentType=null, $location=null, $jsondata = null, $attachments=null) {
		$this->httpResponseCode=$httpResponseCode;
		$this->contentType=$contentType;
		$this->location=$location;
		
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'inboundMessageList')) {				
				$inboundMessageList=new InboundMessageList($jsondata->inboundMessageList);				
				$this->inboundMessageList = $inboundMessageList;	
			} 
		}
	}		

	public function toString() {
		$buffer='httpResponseCode='.$this->httpResponseCode.', contentType='.$this->contentType.', location='.$this->location;
		$buffer=$buffer.', requestError=';
		if (!is_null($this->requestError)) {
			$buffer=$buffer.'{'.$this->requestError->toString().'}';
		}
		$buffer=$buffer.', inboundMessageList=';
		if (!is_null($this->inboundMessageList)) {
			$buffer=$buffer.'{'.$this->inboundMessageList->toString().'}';
		}
		return $buffer;
	}
	
}

?>