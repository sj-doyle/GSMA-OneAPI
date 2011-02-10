package org.gsm.oneapi.responsebean.sms;

/**
 * inboundSMSMessageNotificationWrapper is a utility class used internally in converting an inboundSMSMessageNotification to/from JSON
 */
public class InboundSMSMessageNotificationWrapper implements java.io.Serializable {
	private static final long serialVersionUID = -1490772217885986211L;

	/**
	 * the actual inboundSMSMessageNotification to/from JSON
	 */
	InboundSMSMessageNotification inboundSMSMessageNotification=null;
	/**
	 * return an instance of an inboundSMSMessageNotification
	 */
	public InboundSMSMessageNotification getInboundSMSMessageNotification() { return inboundSMSMessageNotification; }
	/**
	 * set the inboundSMSMessageNotification element. This is called internally to set the contents according to the JSON response.
	 */
	public void setInboundSMSMessageNotification(InboundSMSMessageNotification inboundSMSMessageNotification) { this.inboundSMSMessageNotification=inboundSMSMessageNotification; }
	
}
