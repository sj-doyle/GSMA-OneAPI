require "response/ResourceReference"
require "response/mms/SendMMSResponse"
require "response/mms/DeliveryInfo"
require "response/mms/DeliveryInfoList"
require "response/mms/MMSSendDeliveryStatusResponse"
require "response/mms/CallbackReference"
require "response/mms/DeliveryReceiptSubscription"
require "response/mms/MMSDeliveryReceiptSubscriptionResponse"
require "response/mms/HTTPResponse"
require 'json'
require 'uri'
require 'net/http'
require 'foundation/FormParameters'
require 'foundation/JSONRequest'
require 'Base64'
require 'uuid'

class MMSSend

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

	
	def sendMMS(senderAddress,address,message,attachments,clientCorrelator,notifyURL,senderName,callbackData)
		baseurl=@endpoints.getSendMMSEndpoint()
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
		uuid=UUID.new
		boundary=uuid.generate
		msg = '\r\nMIME-Version: 1.0\r\n'
		msg << '\r\n--'+boundary+'\r\n'
		msg << 'Content-Disposition: form-data; name="root-fields"\r\n'
		msg << 'Content-Type: application/x-www-form-urlencoded\r\n\r\n'
		msg << postdata
		msg << '\r\n'
		if attachments!=nil then
			for item in attachments
				if (item!=nil) && (item.getData!=nil) then
					msg << '\r\n--'+boundary+'\r\n'
					attachmentName=item.getName()
					if attachmentName!=nil then
						msg << 'Content-Disposition: form-data; name="attachments"; filename="'+attachmentName+'"\r\n'
					else
						msg << 'Content-Disposition: form-data; name="attachments"\r\n'
					end
					msg << 'Content-Transfer-Encoding: base64\r\n'
					attachmentContentType=item.getContentType()
					attachmentData=item.getData()
					if attachmentContentType!=nil then
						msg << 'Content-Type:'+attachmentContentType+'\r\n'
					else
						msg << 'Content-Type: application/octet-stream\r\n'
					end
					msg << '\r\n'
					msg << Base64.encode64(attachmentData).gsub('\n','\r\n')
					msg << '\r\n'
				end
			end
		end
		msg << '\r\n--'+boundary+'--\r\n'
		msg = msg.gsub("\\r", "\r")
		msg = msg.gsub("\\n", "\n")
		rawresponse=requestProcessor.postMultipart(baseurl,msg,'application/json', @username, @password, boundary)
		response=SendMMSResponse.new()
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
		baseurl=@endpoints.getQueryMMSDeliveryEndpoint()
		requestProcessor=JSONRequest.new()
		if baseurl.index('{senderAddress}')!=nil then
			baseurl=baseurl.gsub('{senderAddress}',CGI::escape(senderAddress.to_s))
		end
		if baseurl.index('{requestId}')!=nil then
			baseurl=baseurl.gsub('{requestId}',CGI::escape(requestId.to_s))
		end
		rawresponse=requestProcessor.get(baseurl,'application/json', @username, @password)
		response=MMSSendDeliveryStatusResponse.new()
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
		baseurl=@endpoints.getMMSDeliverySubscriptionsEndpoint()
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
		response=MMSDeliveryReceiptSubscriptionResponse.new()
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
		baseurl=@endpoints.getCancelMMSDeliverySubscriptionEndpoint()
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
