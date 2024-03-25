package ca.tweetzy.funds;

import ca.tweetzy.flight.FlightPlugin;
import ca.tweetzy.flight.command.CommandManager;
import ca.tweetzy.flight.config.tweetzy.TweetzyYamlConfig;
import ca.tweetzy.flight.database.DataMigrationManager;
import ca.tweetzy.flight.database.DatabaseConnector;
import ca.tweetzy.flight.database.SQLiteConnector;
import ca.tweetzy.flight.gui.GuiManager;
import ca.tweetzy.flight.utils.Common;
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
import ca.tweetzy.funds.settings.Settings;
import ca.tweetzy.funds.settings.Translations;
import lombok.SneakyThrows;

/**
 * Date Created: April 08 2022
 * Time Created: 7:01 p.m.
 *
 * @author Kiran Hart
 */
public final class Funds extends FlightPlugin {

	private final TweetzyYamlConfig coreConfig = new TweetzyYamlConfig(this, "config.yml");

	private final AccountManager accountManager = new AccountManager();
	private final CurrencyManager currencyManager = new CurrencyManager();
	private final GuiManager guiManager = new GuiManager(this);
	private final CommandManager commandManager = new CommandManager(this);

	private DatabaseConnector databaseConnector;
	private DataManager dataManager;

	@SneakyThrows
	protected void onFlight() {

		// settings & locale setup
		Settings.setup();

		Translations.init();

		Common.setPrefix(Settings.METRICS.getBoolean() ? Settings.PREFIX.getString() : "&8[&eFunds&8]");

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

		// load currencies -> then accounts
		this.currencyManager.loadCurrencies((loaded) -> this.accountManager.loadAccounts());

		// placeholder api
		HookManager.getInstance().registerPlaceholders();

		// initialize gui manager
		this.guiManager.init();

		// register main command
		this.commandManager.registerCommandDynamically("funds").addCommand(new FundsCommand()).addSubCommands(
				new SupportCommand(), new BalanceCommand(), new PayCommand(), new AddCommand()
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
		return (Funds) FlightPlugin.getInstance();
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
