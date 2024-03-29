package ca.tweetzy.funds.guis.admin;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.template.BaseGUI;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.ChatUtil;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.flight.utils.input.TitleInput;
import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.guis.FundsBaseGUI;
import ca.tweetzy.funds.guis.template.PluginListGUI;
import ca.tweetzy.funds.impl.FundCurrency;
import ca.tweetzy.funds.settings.Translations;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Date Created: April 10 2022
 * Time Created: 12:53 a.m.
 *
 * @author Kiran Hart
 */
public final class AdminMainGUI extends FundsBaseGUI {

	private final Account account;

	public AdminMainGUI(@NonNull final Account account) {
		super(TranslationManager.string(Translations.GUI_MAIN_TITLE, "plugin_version", Funds.getInstance().getVersion()));
		this.account = account;
		draw();
	}

	@Override
	protected void draw() {
		reset();

		// Currencies Button
		setButton(1, 2, QuickItem.of(CompMaterial.GOLD_INGOT)
				.name(TranslationManager.string(Translations.GUI_MAIN_ITEMS_CURRENCY_NAME))
				.lore(Funds.getCurrencyManager().getCurrencies().isEmpty() ?
						TranslationManager.list(Translations.GUI_MAIN_ITEMS_CURRENCY_LORE_CREATE) :
						TranslationManager.list(Translations.GUI_MAIN_ITEMS_CURRENCY_LORE_VIEW, "total_currencies", Funds.getCurrencyManager().getCurrencies().size())
				).make(), click -> {

			if (Funds.getCurrencyManager().getCurrencies().isEmpty()) {
				new TitleInput(Funds.getInstance(), click.player, Common.colorize(TranslationManager.string(Translations.CURRENCY_CREATE_TITLE)), Common.colorize(TranslationManager.string(Translations.CURRENCY_CREATE_SUBTITLE))) {

					@Override
					public void onExit(Player player) {
						click.manager.showGUI(player, AdminMainGUI.this);
					}

					@Override
					public boolean onResult(String string) {
						string = ChatColor.stripColor(string);

						if (string.isEmpty())
							return false;

						Funds.getCurrencyManager().createCurrency(new FundCurrency(string), (success, created) -> {
							if (!success) {
								Common.tell(click.player, TranslationManager.string(Translations.CURRENCY_CREATION_FAIL));
								return;
							}

							click.manager.showGUI(click.player, new CurrencyListGUI(new AdminMainGUI(account), account));
							Common.tell(click.player, TranslationManager.string(Translations.CURRENCY_CREATED, "currency_name", created.getId()));
						});

						return true;
					}
				};
				return;
			}

			click.manager.showGUI(click.player, new CurrencyListGUI(this, this.account));
		});

		setButton(1, 6, QuickItem.of("http://textures.minecraft.net/texture/15dfc521807dce2485c4032b1350303540325715eb309dd2bcbba4e27df83fe1")
				.name(TranslationManager.string(Translations.GUI_MAIN_ITEMS_ACCOUNTS_NAME))
				.lore(Funds.getAccountManager().getAccounts().isEmpty() ?
						TranslationManager.list(Translations.GUI_MAIN_ITEMS_ACCOUNTS_LORE_CREATE) :
						TranslationManager.list(Translations.GUI_MAIN_ITEMS_ACCOUNTS_LORE_VIEW, "total_accounts", Funds.getAccountManager().getAccounts().size())
				)
				.make(), click -> click.manager.showGUI(click.player, new AccountListGUI(this, this.account)));

		// Discord Button
		setButton(4, 1, QuickItem.of("http://textures.minecraft.net/texture/4d42337be0bdca2128097f1c5bb1109e5c633c17926af5fb6fc20000011aeb53")
				.name("&e&LDiscord")
				.lore(
						"&8Ask questions, Get support",
						"&7Need help with &eFunds&7?, Join our",
						"&7Discord server to ask questions.",
						"",
						"&8» &e&ldiscord.tweetzy.ca"
				)
				.make(), click -> {

			click.gui.close();
			Common.tellNoPrefix(click.player,
					"&8&m-----------------------------------------------------",
					"",
					ChatUtil.centerMessage("&E&lFunds Support"),
					ChatUtil.centerMessage("&bhttps://discord.tweetzy.ca"),
					"&8&m-----------------------------------------------------"
			);
		});


		// Patron
		setButton(4, 4, QuickItem.of(CompMaterial.DIAMOND)
				.name("&e&lPatreon")
				.lore(
						"&8Support me on Patreon",
						"&7By supporting me on Patreon you will",
						"&7be helping me be able to continue updating",
						"&7and creating free plugins.",
						"",
						"&e&lClick &8» &7To view Patreon"
				)
				.glow(true)
				.make(), click -> {

			click.gui.close();
			Common.tellNoPrefix(click.player,
					"&8&m-----------------------------------------------------",
					"",
					ChatUtil.centerMessage("&E&lTweetzy Patreon"),
					ChatUtil.centerMessage("&bhttps://patreon.tweetzy.ca"),
					"&8&m-----------------------------------------------------"
			);
		});

		// More Plugins Button
		setButton(4, 7, QuickItem.of("http://textures.minecraft.net/texture/b94ac36d9a6fbff1c558941381e4dcf595df825913f6c383ffaa71b756a875d3")
				.name("<GRADIENT:00a87f>&lMore Plugins</GRADIENT:00ce74>")
				.lore(
						"&8View more of my plugins",
						"&7Like &eFunds&7? Take a look at my other",
						"&7plugins, you might like them.",
						"",
						"&e&lClick &8» &7To view Plugins"
				)
				.make(), click -> click.manager.showGUI(click.player, new PluginListGUI(this.account)));

	}
}
