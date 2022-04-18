package ca.tweetzy.funds.settings;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.model.Helper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Date Created: April 18 2022
 * Time Created: 3:18 p.m.
 *
 * @author Kiran Hart
 */
@AllArgsConstructor
@Getter
public enum Translation {

	// MAIN GUI
	GUI_MAIN_TITLE("gui.main.title", "&eFunds &fv&7%plugin_version%"),
	GUI_MAIN_ITEMS_CURRENCY_NAME("gui.main.items.currency.name", "&e&lCurrencies"),
	GUI_MAIN_ITEMS_CURRENCY_LORE_CREATE("gui.main.items.currency.lore.create", Arrays.asList(
			"&8All the currencies you've made",
			"&cNo currencies found", "", "&e&lClick &8» &7To create your first currency"
	)),
	GUI_MAIN_ITEMS_CURRENCY_LORE_VIEW("gui.main.items.currency.lore.view", Arrays.asList(
			"&8All the currencies you've made",
			"&a%total_currencies% &7currencies found", "", "&e&lClick &8» &7To view currencies"
	)),

	GUI_MAIN_ITEMS_ACCOUNTS_NAME("gui.main.items.account.name", "&d&lAccounts"),
	GUI_MAIN_ITEMS_ACCOUNTS_LORE_CREATE("gui.main.items.account.lore.create", Arrays.asList(
			"&8View all known player accounts",
			"&7An account is used to store each",
			"&7player's different currency count",
			"",
			"&cNo accounts found", "", "&e&lClick &8» &7To manually create account"
	)),
	GUI_MAIN_ITEMS_ACCOUNTS_LORE_VIEW("gui.main.items.account.lore.view", Arrays.asList(
			"&8View all known player accounts",
			"&7An account is used to store each",
			"&7player's different currency count",
			"",
			"&a%total_accounts% &7accounts found", "", "&e&lClick &8» &7To view accounts"
	)),

	;

	final String key;
	final Object value;

	public String getString(Object... replacements) {
		return Helper.replaceVariables(Locale.getString(this.key), replacements);
	}

	public List<String> getList(Object... replacements) {
		return Helper.replaceVariables(Locale.getList(this.key), replacements);
	}
}
