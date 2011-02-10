<?php

class AmountTransaction {
	protected $clientCorrelator=null;
	protected $endUserId=null;
	protected $paymentAmount=null;
	protected $referenceCode=null;
	protected $resourceURL=null;
	protected $serverReferenceCode=null;
	protected $transactionOperationStatus=null;
			
	public function getClientCorrelator() { return $this->clientCorrelator; }
	public function getEndUserId() { return $this->endUserId; }
	public function getPaymentAmount() { return $this->paymentAmount; }
	public function getReferenceCode() { return $this->referenceCode; }
	public function getServerReferenceCode() { return $this->serverReferenceCode; }
	public function getResourceURL() { return $this->resourceURL; }
	public function getTransactionOperationStatus() { return $this->transactionOperationStatus; }
			
	public function __construct($jsondata) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'clientCorrelator')) {
				$this->clientCorrelator = $jsondata->clientCorrelator;
			}
			if (property_exists($jsondata, 'endUserId')) {
				$this->endUserId = $jsondata->endUserId;
			}
			if (property_exists($jsondata, 'paymentAmount')) {
				$paymentAmount = new PaymentAmount($jsondata->paymentAmount);
				$this->paymentAmount = & $paymentAmount;
			}
			if (property_exists($jsondata, 'referenceCode')) {
				$this->referenceCode = $jsondata->referenceCode;
			}
			if (property_exists($jsondata, 'serverReferenceCode')) {
				$this->serverReferenceCode = $jsondata->serverReferenceCode;
			}
			if (property_exists($jsondata, 'resourceURL')) {
				$this->resourceURL = $jsondata->resourceURL;
			}
			if (property_exists($jsondata, 'transactionOperationStatus')) {
				$this->transactionOperationStatus = $jsondata->transactionOperationStatus;
			}
		}
	}
	
	public function toString() {
		$buffer='clientCorrelator='.$this->clientCorrelator.', endUserId='.$this->endUserId.
											', paymentAmount=';
		if (!is_null($this->paymentAmount)) {
			$buffer=$buffer.'{'.$this->paymentAmount->toString().'}';
		}
		$buffer=$buffer.', referenceCode='.$this->referenceCode.
											', resourceURL='.$this->resourceURL.
											', serverReferenceCode='.$this->serverReferenceCode.
											', transactionOperationStatus='.$this->transactionOperationStatus;
		return $buffer;
	}
	
}

?>