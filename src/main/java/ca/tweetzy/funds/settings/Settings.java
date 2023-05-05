package ca.tweetzy.funds.settings;

import ca.tweetzy.flight.config.ConfigEntry;
import ca.tweetzy.flight.settings.FlightSettings;
import ca.tweetzy.funds.Funds;
import lombok.SneakyThrows;

/**
 * Date Created: April 15 2022
 * Time Created: 12:35 a.m.
 *
 * @author Kiran Hart
 */
public final class Settings extends FlightSettings {

	public static final ConfigEntry PREFIX = create("prefix", "&8[&eFunds&8]", "The global prefix for the plugin");
	public static final ConfigEntry LANGUAGE = create("language", "en_us", "The default language for the plugin");
	public static final ConfigEntry METRICS = create("metrics", true, "Allows me to see how many servers are using Funds");

	public static final ConfigEntry AUTO_DEPOSIT_PICKED_UP_CURRENCY = create("settings.auto deposit picked up currency", true, "If true, if a player picks up a currency item, it will be automatically deposited");
	public static final ConfigEntry USE_CHAT_BALANCE = create("settings.use chat balance", false, "If true, currency balances will show up in chat after /balance instead of a gui.");

	@SneakyThrows
	public static void setup() {
		Funds.getCoreConfig().init();
	}
}
