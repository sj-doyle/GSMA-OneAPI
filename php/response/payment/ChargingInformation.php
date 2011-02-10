<?php

class ChargingInformation {
	protected $amount=null;
	protected $currency=null;
	protected $description=null;
			
	public function getAmount() { return $this->amount; }
	public function getCurrency() { return $this->currency; }
	public function getDescription() { return $this->description; }
			
	public function __construct($jsondata) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'amount')) {
				$this->amount=$jsondata->amount;
			}
			if (property_exists($jsondata, 'currency')) {
				$this->currency=$jsondata->currency;
			}
			if (property_exists($jsondata, 'description')) {
				$this->description=$jsondata->description;
			}
		}
	}

	public function toString() {
		$buffer='amount='.$this->amount.', currency='.$this->currency.', description='.$this->description;
		return $buffer;
	}
	
}

?>