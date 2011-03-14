require "response/mms/InboundMMSMessage"
require "response/mms/InboundMessageList"
require "response/mms/RetrieveMMSResponse"
require "response/mms/InboundMessage"
require "response/mms/RetrieveMMSMessageResponse"
require "response/ResourceReference"
require "response/mms/MMSMessageReceiptSubscriptionResponse"
require "response/mms/HTTPResponse"
require 'json'
require 'uri'
require 'net/http'
require 'foundation/FormParameters'
require 'foundation/JSONRequest'
require 'Mail'
require 'response/Attachment'

class MMSRetrieve

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
		baseurl=@endpoints.getRetrieveMMSEndpoint()
		requestProcessor=JSONRequest.new()
		if baseurl.index('{registrationId}')!=nil then
			baseurl=baseurl.gsub('{registrationId}',CGI::escape(registrationId.to_s))
		end
		if baseurl.index('{maxBatchSize}')!=nil then
			baseurl=baseurl.gsub('{maxBatchSize}',CGI::escape(maxBatchSize.to_s))
		end
		rawresponse=requestProcessor.get(baseurl,'application/json', @username, @password)
		response=RetrieveMMSResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['inboundMessageList']!=nil) then
				response.setInboundMessageListJSON(jsondata['inboundMessageList'])
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
	
	def retrieveMessageContent(registrationId,messageId,resFormat)
		baseurl=@endpoints.getRetrieveMMSMessageEndpoint()
		requestProcessor=JSONRequest.new()
		if baseurl.index('{registrationId}')!=nil then
			baseurl=baseurl.gsub('{registrationId}',CGI::escape(registrationId.to_s))
		end
		if baseurl.index('{messageId}')!=nil then
			baseurl=baseurl.gsub('{messageId}',CGI::escape(messageId.to_s))
		end
		if baseurl.index('{resFormat}')!=nil then
			baseurl=baseurl.gsub('{resFormat}',CGI::escape(resFormat.to_s))
		end
		rawresponse=requestProcessor.get(baseurl,'application/json', @username, @password)
		response=RetrieveMMSMessageResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil) then
			mimecontent="Content-Type: "+rawresponse.getContentType()+'\r\n\r\n'+rawresponse.getContent()
			mimemsg=Mail.read_from_string(mimecontent.gsub("\\t","\t").gsub("\\n","\n").gsub("\\r","\r").gsub("\\\"","\""))
			response=RetrieveMMSMessageResponse.new()
			attachments=nil
			for part in mimemsg.parts
				contentType=part.content_type.strip
				if (contentType.index(";")!=nil)
					ct=contentType.split(";")
					contentType=ct[0].strip
				end
				filename=nil
				contentDisposition=part.content_disposition
				if (contentDisposition!=nil)
					cdp=contentDisposition.split(";")
					for cvp in cdp
						kv=cvp.split("=")
						if kv[0].strip=="name"
							filename=kv[1].gsub("\"","").strip
						end
					end
				end
				if contentType!='multipart/mixed'
					payload=part.body.to_s
					if (contentType!=nil) && (contentType=='application/json') && (filename!=nil) && (filename=="root-fields") && (payload!=nil)
						jsondata=JSON.parse(payload)
						if (jsondata!=nil) && (jsondata['inboundMessage']!=nil)
							response.setInboundMessage(jsondata['inboundMessage'])
						end
					else
						attachment=Attachment.new
						attachment.setContentType(contentType)
						attachment.setName(filename)
						attachment.setData(payload)
						if attachments==nil
							attachments=Array.new
						end
						attachments[attachments.length]=attachment
					end
				end
			end
			response.setAttachment(attachments)
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
		baseurl=@endpoints.getMMSReceiptSubscriptionsEndpoint()
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
		response=MMSMessageReceiptSubscriptionResponse.new()
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
		baseurl=@endpoints.getCancelMMSReceiptSubscriptionEndpoint()
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
