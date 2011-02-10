<?php 

class DeliveryInfo {

	protected $address=null;
	protected $deliveryStatus=null;
	
	public function getAddress() { return $this->address; }
	public function getDeliveryStatus() { return $this->deliveryStatus; }
	
	public function setAddress($address) { $this->address=$address; }
	public function setDeliveryStatus($deliveryStatus) { $this->deliveryStatus=$deliveryStatus; }

	public function __construct($jsondata) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'address')) {
				$this->address = $jsondata->address;
			}
			if (property_exists($jsondata, 'deliveryStatus')) {
				$this->deliveryStatus = $jsondata->deliveryStatus;
			}
		}
	}

	public function toString() {
		$buffer='address='.$this->address.', deliveryStatus='.$this->deliveryStatus;
		return $buffer;
	}
			
}

?>	
