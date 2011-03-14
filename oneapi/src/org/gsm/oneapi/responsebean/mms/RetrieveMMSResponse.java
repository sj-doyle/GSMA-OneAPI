package org.gsm.oneapi.responsebean.mms;

import org.gsm.oneapi.responsebean.RequestError;

/**
 * The full response from the OneAPI server for a request to list received MMS messages
 */
public class RetrieveMMSResponse implements java.io.Serializable {	
	private static final long serialVersionUID = 8629657387805466843L;

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
	 * inboundMessageList contains a count of the number of available MMS messages, and an array of MMS message (headers)
	 */
	InboundMessageList inboundMessageList=null;
	/**
	 * return the count of the number of available MMS messages, and an array of MMS message (headers)
	 */
	public InboundMessageList getInboundMessageList() { return inboundMessageList; }
	/**
	 * set the inboundMessageList element. This is called internally to set the contents according to the JSON response.
	 * @param inboundMessageList
	 */
	public void setInboundMessageList(InboundMessageList inboundMessageList) { this.inboundMessageList=inboundMessageList; }

	/** 
	 * generate a textual representation of the RetrieveMMSResponse instance including all nested elements and classes 
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
		if (inboundMessageList!=null) {
			buffer.append(", inboundMessageList = {");
			buffer.append(inboundMessageList.toString());
			buffer.append("}");
		}
		return buffer.toString();
	}
	
}
