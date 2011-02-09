package org.gsm.oneapi.server;

/**
 * Internally used to assist with OneAPI server request parameter validation.
 * @see OneAPIServlet
 */
public class ValidationRule {
	/**
	 * Effectively means no validation is required
	 */
	public static final int VALIDATION_TYPE_OPTIONAL=0;
	
	/**
	 * Require a parameter value to be supplied
	 */
	public static final int VALIDATION_TYPE_MANDATORY=1;
	/**
	 * The parameter must supplied and formatted as a number (double format) greater than zero
	 */
	public static final int VALIDATION_TYPE_MANDATORY_DOUBLE_GT_ZERO=2;
	/**
	 * The parameter must be supplied and formatted as a number (double format) greater than or equal to zero
	 */
	public static final int VALIDATION_TYPE_MANDATORY_DOUBLE_GE_ZERO=3;
	/**
	 * The parameter must be supplied and formatted as a telephone number
	 */
	public static final int VALIDATION_TYPE_MANDATORY_TEL=5;
	/**
	 * The parameter must be supplied and formatted as a URL
	 */
	public static final int VALIDATION_TYPE_MANDATORY_URL=10;
	/**
	 * The parameter must supplied and formatted as a number (integer format) greater than or equal to zero
	 */
	public static final int VALIDATION_TYPE_MANDATORY_INT_GE_ZERO=15;
	/**
	 * The parameter must supplied and formatted as a number (integer format) greater than 1
	 */
	public static final int VALIDATION_TYPE_MANDATORY_INT_GT_ONE=16;
	/**
	 * The parameter must supplied and specify 'JSON'
	 */
	public static final int VALIDATION_TYPE_MANDATORY_JSON=20;
	/**
	 * The parameter must supplied and match one of the valid channel types ('Wap', 'Web', 'SMS')
	 */
	public static final int VALIDATION_TYPE_MANDATORY_PAYMENT_CHANNEL=30;
	
	/**
	 * The parameter may be omitted but if supplied must have a (double) value greater than zero
	 */
	public static final int VALIDATION_TYPE_OPTIONAL_DOUBLE_GT_ZERO=102;
	/**
	 * The parameter may be omitted but if supplied must have a (double) value greater than or equal to zero
	 */
	public static final int VALIDATION_TYPE_OPTIONAL_DOUBLE_GE_ZERO=103;
	/**
	 * The parameter may be omitted but if supplied must be in a correct telephone number format
	 */
	public static final int VALIDATION_TYPE_OPTIONAL_TEL=105;
	/**
	 * The parameter may be omitted but if supplied must be in a correct URL format
	 */
	public static final int VALIDATION_TYPE_OPTIONAL_URL=110;
	/**
	 * The parameter may be omitted but if supplied must have an (integer) value greater than or equal to zero
	 */
	public static final int VALIDATION_TYPE_OPTIONAL_INT_GE_ZERO=115;
	/**
	 * The parameter may be omitted but if supplied must have an (integer) value greater than 1
	 */
	public static final int VALIDATION_TYPE_OPTIONAL_INT_GT_ONE=116;
	/**
	 * The parameter may be omitted but if supplied must match the string 'JSON'
	 */
	public static final int VALIDATION_TYPE_OPTIONAL_JSON=120;
	/**
	 * The parameter may be omitted but if supplied must match one of the valid channel types ('Wap', 'Web', 'SMS')
	 */
	public static final int VALIDATION_TYPE_OPTIONAL_PAYMENT_CHANNEL=130;
	
	/**
	 * Instance variable - validation type defaults to optional (no validation)
	 */
	int validationType=VALIDATION_TYPE_OPTIONAL;
	/**
	 * Parameter name is used in sending an error message back to the calling client if there is a problem
	 */
	String parameterName=null;
	/**
	 * The parameter value to check against. The validation functions check the object type when validating.
	 */
	Object parameterValue=null;
	/**
	 * Where a parameter value must match a specific value
	 */
	String specificValue=null;

	/**
	 * Normal constructor where specific value matching is not required
	 * @param validationType
	 * @param parameterName
	 * @param parameterValue
	 */
	public ValidationRule(int validationType, String parameterName, Object parameterValue) {
		this.validationType=validationType;
		this.parameterName=parameterName;
		this.parameterValue=parameterValue;
	}

	/**
	 * Extended constructor where specific value matching is required
	 * @param validationType
	 * @param parameterName
	 * @param parameterValue
	 * @param specificValue
	 */
	public ValidationRule(int validationType, String parameterName, Object parameterValue, String specificValue) {
		this.validationType=validationType;
		this.parameterName=parameterName;
		this.parameterValue=parameterValue;
		this.specificValue=specificValue;
	}

	/**
	 * Identifies which validation types are mandatory 
	 * @param type
	 */
	public static boolean isMandatory(int type) {
		boolean mandatory=false;
		switch (type) {
		case VALIDATION_TYPE_MANDATORY: mandatory=true; break;
		case VALIDATION_TYPE_MANDATORY_DOUBLE_GT_ZERO: mandatory=true; break;
		case VALIDATION_TYPE_MANDATORY_DOUBLE_GE_ZERO: mandatory=true; break;
		case VALIDATION_TYPE_MANDATORY_TEL: mandatory=true; break;
		case VALIDATION_TYPE_MANDATORY_URL: mandatory=true; break;
		case VALIDATION_TYPE_MANDATORY_INT_GE_ZERO: mandatory=true; break;
		case VALIDATION_TYPE_MANDATORY_INT_GT_ONE: mandatory=true; break;
		case VALIDATION_TYPE_MANDATORY_JSON: mandatory=true; break;
		case VALIDATION_TYPE_MANDATORY_PAYMENT_CHANNEL: mandatory=true; break;
		}
		
		return mandatory;
	}
	
}
