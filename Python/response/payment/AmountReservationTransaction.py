
class AmountReservationTransaction:
	"""class containing the payment details response received from the OneAPI server when a more complex payment reservation type transaction is applied to the mobile user account"""
	
	def __init__(self):
		"""Default class constructor"""
		self.clientCorrelator=None
		self.endUserId=None
		self.paymentAmount=None
		self.referenceCode=None
		self.referenceSequence=0
		self.resourceURL=None
		self.serverReferenceCode=None
		self.transactionStatus=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.clientCorrelator=None
		if jsondict is not None and 'clientCorrelator' in jsondict and jsondict['clientCorrelator'] is not None:
			self.clientCorrelator=jsondict['clientCorrelator']
		self.endUserId=None
		if jsondict is not None and 'endUserId' in jsondict and jsondict['endUserId'] is not None:
			self.endUserId=jsondict['endUserId']
		self.paymentAmount=None
		if jsondict is not None and 'paymentAmount' in jsondict and jsondict['paymentAmount'] is not None:
			self.paymentAmount=PaymentAmount(jsondict['paymentAmount'])
		self.referenceCode=None
		if jsondict is not None and 'referenceCode' in jsondict and jsondict['referenceCode'] is not None:
			self.referenceCode=jsondict['referenceCode']
		self.referenceSequence=0
		if jsondict is not None and 'referenceSequence' in jsondict and jsondict['referenceSequence'] is not None:
			self.referenceSequence=int( jsondict['referenceSequence'])
		self.resourceURL=None
		if jsondict is not None and 'resourceURL' in jsondict and jsondict['resourceURL'] is not None:
			self.resourceURL=jsondict['resourceURL']
		self.serverReferenceCode=None
		if jsondict is not None and 'serverReferenceCode' in jsondict and jsondict['serverReferenceCode'] is not None:
			self.serverReferenceCode=jsondict['serverReferenceCode']
		self.transactionStatus=None
		if jsondict is not None and 'transactionStatus' in jsondict and jsondict['transactionStatus'] is not None:
			self.transactionStatus=jsondict['transactionStatus']
	
	def getClientCorrelator(self):
		"""getter for clientCorrelator : uniquely identifies this charging request"""
		return self.clientCorrelator
	
	def setClientCorrelator(self,clientCorrelator):
		"""setter for clientCorrelator : uniquely identifies this charging request"""
		self.clientCorrelator=clientCorrelator
	
	def getEndUserId(self):
		"""getter for endUserId : MSISDN or ACR of the mobile user being charged"""
		return self.endUserId
	
	def setEndUserId(self,endUserId):
		"""setter for endUserId : MSISDN or ACR of the mobile user being charged"""
		self.endUserId=endUserId
	
	def getPaymentAmount(self):
		"""getter for paymentAmount : detailed payment information for the charge/ refund"""
		return self.paymentAmount
	
	def setPaymentAmount(self,paymentAmount):
		"""setter for paymentAmount : detailed payment information for the charge/ refund"""
		self.paymentAmount=paymentAmount
	
	def getReferenceCode(self):
		"""getter for referenceCode : reference (supplier) for this request"""
		return self.referenceCode
	
	def setReferenceCode(self,referenceCode):
		"""setter for referenceCode : reference (supplier) for this request"""
		self.referenceCode=referenceCode
	
	def getReferenceSequence(self):
		"""getter for referenceSequence : reference sequence for the charging operation (starting from 1)"""
		return self.referenceSequence
	
	def setReferenceSequence(self,referenceSequence):
		"""setter for referenceSequence : reference sequence for the charging operation (starting from 1)"""
		self.referenceSequence=referenceSequence
	
	def getResourceURL(self):
		"""getter for resourceURL : specifies the URL to this charging request on the server"""
		return self.resourceURL
	
	def setResourceURL(self,resourceURL):
		"""setter for resourceURL : specifies the URL to this charging request on the server"""
		self.resourceURL=resourceURL
	
	def getServerReferenceCode(self):
		"""getter for serverReferenceCode : reference (server) for this request"""
		return self.serverReferenceCode
	
	def setServerReferenceCode(self,serverReferenceCode):
		"""setter for serverReferenceCode : reference (server) for this request"""
		self.serverReferenceCode=serverReferenceCode
	
	def getTransactionStatus(self):
		"""getter for transactionStatus : status for this charge/refund transaction"""
		return self.transactionStatus
	
	def setTransactionStatus(self,transactionStatus):
		"""setter for transactionStatus : status for this charge/refund transaction"""
		self.transactionStatus=transactionStatus
	
