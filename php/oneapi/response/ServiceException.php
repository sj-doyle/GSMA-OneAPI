<?php

class ServiceException {
	protected $messageId=null;
	protected $text=null;
	protected $variables=null;
			
	public function getMessageId() { return $this->messageId; }
	public function getText() { return $this->text; }
	public function getVariables() { return $this->variables; }
			
	public function setMessageId($messageId) { $this->messageId=(string) $messageId; }
	public function setText($text) { $this->text=(string) $text; }
	public function setVariables($variables) { $this->variables=(string) $variables; }
			
	public function __construct($jsondata) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'messageId')) {
				$this->messageId = $jsondata->messageId;
			}
			if (property_exists($jsondata, 'text')) {
				$this->text = $jsondata->text;
			}
			if (property_exists($jsondata, 'variables')) {
				$this->variables = $jsondata->variables;
			}
		}
	}
	
	public function toString() {
		$buffer='messageId='.$this->messageId.', text='.$this->text.', variables=';
		if (!is_null($this->variables)) {
			if (is_array($this->variables)) {
				for ($i=0; $i<count($this->variables); $i++) {
					if ($i>0) {
						$buffer=$buffer.', ['.$i.'] = '.$this->variables[$i];
					} else {
						$buffer=$buffer.'['.$i.'] = '.$this->variables[$i];
					}
				}
			} else {
				$buffer=$buffer.$this->variables;
			}
		}
		return $buffer;
	}
		
}

?>