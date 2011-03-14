from response.payment.AmountReservationTransaction import AmountReservationTransaction
from response.payment.AmountReservationResponse import AmountReservationResponse
import json
from foundation.FormParameters import FormParameters
from foundation.JSONRequest import JSONRequest

class Reservation:
	"""This class implements the OneAPI payment functions for payment reservation related functions"""
	
	def __init__(self, endpoints, username, password):
		"""default constructor for Reservation requires definition of OneAPI server endpoints and the username/password for authentication"""
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
	
	
	def reserveInitialAmount(self,endUserId,referenceCode,description,currency,amount,code,clientCorrelator,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId):
		"""
		reserveInitialAmount : reserve an amount for subsequent charging to an end user's bill
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
		baseurl=self.endpoints.getAmountReserveEndpoint()
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
		response=AmountReservationResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['amountReservationTransaction'] is not None:
				response.setAmountReservationTransactionJSON(jsondata['amountReservationTransaction'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def reserveAdditionalAmount(self,endUserId,transactionId,referenceCode,description,currency,amount,referenceSequence,code,clientCorrelator,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId):
		"""
		reserveAdditionalAmount : reserve an additional amount
		Parameters:
		endUserId : is end user ID; either MSISDN including the 'tel:' protocol identifier. OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator.
		transactionId : uniquely identifies the reservation transaction - used in maintaining the sequence of transactions
		referenceCode : (string, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
		description : is the human-readable text to appear on the bill, so the user can easily see what they bought
		currency : is the 3-figure code as per ISO 4217
		amount : (decimal) can be a whole number or decimal
		referenceSequence : (integer) - this allows the server to distinguish easily between new and repeated requests in the case of a communication failure. For each transaction within a reservation sequence, iterate the referenceSequence by 1
		code : (string) a code provided by the OneAPI implementation that is used to reference an operator price point.
		clientCorrelator : (string) uniquely identifies this create charge request. If there is a communication failure during the charge request, using the same clientCorrelator when retrying the request allows the operator to avoid applying the same charge twice.
		onBehalfOf : (string) allows aggregators/partners to specify the actual payee.
		purchaseCategoryCode : (string) an indication of the content type. Values meaningful to the billing system would be published by a OneAPI implementation.
		channel : (string) can be 'Wap', 'Web', 'SMS', depending on the source of user interaction
		taxAmount : decimal) tax already charged by the merchant.
		serviceId : (string) The ID of the partner/merchant service
		productId : (string) combines with the serviceID to uniquely identify the product being purchased.
		"""
		baseurl=self.endpoints.getAmountReserveAdditionalEndpoint()
		requestProcessor=JSONRequest()
		formparameters=FormParameters()
		formparameters.put('endUserId',endUserId)
		if '{endUserId}' in baseurl: baseurl=baseurl.replace('{endUserId}',str(endUserId))
		formparameters.put('transactionId',transactionId)
		if '{transactionId}' in baseurl: baseurl=baseurl.replace('{transactionId}',str(transactionId))
		formparameters.put('referenceCode',referenceCode)
		formparameters.put('description',description)
		formparameters.put('currency',currency)
		formparameters.put('amount',amount)
		formparameters.put('referenceSequence',referenceSequence)
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
		response=AmountReservationResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['amountReservationTransaction'] is not None:
				response.setAmountReservationTransactionJSON(jsondata['amountReservationTransaction'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def reserveAdditionalAmountSimple(self,endUserId,transactionId,referenceCode,description,currency,amount,referenceSequence,code):
		"""
		reserveAdditionalAmountSimple : reserve an additional amount
		Parameters:
		endUserId : is end user ID; either MSISDN including the 'tel:' protocol identifier. OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator.
		transactionId : uniquely identifies the reservation transaction - used in maintaining the sequence of transactions
		referenceCode : (string, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
		description : is the human-readable text to appear on the bill, so the user can easily see what they bought
		currency : is the 3-figure code as per ISO 4217
		amount : (decimal) can be a whole number or decimal
		referenceSequence : (integer) - this allows the server to distinguish easily between new and repeated requests in the case of a communication failure. For each transaction within a reservation sequence, iterate the referenceSequence by 1
		code : (string) a code provided by the OneAPI implementation that is used to reference an operator price point.
		"""
		baseurl=self.endpoints.getAmountReserveAdditionalEndpoint()
		requestProcessor=JSONRequest()
		formparameters=FormParameters()
		formparameters.put('endUserId',endUserId)
		if '{endUserId}' in baseurl: baseurl=baseurl.replace('{endUserId}',str(endUserId))
		formparameters.put('transactionId',transactionId)
		if '{transactionId}' in baseurl: baseurl=baseurl.replace('{transactionId}',str(transactionId))
		formparameters.put('referenceCode',referenceCode)
		formparameters.put('description',description)
		formparameters.put('currency',currency)
		formparameters.put('amount',amount)
		formparameters.put('referenceSequence',referenceSequence)
		formparameters.put('code',code)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', self.username, self.password)
		response=AmountReservationResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['amountReservationTransaction'] is not None:
				response.setAmountReservationTransactionJSON(jsondata['amountReservationTransaction'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def chargeAmount(self,endUserId,transactionId,referenceCode,description,currency,amount,referenceSequence,code,clientCorrelator,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId):
		"""
		chargeAmount : charge against the reservation
		Parameters:
		endUserId : is end user ID; either MSISDN including the 'tel:' protocol identifier. OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator.
		transactionId : uniquely identifies the reservation transaction - used in maintaining the sequence of transactions
		referenceCode : (string, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
		description : is the human-readable text to appear on the bill, so the user can easily see what they bought
		currency : is the 3-figure code as per ISO 4217
		amount : (decimal) can be a whole number or decimal
		referenceSequence : (integer) - this allows the server to distinguish easily between new and repeated requests in the case of a communication failure. For each transaction within a reservation sequence, iterate the referenceSequence by 1
		code : (string) a code provided by the OneAPI implementation that is used to reference an operator price point.
		clientCorrelator : (string) uniquely identifies this create charge request. If there is a communication failure during the charge request, using the same clientCorrelator when retrying the request allows the operator to avoid applying the same charge twice.
		onBehalfOf : (string) allows aggregators/partners to specify the actual payee.
		purchaseCategoryCode : (string) an indication of the content type. Values meaningful to the billing system would be published by a OneAPI implementation.
		channel : (string) can be 'Wap', 'Web', 'SMS', depending on the source of user interaction
		taxAmount : decimal) tax already charged by the merchant.
		serviceId : (string) The ID of the partner/merchant service
		productId : (string) combines with the serviceID to uniquely identify the product being purchased.
		"""
		baseurl=self.endpoints.getAmountReservationChargeEndpoint()
		requestProcessor=JSONRequest()
		formparameters=FormParameters()
		formparameters.put('endUserId',endUserId)
		if '{endUserId}' in baseurl: baseurl=baseurl.replace('{endUserId}',str(endUserId))
		formparameters.put('transactionId',transactionId)
		if '{transactionId}' in baseurl: baseurl=baseurl.replace('{transactionId}',str(transactionId))
		formparameters.put('referenceCode',referenceCode)
		formparameters.put('description',description)
		formparameters.put('currency',currency)
		formparameters.put('amount',amount)
		formparameters.put('referenceSequence',referenceSequence)
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
		response=AmountReservationResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['amountReservationTransaction'] is not None:
				response.setAmountReservationTransactionJSON(jsondata['amountReservationTransaction'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def chargeAmountWithCallback(self,endUserId,transactionId,referenceCode,description,currency,amount,referenceSequence,code,callbackURL,clientCorrelator,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId):
		"""
		chargeAmountWithCallback : charge against the reservation - function variant using Oauth based flow
		Parameters:
		endUserId : is end user ID; either MSISDN including the 'tel:' protocol identifier. OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator.
		transactionId : uniquely identifies the reservation transaction - used in maintaining the sequence of transactions
		referenceCode : (string, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
		description : is the human-readable text to appear on the bill, so the user can easily see what they bought
		currency : is the 3-figure code as per ISO 4217
		amount : (decimal) can be a whole number or decimal
		referenceSequence : (integer) - this allows the server to distinguish easily between new and repeated requests in the case of a communication failure. For each transaction within a reservation sequence, iterate the referenceSequence by 1
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
		baseurl=self.endpoints.getAmountReservationChargeEndpoint()
		requestProcessor=JSONRequest()
		formparameters=FormParameters()
		formparameters.put('endUserId',endUserId)
		if '{endUserId}' in baseurl: baseurl=baseurl.replace('{endUserId}',str(endUserId))
		formparameters.put('transactionId',transactionId)
		if '{transactionId}' in baseurl: baseurl=baseurl.replace('{transactionId}',str(transactionId))
		formparameters.put('referenceCode',referenceCode)
		formparameters.put('description',description)
		formparameters.put('currency',currency)
		formparameters.put('amount',amount)
		formparameters.put('referenceSequence',referenceSequence)
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
		response=AmountReservationResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['amountReservationTransaction'] is not None:
				response.setAmountReservationTransactionJSON(jsondata['amountReservationTransaction'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
	
	def releaseReservation(self,endUserId,transactionId,referenceCode,referenceSequence):
		"""
		releaseReservation : release the reservation
		Parameters:
		endUserId : is end user ID; either MSISDN including the 'tel:' protocol identifier. OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator.
		transactionId : uniquely identifies the reservation transaction - used in maintaining the sequence of transactions
		referenceCode : (string, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
		referenceSequence : (integer) - this allows the server to distinguish easily between new and repeated requests in the case of a communication failure. For each transaction within a reservation sequence, iterate the referenceSequence by 1
		"""
		baseurl=self.endpoints.getAmountReservationReleaseEndpoint()
		requestProcessor=JSONRequest()
		formparameters=FormParameters()
		formparameters.put('endUserId',endUserId)
		if '{endUserId}' in baseurl: baseurl=baseurl.replace('{endUserId}',str(endUserId))
		formparameters.put('transactionId',transactionId)
		if '{transactionId}' in baseurl: baseurl=baseurl.replace('{transactionId}',str(transactionId))
		formparameters.put('referenceCode',referenceCode)
		formparameters.put('referenceSequence',referenceSequence)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', self.username, self.password)
		response=AmountReservationResponse()
		if rawresponse is not None and rawresponse.getContent() is not None:
			jsondata=json.loads(rawresponse.getContent())
			if jsondata is not None and jsondata['amountReservationTransaction'] is not None:
				response.setAmountReservationTransactionJSON(jsondata['amountReservationTransaction'])
		if rawresponse.getCode() is not None: response.setHTTPResponseCode(rawresponse.getCode())
		if rawresponse.getLocation() is not None: response.setLocation(rawresponse.getLocation())
		if rawresponse.getContentType() is not None: response.setContentType(rawresponse.getContentType())
		return response
