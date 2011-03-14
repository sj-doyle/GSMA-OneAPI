from response.ResourceReference import ResourceReference
from response.mms.SendMMSResponse import SendMMSResponse
from response.mms.DeliveryInfo import DeliveryInfo
from response.mms.DeliveryInfoList import DeliveryInfoList
from response.mms.MMSSendDeliveryStatusResponse import MMSSendDeliveryStatusResponse
from response.mms.CallbackReference import CallbackReference
from response.mms.DeliveryReceiptSubscription import DeliveryReceiptSubscription
from response.mms.MMSDeliveryReceiptSubscriptionResponse import MMSDeliveryReceiptSubscriptionResponse
from response.mms.HTTPResponse import HTTPResponse
import json
from foundation.FormParameters import FormParameters
from foundation.JSONRequest import JSONRequest
import re
import base64
from email import encoders
from email.message import Message
from email.mime.base import MIMEBase
from email.mime.application import MIMEApplication
from email.mime.multipart import MIMEMultipart

class MMSSend:
	"""This class implements the MMS send group of functions"""
	
	def __init__(self, endpoints, username, password):
		"""default constructor for MMSSend requires definition of OneAPI server endpoints and the username/password for authentication"""
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
	
	
	def sendMMS(self,senderAddress,address,message,attachments,clientCorrelator,notifyURL,senderName,callbackData):
		"""
		sendMMS : Send an SMS from your Web application
		Parameters:
		senderAddress : MSISDN or allocated code of the sender
		address : MSISDN or ACR of the mobile terminal to send to
		message : text part of the message to send to the recipient(s)
		attachments : file attachments (see the Attachments class) to send as part of the MMS
		clientCorrelator : (string) uniquely identifies this create MMS request. If there is a communication failure during the request, using the same clientCorrelator when retrying the request allows the operator to avoid sending the same MMS twice.
		notifyURL : (anyURI) is the URL-escaped URL to which you would like a notification of delivery sent. The format of this notification is shown below.
		senderName : (string) is the name to appear on the user's terminal as the sender of the message.
		callbackData : (string) is any meaningful data you woul like send back in the notification, for example to identify the message or pass a function name, etc.
		"""
		baseurl=self.endpoints.getSendMMSEndpoint()
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
		outer=MIMEMultipart()
		jsonpart=MIMEBase('application', 'x-www-form-urlencoded')
		jsonpart.set_payload(postdata)
		jsonpart.add_header('Content-Disposition', 'form-data; name="root-fields"')
		outer.attach(jsonpart)
		if attachments is not None:
			for item in attachments:
				if item is not None:
					attachmentName=item.getName()
					attachmentContentType=item.getContentType()
					attachmentData=item.getData()
					if attachmentData is not None:
						if attachmentContentType is not None:
							maintype, subtype = attachmentContentType.split('/', 1)
							msga = MIMEBase(maintype, subtype)
						else:
							msga = MIMEBase('application','octet-stream')
						msga.set_payload(base64.b64encode(attachmentData))
						msga.add_header('Content-Disposition', 'form-data', name='attachments', filename=attachmentName)
						msga.add_header('Content-Transfer-Encoding', 'base64')
						outer.attach(msga)
		payload=outer.as_string(False)
		boundary=outer.get_boundary()
		trimmed=re.sub('Content-Type: multipart/mixed\\; [A-Za-z0-9\\=\\"]*','',payload)
		reformatted=trimmed.replace('\r\n','\n').replace('\n','\r\n')
		rawresponse=requestProcessor.postMultipart(baseurl,reformatted,'application/json', self.username, self.password, boundary)
		response=SendMMSResponse()
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
		queryDeliveryStatus : query the delivery status of an MMS
		Parameters:
		senderAddress : MSISDN or allocated code of the sender
		requestId : the requestID which was  returned in sendMMS function
		"""
		baseurl=self.endpoints.getQueryMMSDeliveryEndpoint()
		requestProcessor=JSONRequest()
		if '{senderAddress}' in baseurl: baseurl=baseurl.replace('{senderAddress}',str(senderAddress))
		if '{requestId}' in baseurl: baseurl=baseurl.replace('{requestId}',str(requestId))
		rawresponse=requestProcessor.get(baseurl,'application/json', self.username, self.password)
		response=MMSSendDeliveryStatusResponse()
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
		subscribeToDeliveryNotifications : subscribe to MMS delivery notifications
		Parameters:
		senderAddress : MSISDN or allocated code of the sender
		clientCorrelator : (string) uniquely identifies this create subscription request. If there is a communication failure during the request, using the same clientCorrelator when retrying the request allows the operator to avoid creating a duplicate subscription.
		notifyURL : (URL) This will be used by the server to POST the notifications to you, so include the URL of your own listener application.
		callbackData : (string) is a function name or other data that you would like included when the POST is received.
		"""
		baseurl=self.endpoints.getMMSDeliverySubscriptionsEndpoint()
		requestProcessor=JSONRequest()
		formparameters=FormParameters()
		formparameters.put('senderAddress',senderAddress)
		if '{senderAddress}' in baseurl: baseurl=baseurl.replace('{senderAddress}',str(senderAddress))
		formparameters.put('clientCorrelator',clientCorrelator)
		formparameters.put('notifyURL',notifyURL)
		formparameters.put('callbackData',callbackData)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', self.username, self.password)
		response=MMSDeliveryReceiptSubscriptionResponse()
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
		subscriptionId : a previously created subscriptionId from the subscribeToDeliveryNotification function
		"""
		baseurl=self.endpoints.getCancelMMSDeliverySubscriptionEndpoint()
		requestProcessor=JSONRequest()
		if '{subscriptionId}' in baseurl: baseurl=baseurl.replace('{subscriptionId}',str(subscriptionId))
		rawresponse=requestProcessor.delete(baseurl,self.username,self.password)
		response=HTTPResponse()
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
