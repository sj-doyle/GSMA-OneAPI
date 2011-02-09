package org.gsm.oneapi.payment;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.gsm.oneapi.endpoints.ServiceEndpoints;
import org.gsm.oneapi.foundation.CommandLineOptions;
import org.gsm.oneapi.foundation.FormParameters;
import org.gsm.oneapi.foundation.JSONRequest;
import org.gsm.oneapi.responsebean.RequestError;
import org.gsm.oneapi.responsebean.payment.PaymentResponse;
import org.gsm.oneapi.server.OneAPIServlet;

public class Reservation {
	
	static Logger logger=Logger.getLogger(Reservation.class);
	
	ServiceEndpoints endPoints=null;
	String authorisationHeader=null;
	
	public static boolean dumpRequestAndResponse=false;
	private static JSONRequest<PaymentResponse> paymentResponseProcessor=new JSONRequest<PaymentResponse>(new PaymentResponse());

	/**
	 Creates a new instance of the Payment Reservation API main interface. Requires endPoints to define the URL targets of the reservation network call and authorisationHeader containing the username/password used for HTTP Basic authorisation with the OneAPI server.  
	                          
	@param  endPoints  contains a set of service/ call specific endpoints 	
	@param  authorisationHeader Base 64 encoded username/ password
	     
	@see org.gsm.oneapi.endpoints.ServiceEndpoints
	@see org.gsm.oneapi.foundation.JSONRequest#getAuthorisationHeader(String, String)
	 */	

	public Reservation(ServiceEndpoints endPoints, String authorisationHeader) {
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

	/**
	 Reserve an initial payment amount on the end user's bill / mobile phone account.  
	                          
	@param endUserId (mandatory) is the end user ID; in this case their MSISDN including the 'tel:' protocol identifier and the country code preceded by '+'. i.e., tel:+16309700001. OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator. Do not URL encode this value prior to passing
	@param referenceCode (mandatory, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
	@param description (mandatory) is the human-readable text to appear on the bill, so the user can easily see what they bought.
	@param currency (mandatory) is the 3-figure code as per ISO 4217. Note either amount and currency or code must be provided.
	@param amount (mandatory) can be a whole number or decimal. Here means the amount to refund, which can be a full or partial refund of the original charge. Note either amount and currency or code must be provided.
	@param code (mandatory) a code provided by the OneAPI implementation that is used to reference an operator price point. Note either amount and currency or code must be provided.
	@param clientCorrelator (optional) uniquely identifies the refund request. If there is a communication failure during the refund request, using the same clientCorrelator when retrying the request allows the operator to avoid applying the same refund twice.
	@param onBehalfOf (optional) allows aggregators/partners to specify the actual payee.
	@param purchaseCategoryCode (optional) specifies the type/category of purchase e.g. "Video", "Game"
	@param channel (optional) can be "Wap", "Web", "SMS", depending on the source of user interaction
	@param taxAmount (optional) tax already charged by the merchant.
	@param serviceId (optional) The ID of the partner/merchant service
	@param productId (optional) combines with the serviceID to uniquely identify the product being purchased.
	                          
	@see PaymentResponse
	*/
	public PaymentResponse reserveInitialAmount(String endUserId, String referenceCode, String description, String currency, double amount, String code,
			String clientCorrelator, String onBehalfOf, String purchaseCategoryCode, String channel, double taxAmount, String serviceId, String productId) {
		PaymentResponse response=new PaymentResponse();
		
		FormParameters formParameters=new FormParameters();
		formParameters.put("endUserId", endUserId);
		formParameters.put("transactionOperationStatus", "reserved");
		formParameters.put("referenceSequence", Integer.toString(1));
		formParameters.put("amount", Double.toString(amount));
		formParameters.put("currency", currency);
		formParameters.put("description", description);
		formParameters.put("referenceCode", referenceCode);
		formParameters.put("code", code);
		formParameters.put("clientCorrelator", clientCorrelator);
		formParameters.put("onBehalfOf", onBehalfOf);
		formParameters.put("purchaseCategoryCode", purchaseCategoryCode);
		formParameters.put("channel", channel);
		formParameters.put("taxAmount", Double.toString(taxAmount));
		formParameters.put("serviceId", serviceId);
		formParameters.put("productId", productId);
		
    	int responseCode=0;
        String contentType = null;
		
		try {
			
			String endpoint=endPoints.getAmountReserveEndpoint().replace("{endUserId}", URLEncoder.encode(endUserId, "utf-8"));
			
			if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, formParameters); 

			HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
	        con.setDoOutput(true);
	        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
	        
	        String requestBody=JSONRequest.formEncodeParams(formParameters);
	        out.write(requestBody);
	        out.close();
	        
        	responseCode=con.getResponseCode();
            contentType = con.getContentType();
	        
            response=paymentResponseProcessor.getResponse(con, OneAPIServlet.CREATED);
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
			e.printStackTrace();
			response.setHTTPResponseCode(responseCode);
			response.setContentType(contentType);
			
			response.setRequestError(new RequestError(RequestError.SERVICEEXCEPTION, "SVCJAVA", e.getMessage(), e.getClass().getName()));
		}	        
		return response;
	}

	/**
	 Reserve an additional payment amount on the end user's bill / mobile phone account.  
	                          
	@param endUserId (mandatory) is the end user ID; in this case their MSISDN including the 'tel:' protocol identifier and the country code preceded by '+'. i.e., tel:+16309700001. OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator. Do not URL encode this value prior to passing
	@param referenceCode (mandatory, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
	@param description (mandatory) is the human-readable text to appear on the bill, so the user can easily see what they bought.
	@param currency (mandatory) is the 3-figure code as per ISO 4217. Note either amount and currency or code must be provided.
	@param amount (mandatory) can be a whole number or decimal. Here means the additional amount to reserve. Note either amount and currency or code must be provided.
	@param referenceSequence (mandatory) can be a whole number or decimal. 
	@param code (mandatory) a code provided by the OneAPI implementation that is used to reference an operator price point. Note either amount and currency or code must be provided.
	@param onBehalfOf (optional) allows aggregators/partners to specify the actual payee.
	@param purchaseCategoryCode (optional) specifies the type/category of purchase e.g. "Video", "Game"
	@param channel (optional) can be "Wap", "Web", "SMS", depending on the source of user interaction
	@param taxAmount (optional) tax already charged by the merchant.
	@param serviceId (optional) The ID of the partner/merchant service
	@param productId (optional) combines with the serviceID to uniquely identify the product being purchased.
	                          
	@see PaymentResponse
	*/
	public PaymentResponse reserveAdditionalAmount(String endUserId, String referenceCode, String description, String currency, double amount, int referenceSequence, String code, 
			String onBehalfOf, String purchaseCategoryCode, String channel, double taxAmount, String serviceId, String productId) {
		PaymentResponse response=new PaymentResponse();
		
		FormParameters formParameters=new FormParameters();
		formParameters.put("endUserId", endUserId);
		formParameters.put("transactionOperationStatus", "reserved");
		formParameters.put("referenceSequence", Integer.toString(referenceSequence));
		formParameters.put("amount", Double.toString(amount));
		formParameters.put("currency", currency);
		formParameters.put("description", description);
		formParameters.put("referenceCode", referenceCode);
		formParameters.put("code", code);
		formParameters.put("onBehalfOf", onBehalfOf);
		formParameters.put("purchaseCategoryCode", purchaseCategoryCode);
		formParameters.put("channel", channel);
		formParameters.put("taxAmount", Double.toString(taxAmount));
		formParameters.put("serviceId", serviceId);
		formParameters.put("productId", productId);
		
    	int responseCode=0;
        String contentType = null;

        try {
			String endpoint=endPoints.getAmountReserveAdditionalEndpoint().replace("{endUserId}", URLEncoder.encode(endUserId, "utf-8")).replace("{transactionId}", URLEncoder.encode(referenceCode, "utf-8"));

			if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, formParameters); 
		
			HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
	        con.setDoOutput(true);
	        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
	        
	        String requestBody=JSONRequest.formEncodeParams(formParameters);
	        out.write(requestBody);
	        out.close();
	        
        	responseCode=con.getResponseCode();
            contentType = con.getContentType();
	        
            response=paymentResponseProcessor.getResponse(con, OneAPIServlet.OK);
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
			e.printStackTrace();
			response.setHTTPResponseCode(responseCode);
			response.setContentType(contentType);
			
			response.setRequestError(new RequestError(RequestError.SERVICEEXCEPTION, "SVCJAVA", e.getMessage(), e.getClass().getName()));
		}	        
		return response;
	}

	/**
	 Reserve an additional payment amount on the end user's bill / mobile phone account. This is an alternative API where there is no change in other optional parameters 
	                          
	@param endUserId (mandatory) is the end user ID; in this case their MSISDN including the 'tel:' protocol identifier and the country code preceded by '+'. i.e., tel:+16309700001. OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator. Do not URL encode this value prior to passing
	@param referenceCode (mandatory, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
	@param description (mandatory) is the human-readable text to appear on the bill, so the user can easily see what they bought.
	@param currency (mandatory) is the 3-figure code as per ISO 4217. Note either amount and currency or code must be provided.
	@param amount (mandatory) can be a whole number or decimal. Here means the additional amount to reserve. Note either amount and currency or code must be provided.
	@param referenceSequence (mandatory) can be a whole number or decimal. Here means the amount to refund, which can be a full or partial refund of the original charge
	@param code (mandatory) a code provided by the OneAPI implementation that is used to reference an operator price point. Note either amount and currency or code must be provided.
	                          
	@see PaymentResponse
	*/
	public PaymentResponse reserveAdditionalAmount(String endUserId, String referenceCode, String description, String currency, double amount, int referenceSequence, String code) {
		PaymentResponse response=new PaymentResponse();
		
		FormParameters formParameters=new FormParameters();
		formParameters.put("endUserId", endUserId);
		formParameters.put("transactionOperationStatus", "reserved");
		formParameters.put("referenceSequence", Integer.toString(referenceSequence));
		formParameters.put("amount", Double.toString(amount));
		formParameters.put("currency", currency);
		formParameters.put("code", code);
		formParameters.put("description", description);
		formParameters.put("referenceCode", referenceCode);
		
    	int responseCode=0;
        String contentType = null;

        try {
			String endpoint=endPoints.getAmountReserveAdditionalEndpoint().replace("{endUserId}", URLEncoder.encode(endUserId, "utf-8")).replace("{transactionId}", URLEncoder.encode(referenceCode, "utf-8"));

			if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, formParameters); 
		
			HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
	        con.setDoOutput(true);
	        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
	        
	        String requestBody=JSONRequest.formEncodeParams(formParameters);
	        out.write(requestBody);
	        out.close();
	        
        	responseCode=con.getResponseCode();
            contentType = con.getContentType();
	        
            response=paymentResponseProcessor.getResponse(con, OneAPIServlet.OK);
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
			e.printStackTrace();
			response.setHTTPResponseCode(responseCode);
			response.setContentType(contentType);
			
			response.setRequestError(new RequestError(RequestError.SERVICEEXCEPTION, "SVCJAVA", e.getMessage(), e.getClass().getName()));
		}	        
		return response;
	}

	/**
	 Charge an amount to the end user's bill / mobile phone account from reserved amounts  
	                          
	@param endUserId (mandatory) is the end user ID; in this case their MSISDN including the 'tel:' protocol identifier and the country code preceded by '+'. i.e., tel:+16309700001. OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator. Do not URL encode this value prior to passing
	@param referenceCode (mandatory, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
	@param description (mandatory) is the human-readable text to appear on the bill, so the user can easily see what they bought.
	@param currency (mandatory) is the 3-figure code as per ISO 4217. Note either amount and currency or code must be provided.
	@param amount (mandatory) can be a whole number or decimal. Here means the amount to charge against a prior reservation. Note either amount and currency or code must be provided.
	@param referenceSequence (mandatory) can be a whole number or decimal. 
	@param code (mandatory) a code provided by the OneAPI implementation that is used to reference an operator price point. Note either amount and currency or code must be provided.
	@param callbackURL (optional) URL to post the result of the charge operation to
	@param clientCorrelator (optional) uniquely identifies the refund request. If there is a communication failure during the refund request, using the same clientCorrelator when retrying the request allows the operator to avoid applying the same refund twice.
	@param onBehalfOf (optional) allows aggregators/partners to specify the actual payee.
	@param purchaseCategoryCode (optional) specifies the type/category of purchase e.g. "Video", "Game"
	@param channel (optional) can be "Wap", "Web", "SMS", depending on the source of user interaction
	@param taxAmount (optional) tax already charged by the merchant.
	@param serviceId (optional) The ID of the partner/merchant service
	@param productId (optional) combines with the serviceID to uniquely identify the product being purchased.
	                          
	@see PaymentResponse
	*/
	public PaymentResponse chargeAmount(String endUserId, String referenceCode, String description, String currency, double amount, int referenceSequence, String code, String callbackURL,
			String clientCorrelator, String onBehalfOf, String purchaseCategoryCode, String channel, double taxAmount, String serviceId, String productId) {
		PaymentResponse response=new PaymentResponse();
		
		FormParameters formParameters=new FormParameters();
		formParameters.put("endUserId", endUserId);
		formParameters.put("transactionOperationStatus", "charged");
		formParameters.put("referenceSequence", Integer.toString(referenceSequence));
		formParameters.put("amount", Double.toString(amount));
		formParameters.put("currency", currency);
		formParameters.put("description", description);
		formParameters.put("referenceCode", referenceCode);
		formParameters.put("code", code);
		formParameters.put("callbackURL", callbackURL);

		formParameters.put("clientCorrelator", clientCorrelator);
		formParameters.put("onBehalfOf", onBehalfOf);
		formParameters.put("purchaseCategoryCode", purchaseCategoryCode);		
		formParameters.put("channel", channel);
		formParameters.put("taxAmount", Double.toString(taxAmount));
		formParameters.put("serviceId", serviceId);
		formParameters.put("productId", productId);
		
    	int responseCode=0;
        String contentType = null;

        try {
			String endpoint=endPoints.getAmountReservationChargeEndpoint().replace("{endUserId}", URLEncoder.encode(endUserId, "utf-8")).replace("{transactionId}", URLEncoder.encode(referenceCode, "utf-8"));

			if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, formParameters); 

			HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
	        con.setDoOutput(true);
	        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
	        
	        String requestBody=JSONRequest.formEncodeParams(formParameters);
	        out.write(requestBody);
	        out.close();
	        
        	responseCode=con.getResponseCode();
            contentType = con.getContentType();

            if (callbackURL==null) {
            	response=paymentResponseProcessor.getResponse(con, OneAPIServlet.OK);
            } else {
            	response=paymentResponseProcessor.getResponse(con, OneAPIServlet.ACCEPTED);
            }
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
			e.printStackTrace();
			response.setHTTPResponseCode(responseCode);
			response.setContentType(contentType);
			
			response.setRequestError(new RequestError(RequestError.SERVICEEXCEPTION, "SVCJAVA", e.getMessage(), e.getClass().getName()));
		}	        
		return response;
	}

	/**
	 Release a reserved amount Charge an amount to the end user's bill / mobile phone account from reserved amounts  
	                          
	@param endUserId (mandatory) is the end user ID; in this case their MSISDN including the 'tel:' protocol identifier and the country code preceded by '+'. i.e., tel:+16309700001. OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator. Do not URL encode this value prior to passing
	@param referenceCode (mandatory, unique per charge event) is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
	@param referenceSequence (mandatory) can be a whole number or decimal. 
	                          
	@see PaymentResponse
	*/
	public PaymentResponse releaseReservation(String endUserId, String referenceCode, int referenceSequence) {
		PaymentResponse response=new PaymentResponse();
		
		FormParameters formParameters=new FormParameters();
		formParameters.put("endUserId", endUserId);
		formParameters.put("transactionOperationStatus", "released");
		formParameters.put("referenceSequence", Integer.toString(referenceSequence));
		formParameters.put("referenceCode", referenceCode);
		
    	int responseCode=0;
        String contentType = null;

        try {
			String endpoint=endPoints.getAmountReservationReleaseEndpoint().replace("{endUserId}", URLEncoder.encode(endUserId, "utf-8")).replace("{transactionId}", URLEncoder.encode(referenceCode, "utf-8"));

			if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, formParameters); 
		
			HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
	        con.setDoOutput(true);
	        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
	        
	        String requestBody=JSONRequest.formEncodeParams(formParameters);
	        out.write(requestBody);
	        out.close();
	        
        	responseCode=con.getResponseCode();
            contentType = con.getContentType();
	        
            response=paymentResponseProcessor.getResponse(con, OneAPIServlet.OK);
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
			e.printStackTrace();
			response.setHTTPResponseCode(responseCode);
			response.setContentType(contentType);
			
			response.setRequestError(new RequestError(RequestError.SERVICEEXCEPTION, "SVCJAVA", e.getMessage(), e.getClass().getName()));
		}	        
		return response;
	}


	public static void main(String[] args) {
		BasicConfigurator.configure();
		dumpRequestAndResponse=true;

		ServiceEndpoints serviceEndpoints=CommandLineOptions.getServiceEndpoints(args);
		
		String username=CommandLineOptions.getUsername(args);
		String password=CommandLineOptions.getPassword(args);

		if (username==null) username="Fred.Jones";
		if (password==null) password="1234";		
		if (serviceEndpoints==null) serviceEndpoints=new ServiceEndpoints();

		logger.debug("Reservation endpoint="+serviceEndpoints.getAmountReserveEndpoint());
		
		String authorisationHeader=JSONRequest.getAuthorisationHeader(username, password);		
		
		logger.debug("AuthorisationHeader="+authorisationHeader);
		
		Reservation me=new Reservation(serviceEndpoints, authorisationHeader);
		
		PaymentResponse reserveInitialResponse=me.reserveInitialAmount("tel:1234567890", "REF-12345", "The Big Fight", "USD",  3.0, "C258", "CORR-0001", "Jupiter TV", "Video", "Wap", 0.0, "JUP-1", "JUP-001234");
						
		if (reserveInitialResponse!=null) {
			logger.debug("Reserve initial response:\n"+reserveInitialResponse.toString());
		} else {
			logger.debug("No response obtained");
		}

		int sequenceNumber=2;
		
		logger.debug("Reserve additional endpoint="+serviceEndpoints.getAmountReserveAdditionalEndpoint());

		PaymentResponse reserveAdditionalResponse=me.reserveAdditionalAmount("tel:1234567890", "REF-12345", "The Big Fight", "USD",  1.5, sequenceNumber++, "C258");
		
		if (reserveAdditionalResponse!=null) {
			logger.debug("Reserve additional response:\n"+reserveAdditionalResponse.toString());
		} else {
			logger.debug("No response obtained");
		}

		logger.debug("Reservation charge endpoint="+serviceEndpoints.getAmountReservationChargeEndpoint());

		PaymentResponse chargeResponse=me.chargeAmount("tel:1234567890", "REF-12345", "The Big Fight", "USD",  3.0, sequenceNumber++, "C528", "http://notaurl.com", "CORR-0001", "Jupiter TV", "Video", "Wap", 0.0, "JUP-1", "JUP-001234");
		
		if (reserveInitialResponse!=null) {
			logger.debug("Reservation charge response:\n"+chargeResponse.toString());
		} else {
			logger.debug("No response obtained");
		}

		logger.debug("Reservation release endpoint="+serviceEndpoints.getAmountReservationReleaseEndpoint());

		PaymentResponse releaseResponse=me.releaseReservation("tel:1234567890", "REF-12345", sequenceNumber);
		
		if (releaseResponse!=null) {
			logger.debug("Reservation response:\n"+releaseResponse.toString());
		} else {
			logger.debug("No response obtained");
		}
		
	}

}
