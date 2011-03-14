
class PaymentAmount

	def initialize
		@amountReserved=0.0
		@chargingInformation=nil
		@totalAmountCharged=0.0
	end

	def initializeJSON(jsondict)
		@amountReserved=0.0
		if (jsondict!=nil) && (jsondict.has_key?'amountReserved') && (jsondict['amountReserved']!=nil)
			@amountReserved=jsondict['amountReserved'].to_f
		end
		@chargingInformation=nil
		if (jsondict!=nil) && (jsondict.has_key?'chargingInformation') && (jsondict['chargingInformation']!=nil) then
			@chargingInformation=ChargingInformation.new
			@chargingInformation.initializeJSON(jsondict['chargingInformation'])
		end
		@totalAmountCharged=0.0
		if (jsondict!=nil) && (jsondict.has_key?'totalAmountCharged') && (jsondict['totalAmountCharged']!=nil)
			@totalAmountCharged=jsondict['totalAmountCharged'].to_f
		end
	end

	def getAmountReserved
		@amountReserved
	end

	def setAmountReserved(amountReserved)
		@amountReserved=amountReserved
	end

	def getChargingInformation
		@chargingInformation
	end

	def setChargingInformation(chargingInformation)
		@chargingInformation=chargingInformation
	end

	def getTotalAmountCharged
		@totalAmountCharged
	end

	def setTotalAmountCharged(totalAmountCharged)
		@totalAmountCharged=totalAmountCharged
	end


end 
