
class AmountReservationTransaction

	def initialize
		@clientCorrelator=nil
		@endUserId=nil
		@paymentAmount=nil
		@referenceCode=nil
		@referenceSequence=0
		@resourceURL=nil
		@serverReferenceCode=nil
		@transactionStatus=nil
	end

	def initializeJSON(jsondict)
		@clientCorrelator=nil
		if (jsondict!=nil) && (jsondict.has_key?'clientCorrelator') && (jsondict['clientCorrelator']!=nil)
			@clientCorrelator=jsondict['clientCorrelator']
		end
		@endUserId=nil
		if (jsondict!=nil) && (jsondict.has_key?'endUserId') && (jsondict['endUserId']!=nil)
			@endUserId=jsondict['endUserId']
		end
		@paymentAmount=nil
		if (jsondict!=nil) && (jsondict.has_key?'paymentAmount') && (jsondict['paymentAmount']!=nil) then
			@paymentAmount=PaymentAmount.new
			@paymentAmount.initializeJSON(jsondict['paymentAmount'])
		end
		@referenceCode=nil
		if (jsondict!=nil) && (jsondict.has_key?'referenceCode') && (jsondict['referenceCode']!=nil)
			@referenceCode=jsondict['referenceCode']
		end
		@referenceSequence=0
		if (jsondict!=nil) && (jsondict.has_key?'referenceSequence') && (jsondict['referenceSequence']!=nil)
			@referenceSequence=jsondict['referenceSequence'].to_i
		end
		@resourceURL=nil
		if (jsondict!=nil) && (jsondict.has_key?'resourceURL') && (jsondict['resourceURL']!=nil)
			@resourceURL=jsondict['resourceURL']
		end
		@serverReferenceCode=nil
		if (jsondict!=nil) && (jsondict.has_key?'serverReferenceCode') && (jsondict['serverReferenceCode']!=nil)
			@serverReferenceCode=jsondict['serverReferenceCode']
		end
		@transactionStatus=nil
		if (jsondict!=nil) && (jsondict.has_key?'transactionStatus') && (jsondict['transactionStatus']!=nil)
			@transactionStatus=jsondict['transactionStatus']
		end
	end

	def getClientCorrelator
		@clientCorrelator
	end

	def setClientCorrelator(clientCorrelator)
		@clientCorrelator=clientCorrelator
	end

	def getEndUserId
		@endUserId
	end

	def setEndUserId(endUserId)
		@endUserId=endUserId
	end

	def getPaymentAmount
		@paymentAmount
	end

	def setPaymentAmount(paymentAmount)
		@paymentAmount=paymentAmount
	end

	def getReferenceCode
		@referenceCode
	end

	def setReferenceCode(referenceCode)
		@referenceCode=referenceCode
	end

	def getReferenceSequence
		@referenceSequence
	end

	def setReferenceSequence(referenceSequence)
		@referenceSequence=referenceSequence
	end

	def getResourceURL
		@resourceURL
	end

	def setResourceURL(resourceURL)
		@resourceURL=resourceURL
	end

	def getServerReferenceCode
		@serverReferenceCode
	end

	def setServerReferenceCode(serverReferenceCode)
		@serverReferenceCode=serverReferenceCode
	end

	def getTransactionStatus
		@transactionStatus
	end

	def setTransactionStatus(transactionStatus)
		@transactionStatus=transactionStatus
	end


end 
