require "response/sms/InboundSMSMessage"
require "response/sms/InboundSMSMessageList"
require "response/sms/RetrieveSMSResponse"
require "response/ResourceReference"
require "response/sms/SMSMessageReceiptSubscriptionResponse"
require "response/sms/HTTPResponse"
require 'json'
require 'uri'
require 'net/http'
require 'foundation/FormParameters'
require 'foundation/JSONRequest'

class SMSRetrieve

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

	
	def retrieveMessages(registrationId,maxBatchSize)
		baseurl=@endpoints.getRetrieveSMSEndpoint()
		requestProcessor=JSONRequest.new()
		if baseurl.index('{registrationId}')!=nil then
			baseurl=baseurl.gsub('{registrationId}',CGI::escape(registrationId.to_s))
		end
		if baseurl.index('{maxBatchSize}')!=nil then
			baseurl=baseurl.gsub('{maxBatchSize}',CGI::escape(maxBatchSize.to_s))
		end
		rawresponse=requestProcessor.get(baseurl,'application/json', @username, @password)
		response=RetrieveSMSResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['inboundSMSMessageList']!=nil) then
				response.setInboundSMSMessageListJSON(jsondata['inboundSMSMessageList'])
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
	
	def subscribeToReceiptNotifications(destinationAddress,notifyURL,criteria,notificationFormat,clientCorrelator,callbackData)
		baseurl=@endpoints.getSMSReceiptSubscriptionsEndpoint()
		requestProcessor=JSONRequest.new()
		formparameters=FormParameters.new()
		formparameters.put('destinationAddress',destinationAddress)
		formparameters.put('notifyURL',notifyURL)
		formparameters.put('criteria',criteria)
		formparameters.put('notificationFormat',notificationFormat)
		formparameters.put('clientCorrelator',clientCorrelator)
		formparameters.put('callbackData',callbackData)
		postdata=formparameters.encodeParameters()
		rawresponse=requestProcessor.post(baseurl,postdata,'application/json', @username, @password)
		response=SMSMessageReceiptSubscriptionResponse.new()
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
	
	def cancelReceiptNotifications(subscriptionId)
		baseurl=@endpoints.getCancelSMSReceiptSubscriptionEndpoint()
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
