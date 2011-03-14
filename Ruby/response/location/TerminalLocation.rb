require "response/location/CurrentLocation"
require "response/location/ErrorInformation"

class TerminalLocation

	def initialize
		@address=nil
		@currentLocation=nil
		@errorInformation=nil
		@locationRetrievalStatus=nil
	end

	def initializeJSON(jsondict)
		@address=nil
		if (jsondict!=nil) && (jsondict.has_key?'address') && (jsondict['address']!=nil)
			@address=jsondict['address']
		end
		@currentLocation=nil
		if (jsondict!=nil) && (jsondict.has_key?'currentLocation') && (jsondict['currentLocation']!=nil) then
			@currentLocation=CurrentLocation.new
			@currentLocation.initializeJSON(jsondict['currentLocation'])
		end
		@errorInformation=nil
		if (jsondict!=nil) && (jsondict.has_key?'errorInformation') && (jsondict['errorInformation']!=nil) then
			@errorInformation=ErrorInformation.new
			@errorInformation.initializeJSON(jsondict['errorInformation'])
		end
		@locationRetrievalStatus=nil
		if (jsondict!=nil) && (jsondict.has_key?'locationRetrievalStatus') && (jsondict['locationRetrievalStatus']!=nil)
			@locationRetrievalStatus=jsondict['locationRetrievalStatus']
		end
	end

	def getAddress
		@address
	end

	def setAddress(address)
		@address=address
	end

	def getCurrentLocation
		@currentLocation
	end

	def setCurrentLocation(currentLocation)
		@currentLocation=currentLocation
	end

	def getErrorInformation
		@errorInformation
	end

	def setErrorInformation(errorInformation)
		@errorInformation=errorInformation
	end

	def getLocationRetrievalStatus
		@locationRetrievalStatus
	end

	def setLocationRetrievalStatus(locationRetrievalStatus)
		@locationRetrievalStatus=locationRetrievalStatus
	end


end 
