package org.gsm.oneapi.responsebean.mms;

/**
 * DeliveryInfoNotificationWrapper is a utility class used internally in converting an deliveryInfoNotification to/from JSON
 */
public class DeliveryInfoNotificationWrapper implements java.io.Serializable {
	private static final long serialVersionUID = -2834658221084258173L;

	/**
	 * the actual deliveryInfoNotification to/from JSON
	 */
	DeliveryInfoNotification deliveryInfoNotification=null;
	/**
	 * return an instance of an deliveryInfoNotification
	 */
	public DeliveryInfoNotification getDeliveryInfoNotification() { return deliveryInfoNotification; }
	/**
	 * set the deliveryInfoNotification element. This is called internally to set the contents according to the JSON response.
	 */
	public void setDeliveryInfoNotification(DeliveryInfoNotification deliveryInfoNotification) { this.deliveryInfoNotification=deliveryInfoNotification; }
	
}
