require "response/payment/AmountTransaction"
require "response/payment/AmountResponse"
require 'json'
require 'uri'
require 'net/http'
require 'foundation/FormParameters'
require 'foundation/JSONRequest'

class Charge

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

	
	def charge(endUserId,referenceCode,description,currency,amount,code,clientCorrelator,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId)
		baseurl=@endpoints.getAmountChargeEndpoint()
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
		response=AmountResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['amountTransaction']!=nil) then
				response.setAmountTransactionJSON(jsondata['amountTransaction'])
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
	
	def chargeWithCallback(endUserId,referenceCode,description,currency,amount,code,callbackURL,clientCorrelator,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId)
		baseurl=@endpoints.getAmountChargeEndpoint()
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
		response=AmountResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['amountTransaction']!=nil) then
				response.setAmountTransactionJSON(jsondata['amountTransaction'])
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
	
	def refund(endUserId,referenceCode,description,currency,amount,code,clientCorrelator,originalServerReferenceCode,onBehalfOf,purchaseCategoryCode,channel,taxAmount,serviceId,productId)
		baseurl=@endpoints.getAmountRefundEndpoint()
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
		formparameters.put('originalServerReferenceCode',originalServerReferenceCode)
		formparameters.put('onBehalfOf',onBehalfOf)
		formparameters.put('purchaseCategoryCode',purchaseCategoryCode)
		formparameters.put('channel',channel)
		formparameters.put('taxAmount',taxAmount)
		formparameters.put('serviceId',serviceId)
		formparameters.put('productId',productId)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', @username, @password)
		response=AmountResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['amountTransaction']!=nil) then
				response.setAmountTransactionJSON(jsondata['amountTransaction'])
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
