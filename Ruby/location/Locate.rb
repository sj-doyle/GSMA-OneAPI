require "response/location/CurrentLocation"
require "response/ServiceException"
require "response/PolicyException"
require "response/location/ErrorInformation"
require "response/location/TerminalLocation"
require "response/location/TerminalLocationList"
require "response/location/LocationResponse"
require 'json'
require 'uri'
require 'net/http'
require 'foundation/FormParameters'
require 'foundation/JSONRequest'

class Locate

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

	
	def locateTerminal(address,requestedAccuracy)
		baseurl=@endpoints.getLocationEndpoint()
		requestProcessor=JSONRequest.new()
		baseurl=requestProcessor.addQueryParameter(baseurl,'address', address)
		baseurl=requestProcessor.addQueryParameter(baseurl,'requestedAccuracy', requestedAccuracy)
		rawresponse=requestProcessor.get(baseurl,'application/json', @username, @password)
		response=LocationResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['terminalLocationList']!=nil) then
				response.setTerminalLocationListJSON(jsondata['terminalLocationList'])
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
	
	def locateMultipleTerminals(address,requestedAccuracy)
		baseurl=@endpoints.getLocationEndpoint()
		requestProcessor=JSONRequest.new()
		if address!=nil
			for item in address
				baseurl=requestProcessor.addQueryParameter(baseurl,'address',item)
			end
		end
		baseurl=requestProcessor.addQueryParameter(baseurl,'requestedAccuracy', requestedAccuracy)
		rawresponse=requestProcessor.get(baseurl,'application/json', @username, @password)
		response=LocationResponse.new()
		if (rawresponse!=nil) && (rawresponse.getContent()!=nil)
			jsondata=JSON.parse(rawresponse.getContent())
			if (jsondata!=nil) && (jsondata['terminalLocationList']!=nil) then
				response.setTerminalLocationListJSON(jsondata['terminalLocationList'])
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
