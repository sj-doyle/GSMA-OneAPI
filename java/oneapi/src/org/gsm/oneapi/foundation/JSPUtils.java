package org.gsm.oneapi.foundation;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A set of functions that are used in the JSP examples to handle parsing and formatting
 */
public class JSPUtils {

	/**
	 * Ensure the input value is either a null value or a trimmed string
	 */
	public static String nullOrTrimmed(String s) {
		String rv=null;
		if (s!=null && s.trim().length()>0) {
			rv=s.trim();
		}
		return rv;
	}

	/** 
	 * Convert to an integer value
	 */
	public static int parseInt(String s) {
		int rv=0;
		if (s!=null && s.trim().length()>0) {
			if (s.indexOf(",") == -1) {
				try {
					rv=Integer.parseInt(s.trim());
				} catch (NumberFormatException nfe) {}
			} else {
				String[] p=s.trim().split("\\,");
				try {
					rv=Integer.parseInt(p[0].trim());
				} catch (NumberFormatException nfe) {}
			}
		}
		return rv;
	}
	
	/**
	 * Convert to a long value 
	 */
	public static long parseLong(String s) {
		long rv=0;
		if (s!=null && s.trim().length()>0) {
			if (s.indexOf(",") == -1) {
				try {
					rv=Long.parseLong(s.trim());
				} catch (NumberFormatException nfe) {}
			} else {
				String[] p=s.trim().split("\\,");
				try {
					rv=Long.parseLong(p[0].trim());
				} catch (NumberFormatException nfe) {}
			}
		}
		return rv;
	}

	/**
	 * Convert to a boolean value
	 */
	public static boolean isTrue(String s) {
		boolean rv=false;
		if (s!=null && (s.trim().equalsIgnoreCase("true") || s.trim().equals("1") || s.trim().equalsIgnoreCase("yes"))) {
			rv=true;
		}
		return rv;
	}

	/**
	 * Alternate function name to convert to a boolean value
	 */
	public static boolean parseBoolean(String s) {
		return isTrue(s);
	}


	private static Pattern nf1 = Pattern.compile("[\\-\\+]?[0-9]*\\.?[0-9]+");
	private static Pattern nf2 = Pattern.compile("[\\-\\+]?\\.?[0-9]+");

	/**
	 * Convert to a double value
	 */
	public static double parseDouble(String s) {
		double rv=0;
		if (s!=null && s.trim().length()>0) {
			try {
				rv=Double.parseDouble(s.trim());
			} catch (NumberFormatException nfe) {
				boolean parsed=false;
				String trimmed=s.trim();
				Matcher m1=nf1.matcher(trimmed);
				if (m1.find()) {
					try {
						String pv=m1.group();
						rv=Double.parseDouble(pv);
						parsed=true;
					} catch (NumberFormatException nfe1) {}
				}
				if (!parsed) {
					Matcher m2=nf2.matcher(trimmed);
					if (m2.find()) {
						try {
							String pv=m1.group();
							rv=Double.parseDouble(pv);
							parsed=true;
						} catch (NumberFormatException nfe2) {}
					}
				}
			}
		}
		return rv;
	}

	/** 
	 * Do basic URL encoding based on UTF-8
	 */
	public static String urlEncode(String s) {
		String rv=s;
		if (s!=null) {
			try {
				rv=URLEncoder.encode(s, "UTF-8");
			} catch (UnsupportedEncodingException uee) {}
		} else {
			rv="";
		}
		return rv;
	}
	
}
