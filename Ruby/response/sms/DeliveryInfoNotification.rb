require "response/sms/DeliveryInfo"

class DeliveryInfoNotification

	def initialize
		@callbackData=nil
		@deliveryInfo=nil
	end

	def initializeJSON(jsondict)
		@callbackData=nil
		if (jsondict!=nil) && (jsondict.has_key?'callbackData') && (jsondict['callbackData']!=nil)
			@callbackData=jsondict['callbackData']
		end
		@deliveryInfo=nil
		if (jsondict!=nil) && (jsondict.has_key?'deliveryInfo') && (jsondict['deliveryInfo']!=nil) then
			@deliveryInfo=DeliveryInfo.new
			@deliveryInfo.initializeJSON(jsondict['deliveryInfo'])
		end
	end

	def getCallbackData
		@callbackData
	end

	def setCallbackData(callbackData)
		@callbackData=callbackData
	end

	def getDeliveryInfo
		@deliveryInfo
	end

	def setDeliveryInfo(deliveryInfo)
		@deliveryInfo=deliveryInfo
	end


end 
