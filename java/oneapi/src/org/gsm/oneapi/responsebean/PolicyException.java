package org.gsm.oneapi.responsebean;

/**
 * specific error case indicated by the OneAPI server as a Policy Exception
 */
public class PolicyException implements java.io.Serializable {
	private static final long serialVersionUID = -6032985525466832920L;
	
	/**
	 * the distinctive error message identifier
	 */
	private String messageId=null;
	/**
	 * the textual representation of the error
	 */
	private String text=null;
	/**
	 * any instance specific error variables
	 */
	private String[] variables=null;
	
	/**
	 * return the distinctive error message identifier
	 */
	public String getMessageId() { return messageId; }
	/**
	 * return the textual representation of the error
	 */
	public String getText() { return text; }
	/**
	 * return any instance specific error variables
	 */
	public String[] getVariables() { return variables; }
	
	/**
	 * set the distinctive error message identifier. This is called internally to set the contents according to the JSON response. 
	 */
	public void setMessageId(String messageId) { this.messageId=messageId; }
	/**
	 * set the textual representation of the error. This is called internally to set the contents according to the JSON response. 
	 */
	public void setText(String text) { this.text=text; }
	/**
	 * set any instance specific error variables. This is called internally to set the contents according to the JSON response. 
	 */
	public void setVariables(String[] variables) { this.variables=variables; }
	
	/**
	 * default constructor
	 */
	public PolicyException() {			
	}
	
	/**
	 * utility constructor to create a ServiceException object with all fields set
	 * @param messageId
	 * @param text
	 * @param variables
	 */
	public PolicyException(String messageId, String text, String[] variables) {
		this.messageId=messageId;
		this.text=text;
		this.variables=variables;			
	}
		
	/** 
	 * generate a textual representation of the ServiceException instance  
	 */
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("messageId = ");
		buffer.append(messageId);
		buffer.append(", text = ");
		buffer.append(text);
		buffer.append(", variables = {");
		if (variables!=null) {
			for (int i=0; i<variables.length; i++) {
				buffer.append("[");
				buffer.append(i);
				buffer.append("] = ");
				buffer.append(variables[i]);
			}
		}
		buffer.append("}");
		return buffer.toString();		
	}
	
	
}

