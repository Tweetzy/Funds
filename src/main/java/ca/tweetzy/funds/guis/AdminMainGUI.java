package ca.tweetzy.funds.guis;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.guis.template.BaseGUI;
import ca.tweetzy.funds.impl.FundCurrency;
import ca.tweetzy.funds.model.OrList;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.rose.comp.NBTEditor;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.QuickItem;
import ca.tweetzy.rose.utils.input.TitleInput;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * Date Created: April 10 2022
 * Time Created: 12:53 a.m.
 *
 * @author Kiran Hart
 */
public final class AdminMainGUI extends BaseGUI {

	public AdminMainGUI() {
		super("&eFunds &fv&7" + Funds.getInstance().getVersion());
	}

	@Override
	protected void draw() {
		reset();

		//

		// Currencies Button
		setButton(1, 2, QuickItem.of(CompMaterial.GOLD_INGOT)
				.name("&e&lCurrencies")
				.lore("&8All the currencies you've made")
				.lore(OrList.get(Funds.getCurrencyManager().getCurrencies().isEmpty(),
						Arrays.asList("&cNo currencies found", "", "&e&lClick &8» &7To create your first currency"),
						Arrays.asList("&a" + Funds.getCurrencyManager().getCurrencies().size() + " currencies found", "", "&e&lClick &8» &7To view currencies")
				)).make(), click -> {

			if (Funds.getCurrencyManager().getCurrencies().isEmpty()) {
				new TitleInput(click.player, Common.colorize("&eEnter Currency Name"), Common.colorize("&fEnter the id for the currency into chat")) {

					@Override
					public void onExit(Player player) {
						click.manager.showGUI(player, AdminMainGUI.this);
					}

					@Override
					public boolean onResult(String string) {
						if (string.isEmpty())
							return false;

						Funds.getDataManager().createCurrency(new FundCurrency(string), (error, created) -> {
							if (error != null) {
								Common.tell(click.player, Locale.CURRENCY_CREATE_ERROR.getString());
								return;
							}

							Funds.getCurrencyManager().addCurrency(created);
							Common.tell(click.player, Locale.CURRENCY_CREATED.getString().replace("%currency_name%", string));
						});
						return true;
					}
				};
				return;
			}

			click.manager.showGUI(click.player, new CurrencyListGUI(this));
		});

		setButton(1, 4, QuickItem.of(NBTEditor.getHead("http://textures.minecraft.net/texture/15dfc521807dce2485c4032b1350303540325715eb309dd2bcbba4e27df83fe1"))
				.name("&d&lAccounts")
				.lore(
						"&8View all known player accounts",
						"&7An account is used to store each",
						"&7player's different currency count",
						"",
						"&e&lClick &8» &7To view accounts"
				)
				.make(), null);

		// Discord Button
		setItem(4, 1, QuickItem.of(NBTEditor.getHead("http://textures.minecraft.net/texture/4d42337be0bdca2128097f1c5bb1109e5c633c17926af5fb6fc20000011aeb53"))
				.name("&e&LDiscord")
				.lore(
						"&8Ask questions, Get support",
						"&7Need help with &eFunds&7?, Join our",
						"&7Discord server to ask questions.",
						"",
						"&8» &e&ldiscord.tweetzy.ca"
				)
				.make());

		// More Plugins Button
		setButton(4, 6, QuickItem.of(NBTEditor.getHead("http://textures.minecraft.net/texture/b94ac36d9a6fbff1c558941381e4dcf595df825913f6c383ffaa71b756a875d3"))
				.name("<GRADIENT:00a87f>&lMore Plugins</GRADIENT:00ce74>")
				.lore(
						"&8View more of my plugins",
						"&7Like &eFunds&7? Take a look at my other",
						"&7plugins, you might like them.",
						"",
						"&e&lClick &8» &7To view Plugins"
				)
				.make(), null);


		// Patron
		setButton(4, 7, QuickItem.of(CompMaterial.DIAMOND)
				.name("&e&lPatreon")
				.lore(
						"&8Support me on Patreon",
						"&7By supporting me on Patreon you will",
						"&7be helping me be able to continue updating",
						"&7and creating free plugins.",
						"",
						"&e&lClick &8» &7To view Patrons"
				)
				.glow(true)
				.make(), null);
	}
}
