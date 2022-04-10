package ca.tweetzy.funds;

import ca.tweetzy.funds.commands.FundsCommand;
import ca.tweetzy.funds.database.DataManager;
import ca.tweetzy.funds.database.migrations._1_CurrencyTableMigration;
import ca.tweetzy.funds.guis.template.MaterialPicker;
import ca.tweetzy.funds.hooks.VaultHook;
import ca.tweetzy.funds.model.CurrencyManager;
import ca.tweetzy.funds.settings.Settings;
import ca.tweetzy.rose.RosePlugin;
import ca.tweetzy.rose.command.CommandManager;
import ca.tweetzy.rose.database.DataMigrationManager;
import ca.tweetzy.rose.database.DatabaseConnector;
import ca.tweetzy.rose.database.SQLiteConnector;
import ca.tweetzy.rose.gui.GuiManager;
import ca.tweetzy.rose.utils.Common;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

/**
 * Date Created: April 08 2022
 * Time Created: 7:01 p.m.
 *
 * @author Kiran Hart
 */
public final class Funds extends RosePlugin {

	private final CurrencyManager currencyManager = new CurrencyManager();
	private final GuiManager guiManager = new GuiManager(this);
	private final CommandManager commandManager = new CommandManager(this);

	private DatabaseConnector databaseConnector;
	private DataManager dataManager;

	@SneakyThrows
	@Override
	protected void onWake() {
		// settings & locale setup
		Settings.setup();
		Common.setPrefix(Settings.PREFIX.getString());

		// Set up the database if enabled
		this.databaseConnector = new SQLiteConnector(this);
		this.dataManager = new DataManager(this.databaseConnector, this);

		final DataMigrationManager dataMigrationManager = new DataMigrationManager(this.databaseConnector, this.dataManager,
				new _1_CurrencyTableMigration()
		);

		new VaultHook();

		// run migrations for tables
		dataMigrationManager.runMigrations();
	}

	@Override
	protected void onFlight() {

		// load currencies
		this.currencyManager.loadCurrencies();
		this.guiManager.init();

		// register main command
		this.commandManager.registerCommandDynamically("funds").addCommand(new FundsCommand());

		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent chatEvent) {


		if (chatEvent.getMessage().equalsIgnoreCase("materialpicker")) {
			this.guiManager.showGUI(chatEvent.getPlayer(), new MaterialPicker(null, null, (event, selected) -> {
				event.player.closeInventory();
			}));
		} else {
			chatEvent.setMessage(Common.colorize(chatEvent.getMessage()));
		}
	}

	@Override
	protected int getBStatsId() {
		return 14883;
	}

	// instance
	public static Funds getInstance() {
		return (Funds) RosePlugin.getInstance();
	}

	// gui manager
	public static GuiManager getGuiManager() {
		return getInstance().guiManager;
	}

	// data manager
	public static DataManager getDataManager() {
		return getInstance().dataManager;
	}
}
