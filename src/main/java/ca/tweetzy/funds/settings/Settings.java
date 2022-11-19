package ca.tweetzy.funds.settings;

import ca.tweetzy.flight.config.ConfigEntry;
import ca.tweetzy.flight.config.tweetzy.TweetzyYamlConfig;
import ca.tweetzy.funds.Funds;
import lombok.SneakyThrows;

/**
 * Date Created: April 15 2022
 * Time Created: 12:35 a.m.
 *
 * @author Kiran Hart
 */
public final class Settings {

	static final TweetzyYamlConfig config = Funds.getInstance().getCoreConfig();

	public static final ConfigEntry PREFIX = config.createEntry("prefix", "&8[&eFunds&8]").withComment("The global prefix for the plugin");
	public static final ConfigEntry LANGUAGE = config.createEntry("language", "english").withComment("The default language for the plugin");
	public static final ConfigEntry METRICS = config.createEntry("metrics", true).withComment("Allows me to see how many servers are using Funds");

	public static final ConfigEntry AUTO_DEPOSIT_PICKED_UP_CURRENCY = config.createEntry("settings.auto deposit picked up currency", true).withComment("If true, if a player picks up a currency item, it will be automatically deposited");
	public static final ConfigEntry USE_CHAT_BALANCE = config.createEntry("settings.use chat balance", false).withComment("If true, currency balances will show up in chat after /balance instead of a gui.");

	@SneakyThrows
	public static void setup() {
		config.init();
	}
}
