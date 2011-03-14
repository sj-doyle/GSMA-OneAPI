from response.sms.InboundSMSMessage import InboundSMSMessage

class InboundSMSMessageNotification:
	"""the inboundMessageNotification object includes any callbackData and an inboundSMSMessage array with message detail elements"""
	
	def __init__(self):
		"""Default class constructor"""
		self.callbackData=None
		self.inboundSMSMessage=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.callbackData=None
		if jsondict is not None and 'callbackData' in jsondict and jsondict['callbackData'] is not None:
			self.callbackData=jsondict['callbackData']
		self.inboundSMSMessage=None
		if jsondict is not None and 'inboundSMSMessage' in jsondict and jsondict['inboundSMSMessage'] is not None:
			self.inboundSMSMessage=InboundSMSMessage(jsondict['inboundSMSMessage'])
	
	def getCallbackData(self):
		"""getter for callbackData : specifies the information that will accompany a notification"""
		return self.callbackData
	
	def setCallbackData(self,callbackData):
		"""setter for callbackData : specifies the information that will accompany a notification"""
		self.callbackData=callbackData
	
	def getInboundSMSMessage(self):
		"""getter for inboundSMSMessage : specifies the message details including sender, recipient and message"""
		return self.inboundSMSMessage
	
	def setInboundSMSMessage(self,inboundSMSMessage):
		"""setter for inboundSMSMessage : specifies the message details including sender, recipient and message"""
		self.inboundSMSMessage=inboundSMSMessage
	
