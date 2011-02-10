<?php 

class DeliveryReceiptSubscription {

	protected $callbackReference=null;
	protected $resourceURL=null;
	
	public function getCallbackReference() { return $this->callbackReference; }
	public function getResourceURL() { return $this->resourceURL; }
	
	public function setCallbackReference($callbackReference) { $this->callbackReference=$callbackReference; }
	public function setResourceURL($resourceURL) { $this->resourceURL=$resourceURL; }

	public function __construct($jsondata) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'callbackReference')) {
				$this->callbackReference = new CallbackReference($jsondata->callbackReference);
			}
			if (property_exists($jsondata, 'resourceURL')) {
				$this->resourceURL = $jsondata->resourceURL;
			}
		}
	}

	public function toString() {
		$buffer='callbackReference=';
		if (!is_null($this->callbackReference)) {
			$buffer=$buffer.'{'.$this->callbackReference->toString().'}';
		}		
		$buffer=$buffer.', resourceURL='.$this->resourceURL;
		return $buffer;
	}

}


?>	
