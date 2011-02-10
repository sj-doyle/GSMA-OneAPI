package org.gsm.oneapi.responsebean.payment;

/**
 *AmountTransactionWrapper is a utility class used internally in converting an amountTransaction to/from JSON
 */
public class AmountTransactionWrapper implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * the actual amountTransaction to/from JSON
	 */
	AmountTransaction amountTransaction=null;
	/**
	 * return an instance of an amountTransaction
	 */
	public AmountTransaction getAmountTransaction() { return amountTransaction; }
	/**
	 * set the amountTransaction element. This is called internally to set the contents according to the JSON response.
	 */
	public void setAmountTransaction(AmountTransaction amountTransaction) { this.amountTransaction=amountTransaction; }
	
}
