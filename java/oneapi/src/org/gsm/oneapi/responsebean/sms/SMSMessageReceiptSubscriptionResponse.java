package org.gsm.oneapi.responsebean.sms;

import org.gsm.oneapi.responsebean.RequestError;
import org.gsm.oneapi.responsebean.ResourceReference;

/**
 * The full response from the OneAPI server for a request to subscribe to received SMS Message Receipts
 */
public class SMSMessageReceiptSubscriptionResponse implements java.io.Serializable {
	
	private static final long serialVersionUID = -6611385179127153724L;

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
	 * resourceReference contains a URL uniquely identifying a successful request to create the SMS message receipt subscription
	 */
	ResourceReference resourceReference=null;
	/**
	 * return resourceReference - a URL uniquely identifying a successful request to create the SMS message receipt subscription
	 */
	public ResourceReference getResourceReference() { return resourceReference; }
	/**
	 * set resourceReference, the URL uniquely identifying a successful request to create the SMS message receipt subscription. This is called internally to set the contents according to the JSON response.
	 * @param resourceReference
	 */
	public void setResourceReference(ResourceReference resourceReference) { this.resourceReference=resourceReference; } 

	/** 
	 * generate a textual representation of the SMSMessageReceiptSubscriptionResponse including all nested elements and classes 
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
		if (resourceReference!=null) {
			buffer.append(", resourceReference = {");
			buffer.append(resourceReference.toString());
			buffer.append("}");
		}
		return buffer.toString();
	}
	
}
