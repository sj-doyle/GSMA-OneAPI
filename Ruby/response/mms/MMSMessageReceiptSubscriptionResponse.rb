require "response/ResourceReference"

class MMSMessageReceiptSubscriptionResponse

	def initialize
		@httpResponseCode=0
		@contentType=nil
		@location=nil
		@resourceReference=nil
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

	def getResourceReference
		@resourceReference
	end

	def setResourceReference(resourceReference)
		@resourceReference=resourceReference
	end

	def setResourceReferenceJSON(jsondata)
		@resourceReference=ResourceReference.new
		@resourceReference.initializeJSON(jsondata)
	end

end 
