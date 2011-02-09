package org.gsm.oneapi.foundation;

/**
 * These utilities are only used within the ComHelper class which is not currently used 
 */
@Deprecated
public class NumericUtils {
  
	@Deprecated
  	public static long longValue(String s) {
  		long rv=0L;
  		try {
  			Long lv=new Long(s);
  			rv=lv.longValue();
  		} catch (NumberFormatException nfe) {}
  		return rv;
  	}

	@Deprecated
  	public static int intValue(String s) {
  		int rv=0;
  		try {
  			Integer iv=new Integer(s);
  			rv=iv.intValue();
  		} catch (NumberFormatException nfe) {}
  		return rv;
  	}

	@Deprecated
 	public static int intDecode(String s) {
  		int rv=0;
  		try {
  			Integer iv=Integer.decode(s);
  			rv=iv.intValue();
  		} catch (NumberFormatException nfe) {}
  		return rv;
  	}

	@Deprecated
 	public static boolean booleanValue(String s) {
 		boolean rv=false;
 		if (s!=null && s.equalsIgnoreCase("true")) rv=true;
  		return rv;
  	}
   
	@Deprecated
	public long parseIPV4String(String s) {
		long parsed=0L;
		if (s!=null) {
			String [] splits=s.trim().split("\\.");
			if (splits!=null && splits.length==4) {
				int part0=0, part1=0, part2=0, part3=0;
				part0=intDecode(splits[0].trim());
				part1=intDecode(splits[1].trim());
				part2=intDecode(splits[2].trim());
				part3=intDecode(splits[3].trim());
				parsed=(((long) part0)<<24) | (((long) part1)<<16) | (((long) part2) << 8) | ((long) part3) ;
			}
		}
		return parsed;
	}		

   
}

