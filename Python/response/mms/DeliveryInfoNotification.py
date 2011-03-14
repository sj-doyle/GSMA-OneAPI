from response.mms.DeliveryInfo import DeliveryInfo

class DeliveryInfoNotification:
	"""deliveryInfoNotification contains address and deliveryStatus pairs for each user you have sent the SMS to"""
	
	def __init__(self):
		"""Default class constructor"""
		self.callbackData=None
		self.deliveryInfo=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.callbackData=None
		if jsondict is not None and 'callbackData' in jsondict and jsondict['callbackData'] is not None:
			self.callbackData=jsondict['callbackData']
		self.deliveryInfo=None
		if jsondict is not None and 'deliveryInfo' in jsondict and jsondict['deliveryInfo'] is not None:
			self.deliveryInfo=DeliveryInfo(jsondict['deliveryInfo'])
	
	def getCallbackData(self):
		"""getter for callbackData : specifies the information that will accompany a notification"""
		return self.callbackData
	
	def setCallbackData(self,callbackData):
		"""setter for callbackData : specifies the information that will accompany a notification"""
		self.callbackData=callbackData
	
	def getDeliveryInfo(self):
		"""getter for deliveryInfo : specifies the MSISDN/ ACR of the mobile terminal and the delivery status"""
		return self.deliveryInfo
	
	def setDeliveryInfo(self,deliveryInfo):
		"""setter for deliveryInfo : specifies the MSISDN/ ACR of the mobile terminal and the delivery status"""
		self.deliveryInfo=deliveryInfo
	
