
class DeliveryInfo

	def initialize
		@address=nil
		@deliveryStatus=nil
	end

	def initializeJSON(jsondict)
		@address=nil
		if (jsondict!=nil) && (jsondict.has_key?'address') && (jsondict['address']!=nil)
			@address=jsondict['address']
		end
		@deliveryStatus=nil
		if (jsondict!=nil) && (jsondict.has_key?'deliveryStatus') && (jsondict['deliveryStatus']!=nil)
			@deliveryStatus=jsondict['deliveryStatus']
		end
	end

	def getAddress
		@address
	end

	def setAddress(address)
		@address=address
	end

	def getDeliveryStatus
		@deliveryStatus
	end

	def setDeliveryStatus(deliveryStatus)
		@deliveryStatus=deliveryStatus
	end


end 
