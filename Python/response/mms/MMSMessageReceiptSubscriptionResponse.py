from response.ResourceReference import ResourceReference

class MMSMessageReceiptSubscriptionResponse:
	"""subscribe to notifications of MMS messages sent to your application"""
	
	def __init__(self):
		"""Default class constructor"""
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.resourceReference=None
	
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
	
	def getResourceReference(self):
		"""getter for resourceReference : the value of the resourceReference field"""
		return self.resourceReference
	
	def setResourceReference(self,resourceReference):
		"""setter for resourceReference : the value of the resourceReference field"""
		self.resourceReference=resourceReference
	
	def setResourceReferenceJSON(self,jsondata):
		self.resourceReference=ResourceReference(jsondata)
	
