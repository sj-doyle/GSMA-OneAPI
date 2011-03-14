from response.mms.InboundMMSMessage import InboundMMSMessage

class InboundMessage:
	"""contains the message details for an MMS message"""
	
	def __init__(self):
		"""Default class constructor"""
		self.dateTime=None
		self.destinationAddress=None
		self.senderAddress=None
		self.inboundMMSMessage=None
		self.messageId=None
		self.resourceURL=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.dateTime=None
		if jsondict is not None and 'dateTime' in jsondict and jsondict['dateTime'] is not None:
			self.dateTime=jsondict['dateTime']
		self.destinationAddress=None
		if jsondict is not None and 'destinationAddress' in jsondict and jsondict['destinationAddress'] is not None:
			self.destinationAddress=jsondict['destinationAddress']
		self.senderAddress=None
		if jsondict is not None and 'senderAddress' in jsondict and jsondict['senderAddress'] is not None:
			self.senderAddress=jsondict['senderAddress']
		self.inboundMMSMessage=None
		if jsondict is not None and 'inboundMMSMessage' in jsondict and jsondict['inboundMMSMessage'] is not None:
			self.inboundMMSMessage=InboundMMSMessage(jsondict['inboundMMSMessage'])
		self.messageId=None
		if jsondict is not None and 'messageId' in jsondict and jsondict['messageId'] is not None:
			self.messageId=jsondict['messageId']
		self.resourceURL=None
		if jsondict is not None and 'resourceURL' in jsondict and jsondict['resourceURL'] is not None:
			self.resourceURL=jsondict['resourceURL']
	
	def getDateTime(self):
		"""getter for dateTime : date and time that the message was sent"""
		return self.dateTime
	
	def setDateTime(self,dateTime):
		"""setter for dateTime : date and time that the message was sent"""
		self.dateTime=dateTime
	
	def getDestinationAddress(self):
		"""getter for destinationAddress : MSISDN or other address of the message recipient"""
		return self.destinationAddress
	
	def setDestinationAddress(self,destinationAddress):
		"""setter for destinationAddress : MSISDN or other address of the message recipient"""
		self.destinationAddress=destinationAddress
	
	def getSenderAddress(self):
		"""getter for senderAddress : MSISDN or other address of the message sender"""
		return self.senderAddress
	
	def setSenderAddress(self,senderAddress):
		"""setter for senderAddress : MSISDN or other address of the message sender"""
		self.senderAddress=senderAddress
	
	def getInboundMMSMessage(self):
		"""getter for inboundMMSMessage : Subject and/or message"""
		return self.inboundMMSMessage
	
	def setInboundMMSMessage(self,inboundMMSMessage):
		"""setter for inboundMMSMessage : Subject and/or message"""
		self.inboundMMSMessage=inboundMMSMessage
	
	def getMessageId(self):
		"""getter for messageId : contains the server message Id field"""
		return self.messageId
	
	def setMessageId(self,messageId):
		"""setter for messageId : contains the server message Id field"""
		self.messageId=messageId
	
	def getResourceURL(self):
		"""getter for resourceURL : specifies the URL to the message on the server"""
		return self.resourceURL
	
	def setResourceURL(self,resourceURL):
		"""setter for resourceURL : specifies the URL to the message on the server"""
		self.resourceURL=resourceURL
	
