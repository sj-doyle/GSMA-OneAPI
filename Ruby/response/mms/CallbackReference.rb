
class CallbackReference

	def initialize
		@notifyURL=nil
		@callbackData=nil
	end

	def initializeJSON(jsondict)
		@notifyURL=nil
		if (jsondict!=nil) && (jsondict.has_key?'notifyURL') && (jsondict['notifyURL']!=nil)
			@notifyURL=jsondict['notifyURL']
		end
		@callbackData=nil
		if (jsondict!=nil) && (jsondict.has_key?'callbackData') && (jsondict['callbackData']!=nil)
			@callbackData=jsondict['callbackData']
		end
	end

	def getNotifyURL
		@notifyURL
	end

	def setNotifyURL(notifyURL)
		@notifyURL=notifyURL
	end

	def getCallbackData
		@callbackData
	end

	def setCallbackData(callbackData)
		@callbackData=callbackData
	end


end 
