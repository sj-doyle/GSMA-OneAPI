
class InboundMMSMessage:
	"""contains the message body and subject for an MMS message"""
	
	def __init__(self):
		"""Default class constructor"""
		self.subject=None
		self.message=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.subject=None
		if jsondict is not None and 'subject' in jsondict and jsondict['subject'] is not None:
			self.subject=jsondict['subject']
		self.message=None
		if jsondict is not None and 'message' in jsondict and jsondict['message'] is not None:
			self.message=jsondict['message']
	
	def getSubject(self):
		"""getter for subject : the message subject"""
		return self.subject
	
	def setSubject(self,subject):
		"""setter for subject : the message subject"""
		self.subject=subject
	
	def getMessage(self):
		"""getter for message : the message body"""
		return self.message
	
	def setMessage(self,message):
		"""setter for message : the message body"""
		self.message=message
	
