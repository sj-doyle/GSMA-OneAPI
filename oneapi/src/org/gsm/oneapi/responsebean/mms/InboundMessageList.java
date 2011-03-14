package org.gsm.oneapi.responsebean.mms;

/**
 * InboundMessageList contains the detail of the response to get a list of received MMS messages
 */
public class InboundMessageList implements java.io.Serializable {
	private static final long serialVersionUID = -1816167056547318579L;

	/**
	 * The inboundMessageList object contains an inboundMessage array detailing: the dateTime that the message was received, 
	 * destinationAddress is the number associated with your service (for example an agreed short code, see ÔWhat do I need?Õ above), 
	 * messageId is a server-generated message identifier, 
	 * inboundMMSMessage object contains a pair showing the subject of the message, 
	 * resourceURL is a link to the message. Use these to retrieve the entire message including attachments,
	 * senderAddress is the MSISDN or Anonymous Customer Reference of the sender.
	 */
	InboundMessage[] inboundMessage=null;
	/**
	 * return the inboundMessage array
	 */
	public InboundMessage[] getInboundMessage() { return inboundMessage; }
	/**
	 * set the inboundMessage array. This is called internally to set the contents according to the JSON response.
	 */
	public void setInboundMessage(InboundMessage[] inboundMessage) { this.inboundMessage=inboundMessage; }
	
	/**
	 * the number of messages returned for this batch
	 */
	Integer numberOfMessagesInThisBatch=null;
	/**
	 * resourceURL containing a URL uniquely identifying this MMS message list 
	 */
	String resourceURL=null;
	/**
	 * the totalNumberOfPendingMessages awaiting retrieval from gateway storage
	 */
	Integer totalNumberOfPendingMessages=null;
			
	/**
	 * return the number of messages returned for this batch
	 */
	public Integer getNumberOfMessagesInThisBatch() { return numberOfMessagesInThisBatch; }
	/**
	 * return resourceURL containing a URL uniquely identifying this MMS message list
	 */
	public String getResourceURL() { return resourceURL; }
	/**
	 * return the totalNumberOfPendingMessages awaiting retrieval from gateway storage
	 */
	public Integer getTotalNumberOfPendingMessages() { return totalNumberOfPendingMessages; }
	
	/**
	 * set the number of messages returned for this batch. This is called internally to set the contents according to the JSON response.
	 */
	public void setNumberOfMessagesInThisBatch(Integer numberOfMessagesInThisBatch) { this.numberOfMessagesInThisBatch=numberOfMessagesInThisBatch; }
	/**
	 * set resourceURL containing a URL uniquely identifying this MMS message list. This is called internally to set the contents according to the JSON response.
	 */
	public void setResourceURL(String resourceURL) { this.resourceURL=resourceURL; }
	/**
	 * set the totalNumberOfPendingMessages awaiting retrieval from gateway storage. This is called internally to set the contents according to the JSON response.
	 */
	public void setTotalNumberOfPendingMessages(Integer totalNumberOfPendingMessages) { this.totalNumberOfPendingMessages=totalNumberOfPendingMessages; }
	
	/** 
	 * generate a textual representation of the inboundMessageList instance including nested elements and classes 
	 */
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("numberOfMessagesInThisBatch = ");
		buffer.append(numberOfMessagesInThisBatch);
		buffer.append(", resourceURL = ");
		buffer.append(resourceURL);
		buffer.append(", totalNumberOfPendingMessages = ");
		buffer.append(totalNumberOfPendingMessages);
		
		buffer.append(", inboundSMSMessage = {");
		if (inboundMessage!=null) {
			for (int i=0; i<inboundMessage.length; i++) {
				buffer.append("[");
				buffer.append(i);
				buffer.append("] = {");
				buffer.append(inboundMessage[i].toString());
				buffer.append("} ");
			}
		}
		buffer.append("} ");
		
		return buffer.toString();
		
	}
}
