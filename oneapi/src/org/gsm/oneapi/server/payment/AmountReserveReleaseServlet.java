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
 * Servlet implementing the OneAPI function for the payment API - releasing a reservation amount on the end user's account
 */
public class AmountReserveReleaseServlet extends PaymentServlet {
	static Logger logger=Logger.getLogger(AmountReserveReleaseServlet.class);

	private static final long serialVersionUID = -6237772242372106922L;

	private final String[] validationRules={"1", "payment", "*", "transactions", "amountReservation", "*"};

	public void init() throws ServletException {
		logger.debug("AmountReserveReleaseServlet initialised");
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

			String transactionOperationStatus=nullOrTrimmed(request.getParameter("transactionOperationStatus"));
			int referenceSequence=parseInt(request.getParameter("referenceSequence"));

			ValidationRule[] rules={
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_TEL, "endUserId", endUserId),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "transactionId", transactionId),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY, "transactionOperationStatus", transactionOperationStatus, "released"),
					new ValidationRule(ValidationRule.VALIDATION_TYPE_MANDATORY_INT_GT_ONE, "referenceSequence", Integer.valueOf(referenceSequence)),
			};

			if (checkRequestParameters(response, rules)) {			

				String serverReferenceCode="DEF-456";
				
				String resourceURL=getRequestHostnameAndContext(request)+request.getServletPath()+"/1/payment/"+(endUserId!=null?urlEncode(endUserId):"")+"/transactions/amountReservation/"+urlEncode(transactionId);
				
				AmountReservationTransaction responseData=new AmountReservationTransaction();

				responseData.setEndUserId(endUserId);
				AmountReservationTransaction.PaymentAmount.ChargingInformation charge=new AmountReservationTransaction.PaymentAmount.ChargingInformation();
				charge.setAmount(10.0);
				charge.setCurrency("USD");
				charge.setDescription("Streaming Video of the Big Fight");
				
				AmountReservationTransaction.PaymentAmount payment=new AmountReservationTransaction.PaymentAmount();
				payment.setChargingInformation(charge);
				payment.setAmountReserved(0.0);
				payment.setTotalAmountCharged(5.0);
				
				responseData.setPaymentAmount(payment);
				responseData.setReferenceCode("REF-12346");
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
