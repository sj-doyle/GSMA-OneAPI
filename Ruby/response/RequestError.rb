require "response/ServiceException"
require "response/PolicyException"

class RequestError

	def initialize
		@serviceException=nil
		@policyException=nil
	end

	def initializeJSON(jsondict)
		@serviceException=nil
		if (jsondict!=nil) && (jsondict.has_key?'serviceException') && (jsondict['serviceException']!=nil) then
			@serviceException=ServiceException.new
			@serviceException.initializeJSON(jsondict['serviceException'])
		end
		@policyException=nil
		if (jsondict!=nil) && (jsondict.has_key?'policyException') && (jsondict['policyException']!=nil) then
			@policyException=PolicyException.new
			@policyException.initializeJSON(jsondict['policyException'])
		end
	end

	def getServiceException
		@serviceException
	end

	def setServiceException(serviceException)
		@serviceException=serviceException
	end

	def getPolicyException
		@policyException
	end

	def setPolicyException(policyException)
		@policyException=policyException
	end


end 
