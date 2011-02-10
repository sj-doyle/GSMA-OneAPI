<?php 

class TerminalLocationList {

	protected $terminalLocation=null;
		
	public function setTerminalLocation($terminalLocation) { $this->terminalLocation=$terminalLocation; }
	public function getTerminalLocation() { return $this->terminalLocation; }

	public function __construct($jsondata=null) {
		if (!is_null($jsondata) && property_exists($jsondata, 'terminalLocation')) {
			error_log('TerminalLocationList :: have terminalLocation');
			$terminalLocationArray = $jsondata->terminalLocation;
			if (is_array($terminalLocationArray) && count($terminalLocationArray)>0) {
				$processedTerminalLocationArray = array();
				for ($i=0; $i<count($terminalLocationArray); $i++) {
					$terminalLocationElement=$terminalLocationArray[$i];
					error_log('Processing Terminal Location ['.$i.'] '.print_r($terminalLocationElement,true));
					$processedTerminalLocation=new TerminalLocation($terminalLocationElement);
					$processedTerminalLocationArray[$i] = $processedTerminalLocation;
				}
				$this->terminalLocation = $processedTerminalLocationArray;
			}
		}
	}
	
	public function toString() {
		$buffer='terminalLocation=';
		if (!is_null($this->terminalLocation) && is_array($this->terminalLocation)) {
			for ($i=0; $i<count($this->terminalLocation); $i++) {
				if ($i>0) {
					$buffer=$buffer.', ['.$i.'] = '.$this->terminalLocation[$i]->toString();
				} else {
					$buffer=$buffer.'['.$i.'] = '.$this->terminalLocation[$i]->toString();
				}			
			}
		}
		return $buffer;
	}
	
	
}

?>
