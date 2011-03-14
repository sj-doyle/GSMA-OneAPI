from response.mms.InboundMMSMessage import InboundMMSMessage

class InboundMessageList:
	"""inboundMMSMessageList object contains an array of inboundSMSMessage objects along with associated information about the request"""
	
	def __init__(self):
		"""Default class constructor"""
		self.inboundMessage=None
		self.numberOfMessagesInThisBatch=0
		self.resourceURL=None
		self.totalNumberOfPendingMessages=0
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.inboundMessage=None
		if jsondict is not None and 'inboundMessage' in jsondict and jsondict['inboundMessage'] is not None:
			self.inboundMessage=list()
			fieldValue=jsondict['inboundMessage']
			if isinstance(fieldValue,list):
				for item in fieldValue:
					self.inboundMessage.append (InboundMMSMessage(item))
			else:
				self.inboundMessage.append (InboundMMSMessage(fieldValue))
		self.numberOfMessagesInThisBatch=0
		if jsondict is not None and 'numberOfMessagesInThisBatch' in jsondict and jsondict['numberOfMessagesInThisBatch'] is not None:
			self.numberOfMessagesInThisBatch=int( jsondict['numberOfMessagesInThisBatch'])
		self.resourceURL=None
		if jsondict is not None and 'resourceURL' in jsondict and jsondict['resourceURL'] is not None:
			self.resourceURL=jsondict['resourceURL']
		self.totalNumberOfPendingMessages=0
		if jsondict is not None and 'totalNumberOfPendingMessages' in jsondict and jsondict['totalNumberOfPendingMessages'] is not None:
			self.totalNumberOfPendingMessages=int( jsondict['totalNumberOfPendingMessages'])
	
	def getInboundMessage(self):
		"""getter for inboundMessage : list of received messages"""
		return self.inboundMessage
	
	def setInboundMessage(self,inboundMessage):
		"""setter for inboundMessage : list of received messages"""
		self.inboundMessage=inboundMessage
	
	def getNumberOfMessagesInThisBatch(self):
		"""getter for numberOfMessagesInThisBatch : the number of messages received in this request"""
		return self.numberOfMessagesInThisBatch
	
	def setNumberOfMessagesInThisBatch(self,numberOfMessagesInThisBatch):
		"""setter for numberOfMessagesInThisBatch : the number of messages received in this request"""
		self.numberOfMessagesInThisBatch=numberOfMessagesInThisBatch
	
	def getResourceURL(self):
		"""getter for resourceURL : specifies the URL to to this response"""
		return self.resourceURL
	
	def setResourceURL(self,resourceURL):
		"""setter for resourceURL : specifies the URL to to this response"""
		self.resourceURL=resourceURL
	
	def getTotalNumberOfPendingMessages(self):
		"""getter for totalNumberOfPendingMessages : the total number of messages held on the server"""
		return self.totalNumberOfPendingMessages
	
	def setTotalNumberOfPendingMessages(self,totalNumberOfPendingMessages):
		"""setter for totalNumberOfPendingMessages : the total number of messages held on the server"""
		self.totalNumberOfPendingMessages=totalNumberOfPendingMessages
	
