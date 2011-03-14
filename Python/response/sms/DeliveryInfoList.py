from response.sms.DeliveryInfo import DeliveryInfo

class DeliveryInfoList:
	"""the deliveryInfoList object contains the delivery information for each address that you asked to send the message to, in a deliveryInfo array."""
	
	def __init__(self):
		"""Default class constructor"""
		self.deliveryInfo=None
		self.resourceURL=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.deliveryInfo=None
		if jsondict is not None and 'deliveryInfo' in jsondict and jsondict['deliveryInfo'] is not None:
			self.deliveryInfo=list()
			fieldValue=jsondict['deliveryInfo']
			if isinstance(fieldValue,list):
				for item in fieldValue:
					self.deliveryInfo.append (DeliveryInfo(item))
			else:
				self.deliveryInfo.append (DeliveryInfo(fieldValue))
		self.resourceURL=None
		if jsondict is not None and 'resourceURL' in jsondict and jsondict['resourceURL'] is not None:
			self.resourceURL=jsondict['resourceURL']
	
	def getDeliveryInfo(self):
		"""getter for deliveryInfo : list of delivery information results"""
		return self.deliveryInfo
	
	def setDeliveryInfo(self,deliveryInfo):
		"""setter for deliveryInfo : list of delivery information results"""
		self.deliveryInfo=deliveryInfo
	
	def getResourceURL(self):
		"""getter for resourceURL : specifies the URL to the delivery information request"""
		return self.resourceURL
	
	def setResourceURL(self,resourceURL):
		"""setter for resourceURL : specifies the URL to the delivery information request"""
		self.resourceURL=resourceURL
	
