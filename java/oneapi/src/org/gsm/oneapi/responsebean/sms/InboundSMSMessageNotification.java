package org.gsm.oneapi.responsebean.sms;

/**
 * The notification from the OneAPI server when an SMS message is received
 */
public class InboundSMSMessageNotification implements java.io.Serializable {
	private static final long serialVersionUID = 2202807980188273565L;

	/**
	 * inboundSMSMessage contains the detail of the inbound SMS message
	 */
	InboundSMSMessage inboundSMSMessage=null;
	/**
	 * return the detail of the inbound SMS message
	 */
	public InboundSMSMessage getInboundSMSMessage() { return inboundSMSMessage; }
	/**
	 * set the detail of the inbound SMS message. This is called internally to set the contents according to the JSON response.
	 */
	public void setInboundSMSMessage(InboundSMSMessage inboundSMSMessage) { this.inboundSMSMessage=inboundSMSMessage; }

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
	 * generate a textual representation of the RetrieveSMSResponse including all nested elements and classes 
	 */
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		if (inboundSMSMessage!=null) {
			buffer.append(", inboundSMSMessage = {");
			buffer.append(inboundSMSMessage.toString());
			buffer.append("}");
		}
		buffer.append(", callbackData = "+callbackData);
		return buffer.toString();
	}
	
}
