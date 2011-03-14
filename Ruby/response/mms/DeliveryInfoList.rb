require "response/mms/DeliveryInfo"

class DeliveryInfoList

	def initialize
		@deliveryInfo=nil
		@resourceURL=nil
	end

	def initializeJSON(jsondict)
		@deliveryInfo=nil
		if (jsondict!=nil) && (jsondict.has_key?'deliveryInfo') && (jsondict['deliveryInfo']!=nil)
			@deliveryInfo=Array.new()
			fieldValue=jsondict['deliveryInfo']
			if fieldValue.kind_of?Array
				for item in fieldValue
					ai=@deliveryInfo.length
					@deliveryInfo[ai]=DeliveryInfo.new()
					@deliveryInfo[ai].initializeJSON(item)
				end
			else
				@deliveryInfo[0]=DeliveryInfo.new()
				@deliveryInfo[0].initializeJSON(fieldValue)
			end
		end
		@resourceURL=nil
		if (jsondict!=nil) && (jsondict.has_key?'resourceURL') && (jsondict['resourceURL']!=nil)
			@resourceURL=jsondict['resourceURL']
		end
	end

	def getDeliveryInfo
		@deliveryInfo
	end

	def setDeliveryInfo(deliveryInfo)
		@deliveryInfo=deliveryInfo
	end

	def getResourceURL
		@resourceURL
	end

	def setResourceURL(resourceURL)
		@resourceURL=resourceURL
	end


end 
