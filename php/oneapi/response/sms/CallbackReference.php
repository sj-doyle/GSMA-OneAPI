<?php 

class CallbackReference {

	protected $callbackData=null;
	protected $notifyURL=null;
	
	public function getCallbackData() { return $this->callbackData; }
	public function getNotifyURL() { return $this->notifyURL; }
	
	public function setCallbackData($callbackData) { $this->callbackData=$callbackData; }
	public function setNotifyURL($notifyURL) { $this->notifyURL=$notifyURL; }

	public function __construct($jsondata) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'callbackData')) {
				$this->callbackData = $jsondata->callbackData;
			}
			if (property_exists($jsondata, 'notifyURL')) {
				$this->notifyURL = $jsondata->notifyURL;
			}
		}
	}
	
	public function toString() {
		$buffer='callbackData='.$this->callbackData.', notifyURL='.$this->notifyURL;
		return $buffer;
	}

}


?>	
