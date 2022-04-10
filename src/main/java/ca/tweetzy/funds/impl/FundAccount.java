package ca.tweetzy.funds.impl;

import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.UUID;

/**
 * Date Created: April 09 2022
 * Time Created: 9:14 p.m.
 *
 * @author Kiran Hart
 */
@AllArgsConstructor
public final class FundAccount implements Account {

	private final UUID owner;
	private final Map<Currency, Double> currencies;
	private final long createdAt;

	@Override
	public UUID getOwner() {
		return this.owner;
	}

	@Override
	public Map<Currency, Double> getCurrencies() {
		return this.currencies;
	}

	@Override
	public long getCreatedAt() {
		return this.createdAt;
	}
}
