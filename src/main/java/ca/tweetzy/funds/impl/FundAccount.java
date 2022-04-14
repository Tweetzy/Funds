package ca.tweetzy.funds.impl;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
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
	private String name;
	private final Map<Currency, Double> currencies;
	private boolean balTopBlocked;
	private final long createdAt;

	public FundAccount(@NonNull final OfflinePlayer owner) {
		this(owner.getUniqueId(), owner.getName(), Funds.getCurrencyManager().getDefaultValueMap(), false, System.currentTimeMillis());
	}

	public FundAccount(@NonNull final UUID owner) {
		this(owner, Bukkit.getOfflinePlayer(owner).getName(), Funds.getCurrencyManager().getDefaultValueMap(), false, System.currentTimeMillis());
	}

	@Override
	public UUID getOwner() {
		return this.owner;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Map<Currency, Double> getCurrencies() {
		return this.currencies;
	}

	@Override
	public boolean isBalTopBlocked() {
		return this.balTopBlocked;
	}

	@Override
	public long getCreatedAt() {
		return this.createdAt;
	}

	@Override
	public void setBalTopBlocked(boolean balTopBlocked) {
		this.balTopBlocked = balTopBlocked;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean transferCurrency(Account account, Currency currency, double amount) {
		if (account == null) return false;
		if (currency == null) return false;
		if (!this.currencies.containsKey(currency)) return false;

		final double payeeBalance = this.currencies.get(currency);
		if (payeeBalance < amount) return false;

		this.currencies.put(currency, payeeBalance - amount);
		account.depositCurrency(currency, amount);
		return true;
	}

	@Override
	public boolean withdrawCurrency(Currency currency, double amount) {
		if (currency == null) return false;
		if (!this.currencies.containsKey(currency)) return false;

		final double payeeBalance = this.currencies.get(currency);
		if (payeeBalance < amount) return false;

		this.currencies.put(currency, payeeBalance - amount);
		return true;
	}

	@Override
	public boolean depositCurrency(Currency currency, double amount) {
		if (currency == null) return false;

		if (!this.currencies.containsKey(currency)) {
			this.currencies.put(currency, amount);
		} else {
			final double existingBalance = this.currencies.get(currency);
			this.currencies.put(currency, existingBalance + amount);
		}

		return true;
	}

	@Override
	public boolean setCurrency(Currency currency, double amount) {
		if (currency == null) return false;
		this.currencies.put(currency, amount);
		return true;
	}

	@Override
	public boolean resetCurrencies(Currency... currencies) {
		if (currencies.length == 0) {
			this.currencies.clear();
			this.currencies.putAll(Funds.getCurrencyManager().getDefaultValueMap());
		} else {
			for (Currency currency : currencies)
				this.currencies.put(currency, currency.getStartingBalance());
		}
		return true;
	}

	@Override
	public String getCurrencyJson() {
		final JsonArray jsonArray = new JsonArray();

		this.currencies.forEach((key, value) -> {
			final JsonObject object = new JsonObject();
			object.addProperty("currency", key.getId());
			object.addProperty("amount", value);
			jsonArray.add(object);
		});

		return jsonArray.toString();
	}

	public static Map<Currency, Double> getCurrencyMapFromJson(@NonNull final String json) {
		final Map<Currency, Double> map = new HashMap<>();

		final JsonParser jsonParser = new JsonParser();
		final JsonArray jsonArray = (JsonArray) jsonParser.parse(json);

		jsonArray.forEach(element -> {
			final JsonObject object = element.getAsJsonObject();
			final Currency currency = Funds.getCurrencyManager().getCurrency(object.get("currency").getAsString());

			if (currency != null)
				map.put(currency, object.get("amount").getAsDouble());
		});

		return map;
	}

	@Override
	public void sync(boolean silent) {
		Funds.getDataManager().updateAccount(silent, this, null);
	}
}
