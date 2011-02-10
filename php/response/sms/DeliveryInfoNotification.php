<?php 

class DeliveryInfoNotification {

	protected $deliveryInfo=null;
	protected $callbackData=null;
	
	public function getDeliveryInfo() { return $this->deliveryInfo; }
	public function getCallbackData() { return $this->callbackData; }
	
	public function setDeliveryInfo($deliveryInfo) { $this->deliveryInfo=$deliveryInfo; }
	public function setCallbackData($callbackData) { $this->callbackData=$callbackData; }

	public function __construct($jsondata=null) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'deliveryInfo')) {
				$deliveryInfoArray = $jsondata->deliveryInfo;
				if (is_array($deliveryInfoArray) && count($deliveryInfoArray)>0) {
					$processedDeliveryInfoArray = array();
					for ($i=0; $i<count($deliveryInfoArray); $i++) {
						$deliveryInfoElement=$deliveryInfoArray[$i];
						$processedDeliveryInfo=new DeliveryInfo($deliveryInfoElement);
						$processedDeliveryInfoArray[$i] = $processedDeliveryInfo;
					}
					$this->deliveryInfo = $processedDeliveryInfoArray;
				}
			}
			if (property_exists($jsondata, 'callbackData')) {
				$this->callbackData=$jsondata->callbackData;
			}
		}
	}

	public function toString() {
		$buffer='deliveryInfo=';
		if (!is_null($this->deliveryInfo) && is_array($this->deliveryInfo)) {
			$buffer=$buffer.'{';
			for ($i=0; $i<count($this->deliveryInfo); $i++) {
				if ($i>0) {
					$buffer=$buffer.', ['.$i.'] = '.$this->deliveryInfo[$i]->toString();
				} else {
					$buffer=$buffer.'['.$i.'] = '.$this->deliveryInfo[$i]->toString();
				}			
			}
			$buffer=$buffer.'}';
		}
		$buffer=$buffer.', callbackData='.$this->callbackData;
		return $buffer;
	}
		
}

?>	
