from response.mms.InboundMMSMessage import InboundMMSMessage
from response.mms.InboundMessageList import InboundMessageList
from response.mms.RetrieveMMSResponse import RetrieveMMSResponse
from response.mms.InboundMessage import InboundMessage
from response.mms.RetrieveMMSMessageResponse import RetrieveMMSMessageResponse
from response.ResourceReference import ResourceReference
from response.mms.MMSMessageReceiptSubscriptionResponse import MMSMessageReceiptSubscriptionResponse
from response.mms.HTTPResponse import HTTPResponse
import json
from foundation.FormParameters import FormParameters
from foundation.JSONRequest import JSONRequest
import email
from response.Attachment import Attachment

class MMSRetrieve:
	"""This class implements the MMS receive group of functions"""
	
	def __init__(self, endpoints, username, password):
		"""default constructor for MMSRetrieve requires definition of OneAPI server endpoints and the username/password for authentication"""
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
		retrieveMessages : retrieve a list of messages sent to your Web application
		Parameters:
		registrationId : the registration ID agreed with the OneAPI operator.
		maxBatchSize : the maximum number of messages to retrieve in this request
		"""
		baseurl=self.endpoints.getRetrieveMMSEndpoint()
		requestProcessor=JSONRequest()
		if '{registrationId}' in baseurl: baseurl=baseurl.replace('{registrationId}',str(registrationId))
		if '{maxBatchSize}' in baseurl: baseurl=baseurl.replace('{maxBatchSize}',str(maxBatchSize))
		rawresponse=requestProcessor.get(baseurl,'application/json', self.username, self.password)
		response=RetrieveMMSResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['inboundMessageList'] is not None:
				response.setInboundMessageListJSON(jsondata['inboundMessageList'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def retrieveMessageContent(self,registrationId,messageId,resFormat):
		"""
		retrieveMessageContent : retrieve the full MMS including attachments
		Parameters:
		registrationId : the registration ID agreed with the OneAPI operator
		messageId : is a server-generated message identifier (which can be obtained from the retrieveMessages response)
		resFormat : resFormat=JSON ensures a JSON response content-type
		"""
		baseurl=self.endpoints.getRetrieveMMSMessageEndpoint()
		requestProcessor=JSONRequest()
		if '{registrationId}' in baseurl: baseurl=baseurl.replace('{registrationId}',str(registrationId))
		if '{messageId}' in baseurl: baseurl=baseurl.replace('{messageId}',str(messageId))
		if '{resFormat}' in baseurl: baseurl=baseurl.replace('{resFormat}',str(resFormat))
		rawresponse=requestProcessor.get(baseurl,'application/json', self.username, self.password)
		response=RetrieveMMSMessageResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			mimecontent='Content-Type: '+rawresponse.getContentType()+'\r\n'+rawresponse.getContent()
			response=RetrieveMMSMessageResponse()
			response.setAttachment(None)
			mimemsg=email.message_from_string(mimecontent)
			for part in mimemsg.walk():
				contentType=part.get_content_type()
				if 'multipart/mixed' not in contentType:
					filename=part.get_filename()
					payload=part.get_payload()
					if contentType is not None and contentType=='application/json' and filename is not None and filename=='root-fields' and payload is not None:
						jsondata=json.loads(payload)
						if jsondata is not None and jsondata['inboundMessage'] is not None:
							response.setInboundMessage(jsondata['inboundMessage'])
					else:
						attachment=Attachment(None)
						attachment.setContentType(contentType)
						attachment.setName(filename)
						attachment.setData(payload)
						if response.getAttachment()==None: response.setAttachment([])
						response.getAttachment().append(attachment)
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def subscribeToReceiptNotifications(self,destinationAddress,notifyURL,criteria,notificationFormat,clientCorrelator,callbackData):
		"""
		subscribeToReceiptNotifications : subscribe to notifications of MMS messages sent to your application
		Parameters:
		destinationAddress : the MSISDN, or code agreed with the operator, to which people may send an MMS to your application
		notifyURL : (URL) is your address to which notifications will be sent
		criteria : (string) is case-insensitve text to match against the first word of the message, ignoring any leading whitespace. This allows you to reuse a short code among various applications, each of which can register their own subscription with different criteria.
		notificationFormat : is the content type that notifications will be sent in - for OneAPI v1.0 only JSON is supported.
		clientCorrelator : (string) uniquely identifies this create subscription request. If there is a communication failure during the request, using the same clientCorrelator when retrying the request allows the operator to avoid creating a duplicate subscription.
		callbackData : (string) is a function name or other data that you would like included when the POST is received.
		"""
		baseurl=self.endpoints.getMMSReceiptSubscriptionsEndpoint()
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
		response=MMSMessageReceiptSubscriptionResponse()
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
		cancelReceiptNotifications : stop the subscription to MMS message notifications
		Parameters:
		subscriptionId : subscriptionId of a previously created deliveryNotification subscription
		"""
		baseurl=self.endpoints.getCancelMMSReceiptSubscriptionEndpoint()
		requestProcessor=JSONRequest()
		if '{subscriptionId}' in baseurl: baseurl=baseurl.replace('{subscriptionId}',str(subscriptionId))
		rawresponse=requestProcessor.delete(baseurl,self.username,self.password)
		response=HTTPResponse()
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
