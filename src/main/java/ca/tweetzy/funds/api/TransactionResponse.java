package ca.tweetzy.funds.api;

/**
 * Date Created: April 13 2022
 * Time Created: 3:32 p.m.
 *
 * @author Kiran Hart
 */
public enum TransactionResponse {

	PAYEE_ACCOUNT_NOT_FOUND,
	PAYER_ACCOUNT_NOT_FOUND,

	PAYEE_INSUFFICIENT_FUNDS,
	PAYER_INSUFFICIENT_FUNDS,

	UNKNOWN_CURRENCY,

	COMPLETED
}
