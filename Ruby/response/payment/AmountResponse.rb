require "response/payment/AmountTransaction"

class AmountResponse

	def initialize
		@httpResponseCode=0
		@contentType=nil
		@location=nil
		@amountTransaction=nil
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

	def getAmountTransaction
		@amountTransaction
	end

	def setAmountTransaction(amountTransaction)
		@amountTransaction=amountTransaction
	end

	def setAmountTransactionJSON(jsondata)
		@amountTransaction=AmountTransaction.new
		@amountTransaction.initializeJSON(jsondata)
	end

end 
