package ca.tweetzy.funds.settings;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Language;
import ca.tweetzy.funds.impl.FundLanguage;
import ca.tweetzy.rose.files.comments.format.YamlCommentFormat;
import ca.tweetzy.rose.files.file.YamlFile;
import ca.tweetzy.rose.utils.Common;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date Created: April 15 2022
 * Time Created: 11:39 a.m.
 *
 * @author Kiran Hart
 */
@SuppressWarnings("all")
public final class Locale {

	private static final Map<String, YamlFile> LOCALES = new HashMap<>();
	private static final Map<String, Object> PHRASES = new HashMap<>();
	private static final Map<String, Language> LANGUAGES = new HashMap<>();

	private static String defaultLanguage = "english";

	static {
		// english will always be available
		LANGUAGES.put("english", new FundLanguage("English", "english", "https://textures.minecraft.net/texture/879d99d9c46474e2713a7e84a95e4ce7e8ff8ea4d164413a592e4435d2c6f9dc"));
	}

	@SneakyThrows
	public static void setup() {
		for (Translation translation : Translation.values())
			PHRASES.put(translation.getKey(), translation.getValue());

		defaultLanguage = Settings.LANGUAGE.getString();

		setupDefaults("english"); // we always want to have english ready
		setupDefaults(defaultLanguage);

		final File[] files = getFiles("locales", "yml");
		for (File file : files) {
			if (file.getName().equalsIgnoreCase("english.yml")) continue;
			if (file.getName().equalsIgnoreCase(defaultLanguage + ".yml")) continue;

			final YamlFile yamlFile = new YamlFile(Funds.getInstance().getDataFolder() + "/locales/" + file.getName());
			yamlFile.createOrLoadWithComments();
			yamlFile.setCommentFormat(YamlCommentFormat.PRETTY);

			PHRASES.forEach((key, value) -> {
				if (!yamlFile.isSet(key))
					yamlFile.set(key, value);
			});

			yamlFile.path("file language").set(file.getName().replace(".yml", "")).comment("For internal use, this is auto generated based on file name");

			if (!yamlFile.isSet("language name"))
				yamlFile.set("language name", StringUtils.capitalize(file.getName().replace(".yml", "")));

			if (!yamlFile.isSet("allow usage")) {
				yamlFile.set("allow usage", true);
				yamlFile.setComment("allow usage", "If true, Funds will let players select this language. By doing so any" +
						"\nmenu or message that can be translated will use translations found in this fle.");
			}

			yamlFile.save();

			LOCALES.put(file.getName().replace(".yml", ""), yamlFile);

			if (yamlFile.getBoolean("allow usage")) {
				LANGUAGES.put(file.getName().replace(".yml", ""), new FundLanguage(
						yamlFile.getString("language name"),
						yamlFile.getString("file language"),
						yamlFile.getString("flag texture", "https://textures.minecraft.net/texture/879e54cbe87867d14b2fbdf3f1870894352048dfecd962846dea893b2154c85"))
				);
			}

		}
	}

	@SneakyThrows
	private static void setupDefaults(String name) {
		final YamlFile yamlFile = new YamlFile(Funds.getInstance().getDataFolder() + "/locales/" + name + ".yml");
		yamlFile.createOrLoadWithComments();
		yamlFile.setCommentFormat(YamlCommentFormat.PRETTY);

		PHRASES.forEach((key, value) -> {
			if (!yamlFile.isSet(key))
				yamlFile.set(key, value);
		});

		yamlFile.path("file language")
				.set(name)
				.comment("This is the default language for Funds to use another language" +
						"\nchange the default language in the config.yml" +
						"\nif the file does not exists, it will generate using the default english" +
						"\ntranslations, you can then make edits from there.");

		if (!yamlFile.isSet("language name"))
			yamlFile.set("language name", StringUtils.capitalize(name.replace(".yml", "")));


		yamlFile.save();

		LOCALES.put(name, yamlFile);

		if (!name.equalsIgnoreCase("english"))
			LANGUAGES.put(name, new FundLanguage(StringUtils.capitalize(name), yamlFile.getName().replace(".yml", ""), yamlFile.getString("flag texture", "https://textures.minecraft.net/texture/879e54cbe87867d14b2fbdf3f1870894352048dfecd962846dea893b2154c85")));
	}

	public static Language getLanague(String name) {
		return LANGUAGES.getOrDefault(name, LANGUAGES.get("english"));
	}

	public static String getString(Account account, String key) {
		return (String) getPhrase(key, account.getPreferredLanguage().getFileName());
	}

	public static List<String> getList(Account account, String key) {
		return (List<String>) getPhrase(key, account.getPreferredLanguage().getFileName());
	}

	public static String getString(String key) {
		return (String) getPhrase(key, defaultLanguage);
	}

	public static List<String> getList(String key) {
		return (List<String>) getPhrase(key, defaultLanguage);
	}

	public static String getString(String key, String language) {
		return (String) getPhrase(key, language);
	}

	public static List<String> getList(String key, String language) {
		return (List<String>) getPhrase(key, language);
	}

	private static Object getPhraseEnglish(String key) {
		return LOCALES.get("english").get(key);
	}

	private static Object getPhrase(String key, String language) {
		YamlFile file = LOCALES.get(language);

		if (file == null)
			return getPhraseEnglish(key);

		return file.get(key, getPhraseEnglish(key));
	}

	public static void tell(CommandSender sender, String key) {

		tell(sender, defaultLanguage, key);
	}

	public static void tell(CommandSender sender, String language, String key) {
		Object phrase = getPhrase(key, language);

		if (phrase instanceof String)
			Common.tell(sender, (String) phrase);

		if (phrase instanceof List)
			Common.tell(sender, ((List<String>) phrase).toArray(new String[0]));
	}

	private static File[] getFiles(@NonNull String directory, @NonNull String extension) {
		// Remove initial dot, if any
		if (extension.startsWith("."))
			extension = extension.substring(1);

		final File dataFolder = new File(Funds.getInstance().getDataFolder(), directory);

		if (!dataFolder.exists())
			dataFolder.mkdirs();

		final String finalExtension = extension;

		return dataFolder.listFiles((FileFilter) file -> !file.isDirectory() && file.getName().endsWith("." + finalExtension));
	}
}
