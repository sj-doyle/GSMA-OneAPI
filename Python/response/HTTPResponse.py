
class HTTPResponse:
	"""defines a generic HTTP response class"""
	
	def __init__(self):
		"""Default class constructor"""
		self.code=0
		self.content=None
		self.contentType=None
		self.location=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.code=0
		if jsondict is not None and 'code' in jsondict and jsondict['code'] is not None:
			self.code=int( jsondict['code'])
		self.content=None
		if jsondict is not None and 'content' in jsondict and jsondict['content'] is not None:
			self.content=jsondict['content']
		self.contentType=None
		if jsondict is not None and 'contentType' in jsondict and jsondict['contentType'] is not None:
			self.contentType=jsondict['contentType']
		self.location=None
		if jsondict is not None and 'location' in jsondict and jsondict['location'] is not None:
			self.location=jsondict['location']
	
	def getCode(self):
		"""getter for code : HTTP Response Code"""
		return self.code
	
	def setCode(self,code):
		"""setter for code : HTTP Response Code"""
		self.code=code
	
	def getContent(self):
		"""getter for content : Response Content Data"""
		return self.content
	
	def setContent(self,content):
		"""setter for content : Response Content Data"""
		self.content=content
	
	def getContentType(self):
		"""getter for contentType : Response Content Type"""
		return self.contentType
	
	def setContentType(self,contentType):
		"""setter for contentType : Response Content Type"""
		self.contentType=contentType
	
	def getLocation(self):
		"""getter for location : Response Location"""
		return self.location
	
	def setLocation(self,location):
		"""setter for location : Response Location"""
		self.location=location
	
