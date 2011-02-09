package org.gsm.oneapi.responsebean.payment;

/**
 * The main part of the OneAPI response for the "Reservation" payment operations including "Reserve Initial", "Reserve Additonal", "Charge Reserved" and "Release Reservation"
 */
public class AmountReservationTransaction implements java.io.Serializable {
	private static final long serialVersionUID = -8018179657702827920L;
	
	/**
	 * the clientCorrelator ensures the same payment request is only applied once
	 */
	private String clientCorrelator=null;
	/**
	 * the MSISDN or Anonymous Caller Reference of the user being charged
	 */
	private String endUserId=null;
	/**
	 * is your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
	 */
	private String referenceCode=null;
	/**
	 * confirms the sequence number for the reservation charging functions. This allows the server to distinguish easily between new and repeated requests in the case of a communication failure.
	 */
	private Integer referenceSequence=null;
	/**
	 * a server allocated reference URL for this payment request
	 */
	private String resourceURL=null;
	/**
	 * the server's reference code for this payment request - it must be used in any related payment requests
	 */
	private String serverReferenceCode=null;
	/**
	 * confirmation of the charging operation 
	 */
	private String transactionOperationStatus=null;

	/**
	 * return the clientCorrelator ensures the same payment request is only applied once
	 */
	public String getClientCorrelator() { return clientCorrelator; }
	/**
	 * return the MSISDN or Anonymous Caller Reference of the user being charged
	 */
	public String getEndUserId() { return endUserId; }
	/**
	 * return your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.
	 */
	public String getReferenceCode() { return referenceCode; }
	/**
	 * return the sequence number for the reservation charging functions. This allows the server to distinguish easily between new and repeated requests in the case of a communication failure.
	 */
	public Integer getReferenceSequence() { return referenceSequence; }
	/**
	 * return the server's reference code for this payment request - it must be used in any related payment requests
	 */
	public String getServerReferenceCode() { return serverReferenceCode; }
	/**
	 * return the server allocated reference URL for this payment request
	 */
	public String getResourceURL() { return resourceURL; }
	/**
	 * return confirmation of the charging operation
	 */
	public String getTransactionOperationStatus() { return transactionOperationStatus; }

	/**
	 * set the clientCorrelator ensures the same payment request is only applied once. This is called internally to set the contents according to the JSON response.
	 */
	public void setClientCorrelator(String clientCorrelator) { this.clientCorrelator=clientCorrelator; }
	/**
	 * set the MSISDN or Anonymous Caller Reference of the user being charged. This is called internally to set the contents according to the JSON response.
	 */
	public void setEndUserId(String endUserId) { this.endUserId=endUserId; }
	/**
	 * set your reference for reconciliation purposes. The operator should include it in reports so that you can match their view of what has been sold with yours by matching the referenceCodes.. This is called internally to set the contents according to the JSON response.
	 */
	public void setReferenceCode(String referenceCode) { this.referenceCode=referenceCode; }
	/**
	 * set the sequence number for the reservation charging functions. This allows the server to distinguish easily between new and repeated requests in the case of a communication failure.
	 */
	public void setReferenceSequence(Integer referenceSequence) { this.referenceSequence=referenceSequence; }
	/**
	 * set the server's reference code for this payment request - it must be used in any related payment requests. This is called internally to set the contents according to the JSON response.
	 */
	public void setServerReferenceCode(String serverReferenceCode) { this.serverReferenceCode=serverReferenceCode; }
	/**
	 * set the server allocated reference URL for this payment request. This is called internally to set the contents according to the JSON response.
	 */
	public void setResourceURL(String resourceURL) { this.resourceURL=resourceURL; }
	/**
	 * set confirmation of the charging operation. This is called internally to set the contents according to the JSON response.
	 */
	public void setTransactionOperationStatus(String transactionOperationStatus) { this.transactionOperationStatus=transactionOperationStatus; }


	/**
	 * the details of the actual payment are confirmed in the PaymentAmount inner class
	 */
	public static class PaymentAmount {
		
		/**
		 * the details of the item being charged/ refunded
		 */
		public static class ChargingInformation {
			/**
			 * the amount to be charged/ refunded
			 */
			private Double amount=0.0;
			/**
			 * the currency being charged
			 */
			private String currency=null;
			/**
			 * a description of the item being charged/ refunded
			 */
			private String description=null;
			/**
			 * an indication of the content type. Values meaningful to the billing system would be published by a OneAPI implementation.
			 */
			private String purchaseCategoryCode=null;
			
			/**
			 * return the amount to be charged/ refunded
			 */
			public Double getAmount() { return amount; }
			/**
			 * return the currency being charged
			 */
			public String getCurrency() { return currency; }
			/**
			 * return the description of the item being charged/ refunded
			 */
			public String getDescription() { return description; }
			/**
			 * return an indication of the content type. Values meaningful to the billing system would be published by a OneAPI implementation.
			 */
			public String getPurchaseCategoryCode() { return purchaseCategoryCode; }
			
			/**
			 * set the amount to be charged/ refunded. This is called internally to set the contents according to the JSON response.
			 */
			public void setAmount(Double amount) { this.amount=amount; }
			/**
			 * set the currency being charged. This is called internally to set the contents according to the JSON response.
			 */
			public void setCurrency(String currency) { this.currency=currency; }
			/**
			 * set the description of the item being charged/ refunded. This is called internally to set the contents according to the JSON response.
			 */
			public void setDescription(String description) { this.description=description; }
			/**
			 * set an indication of the content type. Values meaningful to the billing system would be published by a OneAPI implementation. This is called internally to set the contents according to the JSON response.
			 */
			public void setPurchaseCategoryCode(String purchaseCategoryCode) { this.purchaseCategoryCode=purchaseCategoryCode; }
		}
			
		/**
		 * the amount currently reserved against the end user's account
		 */
		private Double amountReserved=0.0;
		/**
		 * the detail of the charge
		 */
		private ChargingInformation chargingInformation=null;
		/**
		 * the total amount to be charged to the end user
		 */
		private Double totalAmountCharged=0.0;
		
		/**
		 * return the amount currently reserved against the end user's account
		 */
		public Double getAmountReserved() { return this.amountReserved; }
		/**
		 * @return the detail of the charge
		 */
		public ChargingInformation getChargingInformation() { return chargingInformation; }
		/**
		 * return the total amount to be charged to the end user
		 */
		public Double getTotalAmountCharged() { return totalAmountCharged; }
		
		/**
		 * set the amount currently reserved against the end user's account
		 */
		public void setAmountReserved(Double amountReserved) { this.amountReserved=amountReserved; }
		/**
		 * set the detail of the charge
		 */
		public void setChargingInformation(ChargingInformation chargingInformation) { this.chargingInformation=chargingInformation; }
		/**
		 * set the total amount to be charged to the end user
		 */
		public void setTotalAmountCharged(Double totalAmountCharged) { this.totalAmountCharged=totalAmountCharged; }
		
	}
	
	/**
	 * the detail of the payment/ charge
	 */
	private PaymentAmount paymentAmount=null;
	/**
	 * return the detail of the payment/ charge
	 */
	public PaymentAmount getPaymentAmount() { return paymentAmount; }
	/**
	 * set the detail of the payment/ charge. the detail of the payment/ charge
	 */
	public void setPaymentAmount(PaymentAmount paymentAmount) { this.paymentAmount=paymentAmount; }

	/** 
	 * generate a textual representation of the AmountReservationTransaction including all nested elements and classes 
	 */
	public String toString() {
		StringBuffer buffer=new StringBuffer();
		buffer.append("clientCorrelator = ");
		buffer.append(clientCorrelator);
		
		buffer.append(", endUserID = ");
		buffer.append(endUserId);

		buffer.append(", referenceCode = ");
		buffer.append(referenceCode);
		
		buffer.append(", referenceSequence = ");
		buffer.append(referenceSequence);
		
		buffer.append(", resourceURL = ");
		buffer.append(resourceURL);

		buffer.append(", serverReferenceCode = ");
		buffer.append(serverReferenceCode);
		
		buffer.append(", transactionOperationStatus = ");
		buffer.append(transactionOperationStatus);
		
		if (paymentAmount!=null) {
			buffer.append(", paymentAmount = {");
			if (paymentAmount.chargingInformation!=null) {
				buffer.append("chargingInformation = {amount = ");
				buffer.append(paymentAmount.chargingInformation.amount);
				buffer.append(", currency = ");
				buffer.append(paymentAmount.chargingInformation.currency);
				buffer.append(", description = ");
				buffer.append(paymentAmount.chargingInformation.description);
				buffer.append(", purchaseCategoryCode = ");
				buffer.append(paymentAmount.chargingInformation.purchaseCategoryCode);
				buffer.append("}, ");
			}
			buffer.append("amountReserved = ");
			buffer.append(paymentAmount.amountReserved);
			buffer.append(", totalAmountCharged = ");
			buffer.append(paymentAmount.totalAmountCharged);
			buffer.append("} ");
		}		
		
		return buffer.toString();
	}
}
