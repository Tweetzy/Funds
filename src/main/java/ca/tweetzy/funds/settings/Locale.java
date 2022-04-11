package ca.tweetzy.funds.settings;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.rose.configuration.Config;
import ca.tweetzy.rose.configuration.ConfigSetting;

/**
 * Date Created: April 10 2022
 * Time Created: 4:22 p.m.
 *
 * @author Kiran Hart
 */
public final class Locale {

	private static final Config config = Funds.getLangConfig();

	public static final ConfigSetting NOT_A_NUMBER = new ConfigSetting(config, "Not A Number", "&4%value% &cis not a valid number!");


	public static final ConfigSetting CURRENCY_ALREADY_EXISTS = new ConfigSetting(config, "Currency Already Exists", "&cA currency by that id already exists!");
	public static final ConfigSetting CURRENCY_CREATE_ERROR = new ConfigSetting(config, "Currency Creation Failed", "&cSomething went wrong while creating that currency");
	public static final ConfigSetting CURRENCY_CREATED = new ConfigSetting(config, "Currency Created", "&aCreated a new currency named&F: &e%currency_name%");

	public static void setup() {
		config.load();
		config.setAutosave(true).setAutosave(true);
		config.saveChanges();
	}
}
