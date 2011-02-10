<?php 

class PaymentResponse {

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
	
	protected $amountTransaction=null;	
	public function setAmountTransaction($amountTransaction) { $this->amountTransaction=$amountTransaction; }
	public function getAmountTransaction() { return $this->amountTransaction; }
	
	protected $amountReservationTransaction=null;
	public function setAmountReservationTransaction($amountReservationTransaction) { $this->amountReservationTransaction=$amountReservationTransaction; }
	public function getAmountReservationTransaction() { return $this->amountReservationTransaction; }
	
	public function __construct($httpResponseCode=0, $contentType=null, $location=null, $jsondata = null) {
		$this->httpResponseCode=$httpResponseCode;
		$this->contentType=$contentType;
		$this->location=$location;
		
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'amountTransaction')) {				
				$amountTransaction=new AmountTransaction($jsondata->amountTransaction);				
				$this->amountTransaction = $amountTransaction;	
			} 
			if (property_exists($jsondata, 'amountReservationTransaction')) {				
				$amountReservationTransaction=new AmountReservationTransaction($jsondata->amountReservationTransaction);				
				$this->amountReservationTransaction = $amountReservationTransaction;	
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
		if (!is_null($this->amountTransaction)) {
			$buffer=$buffer.', amountTransaction={'.$this->amountTransaction->toString().'}';
		}
		if (!is_null($this->amountReservationTransaction)) {
			$buffer=$buffer.', amountReservationTransaction={'.$this->amountReservationTransaction->toString().'}';
		}
		return $buffer;
	}
		
}

?>