
class PolicyException:
	"""defines a policy exception class - returned by the OneAPI server"""
	
	def __init__(self):
		"""Default class constructor"""
		self.messageId=None
		self.text=None
		self.variables=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.messageId=None
		if jsondict is not None and 'messageId' in jsondict and jsondict['messageId'] is not None:
			self.messageId=jsondict['messageId']
		self.text=None
		if jsondict is not None and 'text' in jsondict and jsondict['text'] is not None:
			self.text=jsondict['text']
		self.variables=None
		if jsondict is not None and 'variables' in jsondict and jsondict['variables'] is not None:
			self.variables=list()
			fieldValue=jsondict['variables']
			if isinstance(fieldValue,list):
				for item in fieldValue:
					self.variables.append (item)
			else:
				self.variables.append (fieldValue)
	
	def getMessageId(self):
		"""getter for messageId : identifying message Id"""
		return self.messageId
	
	def setMessageId(self,messageId):
		"""setter for messageId : identifying message Id"""
		self.messageId=messageId
	
	def getText(self):
		"""getter for text : textual version of the error message"""
		return self.text
	
	def setText(self,text):
		"""setter for text : textual version of the error message"""
		self.text=text
	
	def getVariables(self):
		"""getter for variables : variable information to return"""
		return self.variables
	
	def setVariables(self,variables):
		"""setter for variables : variable information to return"""
		self.variables=variables
	
