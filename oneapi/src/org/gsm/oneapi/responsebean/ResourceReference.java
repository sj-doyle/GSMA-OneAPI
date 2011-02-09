package org.gsm.oneapi.responsebean;

/**
 * reference to a resource created by the OneAPI server - in the form of a generated URL
 */
public class ResourceReference implements java.io.Serializable {
	private static final long serialVersionUID = 3221150938986856655L;
	
	/**
	 * contains a URL uniquely identifying a successful request to the OneAPI server
	 */	
	private String resourceURL=null;	
	/**
	 * return a URL uniquely identifying a successful OneAPI server request
	 */
	public String getResourceURL() { return resourceURL; }
	/**
	 * set a URL uniquely identifying a successful OneAPI server request. This is called internally to set the contents according to the JSON response.
	 */
	public void setResourceURL(String resourceURL) { this.resourceURL=resourceURL; }
	
	/** 
	 * generate a textual representation of the ResourceReference 
	 */
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("resourceURL = ");
		buffer.append(resourceURL);
		return buffer.toString();
	}
}
