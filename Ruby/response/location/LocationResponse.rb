require "response/location/TerminalLocationList"

class LocationResponse

	def initialize
		@httpResponseCode=0
		@contentType=nil
		@location=nil
		@terminalLocationList=nil
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

	def getTerminalLocationList
		@terminalLocationList
	end

	def setTerminalLocationList(terminalLocationList)
		@terminalLocationList=terminalLocationList
	end

	def setTerminalLocationListJSON(jsondata)
		@terminalLocationList=TerminalLocationList.new
		@terminalLocationList.initializeJSON(jsondata)
	end

end 
