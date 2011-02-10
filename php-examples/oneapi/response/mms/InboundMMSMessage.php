<?php

class InboundMMSMessage {
	
	protected $subject=null;
	public function getSubject() { return $this->subject; }
	public function setSubject($subject) { $this->subject=$subject; }

	protected $message=null;
	public function getMessage() { return $this->message; }
	public function setMessage($message) { $this->message=$message; }
	
	public function __construct($jsondata=null) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'subject')) {
				$this->subject=$jsondata->subject;
			}
			if (property_exists($jsondata, 'message')) {
				$this->message=$jsondata->message;
			}
		}
	}
	
	public function toString() {
		$buffer='subject='.$this->subject.', message='.$this->message;
		return $buffer;
	}
	
}

?>
