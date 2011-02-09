package org.gsm.oneapi.responsebean.mms;

/**
 * inboundMessageWrapper is a utility class used internally in converting an inboundMessage to/from JSON
 */
public class InboundMessageWrapper implements java.io.Serializable {
	private static final long serialVersionUID = 2883226244346160536L;

	/**
	 * the actual inboundMessage to/from JSON
	 */
	InboundMessage inboundMessage=null;
	/**
	 * return an instance of an inboundMessage
	 */
	public InboundMessage getInboundMessage() { return inboundMessage; }
	/**
	 * set the inboundMessage element. This is called internally to set the contents according to the JSON response.
	 */
	public void setInboundMessage(InboundMessage inboundMessage) { this.inboundMessage=inboundMessage; }
	
}
