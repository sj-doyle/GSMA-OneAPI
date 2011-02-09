package org.gsm.oneapi.responsebean.sms;

import org.gsm.oneapi.responsebean.RequestError;

/**
 * The full response from the OneAPI server for a request to list received SMS messages
 */
public class RetrieveSMSResponse implements java.io.Serializable {
	
	private static final long serialVersionUID = 5555314302096622836L;

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
	 * inboundSMSMessageList contains a count of the number of available SMS messages, and an array of SMS messages
	 */
	InboundSMSMessageList inboundSMSMessageList=null;
	/**
	 * return a count of the number of available SMS messages, and an array of SMS messages
	 */
	public InboundSMSMessageList getInboundSMSMessageList() { return inboundSMSMessageList; }
	/**
	 * set the inboundSMSMessageList. This is called internally to set the contents according to the JSON response.
	 */
	public void setInboundSMSMessageList(InboundSMSMessageList inboundSMSMessageList) { this.inboundSMSMessageList=inboundSMSMessageList; }

	/** 
	 * generate a textual representation of the RetrieveSMSResponse including all nested elements and classes 
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
		if (inboundSMSMessageList!=null) {
			buffer.append(", inboundSMSMessageList = {");
			buffer.append(inboundSMSMessageList.toString());
			buffer.append("}");
		}
		return buffer.toString();
	}
	
}
