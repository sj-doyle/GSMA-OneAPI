require "response/mms/InboundMessageList"

class RetrieveMMSResponse

	def initialize
		@httpResponseCode=0
		@contentType=nil
		@location=nil
		@inboundMessageList=nil
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

	def getInboundMessageList
		@inboundMessageList
	end

	def setInboundMessageList(inboundMessageList)
		@inboundMessageList=inboundMessageList
	end

	def setInboundMessageListJSON(jsondata)
		@inboundMessageList=InboundMessageList.new
		@inboundMessageList.initializeJSON(jsondata)
	end

end 
