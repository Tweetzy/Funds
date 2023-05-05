package ca.tweetzy.funds.impl;

import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.input.TitleInput;
import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.events.CurrencyTransferEvent;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.api.interfaces.Language;
import ca.tweetzy.funds.guis.player.AccountPickerGUI;
import ca.tweetzy.funds.guis.template.CurrencyPicker;
import ca.tweetzy.funds.settings.Settings;
import ca.tweetzy.funds.settings.Translations;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
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
	private Language preferredLanguage;
	private final long createdAt;

	public FundAccount(@NonNull final OfflinePlayer owner) {
		this(owner.getUniqueId(), owner.getName(), Funds.getCurrencyManager().getDefaultValueMap(), false, new FundLanguage(Settings.LANGUAGE.getString(), Settings.LANGUAGE.getString(), null), System.currentTimeMillis());
	}

	public FundAccount(@NonNull final UUID owner) {
		this(owner, Bukkit.getOfflinePlayer(owner).getName(), Funds.getCurrencyManager().getDefaultValueMap(), false, new FundLanguage(Settings.LANGUAGE.getString(), Settings.LANGUAGE.getString(), null), System.currentTimeMillis());
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
	public Language getPreferredLanguage() {
		return this.preferredLanguage;
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
	public void setPreferredLanguage(Language language) {
		this.preferredLanguage = language;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean transferCurrency(Account account, Currency currency, double amount, boolean asyncEvent) {
		if (account == null) return false;
		if (currency == null) return false;
		if (!this.currencies.containsKey(currency)) return false;

		final double payeeBalance = this.currencies.get(currency);
		if (payeeBalance < amount) return false;

		final CurrencyTransferEvent currencyTransferEvent = new CurrencyTransferEvent(asyncEvent, this, account, currency, amount);
		Funds.getInstance().getServer().getPluginManager().callEvent(currencyTransferEvent);
		if (currencyTransferEvent.isCancelled()) return false;

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
	public void deleteCurrency(Currency currency) {
		if (!this.currencies.containsKey(currency)) return;
		this.currencies.remove(currency);
	}

	@Override
	public void initiateTransfer(Player player, Currency providedCurrency) {
		Funds.getGuiManager().showGUI(player, new AccountPickerGUI(this, (click, selectedAccount) -> {
			if (providedCurrency != null) {
				double currencyTotal = this.getCurrencies().get(providedCurrency);
				requestTransferAmount(player, selectedAccount, providedCurrency, currencyTotal);
				return;
			}

			click.manager.showGUI(click.player, new CurrencyPicker(this, null, true, (event, currency) -> {
				double currencyTotal = this.getCurrencies().get(currency);
				if (currencyTotal <= 0D) {
					Common.tell(click.player, TranslationManager.string(Translations.NOT_ENOUGH_MONEY, "currency_plural_format", currency.getPluralFormat()));
					return;
				}

				requestTransferAmount(player, selectedAccount, currency, currencyTotal);
			}));
		}));
	}

	private void requestTransferAmount(Player player, Account selectedAccount, Currency currency, double currencyTotal) {
		new TitleInput(Funds.getInstance(), player, TranslationManager.string(Translations.SEND_CURRENCY_AMT_TITLE), Common.colorize(TranslationManager.string(Translations.SEND_CURRENCY_AMT_SUBTITLE)), Common.colorize(
				String.format("&e%s %s", String.format("%,.2f", currencyTotal), currencyTotal > 1.0D ? currency.getPluralFormat() : currency.getSingularFormat())
		)) {

			@Override
			public boolean onResult(String string) {
				if (!NumberUtils.isNumber(string)) {
					// tell them to learn what a number is
					Common.tell(player, TranslationManager.string(Translations.NOT_A_NUMBER, "value", string));
					return false;
				}

				final double transferAmount = Double.parseDouble(string);

				// does the player even have enough money
				// todo add method to interface
				if (getCurrencies().get(currency) < transferAmount) {
					Common.tell(player, TranslationManager.string(Translations.NOT_ENOUGH_MONEY, "currency_plural_format", currency.getPluralFormat()));
					return false;
				}

				transferCurrency(selectedAccount, currency, transferAmount, true);
				// todo determine whether I should handle this within the Account#transferCurrency method
				Funds.getAccountManager().updateAccounts(Arrays.asList(FundAccount.this, selectedAccount), null);
				return true;
			}
		};
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
