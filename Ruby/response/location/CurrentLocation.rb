
class CurrentLocation

	def initialize
		@latitude=0.0
		@longitude=0.0
		@altitude=0.0
		@accuracy=0.0
		@timestamp=nil
	end

	def initializeJSON(jsondict)
		@latitude=0.0
		if (jsondict!=nil) && (jsondict.has_key?'latitude') && (jsondict['latitude']!=nil)
			@latitude=jsondict['latitude'].to_f
		end
		@longitude=0.0
		if (jsondict!=nil) && (jsondict.has_key?'longitude') && (jsondict['longitude']!=nil)
			@longitude=jsondict['longitude'].to_f
		end
		@altitude=0.0
		if (jsondict!=nil) && (jsondict.has_key?'altitude') && (jsondict['altitude']!=nil)
			@altitude=jsondict['altitude'].to_f
		end
		@accuracy=0.0
		if (jsondict!=nil) && (jsondict.has_key?'accuracy') && (jsondict['accuracy']!=nil)
			@accuracy=jsondict['accuracy'].to_f
		end
		@timestamp=nil
		if (jsondict!=nil) && (jsondict.has_key?'timestamp') && (jsondict['timestamp']!=nil)
			@timestamp=jsondict['timestamp']
		end
	end

	def getLatitude
		@latitude
	end

	def setLatitude(latitude)
		@latitude=latitude
	end

	def getLongitude
		@longitude
	end

	def setLongitude(longitude)
		@longitude=longitude
	end

	def getAltitude
		@altitude
	end

	def setAltitude(altitude)
		@altitude=altitude
	end

	def getAccuracy
		@accuracy
	end

	def setAccuracy(accuracy)
		@accuracy=accuracy
	end

	def getTimestamp
		@timestamp
	end

	def setTimestamp(timestamp)
		@timestamp=timestamp
	end


end 
