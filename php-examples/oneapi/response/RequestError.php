<?php

class RequestError {
	protected $serviceException=null;
//	protected $policyException=null;
			
	public function getServiceException() { return $this->serviceException; }
			
	public function setServiceException(ServiceException $serviceException) { $this->serviceException=$serviceException; }
			
	public function __construct($jsondata = null) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'serviceException')) {
				$serviceException = new ServiceException($jsondata->serviceException);
				$this->serviceException = $serviceException;
			}
			if (property_exists($jsondata, 'policyException')) {
				$policyException = new ServiceException($jsondata->policyException);
				$this->policyException = $policyException;
			}
		}
	}
	
	public function createServiceException($messageId, $text, $variables) {
		$this->serviceException = new ServiceException($messageId, $text, $variables);
		$this->policyException = null;
	}

	public function createPolicyException($messageId, $text, $variables) {
		$this->policyException = new PolicyException($messageId, $text, $variables);
		$this->serviceException = null;
	}

	public function toString() {
		$buffer='';
		if (!is_null($this->serviceException)) {
			$buffer=$buffer.'ServiceException: {'.$serviceException->toString().'} ';
		}
		if (!is_null($this->policyException)) {
			$buffer=$buffer.'PolicyException: {'.$policyException->toString().'} ';
		}
		return $buffer;
	}
	
}

?>