package org.gsm.oneapi.responsebean;

import java.io.Serializable;

/**
 * RequestErrorWrapper is a utility class used internally in converting a RequestError to/from JSON
 */
public class RequestErrorWrapper implements Serializable {
	private static final long serialVersionUID = 9071303924345690265L;
	
	/**
	 * in case of a server error contains the error detail
	 */
	private RequestError requestError=null;
	/**
	 * return the error detail from a server error
	 */
	public RequestError getRequestError() { return requestError; }
	/**
	 * set the error detail from a server error
	 */
	public void setRequestError(RequestError requestError) { this.requestError=requestError; }
	
	/**
	 * set a ServiceException type error
	 */
	public void setServiceException(ServiceException serviceException) {
		requestError=new RequestError();
		requestError.setServiceException(serviceException);
	}

	/**
	 * set a PolicyException type error
	 */
	public void setPolicyException(PolicyException policyException) {
		requestError=new RequestError();
		requestError.setPolicyException(policyException);
	}


}
