package org.gsm.oneapi.server.location;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.responsebean.RequestError;
import org.gsm.oneapi.responsebean.location.LocationResponse;
import org.gsm.oneapi.responsebean.location.TerminalLocation;
import org.gsm.oneapi.server.OneAPIServlet;
import org.gsm.oneapi.server.ValidationRule;

/**
 * Servlet implementing the OneAPI function for locating one or more mobile terminals
 */
public class LocationServlet extends OneAPIServlet {
	static Logger logger=Logger.getLogger(LocationServlet.class);

	private static final long serialVersionUID = 68103504439958479L;

	public void init() throws ServletException {
		logger.debug("LocationServlet initialised");
    }
	
	private final String[] validationRules={"1", "location", "queries", "location"};

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		String contentType=request.getContentType();
		
		dumpRequestDetails(request, logger);

		String[] requestParts=getRequestParts(request);
		
		if (validateRequest(request, response, requestParts, validationRules)) {
			
			/*
			 * Decode the service parameters - in this case it is an HTTP GET request 
			 */
			Double requestedAccuracy=parseDouble(request.getParameter("requestedAccuracy"));
			String[] addresses=request.getParameterValues("address");	// Note there can be multiple addresses specified
			
			ValidationRule[] rules={
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_DOUBLE_GT_ZERO, "requestedAccuracy", requestedAccuracy),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "address", addresses)
			};
			
			logger.debug("requestedAccuracy = "+requestedAccuracy);
			if (addresses!=null) for (String add:addresses) logger.debug("address = "+add);			
			
			if (checkRequestParameters(response, rules)) {
					
				/*
				 * Generate response
				 */
	
				LocationResponse.TerminalLocationList rd=new LocationResponse.TerminalLocationList();
							
				if (addresses!=null) {
					TerminalLocation[] locations=new TerminalLocation[addresses.length];
					Random rng=new Random();
					rng.setSeed(System.currentTimeMillis());
					
					for (int i=0; i<addresses.length; i++) {
						TerminalLocation loc=new TerminalLocation();
						locations[i]=loc;
						loc.setAddress(addresses[i]);
						
						logger.debug("Processing address="+addresses[i]);
						
						// Simulate erroneous response
						if (addresses[i]==null || addresses[i].trim().length()==0) {
							TerminalLocation.RequestError err=new TerminalLocation.RequestError("LOC001", "Missing terminal address parameter", String.valueOf(i));
							loc.setErrorInformation(err);
							loc.setLocationRetrievalStatus("Error");
						} else if (rng.nextDouble()<0.4) {
							TerminalLocation.RequestError err=new TerminalLocation.RequestError("LOC001", "Terminal is not opted for location finding", addresses[i]);
							loc.setErrorInformation(err);
							loc.setLocationRetrievalStatus("Error");
						} else {
							TerminalLocation.CurrentLocation currentLocation=new TerminalLocation.CurrentLocation();
							currentLocation.setAccuracy(requestedAccuracy);
							currentLocation.setAltitude(75.0);
							currentLocation.setLatitude(41.3833333);
							currentLocation.setLongitude(2.1833333);
							currentLocation.setTimestampAsDate(new java.util.Date());
							loc.setCurrentLocation(currentLocation);
							loc.setLocationRetrievalStatus("Retrieved");
						}
					}
					
					rd.setTerminalLocation(locations);
				}
			
				ObjectMapper mapper=new ObjectMapper();			
				String jsonResponse="{\"terminalLocationList\":"+mapper.writeValueAsString(rd)+"}";
	
				sendJSONResponse(response, jsonResponse, OK, null);
			}
		}
		
	}

}
