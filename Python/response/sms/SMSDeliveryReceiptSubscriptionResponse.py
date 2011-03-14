from response.sms.DeliveryReceiptSubscription import DeliveryReceiptSubscription

class SMSDeliveryReceiptSubscriptionResponse:
	"""subscribe to SMS delivery notifications"""
	
	def __init__(self):
		"""Default class constructor"""
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.deliveryReceiptSubscription=None
	
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
	
	def getDeliveryReceiptSubscription(self):
		"""getter for deliveryReceiptSubscription : the value of the deliveryReceiptSubscription field"""
		return self.deliveryReceiptSubscription
	
	def setDeliveryReceiptSubscription(self,deliveryReceiptSubscription):
		"""setter for deliveryReceiptSubscription : the value of the deliveryReceiptSubscription field"""
		self.deliveryReceiptSubscription=deliveryReceiptSubscription
	
	def setDeliveryReceiptSubscriptionJSON(self,jsondata):
		self.deliveryReceiptSubscription=DeliveryReceiptSubscription(jsondata)
	
