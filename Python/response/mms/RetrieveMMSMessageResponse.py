from response.mms.InboundMessage import InboundMessage

class RetrieveMMSMessageResponse:
	"""retrieve the full MMS including attachments"""
	
	def __init__(self):
		"""Default class constructor"""
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.inboundMessage=None
	
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
	
	def getInboundMessage(self):
		"""getter for inboundMessage : the value of the inboundMessage field"""
		return self.inboundMessage
	
	def setInboundMessage(self,inboundMessage):
		"""setter for inboundMessage : the value of the inboundMessage field"""
		self.inboundMessage=inboundMessage
	
	def setInboundMessageJSON(self,jsondata):
		self.inboundMessage=InboundMessage(jsondata)
	
	def getAttachment(self):
		"""getter for attachment : an array of attachments (files)"""
		return self.attachment
	
	def setAttachment(self,attachment):
		"""setter for attachment : an array of attachments (files)"""
		self.attachment=attachment
	
