
class DeliveryInfo:
	"""contains delivery status information for an MMS sent through the OneAPI server for a single recipient (mobile terminal)"""
	
	def __init__(self):
		"""Default class constructor"""
		self.address=None
		self.deliveryStatus=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.address=None
		if jsondict is not None and 'address' in jsondict and jsondict['address'] is not None:
			self.address=jsondict['address']
		self.deliveryStatus=None
		if jsondict is not None and 'deliveryStatus' in jsondict and jsondict['deliveryStatus'] is not None:
			self.deliveryStatus=jsondict['deliveryStatus']
	
	def getAddress(self):
		"""getter for address : MSISDN or ACR of the mobile terminal being sent a message"""
		return self.address
	
	def setAddress(self,address):
		"""setter for address : MSISDN or ACR of the mobile terminal being sent a message"""
		self.address=address
	
	def getDeliveryStatus(self):
		"""getter for deliveryStatus : status of the message"""
		return self.deliveryStatus
	
	def setDeliveryStatus(self,deliveryStatus):
		"""setter for deliveryStatus : status of the message"""
		self.deliveryStatus=deliveryStatus
	
