package org.gsm.oneapi.responsebean.mms;

import java.util.ArrayList;

import org.gsm.oneapi.responsebean.RequestError;

/**
 * The full response from the OneAPI server for a request to retrieve the full contents of an MMS message including the message body and attachments
 */
public class RetrieveMMSMessageResponse implements java.io.Serializable {
	
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
	 * inboundMessage contains the detail of the MMS message
	 */
	InboundMessage inboundMessage=null;
	/**
	 * return the detail of the MMS message 
	 */
	public InboundMessage getInboundMessage() { return inboundMessage; }
	/**
	 * set the detail of the MMS message element. This is called internally to set the contents according to the MIME response.
	 */
	public void setInboundMessage(InboundMessage inboundMessage) { this.inboundMessage=inboundMessage; }
	
	/**
	 * inner class representing each file attachment returned
	 */
	public static class Attachment {
		/**
		 * the file name of the attachment
		 */
		String attachmentName=null;
		/**
		 * the MIME content type of the attachment
		 */
		String attachmentContentType=null;
		/**
		 * the file contents of the attachment - generically as an array of bytes
		 */
		byte[] attachmentData=null;
		
		/**
		 * return the file name of the attachment
		 */
		public String getAttachmentName() { return this.attachmentName; }
		/**
		 * return the MIME content type of the attachment
		 */
		public String getAttachmentContentType() { return this.attachmentContentType; }
		/**
		 * return the file data of the attachment
		 */
		public byte[] getAttachmentData() { return this.attachmentData; }
		
		/**
		 * set the file name of the attachment. This is called internally to set the contents according to the MIME response.
		 * @param attachmentName
		 */
		public void setAttachmentName(String attachmentName) { this.attachmentName=attachmentName; }
		/**
		 * set the MIME content type of the attachment. This is called internally to set the contents according to the MIME response.
		 * @param attachmentContentType
		 */
		public void setAttachmentContentType(String attachmentContentType) { this.attachmentContentType=attachmentContentType; }
		/**
		 * set the file data of the attachment. This is called internally to set the contents according to of the MIME response.
		 * @param attachmentData
		 */
		public void setAttachmentData(byte[] attachmentData) { this.attachmentData=attachmentData; }
		
		/**
		 * default constructor
		 */
		public Attachment() {} 
	}
	
	/**
	 * a list of attachments to the MMS message 
	 */
	ArrayList<Attachment> attachments=null;
	/**
	 * get the list of attachments to the MMS message
	 */
	public ArrayList<Attachment> getAttachments() { return this.attachments; }
	/**
	 * set the list of attachments to the MMS message. This is called internally to set the contents according to of the MIME response.
	 * @param attachments
	 */
	public void setAttachments(ArrayList<Attachment> attachments) { this.attachments=attachments; }
	

	/** 
	 * generate a textual representation of the RetrieveMMSMessageResponse instance including all nested elements and classes 
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
		if (inboundMessage!=null) {
			buffer.append(", inboundMessage = {");
			buffer.append(inboundMessage.toString());
			buffer.append("}");
		}
		if (attachments!=null) {
			buffer.append(", attachments = {");
			for (int i=0; i<attachments.size(); i++) {
				buffer.append("[");
				buffer.append(i);
				buffer.append("] attachmentName=");
				buffer.append(attachments.get(i).getAttachmentName());
				buffer.append(", attachmentContentType=");
				buffer.append(attachments.get(i).getAttachmentContentType());
				buffer.append(" ");				
			}
			buffer.append("}");
		}
		return buffer.toString();
	}

}
