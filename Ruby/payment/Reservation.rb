require "response/payment/AmountReservationTransaction"
require "response/payment/AmountReservationResponse"
require 'json'
require 'uri'
require 'net/http'
require 'foundation/FormParameters'
require 'foundation/JSONRequest'

class Reservation

	def initialize(endpoints, username, password)
		@endpoints=endpoints
		@username=username
		@password=password
	end
	def getEndpoints
		@endpoints
	end

	def setEndpoints(endpoints)
		@endpoints=endpoints
	end

	def getUsername
		@username
	end

	def setUsername(username)
		@username=username
	end

	def getPassword
		@password
	end

	def setPassword(password)
		@password=password
	end

	
	def reserveInitialAmount(endUserId,referenceCode,description,currency,amount,code,clientCorrelator,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId)
		baseurl=@endpoints.getAmountReserveEndpoint()
		requestProcessor=JSONRequest.new()
		formparameters=FormParameters.new()
		formparameters.put('endUserId',endUserId)
		if baseurl.index('{endUserId}')!=nil then
			baseurl=baseurl.gsub('{endUserId}',CGI::escape(endUserId.to_s))
		end
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
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', @username, @password)
		response=AmountReservationResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['amountReservationTransaction']!=nil) then
				response.setAmountReservationTransactionJSON(jsondata['amountReservationTransaction'])
			end
		end
		if rawresponse.getCode()!=nil then
			response.setHTTPResponseCode(rawresponse.getCode())
		end
		if rawresponse.getLocation()!=nil then
			response.setLocation(rawresponse.getLocation())
		end
		if rawresponse.getContentType()!=nil then
			response.setContentType(rawresponse.getContentType())
		end
		return response
	end
	
	def reserveAdditionalAmount(endUserId,transactionId,referenceCode,description,currency,amount,referenceSequence,code,clientCorrelator,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId)
		baseurl=@endpoints.getAmountReserveAdditionalEndpoint()
		requestProcessor=JSONRequest.new()
		formparameters=FormParameters.new()
		formparameters.put('endUserId',endUserId)
		if baseurl.index('{endUserId}')!=nil then
			baseurl=baseurl.gsub('{endUserId}',CGI::escape(endUserId.to_s))
		end
		formparameters.put('transactionId',transactionId)
		if baseurl.index('{transactionId}')!=nil then
			baseurl=baseurl.gsub('{transactionId}',CGI::escape(transactionId.to_s))
		end
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
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', @username, @password)
		response=AmountReservationResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['amountReservationTransaction']!=nil) then
				response.setAmountReservationTransactionJSON(jsondata['amountReservationTransaction'])
			end
		end
		if rawresponse.getCode()!=nil then
			response.setHTTPResponseCode(rawresponse.getCode())
		end
		if rawresponse.getLocation()!=nil then
			response.setLocation(rawresponse.getLocation())
		end
		if rawresponse.getContentType()!=nil then
			response.setContentType(rawresponse.getContentType())
		end
		return response
	end
	
	def reserveAdditionalAmountSimple(endUserId,transactionId,referenceCode,description,currency,amount,referenceSequence,code)
		baseurl=@endpoints.getAmountReserveAdditionalEndpoint()
		requestProcessor=JSONRequest.new()
		formparameters=FormParameters.new()
		formparameters.put('endUserId',endUserId)
		if baseurl.index('{endUserId}')!=nil then
			baseurl=baseurl.gsub('{endUserId}',CGI::escape(endUserId.to_s))
		end
		formparameters.put('transactionId',transactionId)
		if baseurl.index('{transactionId}')!=nil then
			baseurl=baseurl.gsub('{transactionId}',CGI::escape(transactionId.to_s))
		end
		formparameters.put('referenceCode',referenceCode)
		formparameters.put('description',description)
		formparameters.put('currency',currency)
		formparameters.put('amount',amount)
		formparameters.put('referenceSequence',referenceSequence)
		formparameters.put('code',code)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', @username, @password)
		response=AmountReservationResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['amountReservationTransaction']!=nil) then
				response.setAmountReservationTransactionJSON(jsondata['amountReservationTransaction'])
			end
		end
		if rawresponse.getCode()!=nil then
			response.setHTTPResponseCode(rawresponse.getCode())
		end
		if rawresponse.getLocation()!=nil then
			response.setLocation(rawresponse.getLocation())
		end
		if rawresponse.getContentType()!=nil then
			response.setContentType(rawresponse.getContentType())
		end
		return response
	end
	
	def chargeAmount(endUserId,transactionId,referenceCode,description,currency,amount,referenceSequence,code,clientCorrelator,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId)
		baseurl=@endpoints.getAmountReservationChargeEndpoint()
		requestProcessor=JSONRequest.new()
		formparameters=FormParameters.new()
		formparameters.put('endUserId',endUserId)
		if baseurl.index('{endUserId}')!=nil then
			baseurl=baseurl.gsub('{endUserId}',CGI::escape(endUserId.to_s))
		end
		formparameters.put('transactionId',transactionId)
		if baseurl.index('{transactionId}')!=nil then
			baseurl=baseurl.gsub('{transactionId}',CGI::escape(transactionId.to_s))
		end
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
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', @username, @password)
		response=AmountReservationResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['amountReservationTransaction']!=nil) then
				response.setAmountReservationTransactionJSON(jsondata['amountReservationTransaction'])
			end
		end
		if rawresponse.getCode()!=nil then
			response.setHTTPResponseCode(rawresponse.getCode())
		end
		if rawresponse.getLocation()!=nil then
			response.setLocation(rawresponse.getLocation())
		end
		if rawresponse.getContentType()!=nil then
			response.setContentType(rawresponse.getContentType())
		end
		return response
	end
	
	def chargeAmountWithCallback(endUserId,transactionId,referenceCode,description,currency,amount,referenceSequence,code,callbackURL,clientCorrelator,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId)
		baseurl=@endpoints.getAmountReservationChargeEndpoint()
		requestProcessor=JSONRequest.new()
		formparameters=FormParameters.new()
		formparameters.put('endUserId',endUserId)
		if baseurl.index('{endUserId}')!=nil then
			baseurl=baseurl.gsub('{endUserId}',CGI::escape(endUserId.to_s))
		end
		formparameters.put('transactionId',transactionId)
		if baseurl.index('{transactionId}')!=nil then
			baseurl=baseurl.gsub('{transactionId}',CGI::escape(transactionId.to_s))
		end
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
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', @username, @password)
		response=AmountReservationResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['amountReservationTransaction']!=nil) then
				response.setAmountReservationTransactionJSON(jsondata['amountReservationTransaction'])
			end
		end
		if rawresponse.getCode()!=nil then
			response.setHTTPResponseCode(rawresponse.getCode())
		end
		if rawresponse.getLocation()!=nil then
			response.setLocation(rawresponse.getLocation())
		end
		if rawresponse.getContentType()!=nil then
			response.setContentType(rawresponse.getContentType())
		end
		return response
	end
	
	def releaseReservation(endUserId,transactionId,referenceCode,referenceSequence)
		baseurl=@endpoints.getAmountReservationReleaseEndpoint()
		requestProcessor=JSONRequest.new()
		formparameters=FormParameters.new()
		formparameters.put('endUserId',endUserId)
		if baseurl.index('{endUserId}')!=nil then
			baseurl=baseurl.gsub('{endUserId}',CGI::escape(endUserId.to_s))
		end
		formparameters.put('transactionId',transactionId)
		if baseurl.index('{transactionId}')!=nil then
			baseurl=baseurl.gsub('{transactionId}',CGI::escape(transactionId.to_s))
		end
		formparameters.put('referenceCode',referenceCode)
		formparameters.put('referenceSequence',referenceSequence)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', @username, @password)
		response=AmountReservationResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['amountReservationTransaction']!=nil) then
				response.setAmountReservationTransactionJSON(jsondata['amountReservationTransaction'])
			end
		end
		if rawresponse.getCode()!=nil then
			response.setHTTPResponseCode(rawresponse.getCode())
		end
		if rawresponse.getLocation()!=nil then
			response.setLocation(rawresponse.getLocation())
		end
		if rawresponse.getContentType()!=nil then
			response.setContentType(rawresponse.getContentType())
		end
		return response
	end

end 
