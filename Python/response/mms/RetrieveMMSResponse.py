from response.mms.InboundMessageList import InboundMessageList

class RetrieveMMSResponse:
	"""retrieve a list of messages sent to your Web application"""
	
	def __init__(self):
		"""Default class constructor"""
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.inboundMessageList=None
	
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
	
	def getInboundMessageList(self):
		"""getter for inboundMessageList : the value of the inboundMessageList field"""
		return self.inboundMessageList
	
	def setInboundMessageList(self,inboundMessageList):
		"""setter for inboundMessageList : the value of the inboundMessageList field"""
		self.inboundMessageList=inboundMessageList
	
	def setInboundMessageListJSON(self,jsondata):
		self.inboundMessageList=InboundMessageList(jsondata)
	
