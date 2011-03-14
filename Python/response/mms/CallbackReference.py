
class CallbackReference:
	"""provides the URL and data to be sent from the OneAPI server when a notification is delivered to the subscribing application"""
	
	def __init__(self):
		"""Default class constructor"""
		self.notifyURL=None
		self.callbackData=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.notifyURL=None
		if jsondict is not None and 'notifyURL' in jsondict and jsondict['notifyURL'] is not None:
			self.notifyURL=jsondict['notifyURL']
		self.callbackData=None
		if jsondict is not None and 'callbackData' in jsondict and jsondict['callbackData'] is not None:
			self.callbackData=jsondict['callbackData']
	
	def getNotifyURL(self):
		"""getter for notifyURL : specifies the URL that notifications are sent to"""
		return self.notifyURL
	
	def setNotifyURL(self,notifyURL):
		"""setter for notifyURL : specifies the URL that notifications are sent to"""
		self.notifyURL=notifyURL
	
	def getCallbackData(self):
		"""getter for callbackData : specifies the information that will accompany a notification"""
		return self.callbackData
	
	def setCallbackData(self,callbackData):
		"""setter for callbackData : specifies the information that will accompany a notification"""
		self.callbackData=callbackData
	
