package org.gsm.oneapi.responsebean.mms;

/**
 * inboundMessageNotificationWrapper is a utility class used internally in converting an inboundMessageNotification to/from JSON
 */
public class InboundMessageNotificationWrapper implements java.io.Serializable {
	private static final long serialVersionUID = -1016914217719591715L;
	
	/**
	 * the actual inboundMessageNotification to/from JSON
	 */
	InboundMessageNotification inboundMessageNotification=null;
	/**
	 * return an instance of an inboundMessageNotification
	 */
	public InboundMessageNotification getInboundMessageNotification() { return inboundMessageNotification; }
	/**
	 * set the inboundMessageNotification element. This is called internally to set the contents according to the JSON response.
	 */
	public void setInboundMessageNotification(InboundMessageNotification inboundMessageNotification) { this.inboundMessageNotification=inboundMessageNotification; }
	
}
