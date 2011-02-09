package org.gsm.oneapi.responsebean.sms;

import java.io.Serializable;

/**
 * Confirms the details of a successful request to subscribe to SMS delivery receipts
 */
public class DeliveryReceiptSubscription implements Serializable {

	private static final long serialVersionUID = 7647416372355131397L;
	
	/**
	 * inner class CallbackReference details details the URL of the page/ service to notify and additional data that will be sent 
	 */
	public static class CallbackReference {
		/**
		 * details user requested data to be sent along with the callback notification
		 */
		String callbackData=null;
		/** 
		 * the page / service to send the notification to
		 */
		String notifyURL=null;
		
		/**
		 * return the user data that will be sent along with the callback notification
		 */
		public String getCallbackData() { return callbackData; }
		/**
		 * return the URL of the page / service to send the notification to
		 */
		public String getNotifyURL() { return notifyURL; }
		
		/**
		 * set the user data field that will be sent along with the callback notification
		 * @param callbackData
		 */
		public void setCallbackData(String callbackData) { this.callbackData=callbackData; }
		/**
		 * set the URL for the page / service to send the notification to
		 * @param notifyURL
		 */
		public void setNotifyURL(String notifyURL) { this.notifyURL=notifyURL; }
		
		/**
		 * default constructor
		 */
		public CallbackReference() {
			
		}
		
		/**
		 * alternate constructor setting both callbackData and notifyURL
		 * @param callbackData
		 * @param notifyURL
		 */
		public CallbackReference(String callbackData, String notifyURL) {
			this.callbackData=callbackData;
			this.notifyURL=notifyURL;
		}
		
		/** 
		 * generate a textual representation of the CallbackReference  
		 */
		public String toString() {
			StringBuffer buffer=new StringBuffer();
			buffer.append("callbackData = ");
			buffer.append(callbackData);
			buffer.append(", notifyURL = ");
			buffer.append(notifyURL);
			return buffer.toString();
		}
		
	}
	
	/**
	 * reference to the inner callbackReference class - the notification URL and user supplied callback data
	 */
	CallbackReference callbackReference=null;
	/** 
	 * get the reference to the inner callbackReference class - the notification URL and user supplied callback data 
	 * @return CallbackReference
	 */
	public CallbackReference getCallbackReference() { return callbackReference; }
	/**
	 * set the reference to the inner callbackReference class. This is called internally to set the contents according to the JSON response.
	 * @param callbackReference
	 */
	public void setCallbackReference(CallbackReference callbackReference) { this.callbackReference=callbackReference; }
	
	/**
	 * resourceURL contains a URL uniquely identifying this SMS delivery receipt subscription
	 */		
	private String resourceURL=null;	
	/**
	 * return resourceURL - a URL uniquely identifying this SMS delivery receipt subscription
	 */
	public String getResourceURL() { return resourceURL; }
	/**
	 * set resourceURL, the URL uniquely identifying a successful request to subscribe to SMS delivery receipt subscriptions. This is called internally to set the contents according to the JSON response.
	 * @param resourceURL
	 */
	public void setResourceURL(String resourceURL) { this.resourceURL=resourceURL; }
	
	/** 
	 * generate a textual representation of the deliveryReceiptSubscription instance including nested elements and classes 
	 */
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("callbackReference = {");
		if (callbackReference!=null) buffer.append(callbackReference.toString());
		buffer.append("}, resourceURL = ");
		buffer.append(resourceURL);
		return buffer.toString();
	}


}
