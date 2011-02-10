package org.gsm.oneapi.sms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.endpoints.ServiceEndpoints;
import org.gsm.oneapi.foundation.CommandLineOptions;
import org.gsm.oneapi.foundation.FormParameters;
import org.gsm.oneapi.foundation.JSONRequest;
import org.gsm.oneapi.responsebean.RequestError;
import org.gsm.oneapi.responsebean.sms.InboundSMSMessageNotification;
import org.gsm.oneapi.responsebean.sms.InboundSMSMessageNotificationWrapper;
import org.gsm.oneapi.responsebean.sms.RetrieveSMSResponse;
import org.gsm.oneapi.responsebean.sms.SMSDeliveryReceiptSubscriptionResponse;
import org.gsm.oneapi.responsebean.sms.SMSMessageReceiptSubscriptionResponse;
import org.gsm.oneapi.responsebean.sms.SMSSendResponse;
import org.gsm.oneapi.server.OneAPIServlet;

public class SMSRetrieve {
	static Logger logger=Logger.getLogger(SMSRetrieve.class);
	
	ServiceEndpoints endPoints=null;
	String authorisationHeader=null;
	
	public static boolean dumpRequestAndResponse=false;

	private static JSONRequest<RetrieveSMSResponse> retrieveSMSprocessor=new JSONRequest<RetrieveSMSResponse>(new RetrieveSMSResponse());
	private static JSONRequest<SMSMessageReceiptSubscriptionResponse> smsMessageReceiptSubscriptionProcessor=new JSONRequest<SMSMessageReceiptSubscriptionResponse>(new SMSMessageReceiptSubscriptionResponse());

	/**
	 Creates a new instance of the Receive SMS API main interface. Requires endPoints to define the URL targets of the various Receive SMS network calls and authorisationHeader containing the username/password used for HTTP Basic authorisation with the OneAPI server.  
	                          
	@param  endPoints  contains a set of service/ call specific endpoints 	
	@param  authorisationHeader Base 64 encoded username/ password
	     
	@see org.gsm.oneapi.endpoints.ServiceEndpoints
	@see org.gsm.oneapi.foundation.JSONRequest#getAuthorisationHeader(String, String)
	 */	
	public SMSRetrieve(ServiceEndpoints endPoints, String authorisationHeader) {
		this.endPoints=endPoints;
		this.authorisationHeader=authorisationHeader;
	}

	/**
	 Creates a new instance of the Receive SMS API main interface. Requires endPoints to define the URL targets of the various Receive SMS network calls and the username/password used for HTTP Basic authorisation with the OneAPI server.  
	                          
	@param  endPoints  contains a set of service/ call specific endpoints 	
	@param  username is the account name allocated for use of the service
	@param  password is the corresponding authentication password
	     
	@see org.gsm.oneapi.endpoints.ServiceEndpoints
	 */	
	public SMSRetrieve(ServiceEndpoints endPoints, String username, String password) {
		String authorisationHeader=null;
		if (username!=null && password!=null) {
			authorisationHeader=JSONRequest.getAuthorisationHeader(username, password);
		}
		this.authorisationHeader=authorisationHeader;
		this.endPoints=endPoints;
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
	 Retrieve SMS messages sent to your Web application  
	                          
	@param registrationId (mandatory) is agreed with your network operator for receiving messages
	@param maxBatchSize (mandatory) is the maximum number of messages to retrieve in this request
	     
	@see RetrieveSMSResponse
	*/
	public RetrieveSMSResponse retrieveMessages(String registrationId, int maxBatchSize) {
		RetrieveSMSResponse response=new RetrieveSMSResponse();
		
		if (registrationId!=null && maxBatchSize>=0) {
			
        	int responseCode=0;
            String contentType = null;

            try {
				logger.debug("endpoint="+endPoints.getRetrieveSMSEndpoint());
				String endpoint=endPoints.getRetrieveSMSEndpoint().replaceAll("\\{registrationId\\}", URLEncoder.encode(registrationId, "utf-8")).replaceAll("\\{maxBatchSize\\}", String.valueOf(maxBatchSize));
				
				if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, null);
				
				HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
	
	        	responseCode=con.getResponseCode();
	            contentType = con.getContentType();
	            
	            response.setHTTPResponseCode(responseCode);
	            response.setContentType(contentType);
		        
	            response=retrieveSMSprocessor.getResponse(con, OneAPIServlet.OK);
			} catch (Exception e) {
				logger.error("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
				e.printStackTrace();
				response.setHTTPResponseCode(responseCode);
				response.setContentType(contentType);
				
				response.setRequestError(new RequestError(RequestError.SERVICEEXCEPTION, "SVCJAVA", e.getMessage(), e.getClass().getName()));
			}
		}
		return response;
	}

	/**
	 Start subscribing to notifications of SMS messages sent to your application 
	                          
	@param destinationAddress (mandatory) is the address/ MSISDN, or code agreed with the operator, to which people may send an SMS to your application
	@param notifyURL (mandatory) is the URL to which you would like a notification of message receipts sent
	@param criteria (optional) is case-insensitve text to match against the first word of the message, ignoring any leading whitespace. This allows you to reuse a short code among various applications, each of which can register their own subscription with different criteria
	@param notificationFormat (optional) is the content type that notifications will be sent in Ð for OneAPI v1.0 only JSON is supported
	@param clientCorrelator (optional) uniquely identifies this create subscription request. If there is a communication failure during the request, using the same clientCorrelator when retrying the request allows the operator to avoid creating a duplicate subscription
	@param callbackData (optional) is a function name or other data that you would like included when the POST is sent to your application

	@see SMSMessageReceiptSubscriptionResponse
	*/
	public SMSMessageReceiptSubscriptionResponse subscribeToReceiptNotifications(String destinationAddress, String notifyURL, String criteria, String notificationFormat, String clientCorrelator, String callbackData) {
		SMSMessageReceiptSubscriptionResponse response=new SMSMessageReceiptSubscriptionResponse();

		if (destinationAddress!=null && notifyURL!=null) {
			FormParameters formParameters=new FormParameters();
			formParameters.put("destinationAddress", destinationAddress);
			formParameters.put("notifyURL", notifyURL);
			formParameters.put("criteria", criteria);
			formParameters.put("notificationFormat", notificationFormat);
			formParameters.put("clientCorrelator", clientCorrelator);
			formParameters.put("callbackData", callbackData);
			
	    	int responseCode=0;
	        String contentType = null;
			
			try {
				String endpoint=endPoints.getSMSReceiptSubscriptionsEndpoint();
				
				if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, formParameters); 
	
				HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
		        con.setDoOutput(true);
		        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
		        
		        String requestBody=JSONRequest.formEncodeParams(formParameters);
		        out.write(requestBody);
		        out.close();
	
	        	responseCode=con.getResponseCode();
	            contentType = con.getContentType();
		        
	            response=smsMessageReceiptSubscriptionProcessor.getResponse(con, OneAPIServlet.CREATED);
			} catch (Exception e) {
				response.setHTTPResponseCode(responseCode);
				response.setContentType(contentType);
				
				response.setRequestError(new RequestError(RequestError.SERVICEEXCEPTION, "SVCJAVA", e.getMessage(), e.getClass().getName()));
				
				logger.error("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
			}
		}
		return response;
	}
	
	/**
	 Stop subscribing to message receipt notifications for all your received SMS  
	                          
	@param subscriptionId (mandatory) contains the subscriptionId of a previously created SMS message receipt subscription

	*/
	public int cancelReceiptNotifications(String subscriptionId)  {
		int responseCode=0;
		
		if (subscriptionId!=null) {
			
			try {
				logger.debug("endpoint="+endPoints.getCancelSMSReceiptSubscriptionEndpoint());
				String endpoint=endPoints.getCancelSMSReceiptSubscriptionEndpoint().replaceAll("\\{subscriptionId\\}", URLEncoder.encode(subscriptionId, "utf-8"));
				
				if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, null);
				
				HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
				
				con.setRequestMethod("DELETE");
	
	        	responseCode=con.getResponseCode();
			} catch (Exception e) {
				logger.error("Exception "+e.getMessage()+" "+e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		return responseCode;
	}

	/**
	 * Utility function to process a received JSON formatted message received notification into a usable class instance of InboundSMSMessageNotification
	 * @param request the HttpServletRequest - make sure the input stream has not been read before calling
	 * @return InboundSMSMessageNotification
	 */
	public static InboundSMSMessageNotification convertInboundSMSMessageNotification(HttpServletRequest request) {
		InboundSMSMessageNotification inboundSMSMessageNotification=null;
		if (request.getContentType()!=null && request.getContentType().equalsIgnoreCase("application/json")) {
			try {
				ServletInputStream inputStream=request.getInputStream();
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            int i;
	            while ((i = (byte) inputStream.read()) != -1) baos.write(i);
	           
	            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	            
	            ObjectMapper mapper=new ObjectMapper();
	
	            InboundSMSMessageNotificationWrapper wrapper=mapper.readValue(bais, InboundSMSMessageNotificationWrapper.class);
	            if (wrapper!=null) inboundSMSMessageNotification=wrapper.getInboundSMSMessageNotification();
			} catch (java.io.IOException e) {
				logger.error("IOException "+e.getMessage());				
			}
		}
		return inboundSMSMessageNotification;
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

		logger.debug("Demonstration of inbound SMS API");
		
		String authorisationHeader=JSONRequest.getAuthorisationHeader(username, password);
		
		SMSRetrieve me=new SMSRetrieve(serviceEndpoints, authorisationHeader);
		
		logger.debug("SMS receive endpoint="+serviceEndpoints.getRetrieveSMSEndpoint());		
		RetrieveSMSResponse retrieveResponse=me.retrieveMessages("3456", 2);				
		if (retrieveResponse!=null) {
			logger.debug("Have SMS retrieve response:\n"+retrieveResponse.toString());
		} else {
			logger.debug("No response obtained");
		}

		String notifyURL="http://test.com/notifyThis/Message="+JSONRequest.urlEncode("This is encoded");

		logger.debug("Register a message receipt notification endpoint="+serviceEndpoints.getSMSReceiptSubscriptionsEndpoint());

		SMSMessageReceiptSubscriptionResponse receiptSubscription=me.subscribeToReceiptNotifications("3456", notifyURL, "Vote%", "JSON", "12345", "doSomething()");
		if (receiptSubscription!=null) {
			logger.debug("SMSMessageReceiptSubscriptionResponse="+receiptSubscription.toString());
		} else {
			logger.debug("No response obtained");
		}

		logger.debug("Cancel message receipt notification endpoint="+serviceEndpoints.getCancelSMSReceiptSubscriptionEndpoint());
		
		int cancelSubscriptionResponse=me.cancelReceiptNotifications("sub789");
		logger.debug("Cancel receipt subscription response code="+cancelSubscriptionResponse);


	}


}
