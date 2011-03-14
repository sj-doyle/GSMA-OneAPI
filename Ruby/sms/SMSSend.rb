require "response/ResourceReference"
require "response/sms/SendSMSResponse"
require "response/sms/DeliveryInfo"
require "response/sms/DeliveryInfoList"
require "response/sms/SMSSendDeliveryStatusResponse"
require "response/sms/CallbackReference"
require "response/sms/DeliveryReceiptSubscription"
require "response/sms/SMSDeliveryReceiptSubscriptionResponse"
require "response/sms/HTTPResponse"
require 'json'
require 'uri'
require 'net/http'
require 'foundation/FormParameters'
require 'foundation/JSONRequest'

class SMSSend

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

	
	def sendSMS(senderAddress,address,message,clientCorrelator,notifyURL,senderName,callbackData)
		baseurl=@endpoints.getSendSMSEndpoint()
		requestProcessor=JSONRequest.new()
		formparameters=FormParameters.new()
		formparameters.put('senderAddress',senderAddress)
		if baseurl.index('{senderAddress}')!=nil then
			baseurl=baseurl.gsub('{senderAddress}',CGI::escape(senderAddress.to_s))
		end
		if address!=nil
			for item in address
				formparameters.put('address',item)
			end
		end
		formparameters.put('message',message)
		formparameters.put('clientCorrelator',clientCorrelator)
		formparameters.put('notifyURL',notifyURL)
		formparameters.put('senderName',senderName)
		formparameters.put('callbackData',callbackData)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', @username, @password)
		response=SendSMSResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['resourceReference']!=nil) then
				response.setResourceReferenceJSON(jsondata['resourceReference'])
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
	
	def queryDeliveryStatus(senderAddress,requestId)
		baseurl=@endpoints.getQuerySMSDeliveryEndpoint()
		requestProcessor=JSONRequest.new()
		if baseurl.index('{senderAddress}')!=nil then
			baseurl=baseurl.gsub('{senderAddress}',CGI::escape(senderAddress.to_s))
		end
		if baseurl.index('{requestId}')!=nil then
			baseurl=baseurl.gsub('{requestId}',CGI::escape(requestId.to_s))
		end
		rawresponse=requestProcessor.get(baseurl,'application/json', @username, @password)
		response=SMSSendDeliveryStatusResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['deliveryInfoList']!=nil) then
				response.setDeliveryInfoListJSON(jsondata['deliveryInfoList'])
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
	
	def subscribeToDeliveryNotifications(senderAddress,clientCorrelator,notifyURL,callbackData)
		baseurl=@endpoints.getSMSDeliverySubscriptionsEndpoint()
		requestProcessor=JSONRequest.new()
		formparameters=FormParameters.new()
		formparameters.put('senderAddress',senderAddress)
		if baseurl.index('{senderAddress}')!=nil then
			baseurl=baseurl.gsub('{senderAddress}',CGI::escape(senderAddress.to_s))
		end
		formparameters.put('clientCorrelator',clientCorrelator)
		formparameters.put('notifyURL',notifyURL)
		formparameters.put('callbackData',callbackData)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', @username, @password)
		response=SMSDeliveryReceiptSubscriptionResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['deliveryReceiptSubscription']!=nil) then
				response.setDeliveryReceiptSubscriptionJSON(jsondata['deliveryReceiptSubscription'])
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
	
	def cancelDeliveryNotifications(subscriptionId)
		baseurl=@endpoints.getCancelSMSDeliverySubscriptionEndpoint()
		requestProcessor=JSONRequest.new()
		if baseurl.index('{subscriptionId}')!=nil then
			baseurl=baseurl.gsub('{subscriptionId}',CGI::escape(subscriptionId.to_s))
		end
		rawresponse=requestProcessor.delete(baseurl,@username,@password)
		response=HTTPResponse.new()
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
