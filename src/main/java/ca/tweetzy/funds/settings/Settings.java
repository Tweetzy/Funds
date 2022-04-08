package ca.tweetzy.funds.settings;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.rose.configuration.Config;
import ca.tweetzy.rose.configuration.ConfigSetting;

/**
 * Date Created: April 08 2022
 * Time Created: 7:04 p.m.
 *
 * @author Kiran Hart
 */
public final class Settings {

	private static final Config config = Funds.getInstance().getCoreConfig();

	public static final ConfigSetting LANG = new ConfigSetting(config, "language", "en_US", "The default language for the plugin");

	public static void setup() {
		config.load();
		config.setAutosave(true).setAutosave(true);
		config.saveChanges();
	}
}
