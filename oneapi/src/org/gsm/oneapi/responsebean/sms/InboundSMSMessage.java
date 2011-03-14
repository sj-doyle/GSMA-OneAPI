package org.gsm.oneapi.responsebean.sms;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.gsm.oneapi.foundation.DateParser;

/**
 * InboundMessage contains the main message information for an SMS message (not including attachment information)
 */
public class InboundSMSMessage implements Serializable {
	private static final long serialVersionUID = -5772672709791289909L;
	
	/**
	 * the date/time that the SMS message was sent
	 */
	private java.util.Date dateTime=null;
	/**
	 * the recipient MSISDN or other identifying number
	 */
	private String destinationAddress=null;
	/**
	 * unique messageId for the message
	 */
	private String messageId=null;
	/**
	 * content of the SMS message body
	 */
	private String message=null;
	/**
	 * resourceReference contains a URL uniquely identifying this SMS message
	 */
	String resourceURL=null;
	/**
	 * the sender MSISDN or other identifying number
	 */
	private String senderAddress=null;
	
	/**
	 * return the date/time that the SMS message was sent. The date/time is formatted as an ISO 8601 format string
	 */
	public String getDateTime() { return DateParser.getIsoDateNoMillis(dateTime); }
	/**
	 * return the recipient MSISDN or other identifying number
	 */
	public String getDestinationAddress() { return destinationAddress; }
	/**
	 * return the unique messageId for the message
	 */
	public String getMessageId() { return messageId; }
	/**
	 * return the SMS message body
	 */
	public String getMessage() { return message; }
	/**
	 * return resourceURL containing a URL uniquely identifying this SMS message
	 */
	public String getResourceURL() { return resourceURL; }
	/**
	 * return the sender MSISDN or other identifying number
	 */
	public String getSenderAddress() { return senderAddress; }
	
	/**
	 * alternate method to get the message date/time in java.util.Date format
	 */
	@JsonIgnore
	public java.util.Date getDateTimeAsDate() { return dateTime; }

	/**
	 * set the date/time that the SMS message was sent. The date/time is formatted as an ISO 8601 format string. This is called internally to set the contents according to the JSON response. 
	 */
	public void setDateTime(String dateTime) { this.dateTime=DateParser.parse(dateTime); }
	/**
	 * set the recipient MSISDN or other identifying number. This is called internally to set the contents according to the JSON response. 
	 */
	public void setDestinationAddress(String destinationAddress) { this.destinationAddress=destinationAddress; }
	/**
	 * set the unique messageId for the SMS message. This is called internally to set the contents according to the JSON response. 
	 */
	public void setMessageId(String messageId) { this.messageId=messageId; }
	/**
	 * set the SMS message body. This is called internally to set the contents according to the JSON response. 
	 */
	public void setMessage(String message) { this.message=message; }
	/**
	 * set resourceURL containing a URL uniquely identifying this SMS message. This is called internally to set the contents according to the JSON response. 
	 */
	public void setResourceURL(String resourceURL) { this.resourceURL=resourceURL; }
	/**
	 * set the sender MSISDN or other identifying number. This is called internally to set the contents according to the JSON response. 
	 */
	public void setSenderAddress(String senderAddress) { this.senderAddress=senderAddress; }
	
	@JsonIgnore
	/**
	 * set the date/time that the SMS message was sent. This time formatted as java.util.Date 
	 */
	public void setDateTimeAsDate(java.util.Date dateTime) { this.dateTime=dateTime; }
				
	/**
	 * default constructor
	 */
	public InboundSMSMessage() {
		
	}	
	
	/**
	 * utility constructor to create an InboundMessage class with all fields set
	 * @param dateTime
	 * @param destinationAddress
	 * @param messageId
	 * @param message
	 * @param resourceURL
	 * @param senderAddress
	 */
	public InboundSMSMessage(java.util.Date dateTime, String destinationAddress, String messageId, String message, String resourceURL, String senderAddress) {
		this.dateTime=dateTime;
		this.destinationAddress=destinationAddress;
		this.messageId=messageId;
		this.message=message;
		this.resourceURL=resourceURL;
		this.senderAddress=senderAddress;
	}
	
	/** 
	 * generate a textual representation of the InboundMessage including all nested elements and classes 
	 */
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("dateTime = ");
		buffer.append(dateTime);
		buffer.append(", destinationAddress = ");
		buffer.append(destinationAddress);
		buffer.append(", messageId = ");
		buffer.append(messageId);
		buffer.append(", message = ");
		buffer.append(message);
		buffer.append(", resourceURL = ");
		buffer.append(resourceURL);
		buffer.append(", senderAddress = ");
		buffer.append(senderAddress);
		return buffer.toString();		
	}

}
