
class Attachment:
	"""the attachment class is used to hold file data, file name and content type transferred to/from the OneAPI server"""
	
	def __init__(self):
		"""Default class constructor"""
		self.name=None
		self.contentType=None
		self.data=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.name=None
		if jsondict is not None and 'name' in jsondict and jsondict['name'] is not None:
			self.name=jsondict['name']
		self.contentType=None
		if jsondict is not None and 'contentType' in jsondict and jsondict['contentType'] is not None:
			self.contentType=jsondict['contentType']
		self.data=None
		if jsondict is not None and 'data' in jsondict and jsondict['data'] is not None:
			self.data=jsondict['data']
	
	def getName(self):
		"""getter for name : name of the attachment"""
		return self.name
	
	def setName(self,name):
		"""setter for name : name of the attachment"""
		self.name=name
	
	def getContentType(self):
		"""getter for contentType : MIME content type of the attachment"""
		return self.contentType
	
	def setContentType(self,contentType):
		"""setter for contentType : MIME content type of the attachment"""
		self.contentType=contentType
	
	def getData(self):
		"""getter for data : actual data of the attachment"""
		return self.data
	
	def setData(self,data):
		"""setter for data : actual data of the attachment"""
		self.data=data
	
