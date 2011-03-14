require "response/sms/DeliveryReceiptSubscription"

class SMSDeliveryReceiptSubscriptionResponse

	def initialize
		@httpResponseCode=0
		@contentType=nil
		@location=nil
		@deliveryReceiptSubscription=nil
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

	def getDeliveryReceiptSubscription
		@deliveryReceiptSubscription
	end

	def setDeliveryReceiptSubscription(deliveryReceiptSubscription)
		@deliveryReceiptSubscription=deliveryReceiptSubscription
	end

	def setDeliveryReceiptSubscriptionJSON(jsondata)
		@deliveryReceiptSubscription=DeliveryReceiptSubscription.new
		@deliveryReceiptSubscription.initializeJSON(jsondata)
	end

end 
