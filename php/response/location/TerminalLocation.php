<?php 

class TerminalLocation {

	protected $address=null;
	protected $locationRetrievalStatus=null;		

	public function getAddress() { return $this->address; }
	public function setAddress($address) { $this->address=(string) $address; }
	
	public function getLocationRetrievalStatus() { return $this->locationRetrievalStatus; }
	public function setLocationRetrievalStatus($locationRetrievalStatus) { $this->locationRetrievalStatus=(string) $locationRetrievalStatus; }
	
	protected $errorInformation=null;
	public function getErrorInformation() { return $this->errorInformation; }
	public function setErrorInformation(ErrorInformation $errorInformation) { $this->errorInformation = $errorInformation; }
	
	protected $currentLocation=null;
	
	public function getCurrentLocation() { return $this->currentLocation; }
	public function setCurrentLocation(CurrentLocation $currentLocation) { $this->currentLocation=$currentLocation; }

	public function __construct($jsondata=null) {
		error_log('TerminalLocation :: '.print_r($jsondata, true));
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'address')) {
				$this->address=$jsondata->address;
			}
			if (property_exists($jsondata, 'locationRetrievalStatus')) {
				$this->locationRetrievalStatus=$jsondata->locationRetrievalStatus;
			}
			if (property_exists($jsondata, 'currentLocation')) {
				$currentLocation = new CurrentLocation($jsondata->currentLocation);
				$this->currentLocation = $currentLocation;
			}
			if (property_exists($jsondata, 'errorInformation')) {
				$errorInformation=new ErrorInformation($jsondata->errorInformation);
				$this->errorInformation = $errorInformation;
			}
		}
	}
	
	public function toString() {
		$buffer='address='.$this->address.', locationRetrievalStatus='.$this->locationRetrievalStatus.', errorInformation=';
		if (!is_null($this->errorInformation)) {	
			$buffer=$buffer.'{'.$this->errorInformation->toString().'}';
		}
		$buffer=$buffer.', currentLocation=';
		if (!is_null($this->currentLocation)) {	
			$buffer=$buffer.'{'.$this->currentLocation->toString().'}';
		}
		return $buffer;
	}
	
}

?>