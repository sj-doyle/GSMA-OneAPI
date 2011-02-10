package org.gsm.oneapi.responsebean.payment;

/**
 *AmountReservationTransactionWrapper is a utility class used internally in converting an amountReservationTransaction to/from JSON
 */
public class AmountReservationTransactionWrapper implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * the actual amountReservationTransaction to/from JSON
	 */
	AmountReservationTransaction amountReservationTransaction=null;
	/**
	 * return an instance of an amountReservationTransaction
	 */
	public AmountReservationTransaction getAmountReservationTransaction() { return amountReservationTransaction; }
	/**
	 * set the amountReservationTransaction element. This is called internally to set the contents according to the JSON response.
	 */
	public void setAmountReservationTransaction(AmountReservationTransaction amountReservationTransaction) { this.amountReservationTransaction=amountReservationTransaction; }
	
}
