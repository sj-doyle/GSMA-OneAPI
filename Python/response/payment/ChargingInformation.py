
class ChargingInformation:
	"""holds detailed information about a charge being made/ refunded"""
	
	def __init__(self):
		"""Default class constructor"""
		self.amount=0.0
		self.currency=None
		self.description=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.amount=0.0
		if jsondict is not None and 'amount' in jsondict and jsondict['amount'] is not None:
			self.amount=float( jsondict['amount'])
		self.currency=None
		if jsondict is not None and 'currency' in jsondict and jsondict['currency'] is not None:
			self.currency=jsondict['currency']
		self.description=None
		if jsondict is not None and 'description' in jsondict and jsondict['description'] is not None:
			self.description=jsondict['description']
	
	def getAmount(self):
		"""getter for amount : amount charged/refunded"""
		return self.amount
	
	def setAmount(self,amount):
		"""setter for amount : amount charged/refunded"""
		self.amount=amount
	
	def getCurrency(self):
		"""getter for currency : charging currency"""
		return self.currency
	
	def setCurrency(self,currency):
		"""setter for currency : charging currency"""
		self.currency=currency
	
	def getDescription(self):
		"""getter for description : description of charge/refund"""
		return self.description
	
	def setDescription(self,description):
		"""setter for description : description of charge/refund"""
		self.description=description
	
