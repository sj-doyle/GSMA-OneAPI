require "response/mms/CallbackReference"

class DeliveryReceiptSubscription

	def initialize
		@callbackReference=nil
		@resourceURL=nil
	end

	def initializeJSON(jsondict)
		@callbackReference=nil
		if (jsondict!=nil) && (jsondict.has_key?'callbackReference') && (jsondict['callbackReference']!=nil) then
			@callbackReference=CallbackReference.new
			@callbackReference.initializeJSON(jsondict['callbackReference'])
		end
		@resourceURL=nil
		if (jsondict!=nil) && (jsondict.has_key?'resourceURL') && (jsondict['resourceURL']!=nil)
			@resourceURL=jsondict['resourceURL']
		end
	end

	def getCallbackReference
		@callbackReference
	end

	def setCallbackReference(callbackReference)
		@callbackReference=callbackReference
	end

	def getResourceURL
		@resourceURL
	end

	def setResourceURL(resourceURL)
		@resourceURL=resourceURL
	end


end 
