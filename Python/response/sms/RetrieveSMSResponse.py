from response.sms.InboundSMSMessageList import InboundSMSMessageList

class RetrieveSMSResponse:
	"""Retrieve messages sent to your Web application"""
	
	def __init__(self):
		"""Default class constructor"""
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.inboundSMSMessageList=None
	
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
	
	def getInboundSMSMessageList(self):
		"""getter for inboundSMSMessageList : the value of the inboundSMSMessageList field"""
		return self.inboundSMSMessageList
	
	def setInboundSMSMessageList(self,inboundSMSMessageList):
		"""setter for inboundSMSMessageList : the value of the inboundSMSMessageList field"""
		self.inboundSMSMessageList=inboundSMSMessageList
	
	def setInboundSMSMessageListJSON(self,jsondata):
		self.inboundSMSMessageList=InboundSMSMessageList(jsondata)
	
