package org.gsm.oneapi.responsebean;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * contains an error response returned from the OneAPI server
 */
public class RequestError implements java.io.Serializable {
	private static final long serialVersionUID = -4594109872052136844L;
	
	/**
	 * internally used to indicate the type of exception being stored is a ServiceException
	 */
	public static final int SERVICEEXCEPTION=1;
	/**
	 * internally used to indicate the type of exception being stored is a PolicyException
	 */
	public static final int POLICYEXCEPTION=2;
	
	/**
	 * instance of a ServiceException
	 */
	private ServiceException serviceException=null;
	/** 
	 * instance of a PolicyException
	 */
	private PolicyException policyException=null;
		
	/**
	 * the type of exception being stored
	 */
	private int exceptionType=0;
	
	/**
	 * return the serviceException instance
	 */
	public ServiceException getServiceException() { return serviceException; }
	/**
	 * set the serviceException instance
	 */
	public void setServiceException(ServiceException serviceException) { this.serviceException=serviceException; exceptionType=SERVICEEXCEPTION; }
	
	/**
	 * get the type of the exception
	 */
	@JsonIgnore
	public int getExceptionType() { return exceptionType; }

	/**
	 * return the policyException instance
	 */
	public PolicyException getPolicyException() { return policyException; }
	/**
	 * set the policyException instance
	 */
	public void setPolicyException(PolicyException policyException) { this.policyException=policyException; exceptionType=POLICYEXCEPTION; }

	/**
	 * set the type of the exception
	 */
	@JsonIgnore
	public void setExceptionType(int n) { exceptionType=n; }

	/**
	 * utility constructor to create an RequestError instance with all fields set - this constructor will turn a single String variable into a single element array
	 * @param type
	 * @param messageId
	 * @param text
	 * @param variable
	 */
	public RequestError(int type, String messageId, String text, String variable) {
		if (type==SERVICEEXCEPTION) {
			serviceException=new ServiceException();
			serviceException.setMessageId(messageId);
			serviceException.setText(text);
			String[] variables=null;
			if (variable!=null) variables=new String[] {variable};
			serviceException.setVariables(variables);
		} else if (type==POLICYEXCEPTION) {
			policyException=new PolicyException();
			policyException.setMessageId(messageId);
			policyException.setText(text);
			String[] variables=null;
			if (variable!=null) variables=new String[] {variable};
			policyException.setVariables(variables);			
		}
		exceptionType=type;
	}

	/**
	 * utility constructor to create an RequestError instance with all fields set
	 * @param type
	 * @param messageId
	 * @param text
	 * @param variables
	 */
	public RequestError(int type, String messageId, String text, String[] variables) {
		if (type==SERVICEEXCEPTION) {
			serviceException=new ServiceException();
			serviceException.setMessageId(messageId);
			serviceException.setText(text);
			serviceException.setVariables(variables);
		} else if (type==POLICYEXCEPTION) {
			policyException=new PolicyException();
			policyException.setMessageId(messageId);
			policyException.setText(text);
			policyException.setVariables(variables);			
		}
		exceptionType=type;
	}
	
	/**
	 * default constructor
	 */
	public RequestError() {
	}
	
	/** 
	 * generate a textual representation of the RequestError including all nested elements and classes 
	 */
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		if (serviceException!=null) {
			buffer.append("serviceException = {");
			buffer.append("messageId = ");
			buffer.append(serviceException.getMessageId());
			buffer.append(", text = ");
			buffer.append(serviceException.getText());
			buffer.append(", variables = ");
			if (serviceException.getVariables()!=null) {
				buffer.append("{");
				for (int i=0; i<serviceException.getVariables().length; i++) {
					buffer.append("[");
					buffer.append(i);
					buffer.append("] = ");
					buffer.append(serviceException.getVariables()[i]);
				}
				buffer.append("}");
			}
			buffer.append("}");
		}
		if (policyException!=null) {
			buffer.append("policyException = {");
			buffer.append("messageId = ");
			buffer.append(policyException.getMessageId());
			buffer.append(", text = ");
			buffer.append(policyException.getText());
			buffer.append(", variables = ");
			if (policyException.getVariables()!=null) {
				buffer.append("{");
				for (int i=0; i<policyException.getVariables().length; i++) {
					buffer.append("[");
					buffer.append(i);
					buffer.append("] = ");
					buffer.append(policyException.getVariables()[i]);
				}
				buffer.append("}");
			}
			buffer.append("}");
		}
		
		return buffer.toString();		
	}
	
	
}

