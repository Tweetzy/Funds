package ca.tweetzy.funds.guis.player;

import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.gui.helper.InventoryBorder;
import ca.tweetzy.flight.gui.template.PagedGUI;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.flight.utils.input.TitleInput;
import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.events.CurrencyWithdrawEvent;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.guis.FundsPagedGUI;
import ca.tweetzy.funds.settings.Translations;
import lombok.NonNull;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Date Created: April 23 2022
 * Time Created: 2:20 p.m.
 *
 * @author Kiran Hart
 */
public final class BalanceGUI extends FundsPagedGUI<Currency> {

	private final Account account;

	public BalanceGUI(Gui parent, @NonNull final Account account) {
		super(parent, TranslationManager.string(Translations.GUI_BALANCES_TITLE), 6, new ArrayList<>(account.getCurrencies().keySet()));
		this.account = account;
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Currency currency) {
		return QuickItem.of(currency.getIcon())
				.name(TranslationManager.string(Translations.GUI_BALANCES_ITEMS_CURRENCY_NAME, "currency_name", currency.getName()))
				.lore(TranslationManager.list(Translations.GUI_BALANCES_ITEMS_CURRENCY_LORE, "currency_balance", this.account.getCurrencies().get(currency)))
				.make();
	}

	@Override
	protected void onClick(Currency currency, GuiClickEvent clickEvent) {
		final double currencyTotal = this.account.getCurrencies().get(currency);

		if (currencyTotal <= 0D) {
			Common.tell(clickEvent.player, TranslationManager.string(Translations.NOT_ENOUGH_MONEY, "currency_plural_format", currency.getPluralFormat()));
			return;
		}

		if (currency.isPayingAllowed() && clickEvent.clickType == ClickType.LEFT)
			this.account.initiateTransfer(clickEvent.player, currency);

		if (currency.isWithdrawAllowed() && clickEvent.clickType == ClickType.RIGHT) {

			new TitleInput(Funds.getInstance(), clickEvent.player, Common.colorize("&eWithdrawing Currency"), Common.colorize("&fEnter amount to withdraw"), Common.colorize(
					String.format("&e%s %s", String.format("%,.2f", currencyTotal), currencyTotal > 1.0D ? currency.getPluralFormat() : currency.getSingularFormat())
			)) {

				@Override
				public boolean onResult(String string) {
					if (!NumberUtils.isNumber(string)) {
						// tell them to learn what a number is
						Common.tell(clickEvent.player, TranslationManager.string(Translations.NOT_A_NUMBER, "value", string));
						return false;
					}

					final double withdrawAmount = Double.parseDouble(string);

					// does the player even have enough money
					if (account.getCurrencies().get(currency) < withdrawAmount) {
						Common.tell(clickEvent.player, TranslationManager.string(Translations.NOT_ENOUGH_MONEY, "currency_plural_format", currency.getPluralFormat()));
						return false;
					}

					final CurrencyWithdrawEvent withdrawEvent = new CurrencyWithdrawEvent(true, account, currency, withdrawAmount);
					Funds.getInstance().getServer().getPluginManager().callEvent(withdrawEvent);
					if (withdrawEvent.isCancelled()) return false;

					BalanceGUI.this.account.withdrawCurrency(currency, withdrawAmount);
					BalanceGUI.this.account.sync(true);

					Common.tell(clickEvent.player, TranslationManager.string(Translations.WITHDRAW, "amount", withdrawAmount, "currency_auto_format", withdrawAmount > 1.0D ? currency.getPluralFormat() : currency.getSingularFormat()));
					clickEvent.player.getInventory().addItem(currency.buildPhysicalItem(withdrawAmount));
					return true;
				}
			};
		}
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
