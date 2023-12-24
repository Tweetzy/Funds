package ca.tweetzy.funds.settings;

import ca.tweetzy.flight.settings.TranslationEntry;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.funds.Funds;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

public final class Translations extends TranslationManager {

	public Translations(@NonNull JavaPlugin plugin) {
		super(plugin);
		this.mainLanguage = Settings.LANGUAGE.getString();
	}

	public static TranslationEntry ALLOWED = create("conditionals.allowed", "&aAllowed");
	public static TranslationEntry DISALLOWED = create("conditionals.disallowed", "&cDisallowed");

	public static TranslationEntry CURRENCY_ALREADY_EXISTS = create("misc.currency already exists", "&cA currency by that id already exists!");
	public static TranslationEntry CURRENCY_CREATION_FAIL = create("misc.currency creation failed", "&cSomething went wrong while creating that currency");
	public static TranslationEntry CURRENCY_CREATED = create("misc.currency created", "&aCreated a new currency named&F: &e%currency_name%");

	public static TranslationEntry PLAYER_DOES_NOT_HAVE_ACCOUNT = create("misc.player does not have account", "&CThe player &4%player% &cdoesn't have an account!");
	public static TranslationEntry NOT_ENOUGH_MONEY = create("misc.not enough money", "&cYou do not have enough %currency_plural_format%");
	public static TranslationEntry DOES_NOT_OWN_CURRENCY = create("misc.does not own currency", "&cYou do not own any %currency_plural_format%");
	public static TranslationEntry NO_CURRENCY_SET = create("misc.no currency set", "&cContact a server admin, there are no currencies or vault currency not set!");
	public static TranslationEntry UPDATED_LANGUAGE = create("misc.updated language", "&aUpdated your language to &e%language%");

	public static TranslationEntry MONEY_PAID = create("misc.money paid", "&aYou sent &e%amount% %currency_auto_format% &ato &e%payee_name%");
	public static TranslationEntry MONEY_RECEIVED = create("misc.money received", "&aYou received &e%amount% %currency_auto_format% &afrom &e%payer_name%");

	public static TranslationEntry WITHDRAW = create("misc.withdraw", "&c-%amount% %currency_auto_format%");
	public static TranslationEntry DEPOSIT = create("misc.deposit", "&a+%amount% %currency_auto_format%");

	public static TranslationEntry PHYSICAL_CURRENCY_NAME = create("physical currency.name", "&F%total% %currency_auto_format%");
	public static TranslationEntry PHYSICAL_CURRENCY_LORE = create("physical currency.lore", "&e&lClick &8» &7To deposit currency");

	public static TranslationEntry CURRENCY_BALANCE_CHAT_HEADER = create("currency balance chat format.header", "&8&m-----------------------------------------------------");
	public static TranslationEntry CURRENCY_BALANCE_CHAT_CURRENCY = create("currency balance chat format.currency", "%currency_name% &f/ &a%currency_balance%");
	public static TranslationEntry CURRENCY_BALANCE_CHAT_FOOTER = create("currency balance chat format.footer", "&8&m-----------------------------------------------------");

	/*
	============= Titles / Input =============
	 */
	public static TranslationEntry CURRENCY_EDIT_FORMATTING_TITLE = create("input.currency edit.formatting.title", "&eCurrency Edit");
	public static TranslationEntry CURRENCY_EDIT_FORMATTING_SUBTITLE_PLURAL = create("input.currency edit.formatting.subtitle.plural", "&FEnter plural format for currency");
	public static TranslationEntry CURRENCY_EDIT_FORMATTING_SUBTITLE_SINGULAR = create("input.currency edit.formatting.subtitle.singular", "&fEnter singular format for currency");

	public static TranslationEntry CURRENCY_EDIT_STARTING_BAL_TITLE = create("input.currency edit.starting balance.title", "&eCurrency Edit");
	public static TranslationEntry CURRENCY_EDIT_STARTING_BAL_SUBTITLE = create("input.currency edit.starting balance.subtitle", "&fEnter starting balance for currency");

	public static TranslationEntry CURRENCY_EDIT_NAME_TITLE = create("input.currency edit.name.title", "&eCurrency Edit");
	public static TranslationEntry CURRENCY_EDIT_NAME_SUBTITLE = create("input.currency edit.name.subtitle", "&fEnter display name for currency");

	public static TranslationEntry CURRENCY_EDIT_DESC_TITLE = create("input.currency edit.desc.title", "&eCurrency Edit");
	public static TranslationEntry CURRENCY_EDIT_DESC_SUBTITLE = create("input.currency edit.desc.subtitle", "&fEnter description for currency");

	public static TranslationEntry CURRENCY_CREATE_TITLE = create("input.currency create.title", "&eEnter Currency Name");
	public static TranslationEntry CURRENCY_CREATE_SUBTITLE = create("input.currency create.subtitle", "&fEnter the id for the currency into chat");

	public static TranslationEntry CURRENCY_DEPOSIT_TITLE = create("input.currency deposit.title", "&eCurrency Deposit");
	public static TranslationEntry CURRENCY_DEPOSIT_SUBTITLE = create("input.currency deposit.subtitle", "&fEnter deposit amount for currency");

	public static TranslationEntry CURRENCY_SET_BAL_TITLE = create("input.currency set bal.title", "&eSet Balance");
	public static TranslationEntry CURRENCY_SET_BAL_SUBTITLE = create("input.currency set bal.subtitle", "&fEnter new balance total");

	public static TranslationEntry CURRENCY_ADD_BAL_TITLE = create("input.currency add bal.title", "&eAdd to Balance");
	public static TranslationEntry CURRENCY_ADD_BAL_SUBTITLE = create("input.currency add bal.subtitle", "&fEnter amount to add to balance");

	public static TranslationEntry SEND_CURRENCY_AMT_TITLE = create("input.send currency amount.title", "&ESending Currency");
	public static TranslationEntry SEND_CURRENCY_AMT_SUBTITLE = create("input.send currency amount.subtitle", "&fEnter amount you want to send");


	public static TranslationEntry GUI_SHARED_ITEMS_BACK_BUTTON_NAME = create("gui.shared buttons.back button.name", "<GRADIENT:65B1B4>&LGo Back</GRADIENT:2B6F8A>");
	public static TranslationEntry GUI_SHARED_ITEMS_BACK_BUTTON_LORE = create("gui.shared buttons.back button.lore",
			"&e&l%left_click% &7to go back"
	);

	public static TranslationEntry GUI_SHARED_ITEMS_EXIT_BUTTON_NAME = create("gui.shared buttons.exit button.name", "<GRADIENT:65B1B4>&LExit</GRADIENT:2B6F8A>");
	public static TranslationEntry GUI_SHARED_ITEMS_EXIT_BUTTON_LORE = create("gui.shared buttons.exit button.lore",
			"&e&l%left_click% &7to exit menu"
	);

	public static TranslationEntry GUI_SHARED_ITEMS_PREVIOUS_BUTTON_NAME = create("gui.shared buttons.previous button.name", "<GRADIENT:65B1B4>&lPrevious Page</GRADIENT:2B6F8A>");
	public static TranslationEntry GUI_SHARED_ITEMS_PREVIOUS_BUTTON_LORE = create("gui.shared buttons.previous button.lore",
			"&e&l%left_click% &7to go back a page"
	);

	public static TranslationEntry GUI_SHARED_ITEMS_NEXT_BUTTON_NAME = create("gui.shared buttons.next button.name", "<GRADIENT:65B1B4>&lNext Page</GRADIENT:2B6F8A>");
	public static TranslationEntry GUI_SHARED_ITEMS_NEXT_BUTTON_LORE = create("gui.shared buttons.next button.lore",
			"&e&l%left_click% &7to go to next page"
	);


	/*
	============= LANG MENU =============
	 */

	public static TranslationEntry GUI_SELECT_CURRENCY_TITLE = create("gui.currency picker.title", "&eFunds &7Select Currency");
	public static TranslationEntry GUI_SELECT_CURRENCY_ITEMS_CURRENCY_NAME = create("gui.currency picker.items.lang.name", "%currency_name%");
	public static TranslationEntry GUI_SELECT_CURRENCY_ITEMS_CURRENCY_LORE = create("gui.currency picker.items.lang.lore",
			"&7Identifier&F: &e%currency_id%",
			"&7Description&F:",
			"&f- %currency_description%",
			"",
			"&e&LLeft Click &8» &7To select currency"
	);


	/*
	============= LANG MENU =============
	 */

	public static TranslationEntry GUI_SELECT_LANGUAGE_TITLE = create("gui.select language.title", "&eFunds &7Select Language");
	public static TranslationEntry GUI_SELECT_LANGUAGE_ITEMS_LANG_NAME = create("gui.select language.items.lang.name", "&b&l%language_name%");
	public static TranslationEntry GUI_SELECT_LANGUAGE_ITEMS_LANG_LORE = create("gui.select language.items.lang.lore", "&e&lClick &8» &7To select language");

	/*
	============= MAIN MENU =============
	 */
	public static TranslationEntry GUI_MAIN_TITLE = create("gui.main.title", "&eFunds &fv&7%plugin_version%");
	public static TranslationEntry GUI_MAIN_ITEMS_CURRENCY_NAME = create("gui.main.items.currency.name", "&e&lCurrencies");
	public static TranslationEntry GUI_MAIN_ITEMS_CURRENCY_LORE_CREATE = create("gui.main.items.currency.lore.create",
			"&8All the currencies you've made",
			"&cNo currencies found", "", "&e&lClick &8» &7To create your first currency"
	);
	public static TranslationEntry GUI_MAIN_ITEMS_CURRENCY_LORE_VIEW = create("gui.main.items.currency.lore.view",
			"&8All the currencies you've made",
			"&a%total_currencies% &7currencies found", "", "&e&lClick &8» &7To view currencies"
	);

	public static TranslationEntry GUI_MAIN_ITEMS_ACCOUNTS_NAME = create("gui.main.items.account.name", "&d&lAccounts");
	public static TranslationEntry GUI_MAIN_ITEMS_ACCOUNTS_LORE_CREATE = create("gui.main.items.account.lore.create",
			"&8View all known player accounts",
			"&7An account is used to store each",
			"&7player's different currency count",
			"",
			"&cNo accounts found", "", "&e&lClick &8» &7To manually create account"
	);
	public static TranslationEntry GUI_MAIN_ITEMS_ACCOUNTS_LORE_VIEW = create("gui.main.items.account.lore.view",
			"&8View all known player accounts",
			"&7An account is used to store each",
			"&7player's different currency count",
			"",
			"&a%total_accounts% &7accounts found", "", "&e&lClick &8» &7To view accounts"
	);

	/*
	============= BALANCE MENU =============
	 */
	public static TranslationEntry GUI_BALANCES_TITLE = create("gui.balances.title", "&eFunds &8> &7Your Balances");
	public static TranslationEntry GUI_BALANCES_ITEMS_CURRENCY_NAME = create("gui.balances.items.currency.name", "%currency_name%");
	public static TranslationEntry GUI_BALANCES_ITEMS_CURRENCY_LORE = create("gui.balances.items.currency.lore",
			"&8Handle this currency balance",
			"",
			"&7Total&F: &a%currency_balance%",
			"",
			"&e&lLeft Click &8» &7To pay a user",
			"&e&lRight Click &8» &7To withdraw"
	);

	/*
	============= CONFIRM MENU =============
	 */
	public static TranslationEntry GUI_CONFIRM_TITLE = create("gui.confirm.title", "&eConfirm Action");
	public static TranslationEntry GUI_CONFIRM_ITEMS_CONFIRM_NAME = create("gui.confirm.items.confirm.name", "&a&lConfirm");
	public static TranslationEntry GUI_CONFIRM_ITEMS_CONFIRM_LORE = create("gui.confirm.items.confirm.lore", "&E&LClick &8» &7To confirm action");
	public static TranslationEntry GUI_CONFIRM_ITEMS_CANCEL_NAME = create("gui.confirm.items.cancel.name", "&c&lCancel");
	public static TranslationEntry GUI_CONFIRM_ITEMS_CANCEL_LORE = create("gui.confirm.items.cancel.lore", "&E&LClick &8» &7To cancel action");

	/*
	============= ACCOUNT PICKER MENU =============
	 */
	public static TranslationEntry GUI_ACCOUNT_PICKER_TITLE = create("gui.account picker.title", "&eFunds &8> &7Select Account");
	public static TranslationEntry GUI_ACCOUNT_PICKER_ITEMS_ACCOUNT_NAME = create("gui.account picker.items.account.name", "&B&L%account_name%");
	public static TranslationEntry GUI_ACCOUNT_PICKER_ITEMS_ACCOUNT_LORE = create("gui.account picker.items.account.lore",
			"",
			"&e&lClick &8» &7To pay user"
	);

	public static TranslationEntry GUI_TOP_BALANCE_TITLE = create("gui.top balance.title", "&eFunds &8> &7Top Balances");
	public static TranslationEntry GUI_TOP_BALANCE_ITEMS_ACCOUNT_NAME = create("gui.top balance.items.account.name", "&B&L%account_name%");
	public static TranslationEntry GUI_TOP_BALANCE_ITEMS_ACCOUNT_LORE = create("gui.top balance.items.account.lore",
			"",
			"&7Currency&F: &e%currency_name%",
			"&7Current Balance&F: &e%currency_balance%",
			""
	);

	/*
	============= ACCOUNT LIST MENU =============
	 */
	public static TranslationEntry GUI_ACCOUNT_LIST_TITLE = create("gui.account list.title", "&eFunds &8> &7Account List");
	public static TranslationEntry GUI_ACCOUNT_LIST_ITEMS_ACCOUNT_NAME = create("gui.account list.items.account.name", "&B&L%account_name%");
	public static TranslationEntry GUI_ACCOUNT_LIST_ITEMS_ACCOUNT_LORE = create("gui.account list.items.account.lore",
			"&8Player account info",
			"&7View information about this player's",
			"&7account (ie, balances, transactions)",
			"",
			"&e&lLeft Click &8» &7To view account"
	);

	public static TranslationEntry GUI_ACCOUNT_LIST_ITEMS_RESET_NAME = create("gui.account list.items.reset.name", "&c&lReset Accounts");
	public static TranslationEntry GUI_ACCOUNT_LIST_ITEMS_RESET_LORE = create("gui.account list.items.reset.lore",
			"&8Reset all player accounts",
			"&7By clicking this you will reset every single",
			"&7user account account's currency balance.",
			"",
			"&c&lClick &8» &7To reset accounts"
	);

	/*
	============= ACCOUNT VIEW MENU =============
	 */
	public static TranslationEntry GUI_ACCOUNT_VIEW_TITLE = create("gui.account view.title", "&eFunds &8> &7User &8> &7%account_name%");
	public static TranslationEntry GUI_ACCOUNT_VIEW_ITEMS_CURRENCY_NAME = create("gui.account view.items.currency.name", "%currency_name%");
	public static TranslationEntry GUI_ACCOUNT_VIEW_ITEMS_CURRENCY_LORE = create("gui.account view.items.currency.lore",
			"&7Identifier&F: &e%currency_id%",
			"&7Description&F:",
			"&f- %currency_description%",
			"",
			"&7Current Balance&F: &e%currency_balance%",
			"",
			"&e&lLeft Click &8» &7To set balance",
			"&b&lRight Click &8» &7To add balance",
			"&c&lPress 1 &8» &7To reset to &c%currency_starting_balance%"
	);

	public static TranslationEntry GUI_ACCOUNT_VIEW_ITEMS_BALTOP_BLACKLIST_NAME = create("gui.account view.items.baltop blacklist.name", "&e&lBalance Top Blocked");
	public static TranslationEntry GUI_ACCOUNT_VIEW_ITEMS_BALTOP_BLACKLIST_LORE = create("gui.account view.items.baltop blacklist.lore",
			"&8Blocks user from balance top",
			"&7If true, this player will be completed blacklisted",
			"&7from the balance top lists for each currency.",
			"",
			"&7Current&f: %is_true%",
			"",
			"&e&lClick &8» &7To toggle state"
	);

	public static TranslationEntry GUI_ACCOUNT_VIEW_ITEMS_DEPOSIT_NAME = create("gui.account view.items.deposit.name", "&a&lDeposit Currency");
	public static TranslationEntry GUI_ACCOUNT_VIEW_ITEMS_DEPOSIT_LORE = create("gui.account view.items.deposit.lore",
			"&8Add another currency balance",
			"&7Click to add another available currency",
			"&7to this player's balance list.",
			"",
			"&e&lClick &8» &7To deposit currency"
	);

	public static TranslationEntry GUI_ACCOUNT_VIEW_ITEMS_RESET_NAME = create("gui.account view.items.reset.name", "&c&lReset Balances");
	public static TranslationEntry GUI_ACCOUNT_VIEW_ITEMS_RESET_LORE = create("gui.account view.items.reset.lore",
			"&8Reset player balances",
			"&7By clicking this you will reset every single",
			"&7currency balance from this user's account",
			"",
			"&c&lClick &8» &7To reset balances"
	);

	/*
	============= CURRENCY EDIT MENU =============
	 */
	public static TranslationEntry GUI_CURRENCY_EDIT_TITLE = create("gui.currency edit.title", "&eFunds &8> &7Editing &8> &7%currency_id%");
	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_ICON_NAME = create("gui.currency edit.items.icon.name", "&b&lIcon");
	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_ICON_LORE = create("gui.currency edit.items.icon.lore",
			"&8Change currency's display icon",
			"&7This will be shown within guis.",
			"",
			"&e&lClick &8» &7To change icon"
	);

	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_FORMATTING_NAME = create("gui.currency edit.items.formatting.name", "&b&lFormatting");
	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_FORMATTING_LORE = create("gui.currency edit.items.formatting.lore",
			"&8Change currency's singular/plural format",
			"&7These will be used depending on currency amount",
			"",
			"&7Singular&f: &e%currency_singular_format%",
			"&7Plural&f: &e%currency_plural_format%",
			"",
			"&e&lLeft Click &8» &7To change singular format",
			"&e&lRight Click &8» &7To change plural format"
	);

	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_STARTING_BAL_NAME = create("gui.currency edit.items.stating balance.name", "&b&lStarting Balance");
	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_STARTING_BAL_LORE = create("gui.currency edit.items.stating balance.lore",
			"&8Change currency's starting balance",
			"&7Reset/New accounts will start with this",
			"",
			"&7Current&f: &e%currency_starting_balance% %currency_plural_format%",
			"",
			"&e&lClick &8» &7To change starting balance"
	);

	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_DISPLAY_NAME_NAME = create("gui.currency edit.items.display name.name", "&b&lName");
	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_DISPLAY_NAME_LORE = create("gui.currency edit.items.display name.lore",
			"&8Change currency's display name",
			"&7This will be shown within guis.",
			"",
			"&7Current&f: &e%currency_name%",
			"",
			"&e&lClick &8» &7To change display name"
	);

	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_DESC_NAME = create("gui.currency edit.items.description.name", "&b&lDescription");
	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_DESC_LORE = create("gui.currency edit.items.description.lore",
			"&8Change currency's description",
			"&7This will be shown within guis.",
			"",
			"&7Description&f:",
			"&e%currency_description%",
			"",
			"&e&lClick &8» &7To change description"
	);

	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_WITHDRAW_NAME = create("gui.currency edit.items.withdraw.name", "&b&lWithdrawal");
	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_WITHDRAW_LORE = create("gui.currency edit.items.withdraw.lore",
			"&8Currency Withdrawal",
			"&7Enabling this will allow users to withdraw ",
			"&7the currency into a physical item",
			"",
			"&7Current&f: %is_allowed%",
			"",
			"&e&lClick &8» &7To toggle state"
	);

	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_PAYING_NAME = create("gui.currency edit.items.paying.name", "&b&lPaying");
	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_PAYING_LORE = create("gui.currency edit.items.paying.lore",
			"&8Currency Paying",
			"&7Enabling this will allow users to pay ",
			"&7other users with this currency",
			"",
			"&7Current&f: %is_allowed%",
			"",
			"&e&lClick &8» &7To toggle state"
	);

	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_VAULT_NAME = create("gui.currency edit.items.vault.name", "&b&lMake Vault Currency");
	public static TranslationEntry GUI_CURRENCY_EDIT_ITEMS_VAULT_LORE = create("gui.currency edit.items.vault.lore",
			"&8Currency Paying",
			"&7Making a currency the vault currency is the best",
			"&7way to allow other plugins that use Vault to handle",
			"&7player balances, it's also funds' default go to.",
			"",
			"&7Current&f: %is_true%",
			"", "&e&lClick &8» &7To make vault currency"
	);

	/*
	============= CURRENCY LIST MENU =============
	 */
	public static TranslationEntry GUI_CURRENCY_LIST_TITLE = create("gui.currency list.title", "&eFunds &8> &7Currency List");
	public static TranslationEntry GUI_CURRENCY_LIST_ITEMS_CURRENCY_NAME = create("gui.currency list.items.currency.name", "%currency_name%");
	public static TranslationEntry GUI_CURRENCY_LIST_ITEMS_CURRENCY_LORE = create("gui.currency list.items.currency.lore",
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
	);

	public static TranslationEntry GUI_CURRENCY_LIST_ITEMS_NEW_NAME = create("gui.currency list.items.new.name", "&a&lNew Currency");
	public static TranslationEntry GUI_CURRENCY_LIST_ITEMS_NEW_LORE = create("gui.currency list.items.new.lore",
			"&8Currency creation",
			"&7Create another currency to be used",
			"",
			"&E&lClick &8» &7To Create Currency"
	);

	public static void init() {
		new Translations(Funds.getInstance()).setup();
	}
}
