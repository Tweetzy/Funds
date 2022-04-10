package ca.tweetzy.funds;

import ca.tweetzy.funds.database.DataManager;
import ca.tweetzy.funds.database.migrations._1_CurrencyTableMigration;
import ca.tweetzy.funds.guis.template.MaterialPicker;
import ca.tweetzy.funds.hooks.VaultHook;
import ca.tweetzy.funds.settings.Settings;
import ca.tweetzy.rose.RosePlugin;
import ca.tweetzy.rose.database.DataMigrationManager;
import ca.tweetzy.rose.database.DatabaseConnector;
import ca.tweetzy.rose.database.SQLiteConnector;
import ca.tweetzy.rose.gui.GuiManager;
import lombok.SneakyThrows;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Date Created: April 08 2022
 * Time Created: 7:01 p.m.
 *
 * @author Kiran Hart
 */
public final class Funds extends RosePlugin {

	private final GuiManager guiManager = new GuiManager(this);

	private DatabaseConnector databaseConnector;

	private DataManager dataManager;

	@SneakyThrows
	@Override
	protected void onWake() {
		// settings & locale setup
		Settings.setup();

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
		guiManager.init();
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent chatEvent) {
		if (chatEvent.getMessage().equalsIgnoreCase("materialpicker")) {
			this.guiManager.showGUI(chatEvent.getPlayer(), new MaterialPicker(null, null, (event, selected) -> {
				event.player.closeInventory();
			}));
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
