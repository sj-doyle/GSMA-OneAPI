from response.sms.CallbackReference import CallbackReference

class DeliveryReceiptSubscription:
	"""confirms the detail of a subscription for delivery receipts with the OneAPI server"""
	
	def __init__(self):
		"""Default class constructor"""
		self.callbackReference=None
		self.resourceURL=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.callbackReference=None
		if jsondict is not None and 'callbackReference' in jsondict and jsondict['callbackReference'] is not None:
			self.callbackReference=CallbackReference(jsondict['callbackReference'])
		self.resourceURL=None
		if jsondict is not None and 'resourceURL' in jsondict and jsondict['resourceURL'] is not None:
			self.resourceURL=jsondict['resourceURL']
	
	def getCallbackReference(self):
		"""getter for callbackReference : callback reference"""
		return self.callbackReference
	
	def setCallbackReference(self,callbackReference):
		"""setter for callbackReference : callback reference"""
		self.callbackReference=callbackReference
	
	def getResourceURL(self):
		"""getter for resourceURL : specifies the URL to the delivery receipt subscription"""
		return self.resourceURL
	
	def setResourceURL(self,resourceURL):
		"""setter for resourceURL : specifies the URL to the delivery receipt subscription"""
		self.resourceURL=resourceURL
	
