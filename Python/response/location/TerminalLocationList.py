from response.location.TerminalLocation import TerminalLocation

class TerminalLocationList:
	"""holds an array of one or more mobile terminal location responses"""
	
	def __init__(self):
		"""Default class constructor"""
		self.terminalLocation=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.terminalLocation=None
		if jsondict is not None and 'terminalLocation' in jsondict and jsondict['terminalLocation'] is not None:
			self.terminalLocation=list()
			fieldValue=jsondict['terminalLocation']
			if isinstance(fieldValue,list):
				for item in fieldValue:
					self.terminalLocation.append (TerminalLocation(item))
			else:
				self.terminalLocation.append (TerminalLocation(fieldValue))
	
	def getTerminalLocation(self):
		"""getter for terminalLocation : list of terminal location results"""
		return self.terminalLocation
	
	def setTerminalLocation(self,terminalLocation):
		"""setter for terminalLocation : list of terminal location results"""
		self.terminalLocation=terminalLocation
	
