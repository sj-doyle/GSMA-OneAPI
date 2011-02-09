package org.gsm.oneapi.server.payment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.gsm.oneapi.responsebean.payment.AmountReservationTransaction;
import org.gsm.oneapi.server.ValidationRule;

/**
 * Servlet implementing the OneAPI function for the payment API - reserving an additional amount on the end user's account
 */
public class AmountReserveAdditionalServlet extends PaymentServlet {
	static Logger logger=Logger.getLogger(AmountReserveAdditionalServlet.class);

	private static final long serialVersionUID = -6237772242372106922L;

	private final String[] validationRules={"1", "payment", "*", "transactions", "amountReservation", "*"};

	public void init() throws ServletException {
		logger.debug("AmountReserveAdditionalServlet initialised");
    }

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{		
		dumpRequestDetails(request, logger);
		
		String[] requestParts=getRequestParts(request);
		
		if (validateRequest(request, response, requestParts, validationRules)) {
			
			/*
			 * Decode all provided parameters
			 */
			String endUserId=requestParts[2];
			String transactionId=requestParts[5];

			String referenceCode=nullOrTrimmed(request.getParameter("referenceCode"));
			String transactionOperationStatus=nullOrTrimmed(request.getParameter("transactionOperationStatus"));
			String description=nullOrTrimmed(request.getParameter("description"));
			String currency=nullOrTrimmed(request.getParameter("currency"));
			Double amount=parseDouble(request.getParameter("amount"));			
			String code=nullOrTrimmed(request.getParameter("code"));
			int referenceSequence=parseInt(request.getParameter("referenceSequence"));
			String onBehalfOf=nullOrTrimmed(request.getParameter("onBehalfOf"));
			String channel=nullOrTrimmed(request.getParameter("channel"));
			Double taxAmount=parseDouble(request.getParameter("taxAmount"));
			String serviceID=nullOrTrimmed(request.getParameter("serviceID"));
			String productID=nullOrTrimmed(request.getParameter("productID"));
			String purchaseCategoryCode=nullOrTrimmed(request.getParameter("purchaseCategoryCode"));
			
			ValidationRule[] rules=null;
			
			if (code!=null) {
				rules=new ValidationRule[] {
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "endUserId", endUserId),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "transactionId", transactionId),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "referenceCode", referenceCode),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "transactionOperationStatus", transactionOperationStatus, "reserved"),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_INT_GT_ONE, "referenceSequence", Integer.toString(referenceSequence)),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "description", description),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "code", code),
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
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "transactionId", transactionId),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "referenceCode", referenceCode),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "transactionOperationStatus", transactionOperationStatus, "reserved"),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_INT_GT_ONE, "referenceSequence", Integer.toString(referenceSequence)),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "description", description),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "currency", currency),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_DOUBLE_GE_ZERO, "amount", amount),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "onBehalfOf", onBehalfOf),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "purchaseCategoryCode", purchaseCategoryCode),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL_PAYMENT_CHANNEL, "channel", channel),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL_DOUBLE_GE_ZERO, "taxAmount", taxAmount),					
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "serviceID", serviceID),
						new ValidationRule(ValidationRule.VALIDATION_TYPE_OPTIONAL, "productID", productID),
				};
			}
			
			if (checkRequestParameters(response, rules)) {			
				String serverReferenceCode="DEF-456";
				
				String resourceURL=getRequestHostnameAndContext(request)+request.getServletPath()+"/1/payment/"+(endUserId!=null?urlEncode(endUserId):"")+"/transactions/amountReservation/"+urlEncode(transactionId);
				
				AmountReservationTransaction responseData=new AmountReservationTransaction();

				/*
				 * Processing of the request to reserve an additional amount
				 */
				responseData.setEndUserId(endUserId);

				AmountReservationTransaction.PaymentAmount.ChargingInformation charge=new AmountReservationTransaction.PaymentAmount.ChargingInformation();
				charge.setAmount(amount);
				charge.setCurrency(currency);
				charge.setDescription(description);
				charge.setPurchaseCategoryCode(purchaseCategoryCode);
				
				AmountReservationTransaction.PaymentAmount payment=new AmountReservationTransaction.PaymentAmount();
				payment.setChargingInformation(charge);
				payment.setAmountReserved(amount);
				responseData.setPaymentAmount(payment);
				responseData.setReferenceCode(referenceCode);
				responseData.setTransactionOperationStatus(transactionOperationStatus);
				responseData.setResourceURL(resourceURL);
				responseData.setReferenceSequence(referenceSequence);				
				responseData.setServerReferenceCode(serverReferenceCode);
					
				ObjectMapper mapper=new ObjectMapper();			
				String jsonResponse="{\"amountReservationTransaction\":"+mapper.writeValueAsString(responseData)+"}";
	
				sendJSONResponse(response, jsonResponse, OK, resourceURL);
			}
		}
		
	}

}
