<?php
		
class ResourceReference {

	protected $resourceURL=null;
	
	public function getResourceURL() { return $this->resourceURL; }
	public function setResourceURL($resourceURL) { $this->resourceURL=$resourceURL; }

	public function __construct($jsondata) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'resourceURL')) {
				$this->resourceURL = $jsondata->resourceURL;
			}
		}
	}

	public function toString() {
		$buffer='resourceURL='.$this->resourceURL;
		return $buffer;
	}
	
}

?>
