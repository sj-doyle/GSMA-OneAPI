package org.gsm.oneapi.foundation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.gsm.oneapi.endpoints.ServiceEndpoints;

/**
 * Used with the command line based examples to retrieve command line variables 
 */
public class CommandLineOptions {
	
	private static Logger logger=Logger.getLogger(CommandLineOptions.class);

	/**
	 * Get a username argument, expected to be the argument following '-u', '-user' or '-username'
	 */
	public static String getUsername(String[] args) {
		String username=null;
		if (args!=null && args.length>0) {
			boolean found=false;
			for (int i=0; i<args.length-1 && !found; i++) {
				if (args[i]!=null && (args[i].equals("-u") || args[i].equals("-user") || args[i].equals("-username"))) {
					found=true;
					username=args[i+1];
				}
			}
		}		
		return username;
	}

	/**
	 * Get a password argument, expected to be the argument following '-p', '-pass' or '-password'
	 */
	public static String getPassword(String[] args) {
		String username=null;
		if (args!=null && args.length>0) {
			boolean found=false;
			for (int i=0; i<args.length-1 && !found; i++) {
				if (args[i]!=null && (args[i].equals("-p") || args[i].equals("-pass") || args[i].equals("-password"))) {
					found=true;
					username=args[i+1];
				}
			}
		}		
		return username;
	}

	/**
	 * Get a classname for service endpoints, expected to be the argument following '-e', '-end' or '-endpoints'
	 */
	public static ServiceEndpoints getServiceEndpoints(String[] args) {
		ServiceEndpoints endpoints=null;
		if (args!=null && args.length>0) {
			boolean found=false;
			for (int i=0; i<args.length-1 && !found; i++) {
				if (args[i]!=null && (args[i].equals("-e") || args[i].equals("-end") || args[i].equals("-endpoints"))) {
					String endpointClass = args[i+1];
					try {
						Object c=Class.forName(endpointClass).newInstance();
						if (c!=null && c instanceof ServiceEndpoints) {
							Method m=c.getClass().getMethod("getEndpoints");
							if (m!=null) {
								endpoints=(ServiceEndpoints) m.invoke(c);
								if (endpoints!=null) found=true;
							}
						}
					} catch (ClassNotFoundException e) {
						logger.warn("ServiceEndpoints class "+args[i+1]+" not found");
					} catch (InstantiationException e) {
						logger.warn("InstantiationException "+e.getMessage()+" using ServiceEndpoints class "+args[i+1]);
					} catch (IllegalAccessException e) {
						logger.warn("IllegalAccessException "+e.getMessage()+" using ServiceEndpoints class "+args[i+1]);
					} catch (SecurityException e) {
						logger.warn("SecurityException "+e.getMessage()+" using ServiceEndpoints class "+args[i+1]);
					} catch (NoSuchMethodException e) {
						logger.warn("NoSuchMethodException "+e.getMessage()+" using ServiceEndpoints class "+args[i+1]);
					} catch (IllegalArgumentException e) {
						logger.warn("IllegalArgumentException "+e.getMessage()+" using ServiceEndpoints class "+args[i+1]);
					} catch (InvocationTargetException e) {
						logger.warn("InvocationTargetException "+e.getMessage()+" using ServiceEndpoints class "+args[i+1]);
					}
								
				}
			}
		}
		return endpoints;
	}


}
