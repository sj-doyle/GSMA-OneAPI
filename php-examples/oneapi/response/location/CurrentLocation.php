<?php
	
class CurrentLocation {
	protected $accuracy=0.0;
	protected $altitude=0.0;
	protected $latitude=0.0;
	protected $longitude=0.0;
	protected $timestamp=null;
	
	public function getAccuracy() { return $this->accuracy; }
	public function getAltitude() { return $this->altitude; }
	public function getLatitude() { return $this->latitude; }
	public function getLongitude() { return $this->longitude; }
	public function getTimestamp() { return $this->timestamp; }
	
	public function setAccuracy($accuracy) { $this->accuracy=(double) $accuracy; }
	public function setAltitude($altitude) { $this->altitude=(double) $altitude; }
	public function setLatitude($latitude) { $this->latitude=(double) $latitude; }
	public function setLongitude($longitude) { $this->longitude=(double) $longitude; }
	public function setTimestamp($timestamp) { $this->timestamp=(string) $timestamp; }
	
	public function __construct($jsondata=null) {
		if (!is_null($jsondata)) {
			if (property_exists($jsondata, 'accuracy')) {
				$this->accuracy=$jsondata->accuracy;
			}
			if (property_exists($jsondata, 'altitude')) {
				$this->altitude=$jsondata->altitude;
			}
			if (property_exists($jsondata, 'latitude')) {
				$this->latitude=$jsondata->latitude;
			}
			if (property_exists($jsondata, 'longitude')) {
				$this->longitude=$jsondata->longitude;
			}
			if (property_exists($jsondata, 'timestamp')) {
				$this->timestamp=$jsondata->timestamp;
			}
		}
	}
	
	public function toString() {
		$buffer='accuracy='.$this->accuracy.', altitude='.$this->altitude.', latitude='.$this->latitude.' longitude='.$this->longitude.' timestamp='.$this->timestamp;
		return $buffer;
	}
	
}

?>	
