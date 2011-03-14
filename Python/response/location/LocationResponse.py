from response.location.TerminalLocationList import TerminalLocationList

class LocationResponse:
	"""get the location of a single mobile terminal"""
	
	def __init__(self):
		"""Default class constructor"""
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.terminalLocationList=None
	
	def getHTTPResponseCode(self):
		"""getter for httpResponseCode : the numeric status code result of the HTTP response"""
		return self.httpResponseCode
	
	def setHTTPResponseCode(self,httpResponseCode):
		"""setter for httpResponseCode : the numeric status code result of the HTTP response"""
		self.httpResponseCode=httpResponseCode
	
	def getContentType(self):
		"""getter for contentType : the resulting MIME content type of the HTTP response"""
		return self.contentType
	
	def setContentType(self,contentType):
		"""setter for contentType : the resulting MIME content type of the HTTP response"""
		self.contentType=contentType
	
	def getLocation(self):
		"""getter for location : the contents of the HTTP response location header"""
		return self.location
	
	def setLocation(self,location):
		"""setter for location : the contents of the HTTP response location header"""
		self.location=location
	
	def getTerminalLocationList(self):
		"""getter for terminalLocationList : the value of the terminalLocationList field"""
		return self.terminalLocationList
	
	def setTerminalLocationList(self,terminalLocationList):
		"""setter for terminalLocationList : the value of the terminalLocationList field"""
		self.terminalLocationList=terminalLocationList
	
	def setTerminalLocationListJSON(self,jsondata):
		self.terminalLocationList=TerminalLocationList(jsondata)
	
