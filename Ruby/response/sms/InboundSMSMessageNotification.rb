require "response/sms/InboundSMSMessage"

class InboundSMSMessageNotification

	def initialize
		@callbackData=nil
		@inboundSMSMessage=nil
	end

	def initializeJSON(jsondict)
		@callbackData=nil
		if (jsondict!=nil) && (jsondict.has_key?'callbackData') && (jsondict['callbackData']!=nil)
			@callbackData=jsondict['callbackData']
		end
		@inboundSMSMessage=nil
		if (jsondict!=nil) && (jsondict.has_key?'inboundSMSMessage') && (jsondict['inboundSMSMessage']!=nil) then
			@inboundSMSMessage=InboundSMSMessage.new
			@inboundSMSMessage.initializeJSON(jsondict['inboundSMSMessage'])
		end
	end

	def getCallbackData
		@callbackData
	end

	def setCallbackData(callbackData)
		@callbackData=callbackData
	end

	def getInboundSMSMessage
		@inboundSMSMessage
	end

	def setInboundSMSMessage(inboundSMSMessage)
		@inboundSMSMessage=inboundSMSMessage
	end


end 
