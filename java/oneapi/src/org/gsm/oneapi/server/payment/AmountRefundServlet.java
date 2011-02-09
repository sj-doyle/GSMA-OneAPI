package org.gsm.oneapi.server.payment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.responsebean.payment.AmountTransaction;
import org.gsm.oneapi.server.ValidationRule;

/**
 * Servlet implementing the OneAPI function for the payment API - refunding an end user
 */
public class AmountRefundServlet extends PaymentServlet {
	static Logger logger=Logger.getLogger(AmountRefundServlet.class);

	private static final long serialVersionUID = -6237772242372106922L;
	
	private final String[] validationRules={"1", "payment", "*", "transactions", "amount"};

	public void init() throws ServletException {
		logger.debug("AmountRefundServlet initialised");
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
			String clientCorrelator=nullOrTrimmed(request.getParameter("clientCorrelator"));
			String originalServerReferenceCode=nullOrTrimmed(request.getParameter("originalServerReferenceCode"));
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
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "transactionOperationStatus", transactionOperationStatus, "refunded"),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "description", description),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "code", code),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "clientCorrelator", clientCorrelator),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "originalServerReferenceCode", originalServerReferenceCode),
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
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "transactionOperationStatus", transactionOperationStatus, "refunded"),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "description", description),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "currency", currency),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_DOUBLE_GE_ZERO, "amount", amount),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "clientCorrelator", clientCorrelator),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "originalServerReferenceCode", originalServerReferenceCode),
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
				rd.setTransactionOperationStatus(transactionOperationStatus);
				rd.setResourceURL(resourceURL);
				rd.setClientCorrelator(clientCorrelator);
				rd.setServerReferenceCode(serverReferenceCode);

				ObjectMapper mapper=new ObjectMapper();			
				String jsonResponse="{\"amountTransaction\":"+mapper.writeValueAsString(rd)+"}";
	
				sendJSONResponse(response, jsonResponse, OK, resourceURL);
			}
		}
		
	}

}
