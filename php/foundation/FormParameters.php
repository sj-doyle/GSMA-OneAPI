<?php

class FormParameters {
	protected $parameterSet=null;
	protected $arraylength=0;
	
	public function __construct () {}
	
	public function put($key, $value) {
		if (is_null($this->parameterSet)) {
			$this->parameterSet=array();
			$this->arraylength=0;
		}
		$fp=new FormParameter($key, $value);
		$this->parameterSet[$this->arraylength]=$fp;
		++$this->arraylength;
	}
	
	public function getParameterSet() { return $this->parameterSet; }
	
	public function encodeParameters() {
		$data="";
		if ($this->parameterSet && count($this->parameterSet)>0) {
			for ($i=0; $i<count($this->parameterSet); $i++) {
				$param=$this->parameterSet[$i];
				$key=$param->getKey();
				$value=$param->getValue();
				if (isset($param) && isset($key) && isset($value)) { 
					if ($i>0) $data=$data."&\r\n";
					$data=$data.$key."=".urlencode($value);
				}
			}
		}
		return $data;
	}			
}

?>
