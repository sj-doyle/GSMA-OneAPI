
class ResourceReference:
	"""a generic reference to a OneAPI server resource created by the corresponding request"""
	
	def __init__(self):
		"""Default class constructor"""
		self.resourceURL=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.resourceURL=None
		if jsondict is not None and 'resourceURL' in jsondict and jsondict['resourceURL'] is not None:
			self.resourceURL=jsondict['resourceURL']
	
	def getResourceURL(self):
		"""getter for resourceURL : specifies the URL to a server resource"""
		return self.resourceURL
	
	def setResourceURL(self,resourceURL):
		"""setter for resourceURL : specifies the URL to a server resource"""
		self.resourceURL=resourceURL
	
