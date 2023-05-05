package ca.tweetzy.funds.guis.admin;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.gui.helper.InventoryBorder;
import ca.tweetzy.flight.gui.template.PagedGUI;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.flight.utils.input.TitleInput;
import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.impl.FundCurrency;
import ca.tweetzy.funds.settings.Translations;
import lombok.NonNull;
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

	private final Account account;

	public CurrencyListGUI(final Gui parent, @NonNull final Account account) {
		super(parent, TranslationManager.string(Translations.GUI_CURRENCY_LIST_TITLE), 6, Funds.getCurrencyManager().getCurrencies());
		this.account = account;
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Currency currency) {
		return QuickItem
				.of(currency.getIcon())
				.name(TranslationManager.string(Translations.GUI_CURRENCY_LIST_ITEMS_CURRENCY_NAME, "currency_name", currency.getName()))
				.glow(currency.isVaultCurrency())
				.lore(TranslationManager.list(Translations.GUI_CURRENCY_LIST_ITEMS_CURRENCY_LORE,
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
						.name(TranslationManager.string(Translations.GUI_CURRENCY_LIST_ITEMS_NEW_NAME))
						.lore(TranslationManager.list(Translations.GUI_CURRENCY_LIST_ITEMS_NEW_LORE))
						.make(), click -> new TitleInput(Funds.getInstance(), click.player, Common.colorize(TranslationManager.string(Translations.CURRENCY_CREATE_TITLE)), Common.colorize(TranslationManager.string(Translations.CURRENCY_CREATE_SUBTITLE))) {

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
							Common.tell(click.player, TranslationManager.string(Translations.CURRENCY_ALREADY_EXISTS));
							return false;
						}

						Funds.getCurrencyManager().createCurrency(new FundCurrency(string), (success, created) -> {
							if (!success) {
								Common.tell(click.player, TranslationManager.string(Translations.CURRENCY_CREATION_FAIL));
								return;
							}

							click.manager.showGUI(click.player, new CurrencyListGUI(new AdminMainGUI(account), account));
							Common.tell(click.player, TranslationManager.string(Translations.CURRENCY_CREATED, "%currency_name%", created.getId()));
						});
						return true;
					}
				}
		);
	}

	@Override
	protected void onClick(Currency currency, GuiClickEvent click) {
		if (click.clickType == ClickType.LEFT)
			click.manager.showGUI(click.player, new CurrencyEditGUI(new CurrencyListGUI(new AdminMainGUI(this.account), this.account), this.account, currency));

		if (click.clickType == ClickType.NUMBER_KEY)
			Funds.getCurrencyManager().deleteCurrency(currency.getId(), deleted -> {
				if (deleted) {
					// remove from player accounts
					Funds.getAccountManager().getAccounts().forEach(account -> account.deleteCurrency(currency));
					Funds.getAccountManager().updateAccounts(Funds.getAccountManager().getAccounts(), null);

					click.manager.showGUI(click.player, Funds.getCurrencyManager().getCurrencies().isEmpty() ? new AdminMainGUI(this.account) : new CurrencyListGUI(new AdminMainGUI(this.account), this.account));
				}
			});
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
