<?php 

class SMSSendDeliveryStatusResponse {

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
		
	protected $deliveryInfoList=null;
	public function getDeliveryInfoList() { return $this->deliveryInfoList; }
	public function setDeliveryInfoList($deliveryInfoList) { $this->deliveryInfoList=$deliveryInfoList; }

	public function __construct($httpResponseCode=0, $contentType=null, $location=null, $jsondata = null) {
		$this->httpResponseCode=$httpResponseCode;
		$this->contentType=$contentType;
		$this->location=$location;
		
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'deliveryInfoList')) {				
				$deliveryInfoList=new DeliveryInfoList($jsondata->deliveryInfoList);				
				$this->deliveryInfoList = $deliveryInfoList;	
			} 
		}
		
	}		
	
	public function toString() {
		$buffer='httpResponseCode='.$this->httpResponseCode.', contentType='.$this->contentType.', location='.$this->location;
		$buffer=$buffer.', requestError=';
		if (!is_null($this->requestError)) {
			$buffer=$buffer.'{'.$this->requestError->toString().'}';
		}
		$buffer=$buffer.', deliveryInfoList=';
		if (!is_null($this->deliveryInfoList)) {
			$buffer=$buffer.'{'.$this->deliveryInfoList->toString().'}';
		}
		return $buffer;
	}

			
}

?>	
