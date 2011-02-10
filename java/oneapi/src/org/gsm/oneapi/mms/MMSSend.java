package org.gsm.oneapi.mms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.endpoints.ServiceEndpoints;
import org.gsm.oneapi.foundation.FormParameters;
import org.gsm.oneapi.foundation.JSONRequest;
import org.gsm.oneapi.responsebean.RequestError;
import org.gsm.oneapi.responsebean.mms.MMSDeliveryReceiptSubscriptionResponse;
import org.gsm.oneapi.responsebean.mms.MMSSendDeliveryStatusResponse;
import org.gsm.oneapi.responsebean.mms.MMSSendResponse;
import org.gsm.oneapi.responsebean.mms.DeliveryInfoNotification;
import org.gsm.oneapi.responsebean.mms.DeliveryInfoNotificationWrapper;
import org.gsm.oneapi.server.OneAPIServlet;

public class MMSSend {

	static Logger logger=Logger.getLogger(MMSSend.class);
	
	ServiceEndpoints endPoints=null;
	String authorisationHeader=null;
	
	public static boolean dumpRequestAndResponse=false;

	private static JSONRequest<MMSSendResponse> mmsSendResponseProcessor=new JSONRequest<MMSSendResponse>(new MMSSendResponse());
	private static JSONRequest<MMSSendDeliveryStatusResponse> mmsSendDeliveryStatusProcessor=new JSONRequest<MMSSendDeliveryStatusResponse>(new MMSSendDeliveryStatusResponse());
	private static JSONRequest<MMSDeliveryReceiptSubscriptionResponse> mmsDeliveryReceiptSubscriptionProcessor=new JSONRequest<MMSDeliveryReceiptSubscriptionResponse>(new MMSDeliveryReceiptSubscriptionResponse());

	/**
	 Creates a new instance of the Send MMS API main interface. Requires endPoints to define the URL targets of the various Send MMS network calls and authorisationHeader containing the username/password used for HTTP Basic authorisation with the OneAPI server.  
	                          
	@param  endPoints  contains a set of service/ call specific endpoints 	
	@param  authorisationHeader Base 64 encoded username/ password
	     
	@see org.gsm.oneapi.endpoints.ServiceEndpoints
	@see org.gsm.oneapi.foundation.JSONRequest#getAuthorisationHeader(String, String)
	 */	
	public MMSSend(ServiceEndpoints endPoints, String authorisationHeader) {
		this.endPoints=endPoints;
		this.authorisationHeader=authorisationHeader;
	}

	/**
	 Creates a new instance of the Send MMS API main interface. Requires endPoints to define the URL targets of the various Send MMS network calls and the username/password used for HTTP Basic authorisation with the OneAPI server.  
	                          
	@param  endPoints  contains a set of service/ call specific endpoints 	
	@param  username is the account name allocated for use of the service
	@param  password is the corresponding authentication password
	     
	@see org.gsm.oneapi.endpoints.ServiceEndpoints
	 */	
	public MMSSend(ServiceEndpoints endPoints, String username, String password) {
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
	 Send an MMS to one or more mobile terminals  
	                          
	@param senderAddress (mandatory) is the address to whom a responding MMS may be sent. Do not URL encode this value prior to passing to this function
	@param address (mandatory) contains at least one address for each end user ID to send to; in this case their MSISDN including the ‘tel:’ protocol identifier and the country code preceded by ‘+’. i.e., tel:+16309700001.  OneAPI also supports the Anonymous Customer Reference (ACR) if provided by the operator.
	@param message (mandatory) contains the message text to send
	@param senderName (mandatory) is the name to appear on the user’s terminal as the sender of the message
	@param attachments (optional) contains a list of attachments to be sent along with this MMS request
	@param clientCorrelator (optional) uniquely identifies this create MMS request. If there is a communication failure during the request, using the same clientCorrelator when retrying the request allows the operator to avoid sending the same MMS twice.
	@param notifyURL (optional) is the URL to which you would like a notification of delivery sent
	@param callbackData (optional) will be passed back to the notifyURL location, so you can use it to identify the message the receipt relates to (or any other useful data, such as a function name)
	     
	@see MMSSendResponse
	*/
	public MMSSendResponse sendMMS(String senderAddress, String[] address, String message, ArrayList<FileItem> attachments, String senderName, String clientCorrelator, String notifyURL, String callbackData) {
		MMSSendResponse response=new MMSSendResponse();
		
		FormParameters formParameters=new FormParameters();
		formParameters.put("senderAddress", senderAddress);
		for (String addr:address) if (address!=null) formParameters.put("address", addr);		
		formParameters.put("message", message);				
		formParameters.put("clientCorrelator", clientCorrelator);
		formParameters.put("notifyURL", notifyURL);
		formParameters.put("senderName", senderName);
		formParameters.put("callbackData", callbackData);
				
		if (true /* validation goes here */) {

			String endpoint=endPoints.getSendMMSEndpoint();
			
	    	int responseCode=0;
	        String contentType = null;
			
			try {
		        MimeMultipart mp = new MimeMultipart("mixed");

		        InternetHeaders rootHdr = new InternetHeaders();

		        rootHdr.addHeader("Content-Type", "application/x-www-form-urlencoded");
		        rootHdr.addHeader("Content-Disposition", "form-data; name=\"root-fields\"");

		        byte[] rootData = JSONRequest.formEncodeLineSeparaterParams(formParameters).getBytes();
		        mp.addBodyPart(new MimeBodyPart(rootHdr, rootData));
		        
		        if (attachments!=null && attachments.size()>0) {
		        	for (int i=0; i<attachments.size(); i++) {
		        		FileItem attachment=attachments.get(i);
		        		if (attachment!=null) {
			        		logger.debug("Attaching file ["+i+"] name="+attachment.getName()+" type="+attachment.getContentType());
			        		InternetHeaders attachmentHdr = new InternetHeaders();
			        		if (attachment.getContentType()!=null) attachmentHdr.addHeader("Content-Type", attachment.getContentType());
			        		if (attachment.getName()!=null) attachmentHdr.addHeader("Content-Disposition", "form-data; name=\""+attachment.getName()+"\"");
			        		byte[] dataBytes=attachment.get();
			        		MimeBodyPart mpa=new MimeBodyPart(attachmentHdr, dataBytes);
			        		mp.addBodyPart(mpa);
		        		}
		        	}
		        }
			
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();

		        mp.writeTo(baos);
		        
		        String content=baos.toString();
		        String boundary = content.substring(2, content.indexOf("\r\n"));
				
				if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, formParameters); 
	
				HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
		        con.setRequestMethod("POST");
		        
		        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=\""+boundary+"\"");
		        con.setDoOutput(true);
		        
		        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
	            out.write(content);
	            out.close();
	            
	        	responseCode = con.getResponseCode();
	            contentType = con.getContentType();
		        
	            response=mmsSendResponseProcessor.getResponse(con, OneAPIServlet.CREATED);
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
	 Query the delivery status for an MMS sent to one or more mobile terminals  
	                          
	@param senderAddress (mandatory) is the address from which MMS messages are being sent. Do not URL encode this value prior to passing to this function
	@param requestId (mandatory) contains the requestId returned from a previous call to the sendMMS function
	     
	@see MMSSendDeliveryStatusResponse
	*/
	public MMSSendDeliveryStatusResponse queryDeliveryStatus(String senderAddress, String requestId) {
		MMSSendDeliveryStatusResponse response=new MMSSendDeliveryStatusResponse();
		
		if (senderAddress!=null && requestId!=null) {
			
	    	int responseCode=0;
	        String contentType = null;

			try {
				logger.debug("endpoint="+endPoints.getQueryMMSDeliveryEndpoint());
				String endpoint=endPoints.getQueryMMSDeliveryEndpoint().replaceAll("\\{senderAddress\\}", URLEncoder.encode(senderAddress, "utf-8")).replaceAll("\\{requestId\\}", URLEncoder.encode(requestId, "utf-8"));
				
				if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, null);
				
				HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
	
	        	responseCode=con.getResponseCode();
	            contentType = con.getContentType();
	            
	            response.setHTTPResponseCode(responseCode);
	            response.setContentType(contentType);
		        
	            response=mmsSendDeliveryStatusProcessor.getResponse(con, OneAPIServlet.OK);
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
	 Start subscribing to delivery status notifications for all your sent MMS  
	                          
	@param senderAddress (mandatory) is the address from which MMS messages are being sent. Do not URL encode this value prior to passing to this function
	@param notifyURL (mandatory) is the URL to which you would like a notification of delivery sent
	@param clientCorrelator (optional) uniquely identifies this subscription request. If there is a communication failure during the request, using the same clientCorrelator when retrying the request allows the operator to avoid setting up the same subscription twice.
	@param callbackData (optional) will be passed back to the notifyURL location, so you can use it to identify the message the delivery receipt relates to (or any other useful data, such as a function name)

	@see MMSDeliveryReceiptSubscriptionResponse
	*/
	public MMSDeliveryReceiptSubscriptionResponse subscribeToDeliveryNotifications(String senderAddress, String clientCorrelator, String notifyURL, String callbackData) {
		MMSDeliveryReceiptSubscriptionResponse response=new MMSDeliveryReceiptSubscriptionResponse();

		if (senderAddress!=null && notifyURL!=null) {
			FormParameters formParameters=new FormParameters();
			formParameters.put("clientCorrelator", clientCorrelator);
			formParameters.put("notifyURL", notifyURL);
			formParameters.put("callbackData", callbackData);
			
	    	int responseCode=0;
	        String contentType = null;
			
			try {
				String endpoint=endPoints.getMMSDeliverySubscriptionsEndpoint().replaceAll("\\{senderAddress\\}", URLEncoder.encode(senderAddress, "utf-8"));
				
				if (dumpRequestAndResponse) JSONRequest.dumpRequestVariables(endpoint, authorisationHeader, formParameters); 
	
				HttpURLConnection con = JSONRequest.setupConnection(endpoint, authorisationHeader);
		        con.setDoOutput(true);
		        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
		        
		        String requestBody=JSONRequest.formEncodeParams(formParameters);
		        out.write(requestBody);
		        out.close();
	
	        	responseCode=con.getResponseCode();
	            contentType = con.getContentType();
		        
	            response=mmsDeliveryReceiptSubscriptionProcessor.getResponse(con, OneAPIServlet.CREATED);
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
	 Stop subscribing to delivery status notifications for all your sent MMS  
	                          
	@param subscriptionId (mandatory) contains the subscriptionId of a previously created MMS delivery receipt subscription

	*/
	public int cancelDeliveryNotifications(String subscriptionId)  {
		int responseCode=0;
		
		if (subscriptionId!=null) {			
			try {
				logger.debug("endpoint="+endPoints.getCancelMMSDeliverySubscriptionEndpoint());
				String endpoint=endPoints.getCancelMMSDeliverySubscriptionEndpoint().replaceAll("\\{subscriptionId\\}", URLEncoder.encode(subscriptionId, "utf-8"));
				
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
	 * Utility function to process a received JSON formatted delivery info notification into a usable class instance of DeliveryInfoNotification
	 * @param request the HttpServletRequest - make sure the input stream has not been read before calling
	 * @return DeliveryInfoNotification
	 */
	public static DeliveryInfoNotification convertDeliveryInfoNotification(HttpServletRequest request) {
		DeliveryInfoNotification deliveryInfoNotification=null;
		if (request.getContentType()!=null && request.getContentType().equalsIgnoreCase("application/json")) {
			try {
				ServletInputStream inputStream=request.getInputStream();
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            int i;
	            while ((i = (byte) inputStream.read()) != -1) baos.write(i);
	           
	            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	            
	            ObjectMapper mapper=new ObjectMapper();
	
	            DeliveryInfoNotificationWrapper wrapper=mapper.readValue(bais, DeliveryInfoNotificationWrapper.class);
	            if (wrapper!=null) deliveryInfoNotification=wrapper.getDeliveryInfoNotification();
			} catch (java.io.IOException e) {
				logger.error("IOException "+e.getMessage());				
			}
		}
		return deliveryInfoNotification;
	}


}
