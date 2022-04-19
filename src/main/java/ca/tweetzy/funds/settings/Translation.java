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
	============= misc =============
	 */
	MISC_IS_TRUE("misc.is true", "&ATrue"),
	MISC_IS_FALSE("gui.is false", "&cFalse"),
	MISC_IS_ALLOWED("misc.is allowed", "&aAllowed"),
	MISC_IS_DISALLOWED("gui.is disallowed", "&cDisallowed"),

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

	/*
	============= ACCOUNT VIEW MENU =============
	 */
	GUI_ACCOUNT_VIEW_TITLE("gui.account view.title", "&eFunds &8> &7User &8> &7%account_name%"),
	GUI_ACCOUNT_VIEW_ITEMS_CURRENCY_NAME("gui.account view.items.currency.name", "%currency_name%"),
	GUI_ACCOUNT_VIEW_ITEMS_CURRENCY_LORE("gui.account view.items.currency.lore", Arrays.asList(
			"&7Identifier&F: &e%currency_id%",
			"&7Description&F:",
			"&f- %currency_description%",
			"",
			"&7Current Balance&F: &e%currency_balance%",
			"",
			"&e&lLeft Click &8» &7To set balance",
			"&b&lRight Click &8» &7To add balance",
			"&c&lPress 1 &8» &7To reset to &c%currency_starting_balance%"
	)),

	GUI_ACCOUNT_VIEW_ITEMS_BALTOP_BLACKLIST_NAME("gui.account view.items.baltop blacklist.name", "&e&lBalance Top Blocked"),
	GUI_ACCOUNT_VIEW_ITEMS_BALTOP_BLACKLIST_LORE("gui.account view.items.baltop blacklist.lore", Arrays.asList(
			"&8Blocks user from balance top",
			"&7If true, this player will be completed blacklisted",
			"&7from the balance top lists for each currency.",
			"",
			"&7Current&f: %is_true%",
			"",
			"&e&lClick &8» &7To toggle state"
	)),

	GUI_ACCOUNT_VIEW_ITEMS_DEPOSIT_NAME("gui.account view.items.deposit.name", "&a&lDeposit Currency"),
	GUI_ACCOUNT_VIEW_ITEMS_DEPOSIT_LORE("gui.account view.items.deposit.lore", Arrays.asList(
			"&8Add another currency balance",
			"&7Click to add another available currency",
			"&7to this player's balance list.",
			"",
			"&e&lClick &8» &7To deposit currency"
	)),

	GUI_ACCOUNT_VIEW_ITEMS_RESET_NAME("gui.account view.items.reset.name", "&c&lReset Balances"),
	GUI_ACCOUNT_VIEW_ITEMS_RESET_LORE("gui.account view.items.reset.lore", Arrays.asList(
			"&8Reset player balances",
			"&7By clicking this you will reset every single",
			"&7currency balance from this user's account",
			"",
			"&c&lClick &8» &7To reset balances"
	)),

	/*
	============= CURRENCY EDIT MENU =============
	 */
	GUI_CURRENCY_EDIT_TITLE("gui.currency edit.title", "&eFunds &8> &7Editing &8> &7%currency_id%"),
	GUI_CURRENCY_EDIT_ITEMS_ICON_NAME("gui.currency edit.items.icon.name", "&b&lIcon"),
	GUI_CURRENCY_EDIT_ITEMS_ICON_LORE("gui.currency edit.items.icon.lore", Arrays.asList(
			"&8Change currency's display icon",
			"&7This will be shown within guis.",
			"",
			"&e&lClick &8» &7To change icon"
	)),

	GUI_CURRENCY_EDIT_ITEMS_FORMATTING_NAME("gui.currency edit.items.formatting.name", "&b&lFormatting"),
	GUI_CURRENCY_EDIT_ITEMS_FORMATTING_LORE("gui.currency edit.items.formatting.lore", Arrays.asList(
			"&8Change currency's singular/plural format",
			"&7These will be used depending on currency amount",
			"",
			"&7Singular&f: &e%currency_singular_format%",
			"&7Plural&f: &e%currency_plural_format%",
			"",
			"&e&lLeft Click &8» &7To change singular format",
			"&e&lRight Click &8» &7To change plural format"
	)),

	GUI_CURRENCY_EDIT_ITEMS_STARTING_BAL_NAME("gui.currency edit.items.stating balance.name", "&b&lStarting Balance"),
	GUI_CURRENCY_EDIT_ITEMS_STARTING_BAL_LORE("gui.currency edit.items.stating balance.lore", Arrays.asList(
			"&8Change currency's starting balance",
			"&7Reset/New accounts will start with this",
			"",
			"&7Current&f: &e%currency_starting_balance% %currency_plural_format%",
			"",
			"&e&lClick &8» &7To change starting balance"
	)),

	GUI_CURRENCY_EDIT_ITEMS_DISPLAY_NAME_NAME("gui.currency edit.items.display name.name", "&b&lName"),
	GUI_CURRENCY_EDIT_ITEMS_DISPLAY_NAME_LORE("gui.currency edit.items.display name.lore", Arrays.asList(
			"&8Change currency's display name",
			"&7This will be shown within guis.",
			"",
			"&7Current&f: &e%currency_name%",
			"",
			"&e&lClick &8» &7To change display name"
	)),

	GUI_CURRENCY_EDIT_ITEMS_DESC_NAME("gui.currency edit.items.description.name", "&b&lDescription"),
	GUI_CURRENCY_EDIT_ITEMS_DESC_LORE("gui.currency edit.items.description.lore", Arrays.asList(
			"&8Change currency's description",
			"&7This will be shown within guis.",
			"",
			"&7Description&f:",
			"&e%currency_description%",
			"",
			"&e&lClick &8» &7To change description"
	)),

	GUI_CURRENCY_EDIT_ITEMS_WITHDRAW_NAME("gui.currency edit.items.withdraw.name", "&b&lWithdrawal"),
	GUI_CURRENCY_EDIT_ITEMS_WITHDRAW_LORE("gui.currency edit.items.withdraw.lore", Arrays.asList(
			"&8Currency Withdrawal",
			"&7Enabling this will allow users to withdraw ",
			"&7the currency into a physical item",
			"",
			"&7Current&f: %is_allowed%",
			"",
			"&e&lClick &8» &7To toggle state"
	)),

	GUI_CURRENCY_EDIT_ITEMS_PAYING_NAME("gui.currency edit.items.paying.name", "&b&lPaying"),
	GUI_CURRENCY_EDIT_ITEMS_PAYING_LORE("gui.currency edit.items.paying.lore", Arrays.asList(
			"&8Currency Paying",
			"&7Enabling this will allow users to pay ",
			"&7other users with this currency",
			"",
			"&7Current&f: %is_allowed%",
			"",
			"&e&lClick &8» &7To toggle state"
	)),

	GUI_CURRENCY_EDIT_ITEMS_VAULT_NAME("gui.currency edit.items.vault.name", "&b&lMake Vault Currency"),
	GUI_CURRENCY_EDIT_ITEMS_VAULT_LORE("gui.currency edit.items.vault.lore", Arrays.asList(
			"&8Currency Paying",
			"&7Making a currency the vault currency is the best",
			"&7way to allow other plugins that use Vault to handle",
			"&7player balances, it's also funds' default go to.",
			"",
			"&7Current&f: %is_true%",
			"", "&e&lClick &8» &7To make vault currency"
	)),

	/*
	============= CURRENCY LIST MENU =============
	 */
	GUI_CURRENCY_LIST_TITLE("gui.currency list.title", "&eFunds &8> &7Currency List"),
	GUI_CURRENCY_LIST_ITEMS_CURRENCY_NAME("gui.currency list.items.currency.name", "%currency_name%"),
	GUI_CURRENCY_LIST_ITEMS_CURRENCY_LORE("gui.currency list.items.currency.lore", Arrays.asList(
			"&8Basic currency information",
			"&7Identifier&F: &e%currency_id%",
			"&7Description&F:",
			"&f- %currency_description%",
			"",
			"&7Singular Format&f: &e%currency_singular_format%",
			"&7Plural Format&f: &e%currency_plural_format%",
			"",
			"&e&lLeft Click &8» &7To Edit Currency",
			"&c&lPress 1 &8» &7To Delete Currency"
	)),

	GUI_CURRENCY_LIST_ITEMS_NEW_NAME("gui.currency list.items.new.name", "&a&lNew Currency"),
	GUI_CURRENCY_LIST_ITEMS_NEW_LORE("gui.currency list.items.new.lore", Arrays.asList(
			"&8Currency creation",
			"&7Create another currency to be used",
			"",
			"&E&lClick &8» &7To Create Currency"
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
