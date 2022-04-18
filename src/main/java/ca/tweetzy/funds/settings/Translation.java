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

	/*
	============= MAIN MENU =============
	 */
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


	/*
	============= CONFIRM MENU =============
	 */
	GUI_CONFIRM_TITLE("gui.confirm.title", "&eConfirm Action"),
	GUI_CONFIRM_ITEMS_CONFIRM_NAME("gui.confirm.items.confirm.name", "&a&lConfirm"),
	GUI_CONFIRM_ITEMS_CONFIRM_LORE("gui.confirm.items.confirm.lore", Arrays.asList("", "&E&LClick &8» &7To confirm action")),
	GUI_CONFIRM_ITEMS_CANCEL_NAME("gui.confirm.items.cancel.name", "&c&lCancel"),
	GUI_CONFIRM_ITEMS_CANCEL_LORE("gui.confirm.items.cancel.lore", Arrays.asList("", "&E&LClick &8» &7To cancel action")),

	/*
	============= ACCOUNT LIST MENU =============
	 */
	GUI_ACCOUNT_LIST_TITLE("gui.account list.title", "&eFunds &8> &7Account List"),
	GUI_ACCOUNT_LIST_ITEMS_ACCOUNT_NAME("gui.account list.items.account.name", "&B&L%account_name%"),
	GUI_ACCOUNT_LIST_ITEMS_ACCOUNT_LORE("gui.account list.items.account.lore", Arrays.asList(
			"&8Player account info",
			"&7View information about this player's",
			"&7account (ie, balances, transactions)",
			"",
			"&e&lLeft Click &8» &7To view account"
	)),

	GUI_ACCOUNT_LIST_ITEMS_RESET_NAME("gui.account list.items.reset.name", "&c&lReset Accounts"),
	GUI_ACCOUNT_LIST_ITEMS_RESET_LORE("gui.account list.items.reset.lore", Arrays.asList(
			"&8Reset all player accounts",
			"&7By clicking this you will reset every single",
			"&7user account account's currency balance.",
			"",
			"&c&lClick &8» &7To reset accounts"
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
