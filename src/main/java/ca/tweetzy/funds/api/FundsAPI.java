package ca.tweetzy.funds.api;

import ca.tweetzy.funds.api.interfaces.AccountAPI;
import ca.tweetzy.funds.api.interfaces.CurrencyAPI;
import ca.tweetzy.funds.api.interfaces.FundAPI;
import ca.tweetzy.funds.api.interfaces.TransactionAPI;

/**
 * Date Created: April 13 2022
 * Time Created: 11:47 p.m.
 *
 * @author Kiran Hart
 */
public final class FundsAPI implements FundAPI {

	@Override
	public AccountAPI getAccountAPI() {
		return AccountsAPI.getInstance();
	}

	@Override
	public CurrencyAPI getCurrencyAPI() {
		return null;
	}

	@Override
	public TransactionAPI getTransactionAPI() {
		return null;
	}
}
