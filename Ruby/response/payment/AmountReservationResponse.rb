require "response/payment/AmountReservationTransaction"

class AmountReservationResponse

	def initialize
		@httpResponseCode=0
		@contentType=nil
		@location=nil
		@amountReservationTransaction=nil
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

	def getAmountReservationTransaction
		@amountReservationTransaction
	end

	def setAmountReservationTransaction(amountReservationTransaction)
		@amountReservationTransaction=amountReservationTransaction
	end

	def setAmountReservationTransactionJSON(jsondata)
		@amountReservationTransaction=AmountReservationTransaction.new
		@amountReservationTransaction.initializeJSON(jsondata)
	end

end 
