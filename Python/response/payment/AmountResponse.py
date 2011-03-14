from response.payment.AmountTransaction import AmountTransaction

class AmountResponse:
	"""charge an amount to the end user's bill. Note all parameters are URL encoded by the API functions so this is not needed by the application"""
	
	def __init__(self):
		"""Default class constructor"""
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.amountTransaction=None
	
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
	
	def getAmountTransaction(self):
		"""getter for amountTransaction : the value of the amountTransaction field"""
		return self.amountTransaction
	
	def setAmountTransaction(self,amountTransaction):
		"""setter for amountTransaction : the value of the amountTransaction field"""
		self.amountTransaction=amountTransaction
	
	def setAmountTransactionJSON(self,jsondata):
		self.amountTransaction=AmountTransaction(jsondata)
	
