
class ChargingInformation

	def initialize
		@amount=0.0
		@currency=nil
		@description=nil
	end

	def initializeJSON(jsondict)
		@amount=0.0
		if (jsondict!=nil) && (jsondict.has_key?'amount') && (jsondict['amount']!=nil)
			@amount=jsondict['amount'].to_f
		end
		@currency=nil
		if (jsondict!=nil) && (jsondict.has_key?'currency') && (jsondict['currency']!=nil)
			@currency=jsondict['currency']
		end
		@description=nil
		if (jsondict!=nil) && (jsondict.has_key?'description') && (jsondict['description']!=nil)
			@description=jsondict['description']
		end
	end

	def getAmount
		@amount
	end

	def setAmount(amount)
		@amount=amount
	end

	def getCurrency
		@currency
	end

	def setCurrency(currency)
		@currency=currency
	end

	def getDescription
		@description
	end

	def setDescription(description)
		@description=description
	end


end 
