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


	@SneakyThrows
	public static void setup() {
		config.applySettings();
		config.save();
	}
}
