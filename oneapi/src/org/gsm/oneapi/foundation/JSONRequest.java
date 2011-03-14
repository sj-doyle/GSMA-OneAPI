package org.gsm.oneapi.foundation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.responsebean.RequestError;
import org.gsm.oneapi.responsebean.RequestErrorWrapper;

/**
 * Handle the sending of a request to the OneAPI server, and decode the JSON response putting the result into the specified object type
 */
public class JSONRequest<T> {

	static Logger logger=Logger.getLogger(JSONRequest.class);
	static final boolean DEBUG_ENABLED = logger.isDebugEnabled();
	static final boolean INFO_ENABLED = logger.isInfoEnabled();
	static final boolean WARN_ENABLED = logger.isEnabledFor(Level.WARN);
	static final boolean ERROR_ENABLED = logger.isEnabledFor(Level.ERROR);
	
	T instance=null;
	
	Method responseCodeSetter=null;
	Method contentTypeSetter=null;
	Method requestErrorSetter=null;
	Method locationSetter=null;
	
	public JSONRequest(T initial) {
		instance=initial;
		
		try {
			responseCodeSetter=initial.getClass().getMethod("setHTTPResponseCode", int.class);
		} catch (SecurityException e) {
			logger.error("SecurityException reported for httpResponseCode setter");
		} catch (NoSuchMethodException e) {
			logger.error("NoSuchMethodException reported for httpResponseCode setter");
		}
		try {
			contentTypeSetter=initial.getClass().getMethod("setContentType", String.class);
		} catch (SecurityException e) {
			logger.error("SecurityException reported for contentType setter");
		} catch (NoSuchMethodException e) {
			logger.error("NoSuchMethodException reported for contentType setter");
		}
		try {
			requestErrorSetter=initial.getClass().getMethod("setRequestError", RequestError.class);
		} catch (SecurityException e) {
			logger.error("SecurityException reported for requestError setter");
		} catch (NoSuchMethodException e) {
			logger.error("NoSuchMethodException reported for requestError setter");
		}
		try {
			locationSetter=initial.getClass().getMethod("setLocation", String.class);
		} catch (SecurityException e) {
			logger.error("SecurityException reported for location setter");
		} catch (NoSuchMethodException e) {
			logger.error("NoSuchMethodException reported for location setter");
		}
		
	}
	
    @SuppressWarnings("unchecked")
	public T getResponse (HttpURLConnection con, int requiredStatus) {
    	T response=null;
    	
    	try {
			response=(T) instance.getClass().newInstance();
		} catch (Exception e) {
			logger.error("Couldn't instantiate class "+instance.getClass().getName()+" "+e.getMessage());
		}
    	
    	try {
        	int responseCode=con.getResponseCode();
            String contentType = con.getContentType();
            
        	logger.debug("HTTP Response Code="+responseCode+" Content Type="+contentType);
        	
        	boolean tryAndIdentifyError=true;
        	ByteArrayOutputStream baos=null;

        	if (responseCode>=200 && responseCode<300) {
	    		if (contentType!=null && contentType.equalsIgnoreCase("application/json")) {
	    			logger.debug("Processing JSON Response");
	
	                InputStream is = con.getErrorStream();
	                if (is == null) {
	                    is = con.getInputStream();
	                }
	
	    			// Convert response body so it can be processed through JSON parser 
	                baos = new ByteArrayOutputStream();
	                int i;
	                while ((i = (byte) is.read()) != -1) baos.write(i);
	                
	                logger.debug("Response data: "+new String(baos.toByteArray(), "utf-8"));
	                
	                if (responseCode==requiredStatus) {
	                    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	                    
	                    ObjectMapper mapper=new ObjectMapper();
	                    
	                    T newResponse=(T) mapper.readValue(bais, instance.getClass());
	                    if (newResponse!=null) response=newResponse;
	                    tryAndIdentifyError=false;
	                    
	                    String location=con.getHeaderField("Location");
	                	try {
	    					locationSetter.invoke(response, location);
	    				} catch (Exception e) {
	    					e.printStackTrace();
	    				}

	                } else if (response!=null && requestErrorSetter!=null) {
	                    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		                ObjectMapper errorMapper=new ObjectMapper();
		                
		                RequestError errorResponse=errorMapper.readValue(bais, RequestError.class);
		                if (errorResponse!=null) {
							try {
								requestErrorSetter.invoke(response, errorResponse);
							} catch (Exception e) {
								e.printStackTrace();
							}
		                }
		                tryAndIdentifyError=false;
	                }
	                
	    		}
	    	} else {
                InputStream is = con.getErrorStream();
                if (is == null) {
                    is = con.getInputStream();
                }
                baos = new ByteArrayOutputStream();
                int i;
                while ((i = (byte) is.read()) != -1) baos.write(i);
	    	}
            if (response!=null && responseCodeSetter!=null) {
            	try {
					responseCodeSetter.invoke(response, responseCode);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            if (response!=null && contentTypeSetter!=null) {
            	try {
            		contentTypeSetter.invoke(response, contentType);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            if (tryAndIdentifyError && response!=null && baos!=null && requestErrorSetter!=null) {
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                ObjectMapper errorMapper=new ObjectMapper();
                
                try {
	                RequestErrorWrapper errorResponse=errorMapper.readValue(bais, RequestErrorWrapper.class);
	                if (errorResponse!=null && errorResponse.getRequestError()!=null) {
						requestErrorSetter.invoke(response, errorResponse.getRequestError());
	                }
                } catch (Exception e) {
                	logger.warn("Exception "+e.getMessage()+" trying to identify error response");
                }

            }
    	
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return response;        
    }

    @SuppressWarnings("unchecked")
	public T getResponse (HttpURLConnection con, ObjectMapper customMapper) {
    	T response=null;
    	
    	try {
        	int responseCode=con.getResponseCode();
            String contentType = con.getContentType();

        	if (INFO_ENABLED) logger.info("HTTP Response Code="+responseCode+" Content Type="+contentType);

        	if (responseCode>=200 && responseCode<300) {
	    		if (contentType!=null && contentType.equalsIgnoreCase("application/json")) {
	    			if (DEBUG_ENABLED) logger.debug("Processing JSON Response");
	
	                InputStream is = con.getErrorStream();
	                if (is == null) {
	                    is = con.getInputStream();
	                }
	
	    			// Convert response body so it can be processed through JSON parser 
	                ByteArrayOutputStream baos = new ByteArrayOutputStream();
	                int i;
	                while ((i = (byte) is.read()) != -1) baos.write(i);
	                
	                if (DEBUG_ENABLED) logger.debug("Response data: "+new String(baos.toByteArray(), "utf-8"));
	                
	                if (responseCode==201) {
	                    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	                    
	                    response=customMapper.readValue(bais, (Class<T>) instance);
	                }
	    		}
	    	}
    	
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return response;        
    }

    public static HttpURLConnection setupConnection(String url, String authHeaderValue) throws MalformedURLException, IOException {
        logger.debug("Intitiating connection to URL: " + url);
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        if (authHeaderValue!=null) {
        	con.setRequestProperty("Authorization", "Basic " + authHeaderValue);
        	logger.debug("Authorization type Basic using "+authHeaderValue);
        }
        con.setRequestProperty("Accept", "application/json");
        return con;
    }

    public static HttpURLConnection setupConnectionWithCustomAuthorization(String url, String authorizationScheme, String authHeaderValue) throws MalformedURLException, IOException {
        logger.debug("Intitiating connection to URL: " + url);
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        if (authHeaderValue!=null) {
        	con.setRequestProperty("Authorization", authorizationScheme + " " + authHeaderValue);
        	logger.debug("Authorization type "+authorizationScheme+" using "+authHeaderValue);
        }
        con.setRequestProperty("Accept", "application/json");
        return con;
    }

    public static String formEncodeParams(FormParameters formParameters) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        if (formParameters!=null && formParameters.getParameterSet()!=null) {
        	int i=0;
            for (FormParameter param : formParameters.getParameterSet()) {
            	if (i++>0) sb.append("&");
            	sb.append(URLEncoder.encode(param.getKey(), "utf-8"));
            	sb.append("=");
            	if (param.getValue()!=null) sb.append(URLEncoder.encode(param.getValue(), "utf-8"));
            	
            }
        }
        logger.debug("Request form parameters: "+sb.toString());
        return sb.toString();
    }

    public static String formEncodeLineSeparaterParams(FormParameters formParameters) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        if (formParameters!=null && formParameters.getParameterSet()!=null) {
        	int i=0;
            for (FormParameter param : formParameters.getParameterSet()) {
            	if (i++>0) sb.append("&\n");
            	sb.append(URLEncoder.encode(param.getKey(), "utf-8"));
            	sb.append("=");
            	if (param.getValue()!=null) sb.append(URLEncoder.encode(param.getValue(), "utf-8"));
            }
        }
        logger.debug("Request form parameters: "+sb.toString());
        return sb.toString();
    }

    public static String getAuthorisationHeader(String username, String password) {
    	String credentials = username + ":" + password;    	    	
    	Base64 base64encoder=new Base64();    	
    	return base64encoder.encodeToString(credentials.getBytes()).trim();    	
    }
    
    
    
	public static String base64Encode(String s) {
    	Base64 base64encoder=new Base64();    	
	    return base64encoder.encodeToString(s.getBytes()).trim();
	}
	
	public static String base64Decode(String n) {
    	Base64 base64encoder=new Base64();
    	byte[] bd=base64encoder.decode(n);
    	String s=new String(bd);
		return s;
	}
	
	public static void dumpRequestVariables(String url, String authorisationHeader, FormParameters formParameters) {
		logger.debug("JSON Request : "+url);
		if (authorisationHeader!=null) {
			logger.debug("Authorization: "+authorisationHeader);			
		} else {
			logger.debug("No authorization");
		}
		if (formParameters!=null && formParameters.getParameterSet()!=null) {
			for (FormParameter param:formParameters.getParameterSet()) {
				logger.debug("Parameter "+param.getKey()+" = "+param.getValue());
			}
		}
		
	}

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
