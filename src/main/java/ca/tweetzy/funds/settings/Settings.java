package ca.tweetzy.funds.settings;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.rose.files.ConfigSetting;
import ca.tweetzy.rose.files.file.YamlFile;
import lombok.SneakyThrows;

/**
 * Date Created: April 15 2022
 * Time Created: 12:35 a.m.
 *
 * @author Kiran Hart
 */
public final class Settings {

	static final YamlFile config = Funds.getInstance().getCoreConfig();

	public static final ConfigSetting PREFIX = new ConfigSetting(config, "prefix", "&8[&eFunds&8]", "The global prefix for the plugin");
	public static final ConfigSetting LANGUAGE = new ConfigSetting(config, "language", "english", "The default language for the plugin");

	public static final ConfigSetting AUTO_DEPOSIT_PICKED_UP_CURRENCY = new ConfigSetting(config, "settings.auto deposit picked up currency", true, "If true, if a player picks up a currency item, it will be automatically deposited");

	@SneakyThrows
	public static void setup() {
		config.applySettings();
		config.save();
	}
}
