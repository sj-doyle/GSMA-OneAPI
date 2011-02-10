<?php 

class InboundSMSMessageNotification {

	protected $inboundSMSMessage=null;
	
	public function getInboundSMSMessage() { return $this->inboundSMSMessage; }
	public function setInboundSMSMessage($inboundSMSMessage) { $this->inboundSMSMessage=$inboundSMSMessage; }
	
	protected $callbackData=null;
	public function getCallbackData() { return $this->callbackData; }
	public function setCallbackData($callbackData) { $this->callbackData=$callbackData; }

	public function __construct($jsondata=null) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'inboundSMSMessage')) {
				$this->inboundSMSMessage=new InboundSMSMessage($jsondata->inboundSMSMessage);
			}
			if (property_exists($jsondata, 'callbackData')) {
				$this->callbackData=$jsondata->callbackData;
			}
		}
	}
	
	public function toString() {
		$buffer='inboundSMSMessage=';
		if (!is_null($this->inboundSMSMessage) ) {
			$buffer=$buffer.'{'.$this->inboundSMSMessage->toString().'}';
		}
		$buffer=$buffer.', callbackData='.$this->callbackData;
		return $buffer;
	}
	
}

?>	
