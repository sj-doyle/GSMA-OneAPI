<?php 

class InboundMessageNotification {

	protected $inboundMessage=null;	
	public function getInboundMessage() { return $this->inboundMessage; }
	public function setInboundMessage($inboundMessage) { $this->inboundMessage=$inboundMessage; }
	
	protected $callbackData=null;
	public function getCallbackData() { return $this->callbackData; }
	public function setCallbackData($callbackData) { $this->callbackData=$callbackData; }
	
	public function __construct($jsondata=null) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'inboundMessage')) {
				$this->inboundMessage=new InboundMessage($jsondata->inboundMessage);
			}
			if (property_exists($jsondata, 'callbackData')) {
				$this->callbackData=$jsondata->callbackData;
			}
		}
	}

	public function toString() {
		$buffer='inboundMessage=';
		if (!is_null($this->inboundMessage)) {
			$buffer=$buffer.'{'.$this->inboundMessage->toString().'}';
		}
		$buffer=$buffer.', callbackData='.$this->callbackData;
		return $buffer;
	}

}

?>
