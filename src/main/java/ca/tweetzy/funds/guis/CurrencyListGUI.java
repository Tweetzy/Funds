package ca.tweetzy.funds.guis;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.guis.template.PagedGUI;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.gui.helper.InventoryBorder;
import ca.tweetzy.rose.utils.QuickItem;
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
	}

	@Override
	protected ItemStack makeDisplayItem(Currency object) {
		return QuickItem
				.of(object.getIcon())
				.name(object.getName())
				.lore(
						"&8Basic currency information",
						"&7Identifier&F: &e" + object.getId(),
						"&7Description&F:",
						"&f- " + object.getDescription(),
						"",
						"&e&lLeft Click &8» &7To Edit Currency",
						"&c&lPress 1 &8» &7To Delete Currency"
				)
				.make();
	}

	@Override
	protected void onClick(Currency currency, GuiClickEvent click) {
		if (click.clickType == ClickType.LEFT)
			click.manager.showGUI(click.player, new CurrencyEditGUI(this, currency));

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
