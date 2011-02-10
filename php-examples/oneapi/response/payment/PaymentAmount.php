<?php

class PaymentAmount {
	protected $chargingInformation=null;
	protected $totalAmountCharged=0.0;
	protected $amountReserved=0.0;
			
	public function getChargingInformation() { return $this->chargingInformation; }
	public function gettotalAmountCharged() { return $this->totalAmountCharged; }
	public function getAmountReserved() { return $this->amountReserved; }
			
	public function __construct($jsondata) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'chargingInformation')) {
				$chargingInformation = new ChargingInformation($jsondata->chargingInformation);
				$this->chargingInformation = $chargingInformation;
			}
			if (property_exists($jsondata, 'totalAmountCharged')) {
				$this->totalAmountCharged=$jsondata->totalAmountCharged;
			}
			if (property_exists($jsondata, 'amountReserved')) {
				$this->amountReserved=$jsondata->amountReserved;
			}
		}
	}

	public function toString() {
		$buffer='chargingInformation=';
		if (!is_null($this->chargingInformation)) {
			$buffer=$buffer.'{'.$this->chargingInformation->toString().'}';
		}
		$buffer=$buffer.', totalAmountCharged='.$this->totalAmountCharged.', amountReserved='.$this->amountReserved;
		return $buffer;
	}
	
}


?>