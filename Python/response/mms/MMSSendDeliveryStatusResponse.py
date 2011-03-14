from response.mms.DeliveryInfoList import DeliveryInfoList

class MMSSendDeliveryStatusResponse:
	"""query the delivery status of an MMS"""
	
	def __init__(self):
		"""Default class constructor"""
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.deliveryInfoList=None
	
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
	
	def getDeliveryInfoList(self):
		"""getter for deliveryInfoList : the value of the deliveryInfoList field"""
		return self.deliveryInfoList
	
	def setDeliveryInfoList(self,deliveryInfoList):
		"""setter for deliveryInfoList : the value of the deliveryInfoList field"""
		self.deliveryInfoList=deliveryInfoList
	
	def setDeliveryInfoListJSON(self,jsondata):
		self.deliveryInfoList=DeliveryInfoList(jsondata)
	
