
class FormParameter
  
	def initialize(key=nil, value=nil)
		@key=key
		@value=value
	end
	
	def getKey 
		@key
	end
		
	def getValue
		@value
	end
	
	def setKey(key)
		@key=key
	end
		
	def setValue(value)
		@value=value
	end
	
end
	