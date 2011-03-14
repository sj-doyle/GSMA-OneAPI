require "response/mms/InboundMMSMessage"

class InboundMessage

	def initialize
		@dateTime=nil
		@destinationAddress=nil
		@senderAddress=nil
		@inboundMMSMessage=nil
		@messageId=nil
		@resourceURL=nil
	end

	def initializeJSON(jsondict)
		@dateTime=nil
		if (jsondict!=nil) && (jsondict.has_key?'dateTime') && (jsondict['dateTime']!=nil)
			@dateTime=jsondict['dateTime']
		end
		@destinationAddress=nil
		if (jsondict!=nil) && (jsondict.has_key?'destinationAddress') && (jsondict['destinationAddress']!=nil)
			@destinationAddress=jsondict['destinationAddress']
		end
		@senderAddress=nil
		if (jsondict!=nil) && (jsondict.has_key?'senderAddress') && (jsondict['senderAddress']!=nil)
			@senderAddress=jsondict['senderAddress']
		end
		@inboundMMSMessage=nil
		if (jsondict!=nil) && (jsondict.has_key?'inboundMMSMessage') && (jsondict['inboundMMSMessage']!=nil) then
			@inboundMMSMessage=InboundMMSMessage.new
			@inboundMMSMessage.initializeJSON(jsondict['inboundMMSMessage'])
		end
		@messageId=nil
		if (jsondict!=nil) && (jsondict.has_key?'messageId') && (jsondict['messageId']!=nil)
			@messageId=jsondict['messageId']
		end
		@resourceURL=nil
		if (jsondict!=nil) && (jsondict.has_key?'resourceURL') && (jsondict['resourceURL']!=nil)
			@resourceURL=jsondict['resourceURL']
		end
	end

	def getDateTime
		@dateTime
	end

	def setDateTime(dateTime)
		@dateTime=dateTime
	end

	def getDestinationAddress
		@destinationAddress
	end

	def setDestinationAddress(destinationAddress)
		@destinationAddress=destinationAddress
	end

	def getSenderAddress
		@senderAddress
	end

	def setSenderAddress(senderAddress)
		@senderAddress=senderAddress
	end

	def getInboundMMSMessage
		@inboundMMSMessage
	end

	def setInboundMMSMessage(inboundMMSMessage)
		@inboundMMSMessage=inboundMMSMessage
	end

	def getMessageId
		@messageId
	end

	def setMessageId(messageId)
		@messageId=messageId
	end

	def getResourceURL
		@resourceURL
	end

	def setResourceURL(resourceURL)
		@resourceURL=resourceURL
	end


end 
