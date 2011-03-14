require "response/mms/InboundMessage"

class InboundMessageNotification

	def initialize
		@callbackData=nil
		@inboundMessage=nil
	end

	def initializeJSON(jsondict)
		@callbackData=nil
		if (jsondict!=nil) && (jsondict.has_key?'callbackData') && (jsondict['callbackData']!=nil)
			@callbackData=jsondict['callbackData']
		end
		@inboundMessage=nil
		if (jsondict!=nil) && (jsondict.has_key?'inboundMessage') && (jsondict['inboundMessage']!=nil) then
			@inboundMessage=InboundMessage.new
			@inboundMessage.initializeJSON(jsondict['inboundMessage'])
		end
	end

	def getCallbackData
		@callbackData
	end

	def setCallbackData(callbackData)
		@callbackData=callbackData
	end

	def getInboundMessage
		@inboundMessage
	end

	def setInboundMessage(inboundMessage)
		@inboundMessage=inboundMessage
	end


end 
