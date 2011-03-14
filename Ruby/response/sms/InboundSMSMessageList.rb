require "response/sms/InboundSMSMessage"

class InboundSMSMessageList

	def initialize
		@inboundSMSMessage=nil
		@numberOfMessagesInThisBatch=0
		@resourceURL=nil
		@totalNumberOfPendingMessages=0
	end

	def initializeJSON(jsondict)
		@inboundSMSMessage=nil
		if (jsondict!=nil) && (jsondict.has_key?'inboundSMSMessage') && (jsondict['inboundSMSMessage']!=nil)
			@inboundSMSMessage=Array.new()
			fieldValue=jsondict['inboundSMSMessage']
			if fieldValue.kind_of?Array
				for item in fieldValue
					ai=@inboundSMSMessage.length
					@inboundSMSMessage[ai]=InboundSMSMessage.new()
					@inboundSMSMessage[ai].initializeJSON(item)
				end
			else
				@inboundSMSMessage[0]=InboundSMSMessage.new()
				@inboundSMSMessage[0].initializeJSON(fieldValue)
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

	def getInboundSMSMessage
		@inboundSMSMessage
	end

	def setInboundSMSMessage(inboundSMSMessage)
		@inboundSMSMessage=inboundSMSMessage
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
