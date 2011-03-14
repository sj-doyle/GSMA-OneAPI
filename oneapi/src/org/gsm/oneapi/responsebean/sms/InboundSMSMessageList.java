package org.gsm.oneapi.responsebean.sms;

/**
 * InboundMessageList contains the detail of the response to get a list of received SMS messages
 */
public class InboundSMSMessageList implements java.io.Serializable {
	private static final long serialVersionUID = -1816167056547318579L;

	/**
	 * The inboundSMSMessageList object contains an inboundSMSMessage array detailing: the dateTime that the message was received, 
	 * destinationAddress is the number associated with your service (for example an agreed short code, see ÔWhat do I need?Õ above), 
	 * messageId is a server-generated message identifier,
	 * the message body, 
	 * resourceURL is a link to the message,
	 * senderAddress is the MSISDN or Anonymous Customer Reference of the sender.
	 */
	InboundSMSMessage[] inboundSMSMessage=null;
	/**
	 * return the inboundSMSMessage array
	 */
	public InboundSMSMessage[] getInboundSMSMessage() { return inboundSMSMessage; }
	/**
	 * set the inboundSMSMessage array. This is called internally to set the contents according to the JSON response.
	 */
	public void setInboundSMSMessage(InboundSMSMessage[] inboundSMSMessage) { this.inboundSMSMessage=inboundSMSMessage; }
	
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
	 * generate a textual representation of the inboundSMSMessageList instance including nested elements and classes 
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
		if (inboundSMSMessage!=null) {
			for (int i=0; i<inboundSMSMessage.length; i++) {
				buffer.append("[");
				buffer.append(i);
				buffer.append("] = {");
				buffer.append(inboundSMSMessage[i].toString());
				buffer.append("} ");
			}
		}
		buffer.append("} ");
		
		return buffer.toString();
		
	}
}
