package ca.tweetzy.funds.model;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.rose.utils.Common;
import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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

	/*
	======================== DATA MODIFICATION ========================
	 */

	public void createCurrency(@NonNull final Currency currency, final BiConsumer<Boolean, Currency> consumer) {
		Funds.getDataManager().createCurrency(currency, (error, created) -> {
			if (error == null)
				this.addCurrency(created);

			if (consumer != null)
				consumer.accept(error != null, created);
		});
	}

	public void deleteCurrency(@NonNull final String id, final Consumer<Boolean> wasDeleted) {
		Funds.getDataManager().deleteCurrency(id, (error, deleted) -> {
			if (error == null && deleted)
				this.removeCurrency(id);

			if (wasDeleted != null)
				wasDeleted.accept(error == null && deleted);
		});
	}

	/*
	======================== END ========================
	 */

	public List<Currency> getCurrencies() {
		return List.copyOf(this.currencies.values());
	}

	public void loadCurrencies(Consumer<Integer> finished) {
		this.currencies.clear();
		Funds.getDataManager().getCurrencies((error, found) -> {
			if (error != null) {

				return;
			}

			found.forEach(currency -> {
				addCurrency(currency);
				Common.log("&aLoaded currency&F: &e" + currency.getId());
			});

			finished.accept(currencies.size());
		});
	}
}