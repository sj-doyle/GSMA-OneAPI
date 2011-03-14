from response.ResourceReference import ResourceReference
from response.sms.SendSMSResponse import SendSMSResponse
from response.sms.DeliveryInfo import DeliveryInfo
from response.sms.DeliveryInfoList import DeliveryInfoList
from response.sms.SMSSendDeliveryStatusResponse import SMSSendDeliveryStatusResponse
from response.sms.CallbackReference import CallbackReference
from response.sms.DeliveryReceiptSubscription import DeliveryReceiptSubscription
from response.sms.SMSDeliveryReceiptSubscriptionResponse import SMSDeliveryReceiptSubscriptionResponse
from response.sms.HTTPResponse import HTTPResponse
import json
from foundation.FormParameters import FormParameters
from foundation.JSONRequest import JSONRequest

class SMSSend:
	"""This class implements the SMS send group of functions"""
	
	def __init__(self, endpoints, username, password):
		"""default constructor for SMSSend requires definition of OneAPI server endpoints and the username/password for authentication"""
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
	
	
	def sendSMS(self,senderAddress,address,message,clientCorrelator,notifyURL,senderName,callbackData):
		"""
		sendSMS : Send an SMS from your Web application
		Parameters:
		senderAddress : MSISDN or allocated code of the sender
		address : MSISDN or ACR of the mobile terminal to send to
		message : text part of the message to send to the recipient(s) - long messages may be split by the OneAPI server
		clientCorrelator : (string) uniquely identifies this create SMS request. If there is a communication failure during the request, using the same clientCorrelator when retrying the request allows the operator to avoid sending the same SMS twice.
		notifyURL : (anyURI) is the URL-escaped URL to which you would like a notification of delivery sent.
		senderName : (string) is the URL-escaped name of the sender to appear on the terminal is the address to  whom a responding SMS may be sent
		callbackData : (string) will be passed back in this notification, so you can use it to identify the message the receipt relates to (or any other useful data, such as a function name)
		"""
		baseurl=self.endpoints.getSendSMSEndpoint()
		requestProcessor=JSONRequest()
		formparameters=FormParameters()
		formparameters.put('senderAddress',senderAddress)
		if '{senderAddress}' in baseurl: baseurl=baseurl.replace('{senderAddress}',str(senderAddress))
		if address is not None:
			for item in address:
				formparameters.put('address',item)
		formparameters.put('message',message)
		formparameters.put('clientCorrelator',clientCorrelator)
		formparameters.put('notifyURL',notifyURL)
		formparameters.put('senderName',senderName)
		formparameters.put('callbackData',callbackData)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', self.username, self.password)
		response=SendSMSResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['resourceReference'] is not None:
				response.setResourceReferenceJSON(jsondata['resourceReference'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def queryDeliveryStatus(self,senderAddress,requestId):
		"""
		queryDeliveryStatus : query the delivery status of an SMS send request
		Parameters:
		senderAddress : MSISDN or allocated code of the sender
		requestId : the requestID which was returned in the sendSMS response
		"""
		baseurl=self.endpoints.getQuerySMSDeliveryEndpoint()
		requestProcessor=JSONRequest()
		if '{senderAddress}' in baseurl: baseurl=baseurl.replace('{senderAddress}',str(senderAddress))
		if '{requestId}' in baseurl: baseurl=baseurl.replace('{requestId}',str(requestId))
		rawresponse=requestProcessor.get(baseurl,'application/json', self.username, self.password)
		response=SMSSendDeliveryStatusResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['deliveryInfoList'] is not None:
				response.setDeliveryInfoListJSON(jsondata['deliveryInfoList'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def subscribeToDeliveryNotifications(self,senderAddress,clientCorrelator,notifyURL,callbackData):
		"""
		subscribeToDeliveryNotifications : subscribe to SMS delivery notifications
		Parameters:
		senderAddress : MSISDN or allocated code of the sender
		clientCorrelator : string) uniquely identifies this create subscription request. If there is a communication failure during the request, using the same clientCorrelator when retrying the request allows the operator to avoid creating a duplicate subscription.
		notifyURL : (URL) This will be used by the server to POST the notifications to you, so include the URL of your own listener application.
		callbackData : (string) is a function name or other data that you would like included when the POST is received.
		"""
		baseurl=self.endpoints.getSMSDeliverySubscriptionsEndpoint()
		requestProcessor=JSONRequest()
		formparameters=FormParameters()
		formparameters.put('senderAddress',senderAddress)
		if '{senderAddress}' in baseurl: baseurl=baseurl.replace('{senderAddress}',str(senderAddress))
		formparameters.put('clientCorrelator',clientCorrelator)
		formparameters.put('notifyURL',notifyURL)
		formparameters.put('callbackData',callbackData)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', self.username, self.password)
		response=SMSDeliveryReceiptSubscriptionResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['deliveryReceiptSubscription'] is not None:
				response.setDeliveryReceiptSubscriptionJSON(jsondata['deliveryReceiptSubscription'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def cancelDeliveryNotifications(self,subscriptionId):
		"""
		cancelDeliveryNotifications : stop the subscription to delivery notifications
		Parameters:
		subscriptionId : subscriptionId of a previously created deliveryNotification subscription
		"""
		baseurl=self.endpoints.getCancelSMSDeliverySubscriptionEndpoint()
		requestProcessor=JSONRequest()
		if '{subscriptionId}' in baseurl: baseurl=baseurl.replace('{subscriptionId}',str(subscriptionId))
		rawresponse=requestProcessor.delete(baseurl,self.username,self.password)
		response=HTTPResponse()
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
