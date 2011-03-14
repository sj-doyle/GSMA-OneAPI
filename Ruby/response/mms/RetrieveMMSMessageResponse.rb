require "response/mms/InboundMessage"

class RetrieveMMSMessageResponse

	def initialize
		@httpResponseCode=0
		@contentType=nil
		@location=nil
		@inboundMessage=nil
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

	def getInboundMessage
		@inboundMessage
	end

	def setInboundMessage(inboundMessage)
		@inboundMessage=inboundMessage
	end

	def setInboundMessageJSON(jsondata)
		@inboundMessage=InboundMessage.new
		@inboundMessage.initializeJSON(jsondata)
	end
	def getAttachment
		@attachment
	end

	def setAttachment(attachment)
		@attachment=attachment
	end


end 
