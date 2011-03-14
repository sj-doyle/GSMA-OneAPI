from response.location.CurrentLocation import CurrentLocation
from response.ServiceException import ServiceException
from response.PolicyException import PolicyException
from response.location.ErrorInformation import ErrorInformation
from response.location.TerminalLocation import TerminalLocation
from response.location.TerminalLocationList import TerminalLocationList
from response.location.LocationResponse import LocationResponse
import json
from foundation.FormParameters import FormParameters
from foundation.JSONRequest import JSONRequest

class Locate:
	"""This class implements the OneAPI Location API"""
	
	def __init__(self, endpoints, username, password):
		"""default constructor for Locate requires definition of OneAPI server endpoints and the username/password for authentication"""
		self.endpoints=endpoints
		self.username=username
		self.password=password
	
	def getEndpoints(self):
		"""getter for endpoints : the network endpoint locations for each of the OneAPI services"""
		return self.endpoints
	
	def setEndpoints(self,endpoints):
		"""setter for endpoints : the network endpoint locations for each of the OneAPI services"""
		self.endpoints=endpoints
	
	def getUsername(self):
		"""getter for username : the username required for authentication with the OneAPI server"""
		return self.username
	
	def setUsername(self,username):
		"""setter for username : the username required for authentication with the OneAPI server"""
		self.username=username
	
	def getPassword(self):
		"""getter for password : the password required for authentication with the OneAPI server"""
		return self.password
	
	def setPassword(self,password):
		"""setter for password : the password required for authentication with the OneAPI server"""
		self.password=password
	
	
	def locateTerminal(self,address,requestedAccuracy):
		"""
		locateTerminal : get the location of a single mobile terminal
		Parameters:
		address : MSISDN or ACR of the mobile terminal to locate
		requestedAccuracy : requested positional accuracy
		"""
		baseurl=self.endpoints.getLocationEndpoint()
		requestProcessor=JSONRequest()
		baseurl=requestProcessor.addQueryParameter(baseurl,'address', address)
		baseurl=requestProcessor.addQueryParameter(baseurl,'requestedAccuracy', requestedAccuracy)
		rawresponse=requestProcessor.get(baseurl,'application/json', self.username, self.password)
		response=LocationResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['terminalLocationList'] is not None:
				response.setTerminalLocationListJSON(jsondata['terminalLocationList'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def locateMultipleTerminals(self,address,requestedAccuracy):
		"""
		locateMultipleTerminals : Get the location of multiple mobile terminals listed in the address parameter
		Parameters:
		address : MSISDN or ACR of the mobile terminal to locate
		requestedAccuracy : requested positional accuracy
		"""
		baseurl=self.endpoints.getLocationEndpoint()
		requestProcessor=JSONRequest()
		if address is not None:
			for item in address:
				baseurl=requestProcessor.addQueryParameter(baseurl,'address',item)
		baseurl=requestProcessor.addQueryParameter(baseurl,'requestedAccuracy', requestedAccuracy)
		rawresponse=requestProcessor.get(baseurl,'application/json', self.username, self.password)
		response=LocationResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['terminalLocationList'] is not None:
				response.setTerminalLocationListJSON(jsondata['terminalLocationList'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
