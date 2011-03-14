require "response/mms/InboundMMSMessage"

class InboundMessageList

	def initialize
		@inboundMessage=nil
		@numberOfMessagesInThisBatch=0
		@resourceURL=nil
		@totalNumberOfPendingMessages=0
	end

	def initializeJSON(jsondict)
		@inboundMessage=nil
		if (jsondict!=nil) && (jsondict.has_key?'inboundMessage') && (jsondict['inboundMessage']!=nil)
			@inboundMessage=Array.new()
			fieldValue=jsondict['inboundMessage']
			if fieldValue.kind_of?Array
				for item in fieldValue
					ai=@inboundMessage.length
					@inboundMessage[ai]=InboundMMSMessage.new()
					@inboundMessage[ai].initializeJSON(item)
				end
			else
				@inboundMessage[0]=InboundMMSMessage.new()
				@inboundMessage[0].initializeJSON(fieldValue)
			end
		end
		@numberOfMessagesInThisBatch=0
		if (jsondict!=nil) && (jsondict.has_key?'numberOfMessagesInThisBatch') && (jsondict['numberOfMessagesInThisBatch']!=nil)
			@numberOfMessagesInThisBatch=jsondict['numberOfMessagesInThisBatch'].to_i
		end
		@resourceURL=nil
		if (jsondict!=nil) && (jsondict.has_key?'resourceURL') && (jsondict['resourceURL']!=nil)
			@resourceURL=jsondict['resourceURL']
		end
		@totalNumberOfPendingMessages=0
		if (jsondict!=nil) && (jsondict.has_key?'totalNumberOfPendingMessages') && (jsondict['totalNumberOfPendingMessages']!=nil)
			@totalNumberOfPendingMessages=jsondict['totalNumberOfPendingMessages'].to_i
		end
	end

	def getInboundMessage
		@inboundMessage
	end

	def setInboundMessage(inboundMessage)
		@inboundMessage=inboundMessage
	end

	def getNumberOfMessagesInThisBatch
		@numberOfMessagesInThisBatch
	end

	def setNumberOfMessagesInThisBatch(numberOfMessagesInThisBatch)
		@numberOfMessagesInThisBatch=numberOfMessagesInThisBatch
	end

	def getResourceURL
		@resourceURL
	end

	def setResourceURL(resourceURL)
		@resourceURL=resourceURL
	end

	def getTotalNumberOfPendingMessages
		@totalNumberOfPendingMessages
	end

	def setTotalNumberOfPendingMessages(totalNumberOfPendingMessages)
		@totalNumberOfPendingMessages=totalNumberOfPendingMessages
	end


end 
