package org.gsm.oneapi.responsebean.mms;

import java.io.Serializable;

/**
 * Supplemental information for an MMS message depending on the request made of the OneAPI server
 */
public class InboundMMSMessage implements Serializable {
	private static final long serialVersionUID = -5404991946052297352L;
	
	/**
	 * the subject field for the MMS message
	 */
	String subject=null;
	/**
	 * return the subject field for the MMS message
	 */
	public String getSubject() { return subject; }
	/**
	 * set the subject field for the MMS message. This is called internally to set the contents according to the JSON response.
	 */
	public void setSubject(String subject) { this.subject=subject; }

	/**
	 * the MMS message body
	 */
	private String message=null;
	/**
	 * return the MMS message body
	 */
	public String getMessage() { return message; }
	/**
	 * set the MMS message body. This is called internally to set the contents according to the JSON response.
	 */
	public void setMessage(String message) { this.message=message; }

	/**
	 * default constructor
	 */
	public InboundMMSMessage() { }
	
	/**
	 * utility constructor to create a DeliveryInfo instance with all fields set
	 * @param subject
	 * @param message
	 */
	public InboundMMSMessage(String subject, String message) { 
		this.subject=subject; 
		this.message=message; 
	}
	
	/** 
	 * generate a textual representation of the InboundMMSMessage instance including nested elements and classes 
	 */
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("subject = ");
		buffer.append(subject);
		buffer.append(", message = ");
		buffer.append(message);
		return buffer.toString();
	}

}
