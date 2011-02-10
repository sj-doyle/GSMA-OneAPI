package org.gsm.oneapi.location;

import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.gsm.oneapi.endpoints.ServiceEndpoints;
import org.gsm.oneapi.foundation.CommandLineOptions;
import org.gsm.oneapi.foundation.JSONRequest;
import org.gsm.oneapi.responsebean.RequestError;
import org.gsm.oneapi.responsebean.location.LocationResponse;
import org.gsm.oneapi.responsebean.location.TerminalLocation;
import org.gsm.oneapi.responsebean.payment.PaymentResponse;
import org.gsm.oneapi.server.OneAPIServlet;

public class Locate {
	
	static Logger logger=Logger.getLogger(Locate.class);

	public static boolean dumpRequestAndResponse=false;

	ServiceEndpoints endPoints=null;
	String authorisationHeader=null;
	
	/**
	 Creates a new instance of the Location API main interface. Requires endPoints to define the URL targets of the terminal location network call and authorisationHeader containing the username/password used for HTTP Basic authorisation with the OneAPI server.  
	                          
	@param  endPoints  contains a set of service/ call specific endpoints 	
	@param  authorisationHeader Base 64 encoded username/ password
	     
	@see org.gsm.oneapi.endpoints.ServiceEndpoints
	@see org.gsm.oneapi.foundation.JSONRequest#getAuthorisationHeader(String, String)
	 */	
	public Locate(ServiceEndpoints endPoints, String authorisationHeader) {
		this.endPoints=endPoints;
		this.authorisationHeader=authorisationHeader;
	}
	
	/**
	 Can be used to update the service endpoints  
	                          
	@param  endPoints  contains a set of service/ call specific endpoints 	

	@see org.gsm.oneapi.endpoints.ServiceEndpoints
	 */	
	public void setEndpoints(ServiceEndpoints endPoints) {
		this.endPoints=endPoints;		
	}
	
	/**
	 Can be used to update the service authorisation header  
	                          
	@param  authorisationHeader Base 64 encoded username/ password

	@see org.gsm.oneapi.foundation.JSONRequest#getAuthorisationHeader(String, String)
	 */	
	public void setAuthorisationHeader(String authorisationHeader) {
		this.authorisationHeader=authorisationHeader;
	}

	private static JSONRequest<LocationResponse> locationRequester=new JSONRequest<LocationResponse>(new LocationResponse());

	/**
	 Locate a single specified mobile terminal to the specified level of accuracy
	 
	@param  endUserId The MSISDN or Anonymous Customer Reference of the mobile device to locate. The protocol and Ô+Õ identifier must be used for MSISDN. Do not URL escape prior to passing to the locateTerminal function as this will be done by the API
	@param requestedAccuracy The preferred accuracy of the result, in metres. Typically, when you request an accurate location it will take longer to retrieve than a coarse location. So requestedAccuracy=10 will take longer than requestedAccuracy=100

	@see LocationResponse
	 */
	public LocationResponse locateTerminal(String endUserId, int requestedAccuracy) {
		LocationResponse response=new LocationResponse();
		
		String endpoint=endPoints.getLocationEndpoint()+"?requestedAccuracy="+requestedAccuracy;
		
    	int responseCode=0;
        String contentType = null;
        
		try {
			
			if (endUserId!=null) endpoint=endpoint+"&address="+URLEncoder.encode(endUserId, "utf-8");
			
			if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, null);
			
			HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);

        	responseCode=con.getResponseCode();
            contentType = con.getContentType();
            
            response.setHTTPResponseCode(responseCode);
            response.setContentType(contentType);
	        
            response=locationRequester.getResponse(con, OneAPIServlet.OK);
		} catch (Exception e) {
			logger.error("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
			e.printStackTrace();
			response.setHTTPResponseCode(responseCode);
			response.setContentType(contentType);
			
			response.setRequestError(new RequestError(RequestError.SERVICEEXCEPTION, "SVCJAVA", e.getMessage(), e.getClass().getName()));
		}	        
		return response;
	}

	/**
	 Locate multiple specified mobile terminals to the specified level of accuracy
	 
	@param  endUserId The MSISDN or Anonymous Customer Reference of the mobile device to locate. The protocol and Ô+Õ identifier must be used for MSISDN. Do not URL escape prior to passing to the locateMultipleTerminals function as this will be done by the API. Note that if any element of the address array is null it will not be sent to the OneAPI server.
	@param requestedAccuracy The preferred accuracy of the result, in metres. Typically, when you request an accurate location it will take longer to retrieve than a coarse location. So requestedAccuracy=10 will take longer than requestedAccuracy=100

	@see LocationResponse
	 */
	public LocationResponse locateMultipleTerminals(String[] endUserId, int requestedAccuracy) {
		LocationResponse response=new LocationResponse();
		
		String endpoint=endPoints.getLocationEndpoint()+"?requestedAccuracy="+requestedAccuracy;
		
    	int responseCode=0;
        String contentType = null;
        
		try {
			
			if (endUserId!=null) {
				for (String address:endUserId) {
					if (address!=null) endpoint=endpoint+"&address="+URLEncoder.encode(address, "utf-8");
				}				
			}
			
			if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, null); 

			HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);

        	responseCode=con.getResponseCode();
            contentType = con.getContentType();
            
            response.setHTTPResponseCode(responseCode);
            response.setContentType(contentType);
	        
            response=locationRequester.getResponse(con, OneAPIServlet.OK);
		} catch (Exception e) {
			logger.error("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
			e.printStackTrace();
			
			response.setHTTPResponseCode(responseCode);
			response.setContentType(contentType);
			
			response.setRequestError(new RequestError(RequestError.SERVICEEXCEPTION, "SVCJAVA", e.getMessage(), e.getClass().getName()));
		}	        
		return response;
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();
		ServiceEndpoints serviceEndpoints=CommandLineOptions.getServiceEndpoints(args);
		
		String username=CommandLineOptions.getUsername(args);
		String password=CommandLineOptions.getPassword(args);

		if (username==null) username="Fred.Jones";
		if (password==null) password="1234";		
		if (serviceEndpoints==null) serviceEndpoints=new ServiceEndpoints();
				
		logger.debug("Location endpoint="+serviceEndpoints.getLocationEndpoint());
		
		String authorisationHeader=JSONRequest.getAuthorisationHeader(username, password);
		
		logger.debug("AuthorisationHeader="+authorisationHeader);
		
		Locate me=new Locate(serviceEndpoints, authorisationHeader);
		
		LocationResponse singleResponse=me.locateTerminal("tel:123456", 1000);
		
		logger.debug("Locate single terminal");
		if (singleResponse!=null) {
			logger.debug("Have JSON response:\n"+singleResponse.toString());
			if (singleResponse.getTerminalLocationList()!=null && singleResponse.getTerminalLocationList().getTerminalLocation()!=null) {
				for (TerminalLocation t:singleResponse.getTerminalLocationList().getTerminalLocation())
					logger.debug("Location Response="+t.toString());
			}
		} else {
			logger.debug("No response obtained");
		}

		logger.debug("Make erroneous request");
		LocationResponse errorCase=me.locateTerminal(null, 1000);
		
		if (errorCase!=null) {
			logger.debug("Have JSON response:\n"+errorCase.toString());
			if (errorCase.getTerminalLocationList()!=null && errorCase.getTerminalLocationList().getTerminalLocation()!=null) {
				for (TerminalLocation t:errorCase.getTerminalLocationList().getTerminalLocation())
					logger.debug("Location Response="+t.toString());
			}
		} else {
			logger.debug("No response obtained");
		}

		logger.debug("Locate multiple terminals");
		String[] multipleAddresses={"tel:123456", "tel:987654", "tel:2468", "tel:13579"};
		
		LocationResponse multipleCase=me.locateMultipleTerminals(multipleAddresses, 50);
		
		if (multipleCase!=null) {
			logger.debug("Have JSON response:\n"+multipleCase.toString());
			if (multipleCase.getTerminalLocationList()!=null && multipleCase.getTerminalLocationList().getTerminalLocation()!=null) {
				for (TerminalLocation t:multipleCase.getTerminalLocationList().getTerminalLocation())
					logger.debug("Location Response="+t.toString());
			}
		} else {
			logger.debug("No response obtained");
		}

	}

}
