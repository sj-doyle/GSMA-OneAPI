<%@ page import="org.gsm.oneapi.payment.Reservation"%>
<%@ page import="org.gsm.oneapi.responsebean.payment.AmountReservationTransaction"%>
<%
	System.out.println("Have invoked Payment Charge Reservation Notification callback");

	AmountReservationTransaction amountReservationTransaction=Reservation.convertAmountReservationTransaction(request);

	if (amountReservationTransaction!=null) {
		System.out.println("Received AmountReservationTransaction:");
		System.out.println("clientCorrelator           = "+amountReservationTransaction.getClientCorrelator());
		System.out.println("endUserId                  = "+amountReservationTransaction.getEndUserId());
		System.out.println("referenceCode              = "+amountReservationTransaction.getReferenceCode());
		System.out.println("referenceSequence          = "+amountReservationTransaction.getReferenceSequence());
		System.out.println("resourceURL                = "+amountReservationTransaction.getResourceURL());		
		System.out.println("serverReferenceCode        = "+amountReservationTransaction.getServerReferenceCode());
		System.out.println("transactionOperationStatus = "+amountReservationTransaction.getTransactionOperationStatus());
	}
	response.setStatus(200);
%>