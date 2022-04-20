package ca.tweetzy.funds.guis.admin;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.impl.FundCurrency;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.gui.helper.InventoryBorder;
import ca.tweetzy.rose.gui.template.PagedGUI;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.QuickItem;
import ca.tweetzy.rose.utils.Replacer;
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
		super(parent, Translation.GUI_CURRENCY_LIST_TITLE.getString(), 6, Funds.getCurrencyManager().getCurrencies());
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Currency currency) {
		return QuickItem
				.of(currency.getIcon())
				.name(Translation.GUI_CURRENCY_LIST_ITEMS_CURRENCY_NAME.getString("currency_name", currency.getName()))
				.glow(currency.isVaultCurrency())
				.lore(Translation.GUI_CURRENCY_LIST_ITEMS_CURRENCY_LORE.getList(
						"currency_id", currency.getId(),
						"currency_description", currency.getDescription(),
						"currency_singular_format", currency.getSingularFormat(),
						"currency_plural_format", currency.getPluralFormat()
				))
				.make();
	}

	@Override
	protected void drawAdditional() {
		setButton(5, 4, QuickItem.of(CompMaterial.SLIME_BALL)
						.name(Translation.GUI_CURRENCY_LIST_ITEMS_NEW_NAME.getString())
						.lore(Translation.GUI_CURRENCY_LIST_ITEMS_NEW_LORE.getList())
						.make(), click -> new TitleInput(click.player, Common.colorize(Translation.CURRENCY_CREATE_TITLE.getString()), Common.colorize(Translation.CURRENCY_CREATE_SUBTITLE.getString())) {

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
							Locale.tell(click.player, Translation.CURRENCY_ALREADY_EXISTS.getKey());
							return false;
						}

						Funds.getCurrencyManager().createCurrency(new FundCurrency(string), (error, created) -> {
							if (error) {
								Locale.tell(click.player, Translation.CURRENCY_CREATION_FAIL.getKey());
								return;
							}

							click.manager.showGUI(click.player, new CurrencyListGUI(new AdminMainGUI()));
							Common.tell(click.player, Replacer.replaceVariables(Locale.getString(Translation.CURRENCY_CREATED.getKey()), "%currency_name%", created.getId()));
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
				if (deleted) {
					// remove from player accounts
					Funds.getAccountManager().getAccounts().forEach(account -> account.deleteCurrency(currency));
					Funds.getAccountManager().updateAccounts(Funds.getAccountManager().getAccounts(), null);

					click.manager.showGUI(click.player, Funds.getCurrencyManager().getCurrencies().isEmpty() ? new AdminMainGUI() : new CurrencyListGUI(new AdminMainGUI()));
				}
			});
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
