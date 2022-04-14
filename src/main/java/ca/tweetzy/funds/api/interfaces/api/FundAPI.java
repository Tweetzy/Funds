package ca.tweetzy.funds.api.interfaces.api;

/**
 * Date Created: April 13 2022
 * Time Created: 11:45 p.m.
 *
 * @author Kiran Hart
 */
public interface FundAPI {

	AccountAPI getAccountAPI();

	CurrencyAPI getCurrencyAPI();

	TransactionAPI getTransactionAPI();
}
