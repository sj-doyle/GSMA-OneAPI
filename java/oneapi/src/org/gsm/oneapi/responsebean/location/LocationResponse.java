package org.gsm.oneapi.responsebean.location;

import org.gsm.oneapi.responsebean.RequestError;

/**
 * The LocationResponse contains all information returned from the OneAPI server given a request to locate one or more mobile terminals
 */
public class LocationResponse implements java.io.Serializable {
	private static final long serialVersionUID = 7757756298813071945L;

	/**
	 * contains the HTTP response code returned from the server
	 */
	int httpResponseCode=0;
	/** 
	 * contains the HTTP Content-Type returned from the server if available
	 */
	String contentType=null;
	/**
	 * in the case the server has returned an error contains the error response.
	 * 
	 * @see RequestError
	 */
	RequestError requestError=null;

	/**
	 * return the HTTP response code returned from the server
	 */
	public int getHTTPResponseCode() { return httpResponseCode; }
	/**
	 * return the HTTP Content-Type returned from the server if available
	 */
	public String getContentType() { return contentType; }
	/**
	 * return the server generated error response (from the JSON based error response)
	 */
	public RequestError getRequestError() { return requestError; }
	
	/**
	 * set the stored HTTP response code
	 * @param httpResponseCode sets the stored HTTP response code
	 */
	public void setHTTPResponseCode(int httpResponseCode) { this.httpResponseCode=httpResponseCode; }
	/**
	 * set the HTTP Content-Type header returned by the server
	 * @param contentType sets the stored HTTP Content-Type header
	 */
	public void setContentType(String contentType) { this.contentType=contentType; }
	/**
	 * set the contents of the error response
	 * @param requestError sets the contents of the error response
	 * @see RequestError
	 */
	public void setRequestError(RequestError requestError) { this.requestError=requestError; }

	/**
	 * the contents of the HTTP 'Location' header response if available
	 */
	String location=null;
	/** 
	 * return the HTTP location field returned form the server
	 */
	public String getLocation() { return location; }
	/** 
	 * set the HTTP location field
	 * @param location contents of the HTTP location header
	 */
	public void setLocation(String location) { this.location=location; }
	
	/**
	 * the array of TerminalLocation responses is contained within the TerminalLocationList inner class
	 */
	public static class TerminalLocationList {
		/**
		 * each mobile terminal in the request has a corresponding response in the terminalLocation array 
		 */
		TerminalLocation[] terminalLocation=null;

		/**
		 * return the array of terminal location responses
		 */
		public TerminalLocation[] getTerminalLocation() { return this.terminalLocation; }
		/**
		 * set the array of terminal location responses. This is called internally to set the contents according to the JSON response.
		 */
		public void setTerminalLocation(TerminalLocation[] terminalLocation) { this.terminalLocation=terminalLocation; }

		/** 
		 * generate a textual representation of the TerminalLocationList including all nested elements and classes 
		 */
		public String toString() {
			StringBuffer buffer=new StringBuffer();
			
			buffer.append("terminalLocation = {");

			if (terminalLocation!=null) {
				for (int i=0; i<terminalLocation.length; i++) {
					buffer.append("[");
					buffer.append(i);
					buffer.append("] = {");
					if (terminalLocation[i]!=null) buffer.append(terminalLocation[i].toString());
					buffer.append("} ");
				}
			}
			buffer.append("} ");

			return buffer.toString();
		}

	}
	
	/**
	 * the terminalLocationList contains the actual location responses
	 */
	TerminalLocationList terminalLocationList=null;
	/**
	 * return the actual location responses
	 */
	public TerminalLocationList getTerminalLocationList() { return terminalLocationList; }
	/**
	 * set terminalLocationList containing the actual location responses. This is called internally to set the contents according to the JSON response.
	 */
	public void setTerminalLocationList(TerminalLocationList terminalLocationList) { this.terminalLocationList=terminalLocationList ; }
	
	/** 
	 * generate a textual representation of the LocationResponse including all nested elements and classes 
	 */
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("httpResponseCode = "+httpResponseCode);
		buffer.append(", contentType = "+contentType);
		if (requestError!=null) {
			buffer.append(", requestError = {");
			buffer.append(requestError.toString());
			buffer.append("}");
		}
		if (terminalLocationList!=null) {
			buffer.append(", terminalLocationList = {");			
			buffer.append(terminalLocationList.toString());			
			buffer.append("}");
		}
		return buffer.toString();
	}


}
