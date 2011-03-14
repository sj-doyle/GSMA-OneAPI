require "response/mms/DeliveryInfoList"

class MMSSendDeliveryStatusResponse

	def initialize
		@httpResponseCode=0
		@contentType=nil
		@location=nil
		@deliveryInfoList=nil
	end

	def getHTTPResponseCode
		@httpResponseCode
	end

	def setHTTPResponseCode(httpResponseCode)
		@httpResponseCode=httpResponseCode
	end

	def getContentType
		@contentType
	end

	def setContentType(contentType)
		@contentType=contentType
	end

	def getLocation
		@location
	end

	def setLocation(location)
		@location=location
	end

	def getDeliveryInfoList
		@deliveryInfoList
	end

	def setDeliveryInfoList(deliveryInfoList)
		@deliveryInfoList=deliveryInfoList
	end

	def setDeliveryInfoListJSON(jsondata)
		@deliveryInfoList=DeliveryInfoList.new
		@deliveryInfoList.initializeJSON(jsondata)
	end

end 
