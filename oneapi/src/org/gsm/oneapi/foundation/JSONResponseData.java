package org.gsm.oneapi.foundation;

import java.util.HashMap;

/**
 * Initial framework for a JSON response
 */
@Deprecated
public class JSONResponseData {
	public final static int VALUE_STRING=1;
	public final static int VALUE_RESOURCEURL=2;
	
	protected int HTTPResponseCode=0;
	protected HashMap<String,JSONParameter> responseParameters=null;
	protected String contentType=null;
	
	public JSONResponseData(int _HTTPResponseCode, String _contentType) {
		HTTPResponseCode=_HTTPResponseCode;
		contentType=_contentType;
	}
	
	protected void addParameter(int type, String key, String value) {
		if (responseParameters==null) responseParameters=new HashMap<String,JSONParameter>();
		JSONParameter p=new JSONParameter(type, value);
		responseParameters.put(key, p);
	}
	
	
}

class JSONParameter {
	int parameterType;
	String parameterValue;
	
	JSONParameter(int _parameterType, String _parameterValue) {
		parameterType=_parameterType;
		parameterValue=_parameterValue;
	}
}
