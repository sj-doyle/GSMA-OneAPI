package org.gsm.oneapi.foundation;

/**
 *  Internally used class for storing sets of request parameters
 */
public class FormParameter {
	private String key=null;
	private String value=null;
	
	public String getKey() { return key; }
	public String getValue() { return value; }
	
	public void setKey(String key) { this.key=key; }
	public void setValue(String value) { this.value=value; }
	
	public FormParameter(String key, String value) {
		this.key=key;
		this.value=value;
	}
	
	public FormParameter() {
		
	}
	
}
