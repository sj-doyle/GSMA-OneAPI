package org.gsm.oneapi.foundation;

import java.util.ArrayList;

/**
 * Internally used class to hold all the request parameters. Note these are stored in an ArrayList as some parameters may have multiple values
 */
public class FormParameters {
	ArrayList<FormParameter> parameterSet=null;
	
	public FormParameters() {
		
	}
	
	public void put(String key, String value) {
		if (parameterSet==null) parameterSet=new ArrayList<FormParameter> ();
		FormParameter fp=new FormParameter(key, value);
		parameterSet.add(fp);		
	}
	
	public ArrayList<FormParameter> getParameterSet() { return parameterSet; }
	
}
