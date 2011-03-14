
class InboundMMSMessage

	def initialize
		@subject=nil
		@message=nil
	end

	def initializeJSON(jsondict)
		@subject=nil
		if (jsondict!=nil) && (jsondict.has_key?'subject') && (jsondict['subject']!=nil)
			@subject=jsondict['subject']
		end
		@message=nil
		if (jsondict!=nil) && (jsondict.has_key?'message') && (jsondict['message']!=nil)
			@message=jsondict['message']
		end
	end

	def getSubject
		@subject
	end

	def setSubject(subject)
		@subject=subject
	end

	def getMessage
		@message
	end

	def setMessage(message)
		@message=message
	end


end 
