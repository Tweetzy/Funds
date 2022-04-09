package ca.tweetzy.funds.model;

import ca.tweetzy.funds.api.interfaces.Currency;
import lombok.NonNull;

import java.util.HashMap;

/**
 * Date Created: April 08 2022
 * Time Created: 11:15 p.m.
 *
 * @author Kiran Hart
 */
public final class CurrencyManager {

	private final HashMap<String, Currency> currencies = new HashMap<>();

	public void addCurrency(@NonNull final Currency currency) {
		this.currencies.put(currency.getId().toLowerCase(), currency);
	}

	public void removeCurrency(@NonNull final Currency currency) {
		this.removeCurrency(currency.getId().toLowerCase());
	}

	public void removeCurrency(@NonNull final String id) {
		this.currencies.remove(id.toLowerCase());
	}

	public Currency getCurrency(@NonNull final String id) {
		return this.currencies.getOrDefault(id.toLowerCase(), null);
	}

	public void loadCurrencies() {
		this.currencies.clear();

		// todo data manager load
	}
}