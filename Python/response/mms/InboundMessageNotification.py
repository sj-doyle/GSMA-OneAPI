from response.mms.InboundMessage import InboundMessage

class InboundMessageNotification:
	"""the inboundMessageNotification object includes any callbackData and an inboundMMSMessage array with message detail elements"""
	
	def __init__(self):
		"""Default class constructor"""
		self.callbackData=None
		self.inboundMessage=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.callbackData=None
		if jsondict is not None and 'callbackData' in jsondict and jsondict['callbackData'] is not None:
			self.callbackData=jsondict['callbackData']
		self.inboundMessage=None
		if jsondict is not None and 'inboundMessage' in jsondict and jsondict['inboundMessage'] is not None:
			self.inboundMessage=InboundMessage(jsondict['inboundMessage'])
	
	def getCallbackData(self):
		"""getter for callbackData : specifies the information that will accompany a notification"""
		return self.callbackData
	
	def setCallbackData(self,callbackData):
		"""setter for callbackData : specifies the information that will accompany a notification"""
		self.callbackData=callbackData
	
	def getInboundMessage(self):
		"""getter for inboundMessage : specifies the message details including sender, recipient"""
		return self.inboundMessage
	
	def setInboundMessage(self,inboundMessage):
		"""setter for inboundMessage : specifies the message details including sender, recipient"""
		self.inboundMessage=inboundMessage
	
