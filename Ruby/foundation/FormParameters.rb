
require 'cgi'

require 'foundation/FormParameter'

class FormParameters 

	def initialize
		@parameterSet=Array.new()
	end
		
	def put(key, value)
		fp=FormParameter.new(key, value)
		@parameterSet[@parameterSet.length]=fp
	end
	
	def getParameterSet
		@parameterSet
	end
	
	def encodeParameters
	  rv=""
	  i=0
	  for fp in @parameterSet
	    if (fp.getKey!=nil) && (fp.getValue!=nil)
	      if i>0 
	        rv << "&"
	      end
	      rv << CGI::escape(fp.getKey.to_s)
	      rv << '='
	      rv << CGI::escape(fp.getValue.to_s)
	      i=i+1
	    end
	  end
	  return rv
	end
	
end
	
