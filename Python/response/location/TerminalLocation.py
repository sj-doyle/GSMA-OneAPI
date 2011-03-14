from response.location.CurrentLocation import CurrentLocation
from response.location.ErrorInformation import ErrorInformation

class TerminalLocation:
	"""terminalLocation details the status of a location finding request and the appropriate return data"""
	
	def __init__(self):
		"""Default class constructor"""
		self.address=None
		self.currentLocation=None
		self.errorInformation=None
		self.locationRetrievalStatus=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.address=None
		if jsondict is not None and 'address' in jsondict and jsondict['address'] is not None:
			self.address=jsondict['address']
		self.currentLocation=None
		if jsondict is not None and 'currentLocation' in jsondict and jsondict['currentLocation'] is not None:
			self.currentLocation=CurrentLocation(jsondict['currentLocation'])
		self.errorInformation=None
		if jsondict is not None and 'errorInformation' in jsondict and jsondict['errorInformation'] is not None:
			self.errorInformation=ErrorInformation(jsondict['errorInformation'])
		self.locationRetrievalStatus=None
		if jsondict is not None and 'locationRetrievalStatus' in jsondict and jsondict['locationRetrievalStatus'] is not None:
			self.locationRetrievalStatus=jsondict['locationRetrievalStatus']
	
	def getAddress(self):
		"""getter for address : MSISDN or ACR of the mobile terminal being located"""
		return self.address
	
	def setAddress(self,address):
		"""setter for address : MSISDN or ACR of the mobile terminal being located"""
		self.address=address
	
	def getCurrentLocation(self):
		"""getter for currentLocation : the returned position for the requested mobile terminal"""
		return self.currentLocation
	
	def setCurrentLocation(self,currentLocation):
		"""setter for currentLocation : the returned position for the requested mobile terminal"""
		self.currentLocation=currentLocation
	
	def getErrorInformation(self):
		"""getter for errorInformation : details of error if failed to located the mobile terminal"""
		return self.errorInformation
	
	def setErrorInformation(self,errorInformation):
		"""setter for errorInformation : details of error if failed to located the mobile terminal"""
		self.errorInformation=errorInformation
	
	def getLocationRetrievalStatus(self):
		"""getter for locationRetrievalStatus : result of the positioning request"""
		return self.locationRetrievalStatus
	
	def setLocationRetrievalStatus(self,locationRetrievalStatus):
		"""setter for locationRetrievalStatus : result of the positioning request"""
		self.locationRetrievalStatus=locationRetrievalStatus
	
