package ca.tweetzy.funds;

import ca.tweetzy.funds.commands.*;
import ca.tweetzy.funds.database.DataManager;
import ca.tweetzy.funds.database.migrations._1_CurrencyTableMigration;
import ca.tweetzy.funds.database.migrations._2_AccountTableMigration;
import ca.tweetzy.funds.database.migrations._3_VaultCurrencyMigration;
import ca.tweetzy.funds.database.migrations._4_AccountLanguageMigration;
import ca.tweetzy.funds.hooks.HookManager;
import ca.tweetzy.funds.listeners.AccessListeners;
import ca.tweetzy.funds.listeners.FundsListeners;
import ca.tweetzy.funds.listeners.HookListeners;
import ca.tweetzy.funds.model.AccountManager;
import ca.tweetzy.funds.model.CurrencyManager;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.funds.settings.Settings;
import ca.tweetzy.rose.RoseCore;
import ca.tweetzy.rose.RosePlugin;
import ca.tweetzy.rose.command.CommandManager;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.database.DataMigrationManager;
import ca.tweetzy.rose.database.DatabaseConnector;
import ca.tweetzy.rose.database.SQLiteConnector;
import ca.tweetzy.rose.gui.GuiManager;
import ca.tweetzy.rose.utils.Common;
import lombok.SneakyThrows;

/**
 * Date Created: April 08 2022
 * Time Created: 7:01 p.m.
 *
 * @author Kiran Hart
 */
public final class Funds extends RosePlugin {

	private final AccountManager accountManager = new AccountManager();
	private final CurrencyManager currencyManager = new CurrencyManager();
	private final GuiManager guiManager = new GuiManager(this);
	private final CommandManager commandManager = new CommandManager(this);

	private DatabaseConnector databaseConnector;
	private DataManager dataManager;

	@Override
	protected void onWake() {
		// Set up the database if enabled
		this.databaseConnector = new SQLiteConnector(this);
		this.dataManager = new DataManager(this.databaseConnector, this);

		final DataMigrationManager dataMigrationManager = new DataMigrationManager(this.databaseConnector, this.dataManager,
				new _1_CurrencyTableMigration(),
				new _2_AccountTableMigration(),
				new _3_VaultCurrencyMigration(),
				new _4_AccountLanguageMigration()
		);

		// run migrations for tables
		dataMigrationManager.runMigrations();
	}

	@SneakyThrows
	protected void onFlight() {
		RoseCore.registerPlugin(this, 7, CompMaterial.GOLD_INGOT.name());

		// settings & locale setup
		Settings.setup();
		Locale.setup();

		Common.setPrefix(Settings.METRICS.getBoolean() ? Settings.PREFIX.getString() : "&8[&eFunds&8]");

		// load currencies -> then accounts
		this.currencyManager.loadCurrencies((loaded) -> this.accountManager.loadAccounts());

		// placeholder api
		HookManager.getInstance().registerPlaceholders();

		// initialize gui manager
		this.guiManager.init();

		// register main command
		this.commandManager.registerCommandDynamically("funds").addCommand(new FundsCommand()).addSubCommands(
				new SupportCommand(), new LanguageCommand(), new BalanceCommand(), new PayCommand()
		);

		this.commandManager.registerCommandDynamically("balance").addCommand(new BalanceCommand());
		this.commandManager.registerCommandDynamically("baltop").addCommand(new BalanceTopCommand());
		this.commandManager.registerCommandDynamically("pay").addCommand(new PayCommand());

		// events / listeners
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new AccessListeners(), this);
		getServer().getPluginManager().registerEvents(new HookListeners(), this);
		getServer().getPluginManager().registerEvents(new FundsListeners(), this);
	}

	@Override
	protected void onSleep() {
		shutdownDataManager(this.dataManager);
		HookManager.getInstance().unregisterVault();
	}

	@Override
	protected int getBStatsId() {
		return !Settings.METRICS.getBoolean() ? -1 : 14883;
	}

	// instance
	public static Funds getInstance() {
		return (Funds) RosePlugin.getInstance();
	}

	// gui manager
	public static GuiManager getGuiManager() {
		return getInstance().guiManager;
	}

	// account manager
	public static AccountManager getAccountManager() {
		return getInstance().accountManager;
	}

	// currency manager
	public static CurrencyManager getCurrencyManager() {
		return getInstance().currencyManager;
	}

	// data manager
	public static DataManager getDataManager() {
		return getInstance().dataManager;
	}

}
