
class FormParameter:

	def __init__(self):
		self.key=None
		self.value=None
	
	def __init__(self, key, value):
		self.key=key
		self.value=value
	
	def getKey(self): 
		return self.key
		
	def getValue(self):
		return self.value
	
	def setKey(self,key):
		self.key=key
		
	def setValue(self,value):
		self.value=value
	