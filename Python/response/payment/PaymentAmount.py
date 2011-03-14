
class PaymentAmount:
	"""holds running information about the charge applied to the mobile user account / amount reserved"""
	
	def __init__(self):
		"""Default class constructor"""
		self.amountReserved=0.0
		self.chargingInformation=None
		self.totalAmountCharged=0.0
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.amountReserved=0.0
		if jsondict is not None and 'amountReserved' in jsondict and jsondict['amountReserved'] is not None:
			self.amountReserved=float( jsondict['amountReserved'])
		self.chargingInformation=None
		if jsondict is not None and 'chargingInformation' in jsondict and jsondict['chargingInformation'] is not None:
			self.chargingInformation=ChargingInformation(jsondict['chargingInformation'])
		self.totalAmountCharged=0.0
		if jsondict is not None and 'totalAmountCharged' in jsondict and jsondict['totalAmountCharged'] is not None:
			self.totalAmountCharged=float( jsondict['totalAmountCharged'])
	
	def getAmountReserved(self):
		"""getter for amountReserved : amount reserved"""
		return self.amountReserved
	
	def setAmountReserved(self,amountReserved):
		"""setter for amountReserved : amount reserved"""
		self.amountReserved=amountReserved
	
	def getChargingInformation(self):
		"""getter for chargingInformation : the charging information (amount, currency, description)"""
		return self.chargingInformation
	
	def setChargingInformation(self,chargingInformation):
		"""setter for chargingInformation : the charging information (amount, currency, description)"""
		self.chargingInformation=chargingInformation
	
	def getTotalAmountCharged(self):
		"""getter for totalAmountCharged : total amount charged to mobile user"""
		return self.totalAmountCharged
	
	def setTotalAmountCharged(self,totalAmountCharged):
		"""setter for totalAmountCharged : total amount charged to mobile user"""
		self.totalAmountCharged=totalAmountCharged
	
