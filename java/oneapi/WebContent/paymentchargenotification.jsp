<%@ page import="org.gsm.oneapi.payment.Charge"%>
<%@ page import="org.gsm.oneapi.responsebean.payment.AmountTransaction"%>
<%
	System.out.println("Have invoked Payment Charge Notification callback");

	AmountTransaction amountTransaction=Charge.convertAmountTransaction(request);

	if (amountTransaction!=null) {
		System.out.println("Received AmountTransaction:");
		System.out.println("clientCorrelator           = "+amountTransaction.getClientCorrelator());
		System.out.println("endUserId                  = "+amountTransaction.getEndUserId());
		System.out.println("referenceCode              = "+amountTransaction.getReferenceCode());
		System.out.println("resourceURL                = "+amountTransaction.getResourceURL());
		System.out.println("serverReferenceCode        = "+amountTransaction.getServerReferenceCode());
		System.out.println("transactionOperationStatus = "+amountTransaction.getTransactionOperationStatus());
	}
	response.setStatus(200);
%>