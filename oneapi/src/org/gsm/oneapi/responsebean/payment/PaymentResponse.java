package org.gsm.oneapi.responsebean.payment;

import org.gsm.oneapi.responsebean.RequestError;

/**
 * The full response from the OneAPI server for any of the OneAPI payment calls
 */
public class PaymentResponse implements java.io.Serializable {
	private static final long serialVersionUID = 1746683143328583536L;
	
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
	 * amountTransaction is the response obtained from 'Charge' and 'Refund' payment APIs
	 * @see AmountTransaction
	 * @see org.gsm.oneapi.payment.Charge
	 */
	AmountTransaction amountTransaction=null;
	/**
	 * set the server amountTransaction response
	 * @param amountTransaction contains the server response for amountTransaction
	 * @see AmountTransaction
	 */
	public void setAmountTransaction(AmountTransaction amountTransaction) { this.amountTransaction=amountTransaction; }
	/**
	 * return the amountTransaction response decoded from JSON
	 * @see AmountTransaction
	 */
	public AmountTransaction getAmountTransaction() { return amountTransaction; }
	
	/**
	 * amountReservationTransaction is the response obtained from 'Reservation' payment APIs
	 * @see AmountReservationTransaction
	 * @see org.gsm.oneapi.payment.Reservation
	 */
	AmountReservationTransaction amountReservationTransaction=null;
	/**
	 * set the server amountReservationTransaction response
	 * @param amountReservationTransaction contains the server response for amountReservationTransaction
	 * @see AmountReservationTransaction
	 */
	public void setAmountReservationTransaction(AmountReservationTransaction amountReservationTransaction) { this.amountReservationTransaction=amountReservationTransaction; }
	/**
	 * return the amountReservationTransaction response decoded from JSON
	 * @see AmountReservationTransaction
	 */
	public AmountReservationTransaction getAmountReservationTransaction() { return amountReservationTransaction; }

	
	/** 
	 * generate a textual representation of the PaymentResponse including all nested elements and classes 
	 */
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("httpResponseCode = "+httpResponseCode);
		buffer.append(", contentType = "+contentType);

		if (amountTransaction!=null) {
			buffer.append(", amountTransaction = {");
			buffer.append(amountTransaction.toString());
			buffer.append("} ");			
		}
		
		if (amountReservationTransaction!=null) {
			buffer.append(", amountReservationTransaction = {");
			buffer.append(amountReservationTransaction.toString());
			buffer.append("} ");			
		}
		
		return buffer.toString();
	}
}
