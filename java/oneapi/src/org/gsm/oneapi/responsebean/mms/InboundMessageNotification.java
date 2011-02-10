package org.gsm.oneapi.responsebean.mms;

/**
 * The notification from the OneAPI server for the receipt of an MMS message 
 */
public class InboundMessageNotification implements java.io.Serializable {
	private static final long serialVersionUID = 8461543556486487952L;

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
	 * details user requested data to be sent along with the callback notification
	 */
	String callbackData=null;
	/**
	 * return the user data that will be sent along with the callback notification
	 */
	public String getCallbackData() { return callbackData; }
	/**
	 * set the user data field that will be sent along with the callback notification
	 * @param callbackData
	 */
	public void setCallbackData(String callbackData) { this.callbackData=callbackData; }

	

	/** 
	 * generate a textual representation of the RetrieveMMSMessageResponse instance including all nested elements and classes 
	 */
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		if (inboundMessage!=null) {
			buffer.append(", inboundMessage = {");
			buffer.append(inboundMessage.toString());
			buffer.append("}");
		}
		buffer.append(", callbackData = "+callbackData);
		return buffer.toString();
	}

}
