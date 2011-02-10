<?php 

class SMSDeliveryReceiptSubscriptionResponse {

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
	
	protected $deliveryReceiptSubscription=null;
	public function getDeliveryReceiptSubscription() { return $this->deliveryReceiptSubscription; }
	public function setDeliveryReceiptSubscription($deliveryReceiptSubscription) { $this->deliveryReceiptSubscription=$deliveryReceiptSubscription; } 
	
	public function __construct($httpResponseCode=0, $contentType=null, $location=null, $jsondata = null) {
		$this->httpResponseCode=$httpResponseCode;
		$this->contentType=$contentType;
		$this->location=$location;
		
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'deliveryReceiptSubscription')) {				
				$deliveryReceiptSubscription=new DeliveryReceiptSubscription($jsondata->deliveryReceiptSubscription);				
				$this->deliveryReceiptSubscription = $deliveryReceiptSubscription;	
			} 
		}
		
	}		

	public function toString() {
		$buffer='httpResponseCode='.$this->httpResponseCode.', contentType='.$this->contentType.', location='.$this->location;
		$buffer=$buffer.', requestError=';
		if (!is_null($this->requestError)) {
			$buffer=$buffer.'{'.$this->requestError->toString().'}';
		}
		$buffer=$buffer.', deliveryReceiptSubscription=';
		if (!is_null($this->deliveryReceiptSubscription)) {
			$buffer=$buffer.'{'.$this->deliveryReceiptSubscription->toString().'}';
		}
		return $buffer;
	}
	
	
}

?>	
