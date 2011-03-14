from response.sms.InboundSMSMessage import InboundSMSMessage
from response.sms.InboundSMSMessageList import InboundSMSMessageList
from response.sms.RetrieveSMSResponse import RetrieveSMSResponse
from response.ResourceReference import ResourceReference
from response.sms.SMSMessageReceiptSubscriptionResponse import SMSMessageReceiptSubscriptionResponse
from response.sms.HTTPResponse import HTTPResponse
import json
from foundation.FormParameters import FormParameters
from foundation.JSONRequest import JSONRequest

class SMSRetrieve:
	"""This class implements the SMS receive group of functions"""
	
	def __init__(self, endpoints, username, password):
		"""default constructor for SMSRetrieve requires definition of OneAPI server endpoints and the username/password for authentication"""
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
	
	
	def retrieveMessages(self,registrationId,maxBatchSize):
		"""
		retrieveMessages : Retrieve messages sent to your Web application
		Parameters:
		registrationId : the registration ID agreed with the OneAPI operator.
		maxBatchSize : the maximum number of messages to retrieve in this request
		"""
		baseurl=self.endpoints.getRetrieveSMSEndpoint()
		requestProcessor=JSONRequest()
		if '{registrationId}' in baseurl: baseurl=baseurl.replace('{registrationId}',str(registrationId))
		if '{maxBatchSize}' in baseurl: baseurl=baseurl.replace('{maxBatchSize}',str(maxBatchSize))
		rawresponse=requestProcessor.get(baseurl,'application/json', self.username, self.password)
		response=RetrieveSMSResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['inboundSMSMessageList'] is not None:
				response.setInboundSMSMessageListJSON(jsondata['inboundSMSMessageList'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def subscribeToReceiptNotifications(self,destinationAddress,notifyURL,criteria,notificationFormat,clientCorrelator,callbackData):
		"""
		subscribeToReceiptNotifications : subscribe to notifications of messages sent to your application
		Parameters:
		destinationAddress : the MSISDN, or code agreed with the operator, to which people may send an SMS to your application
		notifyURL : (URL) is your address to which notifications will be sent
		criteria : string) is case-insensitve text to match against the first word of the message, ignoring any leading whitespace. This allows you to reuse a short code among various applications, each of which can register their own subscription with different criteria
		notificationFormat : the content type that notifications will be sent in - for OneAPI v1.0 only JSON is supported.
		clientCorrelator : (string) uniquely identifies this create subscription request. If there is a communication failure during the request, using the same clientCorrelator when retrying the request allows the operator to avoid creating a duplicate subscription
		callbackData : (string) is a function name or other data that you would like included when the POST is received.
		"""
		baseurl=self.endpoints.getSMSReceiptSubscriptionsEndpoint()
		requestProcessor=JSONRequest()
		formparameters=FormParameters()
		formparameters.put('destinationAddress',destinationAddress)
		formparameters.put('notifyURL',notifyURL)
		formparameters.put('criteria',criteria)
		formparameters.put('notificationFormat',notificationFormat)
		formparameters.put('clientCorrelator',clientCorrelator)
		formparameters.put('callbackData',callbackData)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', self.username, self.password)
		response=SMSMessageReceiptSubscriptionResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['resourceReference'] is not None:
				response.setResourceReferenceJSON(jsondata['resourceReference'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def cancelReceiptNotifications(self,subscriptionId):
		"""
		cancelReceiptNotifications : stop the subscription to message notifications
		Parameters:
		subscriptionId : subscriptionId of a previously created receiptNotification subscription
		"""
		baseurl=self.endpoints.getCancelSMSReceiptSubscriptionEndpoint()
		requestProcessor=JSONRequest()
		if '{subscriptionId}' in baseurl: baseurl=baseurl.replace('{subscriptionId}',str(subscriptionId))
		rawresponse=requestProcessor.delete(baseurl,self.username,self.password)
		response=HTTPResponse()
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
