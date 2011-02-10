<?php 

class InboundSMSMessage {

	protected $dateTime=null;
	protected $destinationAddress=null;
	protected $messageId=null;
	protected $message=null;
	protected $resourceURL=null;
	protected $senderAddress=null;
	
	public function getDateTime() { return $this->dateTime; }
	public function getDestinationAddress() { return $this->destinationAddress; }
	public function getMessageId() { return $this->messageId; }
	public function getMessage() { return $this->message; }
	public function getResourceURL() { return $this->resourceURL; }
	public function getSenderAddress() { return $this->senderAddress; }
	
	public function setDateTime($dateTime) { $this->dateTime=$dateTime; }
	public function setDestinationAddress($destinationAddress) { $this->destinationAddress=$destinationAddress; }
	public function setMessageId($messageId) { $this->messageId=$messageId; }
	public function setMessage($message) { $this->message=$message; }
	public function setResourceURL($resourceURL) { $this->resourceURL=$resourceURL; }
	public function setSenderAddress($senderAddress) { $this->senderAddress=$senderAddress; }

	public function __construct($jsondata=null) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'dateTime')) {
				$this->dateTime=$jsondata->dateTime;
			}
			if (property_exists($jsondata, 'destinationAddress')) {
				$this->destinationAddress=$jsondata->destinationAddress;
			}
			if (property_exists($jsondata, 'messageId')) {
				$this->messageId=$jsondata->messageId;
			}
			if (property_exists($jsondata, 'message')) {
				$this->message=$jsondata->message;
			}
			if (property_exists($jsondata, 'resourceURL')) {
				$this->resourceURL=$jsondata->resourceURL;
			}
			if (property_exists($jsondata, 'senderAddress')) {
				$this->senderAddress=$jsondata->senderAddress;
			}
		}
	}

	public function toString() {
		$buffer='dateTime='.$this->dateTime.', destinationAddress='.$this->destinationAddress.
											', messageId='.$this->messageId.
											', message='.$this->message.
											', resourceURL='.$this->resourceURL.
											', senderAddress='.$this->senderAddress;
		return $buffer;
	}
	
}

?>	
