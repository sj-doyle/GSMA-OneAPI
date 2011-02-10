<?php

class RetrieveMMSMessageResponse {

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
		
	protected $inboundMessage=null;
	public function getInboundMessage() { return $this->inboundMessage; }
	public function setInboundMessage($inboundMessage) { $this->inboundMessage=$inboundMessage; }
	
	protected $attachments=null;
	public function getAttachments() { return $this->attachments; }
	public function setAttachments($attachments) { $this->attachments=$attachments; }
		
	public function __construct($httpResponseCode=0, $contentType=null, $location=null, $jsondata = null, $attachments=null) {
		$this->httpResponseCode=$httpResponseCode;
		$this->contentType=$contentType;
		$this->location=$location;
		
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'inboundMessage')) {				
				$inboundMessage=new InboundMessage($jsondata->inboundMessage);				
				$this->inboundMessage = $inboundMessage;	
			} 
		}
		
		$this->attachments=$attachments;		
	}		

	public function toString() {
		$buffer='httpResponseCode='.$this->httpResponseCode.', contentType='.$this->contentType.', location='.$this->location;
		$buffer=$buffer.', requestError=';
		if (!is_null($this->requestError)) {
			$buffer=$buffer.'{'.$this->requestError->toString().'}';
		}
		$buffer=$buffer.', inboundMessage=';
		if (!is_null($this->inboundMessage)) {
			$buffer=$buffer.'{'.$this->inboundMessage->toString().'}';
		}
		$buffer=$buffer.', attachments=';
		if (!is_null($this->attachments) && is_array($this->attachments)) {
			$buffer=$buffer.'{';
			for ($i=0; $i<count($this->attachments); $i++) {
				if ($i>0) {
					$buffer=$buffer.', ['.$i.'] = '.$this->attachments[$i]->toString();
				} else {
					$buffer=$buffer.'['.$i.'] = '.$this->attachments[$i]->toString();
				}			
			}
			$buffer=$buffer.'}';
		}
		return $buffer;
	}
		
}

?>
