<?php

class ErrorInformation {
	protected $serviceException=null;
			
	public function getServiceException() { return $this->serviceException; }			
	public function setServiceException(ServiceException $serviceException) { $this->serviceException=$serviceException; }
			
	public function __construct($jsondata = null) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'serviceException')) {
				$serviceException = new ServiceException($jsondata->serviceException);
				$this->serviceException = $serviceException;
			}
		}
	}	

	public function toString() {
		$buffer='serviceException=';
		if (!is_null($this->serviceException)) {
			$buffer=$buffer.'{'.$this->serviceException->toString().'}';
		}
		return $buffer;
	}
		
}

?>