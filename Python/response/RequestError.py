from response.ServiceException import ServiceException
from response.PolicyException import PolicyException

class RequestError:
	"""defines an error response from the OneAPI server"""
	
	def __init__(self):
		"""Default class constructor"""
		self.serviceException=None
		self.policyException=None
	
	def __init__(self, jsondict):
		"""Class constructor that will create an instance initialised from a parsed JSON data block"""
		self.serviceException=None
		if jsondict is not None and 'serviceException' in jsondict and jsondict['serviceException'] is not None:
			self.serviceException=ServiceException(jsondict['serviceException'])
		self.policyException=None
		if jsondict is not None and 'policyException' in jsondict and jsondict['policyException'] is not None:
			self.policyException=PolicyException(jsondict['policyException'])
	
	def getServiceException(self):
		"""getter for serviceException : details of a service exception"""
		return self.serviceException
	
	def setServiceException(self,serviceException):
		"""setter for serviceException : details of a service exception"""
		self.serviceException=serviceException
	
	def getPolicyException(self):
		"""getter for policyException : details of a service exception"""
		return self.policyException
	
	def setPolicyException(self,policyException):
		"""setter for policyException : details of a service exception"""
		self.policyException=policyException
	
