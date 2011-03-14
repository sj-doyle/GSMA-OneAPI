
class HTTPResponse

	def initialize
		@code=0
		@content=nil
		@contentType=nil
		@location=nil
	end

	def initializeJSON(jsondict)
		@code=0
		if (jsondict!=nil) && (jsondict.has_key?'code') && (jsondict['code']!=nil)
			@code=jsondict['code'].to_i
		end
		@content=nil
		if (jsondict!=nil) && (jsondict.has_key?'content') && (jsondict['content']!=nil)
			@content=jsondict['content']
		end
		@contentType=nil
		if (jsondict!=nil) && (jsondict.has_key?'contentType') && (jsondict['contentType']!=nil)
			@contentType=jsondict['contentType']
		end
		@location=nil
		if (jsondict!=nil) && (jsondict.has_key?'location') && (jsondict['location']!=nil)
			@location=jsondict['location']
		end
	end

	def getCode
		@code
	end

	def setCode(code)
		@code=code
	end

	def getContent
		@content
	end

	def setContent(content)
		@content=content
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


end 
