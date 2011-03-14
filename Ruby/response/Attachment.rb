
class Attachment

	def initialize
		@name=nil
		@contentType=nil
		@data=nil
	end

	def initializeJSON(jsondict)
		@name=nil
		if (jsondict!=nil) && (jsondict.has_key?'name') && (jsondict['name']!=nil)
			@name=jsondict['name']
		end
		@contentType=nil
		if (jsondict!=nil) && (jsondict.has_key?'contentType') && (jsondict['contentType']!=nil)
			@contentType=jsondict['contentType']
		end
		@data=nil
		if (jsondict!=nil) && (jsondict.has_key?'data') && (jsondict['data']!=nil)
			@data=jsondict['data']
		end
	end

	def getName
		@name
	end

	def setName(name)
		@name=name
	end

	def getContentType
		@contentType
	end

	def setContentType(contentType)
		@contentType=contentType
	end

	def getData
		@data
	end

	def setData(data)
		@data=data
	end


end 
