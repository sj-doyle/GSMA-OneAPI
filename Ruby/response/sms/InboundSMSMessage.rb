
class InboundSMSMessage

	def initialize
		@dateTime=nil
		@destinationAddress=nil
		@senderAddress=nil
		@messageId=nil
		@message=nil
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
		@messageId=nil
		if (jsondict!=nil) && (jsondict.has_key?'messageId') && (jsondict['messageId']!=nil)
			@messageId=jsondict['messageId']
		end
		@message=nil
		if (jsondict!=nil) && (jsondict.has_key?'message') && (jsondict['message']!=nil)
			@message=jsondict['message']
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

	def getMessageId
		@messageId
	end

	def setMessageId(messageId)
		@messageId=messageId
	end

	def getMessage
		@message
	end

	def setMessage(message)
		@message=message
	end

	def getResourceURL
		@resourceURL
	end

	def setResourceURL(resourceURL)
		@resourceURL=resourceURL
	end


end 
