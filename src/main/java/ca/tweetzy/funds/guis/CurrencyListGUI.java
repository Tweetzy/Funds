package ca.tweetzy.funds.guis;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.guis.template.PagedGUI;
import ca.tweetzy.funds.impl.FundCurrency;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.gui.helper.InventoryBorder;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.QuickItem;
import ca.tweetzy.rose.utils.input.TitleInput;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Date Created: April 10 2022
 * Time Created: 9:59 p.m.
 *
 * @author Kiran Hart
 */
public final class CurrencyListGUI extends PagedGUI<Currency> {

	public CurrencyListGUI(final Gui parent) {
		super(parent, "&eFunds &8> &7Currency List", 6, Funds.getCurrencyManager().getCurrencies());
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Currency currency) {
		return QuickItem
				.of(currency.getIcon())
				.name(currency.getName())
				.glow(currency.isVaultCurrency())
				.lore(
						"&8Basic currency information",
						"&7Identifier&F: &e" + currency.getId(),
						"&7Description&F:",
						"&f- " + currency.getDescription(),
						"",
						"&7Singular Format&f: &e" + currency.getSingularFormat(),
						"&7Plural Format&f: &e" + currency.getPluralFormat(),
						"",
						"&e&lLeft Click &8» &7To Edit Currency",
						"&c&lPress 1 &8» &7To Delete Currency"
				)
				.make();
	}

	@Override
	protected void drawAdditional() {
		setButton(5, 4, QuickItem.of(CompMaterial.SLIME_BALL).name("&a&lNew Currency").lore(
						"&8Currency creation",
						"&7Create another currency to be used",
						"",
						"&E&lClick &8» &7To Create Currency"
				).make(), click -> new TitleInput(click.player, Common.colorize("&eEnter Currency Name"), Common.colorize("&fEnter the id for the currency into chat")) {

					@Override
					public void onExit(Player player) {
						click.manager.showGUI(player, CurrencyListGUI.this);
					}

					@Override
					public boolean onResult(String string) {
						string = ChatColor.stripColor(string);

						if (string.isEmpty())
							return false;

						if (Funds.getCurrencyManager().getCurrency(string) != null) {
							Common.tell(click.player, "&cA currency by that id already exists!");
							return false;
						}

						Funds.getCurrencyManager().createCurrency(new FundCurrency(string), (error, created) -> {
							if (error) {
								Common.tell(click.player, "&cSomething went wrong while creating that currency");
								return;
							}

							click.manager.showGUI(click.player, new CurrencyListGUI(new AdminMainGUI()));
							Common.tell(click.player, "&aCreated a new currency named&F: &e%currency_name%".replace("%currency_name%", created.getId()));
						});
						return true;
					}
				}
		);
	}

	@Override
	protected void onClick(Currency currency, GuiClickEvent click) {
		if (click.clickType == ClickType.LEFT)
			click.manager.showGUI(click.player, new CurrencyEditGUI(new CurrencyListGUI(new AdminMainGUI()), currency));

		if (click.clickType == ClickType.NUMBER_KEY)
			Funds.getCurrencyManager().deleteCurrency(currency.getId(), deleted -> {
				if (deleted)
					click.manager.showGUI(click.player, Funds.getCurrencyManager().getCurrencies().isEmpty() ? new AdminMainGUI() : new CurrencyListGUI(new AdminMainGUI()));
			});
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
