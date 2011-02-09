// InvalidDateException.java
// $Id: InvalidDateException.java,v 1.3 2008/03/05 10:29:24 psankaran Exp $
// (c) COPYRIGHT MIT, INRIA and Keio, 2000.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.gsm.oneapi.foundation;
//package org.w3c.util;

/**
 * Internal class used in conversion of dates to/from ISO 8601 format
 * @version $Revision: 1.3 $
 * @author  Benoit Mahe (bmahe@w3.org)
 */
public class InvalidDateException extends Exception {

	private final static long serialVersionUID = 1120070112L;

    public InvalidDateException(String msg) {
		super(msg);
    }
    
}
