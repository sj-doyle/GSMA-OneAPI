
class CurrentLocation:
	"""contains the actual location determined for the mobile terminal"""
	
	def __init__(self):
		"""Default class constructor"""
		self.latitude=0.0
		self.longitude=0.0
		self.altitude=0.0
		self.accuracy=0.0
		self.timestamp=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.latitude=0.0
		if jsondict is not None and 'latitude' in jsondict and jsondict['latitude'] is not None:
			self.latitude=float( jsondict['latitude'])
		self.longitude=0.0
		if jsondict is not None and 'longitude' in jsondict and jsondict['longitude'] is not None:
			self.longitude=float( jsondict['longitude'])
		self.altitude=0.0
		if jsondict is not None and 'altitude' in jsondict and jsondict['altitude'] is not None:
			self.altitude=float( jsondict['altitude'])
		self.accuracy=0.0
		if jsondict is not None and 'accuracy' in jsondict and jsondict['accuracy'] is not None:
			self.accuracy=float( jsondict['accuracy'])
		self.timestamp=None
		if jsondict is not None and 'timestamp' in jsondict and jsondict['timestamp'] is not None:
			self.timestamp=jsondict['timestamp']
	
	def getLatitude(self):
		"""getter for latitude : latitude of the mobile terminal"""
		return self.latitude
	
	def setLatitude(self,latitude):
		"""setter for latitude : latitude of the mobile terminal"""
		self.latitude=latitude
	
	def getLongitude(self):
		"""getter for longitude : longitude of the mobile terminal"""
		return self.longitude
	
	def setLongitude(self,longitude):
		"""setter for longitude : longitude of the mobile terminal"""
		self.longitude=longitude
	
	def getAltitude(self):
		"""getter for altitude : altitude of the mobile terminal"""
		return self.altitude
	
	def setAltitude(self,altitude):
		"""setter for altitude : altitude of the mobile terminal"""
		self.altitude=altitude
	
	def getAccuracy(self):
		"""getter for accuracy : accuracy of the position"""
		return self.accuracy
	
	def setAccuracy(self,accuracy):
		"""setter for accuracy : accuracy of the position"""
		self.accuracy=accuracy
	
	def getTimestamp(self):
		"""getter for timestamp : timestamp when position obtained"""
		return self.timestamp
	
	def setTimestamp(self,timestamp):
		"""setter for timestamp : timestamp when position obtained"""
		self.timestamp=timestamp
	
