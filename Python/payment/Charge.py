from response.payment.AmountTransaction import AmountTransaction
from response.payment.AmountResponse import AmountResponse
import json
from foundation.FormParameters import FormParameters
from foundation.JSONRequest import JSONRequest

class Charge:
	"""This class implements the OneAPI payment functions for charge and refund"""
	
	def __init__(self, endpoints, username, password):
		"""default constructor for Charge requires definition of OneAPI server endpoints and the username/password for authentication"""
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
	
	
	def charge(self,endUserId,referenceCode,description,currency,amount,code,clientCorrelator,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId):
		"""
		charge : charge an amount to the end user's bill. Note all parameters are URL encoded by the API functions so this is not needed by the application
		Parameters:
		endUserId : is end user ID; either MSISDN including the 'tel:' protocol identifier. OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator.
		referenceCode : (string, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
		description : is the human-readable text to appear on the bill, so the user can easily see what they bought
		currency : is the 3-figure code as per ISO 4217
		amount : (decimal) can be a whole number or decimal
		code : (string) a code provided by the OneAPI implementation that is used to reference an operator price point.
		clientCorrelator : (string) uniquely identifies this create charge request. If there is a communication failure during the charge request, using the same clientCorrelator when retrying the request allows the operator to avoid applying the same charge twice.
		onBehalfOf : (string) allows aggregators/partners to specify the actual payee.
		purchaseCategoryCode : (string) an indication of the content type. Values meaningful to the billing system would be published by a OneAPI implementation.
		channel : (string) can be 'Wap', 'Web', 'SMS', depending on the source of user interaction
		taxAmount : decimal) tax already charged by the merchant.
		serviceId : (string) The ID of the partner/merchant service
		productId : (string) combines with the serviceID to uniquely identify the product being purchased.
		"""
		baseurl=self.endpoints.getAmountChargeEndpoint()
		requestProcessor=JSONRequest()
		formparameters=FormParameters()
		formparameters.put('endUserId',endUserId)
		if '{endUserId}' in baseurl: baseurl=baseurl.replace('{endUserId}',str(endUserId))
		formparameters.put('referenceCode',referenceCode)
		formparameters.put('description',description)
		formparameters.put('currency',currency)
		formparameters.put('amount',amount)
		formparameters.put('code',code)
		formparameters.put('clientCorrelator',clientCorrelator)
		formparameters.put('onBehalfOf',onBehalfOf)
		formparameters.put('purchaseCategoryCode',purchaseCategoryCode)
		formparameters.put('channel',channel)
		formparameters.put('taxAmount',taxAmount)
		formparameters.put('serviceId',serviceId)
		formparameters.put('productId',productId)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', self.username, self.password)
		response=AmountResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['amountTransaction'] is not None:
				response.setAmountTransactionJSON(jsondata['amountTransaction'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def chargeWithCallback(self,endUserId,referenceCode,description,currency,amount,code,callbackURL,clientCorrelator,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId):
		"""
		chargeWithCallback : charge an amount to the end user's bill. Note all parameters are URL encoded by the API functions so this is not needed by the application
		Parameters:
		endUserId : is end user ID; either MSISDN including the 'tel:' protocol identifier. OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator.
		referenceCode : (string, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
		description : is the human-readable text to appear on the bill, so the user can easily see what they bought
		currency : is the 3-figure code as per ISO 4217
		amount : (decimal) can be a whole number or decimal
		code : (string) a code provided by the OneAPI implementation that is used to reference an operator price point.
		callbackURL : specifies a URL which will receive a notification when the charge request has completed (as part of an Oauth based flow)
		clientCorrelator : (string) uniquely identifies this create charge request. If there is a communication failure during the charge request, using the same clientCorrelator when retrying the request allows the operator to avoid applying the same charge twice.
		onBehalfOf : (string) allows aggregators/partners to specify the actual payee.
		purchaseCategoryCode : (string) an indication of the content type. Values meaningful to the billing system would be published by a OneAPI implementation.
		channel : (string) can be 'Wap', 'Web', 'SMS', depending on the source of user interaction
		taxAmount : decimal) tax already charged by the merchant.
		serviceId : (string) The ID of the partner/merchant service
		productId : (string) combines with the serviceID to uniquely identify the product being purchased.
		"""
		baseurl=self.endpoints.getAmountChargeEndpoint()
		requestProcessor=JSONRequest()
		formparameters=FormParameters()
		formparameters.put('endUserId',endUserId)
		if '{endUserId}' in baseurl: baseurl=baseurl.replace('{endUserId}',str(endUserId))
		formparameters.put('referenceCode',referenceCode)
		formparameters.put('description',description)
		formparameters.put('currency',currency)
		formparameters.put('amount',amount)
		formparameters.put('code',code)
		formparameters.put('callbackURL',callbackURL)
		formparameters.put('clientCorrelator',clientCorrelator)
		formparameters.put('onBehalfOf',onBehalfOf)
		formparameters.put('purchaseCategoryCode',purchaseCategoryCode)
		formparameters.put('channel',channel)
		formparameters.put('taxAmount',taxAmount)
		formparameters.put('serviceId',serviceId)
		formparameters.put('productId',productId)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', self.username, self.password)
		response=AmountResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['amountTransaction'] is not None:
				response.setAmountTransactionJSON(jsondata['amountTransaction'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def refund(self,endUserId,referenceCode,description,currency,amount,code,clientCorrelator,originalServerReferenceCode,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId):
		"""
		refund : refund the user
		Parameters:
		endUserId : is end user ID; either MSISDN including the 'tel:' protocol identifier. OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator.
		referenceCode : (string, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
		description : is the human-readable text to appear on the bill, so the user can easily see what they bought
		currency : is the 3-figure code as per ISO 4217
		amount : (decimal) can be a whole number or decimal
		code : (string) a code provided by the OneAPI implementation that is used to reference an operator price point.
		clientCorrelator : (string) uniquely identifies this create charge request. If there is a communication failure during the charge request, using the same clientCorrelator when retrying the request allows the operator to avoid applying the same charge twice.
		originalServerReferenceCode : if a serverReferenceCode was provided in the response to the orignal charge request, then you must include it in this
		onBehalfOf : (string) allows aggregators/partners to specify the actual payee.
		purchaseCategoryCode : (string) an indication of the content type. Values meaningful to the billing system would be published by a OneAPI implementation.
		channel : (string) can be 'Wap', 'Web', 'SMS', depending on the source of user interaction
		taxAmount : decimal) tax already charged by the merchant.
		serviceId : (string) The ID of the partner/merchant service
		productId : (string) combines with the serviceID to uniquely identify the product being purchased.
		"""
		baseurl=self.endpoints.getAmountRefundEndpoint()
		requestProcessor=JSONRequest()
		formparameters=FormParameters()
		formparameters.put('endUserId',endUserId)
		if '{endUserId}' in baseurl: baseurl=baseurl.replace('{endUserId}',str(endUserId))
		formparameters.put('referenceCode',referenceCode)
		formparameters.put('description',description)
		formparameters.put('currency',currency)
		formparameters.put('amount',amount)
		formparameters.put('code',code)
		formparameters.put('clientCorrelator',clientCorrelator)
		formparameters.put('originalServerReferenceCode',originalServerReferenceCode)
		formparameters.put('onBehalfOf',onBehalfOf)
		formparameters.put('purchaseCategoryCode',purchaseCategoryCode)
		formparameters.put('channel',channel)
		formparameters.put('taxAmount',taxAmount)
		formparameters.put('serviceId',serviceId)
		formparameters.put('productId',productId)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', self.username, self.password)
		response=AmountResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['amountTransaction'] is not None:
				response.setAmountTransactionJSON(jsondata['amountTransaction'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
