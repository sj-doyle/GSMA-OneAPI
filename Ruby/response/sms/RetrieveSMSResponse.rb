require "response/sms/InboundSMSMessageList"

class RetrieveSMSResponse

	def initialize
		@httpResponseCode=0
		@contentType=nil
		@location=nil
		@inboundSMSMessageList=nil
	end

	def getHTTPResponseCode
		@httpResponseCode
	end

	def setHTTPResponseCode(httpResponseCode)
		@httpResponseCode=httpResponseCode
	end

	def getContentType
		@contentType
	end

	def setContentType(contentType)
		@contentType=contentType
	end

	def getLocation
		@location
	end

	def setLocation(location)
		@location=location
	end

	def getInboundSMSMessageList
		@inboundSMSMessageList
	end

	def setInboundSMSMessageList(inboundSMSMessageList)
		@inboundSMSMessageList=inboundSMSMessageList
	end

	def setInboundSMSMessageListJSON(jsondata)
		@inboundSMSMessageList=InboundSMSMessageList.new
		@inboundSMSMessageList.initializeJSON(jsondata)
	end

end 
