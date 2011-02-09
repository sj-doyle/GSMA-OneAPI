package org.gsm.oneapi.server.payment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.responsebean.payment.AmountTransaction;
import org.gsm.oneapi.server.ValidationRule;

/**
 * Servlet implementing the OneAPI function for the payment API - charging an end user
 */
public class AmountChargeServlet extends PaymentServlet implements Runnable {
	static Logger logger=Logger.getLogger(AmountChargeServlet.class);

	private static final long serialVersionUID = -6237772242372106922L;
	
	private final String[] validationRules={"1", "payment", "*", "transactions", "amount"};

	public void init() throws ServletException {
		logger.debug("AmountChargeServlet initialised");
    }
	
	private AmountTransaction callbackData=null;
	private String callbackURL=null;
	
	public AmountChargeServlet() {
	}

	public AmountChargeServlet(AmountTransaction callbackData, String callbackURL) {
		this.callbackData=callbackData;
		this.callbackURL=callbackURL;
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		dumpRequestDetails(request, logger);
		
		String[] requestParts=getRequestParts(request);
		
		if (validateRequest(request, response, requestParts, validationRules)) {
			
			/*
			 * Decode all provided parameters
			 */
			String endUserId=requestParts[2];

			String referenceCode=nullOrTrimmed(request.getParameter("referenceCode"));
			String transactionOperationStatus=nullOrTrimmed(request.getParameter("transactionOperationStatus"));
			String description=nullOrTrimmed(request.getParameter("description"));
			String currency=nullOrTrimmed(request.getParameter("currency"));
			Double amount=parseDouble(request.getParameter("amount"));
			String code=nullOrTrimmed(request.getParameter("code"));
			String callbackURL=nullOrTrimmed(request.getParameter("callbackURL"));
			String clientCorrelator=nullOrTrimmed(request.getParameter("clientCorrelator"));
			String purchaseCategoryCode=nullOrTrimmed(request.getParameter("purchaseCategoryCode"));
			String onBehalfOf=nullOrTrimmed(request.getParameter("onBehalfOf"));
			String channel=nullOrTrimmed(request.getParameter("channel"));
			Double taxAmount=parseDouble(request.getParameter("taxAmount"));
			String serviceID=nullOrTrimmed(request.getParameter("serviceID"));
			String productID=nullOrTrimmed(request.getParameter("productID"));
			
			ValidationRule[] rules=null;
			
			if (code!=null) {
				rules=new ValidationRule[] {
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "endUserId", endUserId),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "referenceCode", referenceCode),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "transactionOperationStatus", transactionOperationStatus, "charged"),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "description", description),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "code", code),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL_URL, "callbackURL", callbackURL),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "clientCorrelator", clientCorrelator),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "onBehalfOf", onBehalfOf),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "purchaseCategoryCode", purchaseCategoryCode),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL_PAYMENT_CHANNEL, "channel", channel),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL_DOUBLE_GE_ZERO, "taxAmount", taxAmount),					
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "serviceID", serviceID),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "productID", productID),
				};
				
			} else {
				rules=new ValidationRule[] {
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "endUserId", endUserId),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "referenceCode", referenceCode),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "transactionOperationStatus", transactionOperationStatus, "charged"),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "description", description),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "currency", currency),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_DOUBLE_GE_ZERO, "amount", amount),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL_URL, "callbackURL", callbackURL),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "clientCorrelator", clientCorrelator),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "onBehalfOf", onBehalfOf),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "purchaseCategoryCode", purchaseCategoryCode),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL_PAYMENT_CHANNEL, "channel", channel),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL_DOUBLE_GE_ZERO, "taxAmount", taxAmount),					
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "serviceID", serviceID),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "productID", productID),
				};
			}

			if (checkRequestParameters(response, rules)) {						
				/*
				 * Generate response
				 */
				
				String serverReferenceCode="ABC-123";
	
				String resourceURL=getRequestHostnameAndContext(request)+request.getServletPath()+"/1/payment/"+(endUserId!=null?urlEncode(endUserId):"")+"/transactions/amount"+(referenceCode!=null?("/"+urlEncode(serverReferenceCode)):"");
				
				AmountTransaction rd=new AmountTransaction();
				
				rd.setClientCorrelator(clientCorrelator);
				rd.setEndUserId(endUserId);
				AmountTransaction.PaymentAmount.ChargingInformation charge=new AmountTransaction.PaymentAmount.ChargingInformation();
				charge.setAmount(amount);
				charge.setCurrency(currency);
				charge.setDescription(description);
				
				AmountTransaction.PaymentAmount payment=new AmountTransaction.PaymentAmount();
				payment.setChargingInformation(charge);
				
				payment.setTotalAmountCharged(amount);
				
				rd.setPaymentAmount(payment);
				rd.setReferenceCode(referenceCode);
				if (callbackURL==null) {
					rd.setTransactionOperationStatus(transactionOperationStatus);
				} else {
					rd.setTransactionOperationStatus("Processing");
				}
				rd.setResourceURL(resourceURL);
				rd.setClientCorrelator(clientCorrelator);
				rd.setServerReferenceCode(serverReferenceCode);
					
				ObjectMapper mapper=new ObjectMapper();			
				String jsonResponse="{\"amountTransaction\":"+mapper.writeValueAsString(rd)+"}";
	
				if (callbackURL==null) {
					sendJSONResponse(response, jsonResponse, CREATED, resourceURL);
				} else {
					sendJSONResponse(response, jsonResponse, ACCEPTED, resourceURL);
					
					AmountChargeServlet t=new AmountChargeServlet(rd, callbackURL);					
					new Thread(t).start();
				}
			}
		}
		
	}

	public void run() {
		logger.debug("Notifier Thread :: Sleeping now...");
		try {
			Thread.sleep(10000L);
		} catch (Exception e) {}
		logger.debug("Notifier Thread ::  Awoken");
		callbackData.setTransactionOperationStatus("charged");
		try {
			logger.debug("Notifier Thread :: Creating connection to "+callbackURL);
			HttpURLConnection con = (HttpURLConnection) new URL(callbackURL).openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			
			ObjectMapper mapper=new ObjectMapper();			
			String jsonResponse="{\"amountTransaction\":"+mapper.writeValueAsString(callbackData)+"}";

			logger.debug("Notifier Thread :: Sending JSON data: "+jsonResponse);
			OutputStream output=con.getOutputStream();
			byte[] ba=jsonResponse.getBytes();
			output.write (ba);
			output.flush();
			output.close();
			logger.debug("Notifier Thread :: Finished output");
			logger.debug("Notifier Thread :: Reading response");
			
			InputStream in=con.getInputStream();
			logger.debug("Notifier Thread :: Response code: "+con.getResponseCode());
			if (in!=null) {
				int c;
				StringBuffer rbuf=new StringBuffer();
				while ((c=in.read())!=-1) {
					rbuf.append((char) c);
				}
				logger.debug("Notifier Thread :: Read: "+rbuf.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
