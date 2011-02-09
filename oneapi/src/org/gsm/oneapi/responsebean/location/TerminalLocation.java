package org.gsm.oneapi.responsebean.location;

import org.gsm.oneapi.foundation.DateParser;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * TerminalLocation contains the result of an attempt to locate a single mobile terminal - either successfully or unsuccessfully
 */
public class TerminalLocation implements java.io.Serializable {
	
	private static final long serialVersionUID = 4670687042831841089L;

	/**
	 * the MSISDN of the mobile terminal 
	 */
	private String address=null;
	/**
	 * the status of the location retrieval request - with possible values: "Retrieved" (success), "NotRetrieved" (unable to retrieve), "Error" (error retrieving location)
	 */
	private String locationRetrievalStatus=null;
	
	/**
	 * return the MSISDN of the mobile terminal 
	 */
	public String getAddress() { return address; }
	/**
	 * set the MSISDN of the mobile terminal. This is called internally to set the contents according to the JSON response.
	 */
	public void setAddress(String address) {this.address=address; }

	/**
	 * return the status of the location retrieval request
	 */
	public String getLocationRetrievalStatus() { return locationRetrievalStatus; }
	/**
	 * set the status of the location retrieval request. This is called internally to set the contents according to the JSON response.
	 */
	public void setLocationRetrievalStatus(String locationRetrievalStatus) { this.locationRetrievalStatus=locationRetrievalStatus; }
	
	/**
	 * the inner class CurrentLocation contains the location information relating to this mobile terminal
	 */
	public static class CurrentLocation {
		/**
		 * accuracy (result accuracy in metres)
		 */
		private Double accuracy=0.0;
		/**
		 * altitude (metres)
		 */
		private Double altitude=0.0;
		/**
		 * latitude (Decimal Degrees, ISO 6709)
		 */
		private Double latitude=0.0;
		/**
		 * longitude (Decimal Degrees, ISO 6709)
		 */
		private Double longitude=0.0;
		/**
		 * timestamp of the positioning request/response
		 */
		private java.util.Date timestamp=null;

		/**
		 * return accuracy in metres
		 */
		public Double getAccuracy() { return accuracy; }
		/**
		 * return altitude in metres
		 */
		public Double getAltitude() { return altitude; }
		/**
		 * return latitude in decimal degrees
		 */
		public Double getLatitude() { return latitude; }
		/**
		 * return longitude in decimal degrees
		 */
		public Double getLongitude() { return longitude; }
		/**
		 * return timestamp as an ISO 8601 format string
		 */
		public String getTimestamp() { return DateParser.getIsoDateNoMillis(timestamp); }

		/**
		 * return timestamp as a java.util.Date object
		 */
		@JsonIgnore
		public java.util.Date getTimestampAsDate() { return timestamp; }
		
		/**
		 * set accuracy in metres. This is called internally to set the contents according to the JSON response.  
		 */
		public void setAccuracy(Double accuracy) { this.accuracy=accuracy; }
		/**
		 * set altitude in metres. This is called internally to set the contents according to the JSON response.  
		 */
		public void setAltitude(Double altitude) { this.altitude=altitude; }
		/**
		 * set latitude in decimal degrees. This is called internally to set the contents according to the JSON response.  
		 */
		public void setLatitude(Double latitude) { this.latitude=latitude; }
		/**
		 * set longitude in decimal degrees. This is called internally to set the contents according to the JSON response.  
		 */
		public void setLongitude(Double longitude) { this.longitude=longitude; }
		/**
		 * set timestamp as an ISO 8601 format string. This is called internally to set the contents according to the JSON response.  
		 */
		public void setTimestamp(String timestamp) { this.timestamp=DateParser.parse(timestamp); }
		
		/**
		 * set timestamp as a java.util.Date object.   
		 */
		@JsonIgnore
		public void setTimestampAsDate(java.util.Date timestamp) { this.timestamp=timestamp; }
	}
	
	/**
	 * in the case there is an error raised by the server the details of the error are included within this customised error response
	 */
	public static class RequestError {
		/**
		 *  for the terminal location the class of error that can be raised is a service exception
		 */
		public static class ServiceException {
			/**
			 * the distinctive error message identifier
			 */
			private String messageId=null;
			/**
			 * the textual representation of the error
			 */
			private String text=null;
			/**
			 * any instance specific error variables
			 */
			private String variables=null;
			
			/**
			 * return the distinctive error message identifier
			 */
			public String getMessageId() { return messageId; }
			/**
			 * return the textual representation of the error
			 */
			public String getText() { return text; }
			/**
			 * return any instance specific error variables
			 */
			public String getVariables() { return variables; }
			
			/**
			 * set the distinctive error message identifier. This is called internally to set the contents according to the JSON response. 
			 */
			public void setMessageId(String messageId) { this.messageId=messageId; }
			/**
			 * set the textual representation of the error. This is called internally to set the contents according to the JSON response. 
			 */
			public void setText(String text) { this.text=text; }
			/**
			 * set any instance specific error variables. This is called internally to set the contents according to the JSON response. 
			 */
			public void setVariables(String variables) { this.variables=variables; }
			
			/**
			 * default constructor
			 */
			public ServiceException() {
				
			}
			
			/**
			 * utility constructor to create a ServiceException object with all fields set
			 * @param messageId
			 * @param text
			 * @param variables
			 */
			public ServiceException (String messageId, String text, String variables) {
				this.messageId=messageId;
				this.text=text;
				this.variables=variables;
			}
			
		}
		
		/**
		 * instance of a serviceException
		 */
		private ServiceException serviceException=null;
		
		/**
		 * return the instance of this serviceException
		 */
		public ServiceException getServiceException() { return serviceException; }
		/**
		 * set an instance of a serviceException. This is called internally to set the contents according to the JSON response. 
		 */
		public void setServiceException(ServiceException serviceException) { this.serviceException=serviceException; }
		
		/**
		 * default constructor
		 */
		public RequestError() {
			
		}
		
		/**
		 * utility constructor to create a RequestError.ServiceException object with all fields set
		 * @param messageId
		 * @param text
		 * @param variables
		 */
		public RequestError(String messageId, String text, String variables) {
			serviceException=new ServiceException();
			serviceException.setMessageId(messageId);
			serviceException.setText(text);
			serviceException.setVariables(variables);		
		}
	}

	/**
	 * in case the terminal was successfully located currentLocation contains the location details
	 */
	private CurrentLocation currentLocation=null;
	/**
	 * return the location details for a successful terminal location request
	 */
	public CurrentLocation getCurrentLocation() { return currentLocation; }
	/**
	 * set the location details for a successful terminal location request. This is called internally to set the contents according to the JSON response. 
	 */
	public void setCurrentLocation(CurrentLocation currentLocation) { this.currentLocation=currentLocation; }
	
	/**
	 * in case the terminal was not successfully located contains the errorInformation
	 */
	private RequestError errorInformation=null;
	/**
	 * return the errorInformation in case the mobile terminal was not successfully located
	 */
	public RequestError getErrorInformation() { return errorInformation; }
	/**
	 * set the errorInformation in case the mobile terminal was not successfully located. This is called internally to set the contents according to the JSON response. 
	 */
	public void setErrorInformation(RequestError requestError) { this.errorInformation=requestError; }
	
	/** 
	 * generate a textual representation of the TerminalLocation instance including all nested elements and classes 
	 */
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("address = ");
		buffer.append(address);
		
		buffer.append(", locationRetrievalStatus = ");
		buffer.append(locationRetrievalStatus);

		buffer.append(", currentLocation={");
		
		if (currentLocation!=null) {		
			buffer.append("accuracy = ");
			buffer.append(currentLocation.accuracy);
			buffer.append(", altitude = ");
			buffer.append(currentLocation.altitude);
			buffer.append(", latitude = ");
			buffer.append(currentLocation.latitude);
			buffer.append(", longitude = ");
			buffer.append(currentLocation.longitude);
			buffer.append(", timestamp = ");
			buffer.append(currentLocation.timestamp);
		}
		
		buffer.append("} errorInformation = {");
		
		if (errorInformation!=null && errorInformation.getServiceException()!=null) {
			buffer.append(" serviceException = {messageId = ");
			buffer.append(errorInformation.getServiceException().getMessageId());
			buffer.append(", text = ");
			buffer.append(errorInformation.getServiceException().getText());
			buffer.append(", variables = ");
			buffer.append(errorInformation.getServiceException().getVariables());
			buffer.append("}");
		}
		buffer.append("}");
		
		return buffer.toString();
	}
}
