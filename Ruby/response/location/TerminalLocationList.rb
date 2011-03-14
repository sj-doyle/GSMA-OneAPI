require "response/location/TerminalLocation"

class TerminalLocationList

	def initialize
		@terminalLocation=nil
	end

	def initializeJSON(jsondict)
		@terminalLocation=nil
		if (jsondict!=nil) && (jsondict.has_key?'terminalLocation') && (jsondict['terminalLocation']!=nil)
			@terminalLocation=Array.new()
			fieldValue=jsondict['terminalLocation']
			if fieldValue.kind_of?Array
				for item in fieldValue
					ai=@terminalLocation.length
					@terminalLocation[ai]=TerminalLocation.new()
					@terminalLocation[ai].initializeJSON(item)
				end
			else
				@terminalLocation[0]=TerminalLocation.new()
				@terminalLocation[0].initializeJSON(fieldValue)
			end
		end
	end

	def getTerminalLocation
		@terminalLocation
	end

	def setTerminalLocation(terminalLocation)
		@terminalLocation=terminalLocation
	end


end 
