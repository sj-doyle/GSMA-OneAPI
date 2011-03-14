from response.payment.AmountReservationTransaction import AmountReservationTransaction

class AmountReservationResponse:
	"""reserve an amount for subsequent charging to an end user's bill"""
	
	def __init__(self):
		"""Default class constructor"""
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.httpResponseCode=0
		self.contentType=None
		self.location=None
		self.amountReservationTransaction=None
	
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
	
	def getAmountReservationTransaction(self):
		"""getter for amountReservationTransaction : the value of the amountReservationTransaction field"""
		return self.amountReservationTransaction
	
	def setAmountReservationTransaction(self,amountReservationTransaction):
		"""setter for amountReservationTransaction : the value of the amountReservationTransaction field"""
		self.amountReservationTransaction=amountReservationTransaction
	
	def setAmountReservationTransactionJSON(self,jsondata):
		self.amountReservationTransaction=AmountReservationTransaction(jsondata)
	
