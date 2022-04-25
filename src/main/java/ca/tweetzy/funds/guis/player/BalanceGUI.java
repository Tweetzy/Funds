package ca.tweetzy.funds.guis.player;

import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.gui.helper.InventoryBorder;
import ca.tweetzy.rose.gui.template.PagedGUI;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.QuickItem;
import ca.tweetzy.rose.utils.Replacer;
import ca.tweetzy.rose.utils.input.TitleInput;
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
public final class BalanceGUI extends PagedGUI<Currency> {

	private final Account account;

	public BalanceGUI(Gui parent, @NonNull final Account account) {
		super(parent, Translation.GUI_BALANCES_TITLE.getString(account), 6, new ArrayList<>(account.getCurrencies().keySet()));
		this.account = account;
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Currency currency) {
		return QuickItem.of(currency.getIcon())
				.name(Translation.GUI_BALANCES_ITEMS_CURRENCY_NAME.getString(this.account, "currency_name", currency.getName()))
				.lore(Translation.GUI_BALANCES_ITEMS_CURRENCY_LORE.getList(this.account, "currency_balance", this.account.getCurrencies().get(currency)))
				.make();
	}

	@Override
	protected void onClick(Currency currency, GuiClickEvent clickEvent) {
		final double currencyTotal = this.account.getCurrencies().get(currency);

		if (currencyTotal <= 0D) {
			Common.tell(clickEvent.player, Replacer.replaceVariables(Locale.getString(Translation.NOT_ENOUGH_MONEY.getKey()), "currency_plural_format", currency.getPluralFormat()));
			return;
		}

		if (currency.isPayingAllowed() && clickEvent.clickType == ClickType.LEFT)
			this.account.initiateTransfer(clickEvent.player, currency);

		if (currency.isWithdrawAllowed() && clickEvent.clickType == ClickType.RIGHT) {

			new TitleInput(clickEvent.player, Common.colorize("&eWithdrawing Currency"), Common.colorize("&fEnter amount to withdraw"), Common.colorize(
					String.format("&e%s %s", String.format("%,.2f", currencyTotal), currencyTotal > 1.0D ? currency.getPluralFormat() : currency.getSingularFormat())
			)) {

				@Override
				public boolean onResult(String string) {
					if (!NumberUtils.isNumber(string)) {
						// tell them to learn what a number is
						Common.tell(clickEvent.player, Replacer.replaceVariables(Locale.getString(Translation.NOT_A_NUMBER.getKey()), "value", string));
						return false;
					}

					final double withdrawAmount = Double.parseDouble(string);

					// does the player even have enough money
					if (account.getCurrencies().get(currency) < withdrawAmount) {
						Common.tell(clickEvent.player, Replacer.replaceVariables(Locale.getString(Translation.NOT_ENOUGH_MONEY.getKey()), "currency_plural_format", currency.getPluralFormat()));
						return false;
					}

					BalanceGUI.this.account.withdrawCurrency(currency, withdrawAmount);
					BalanceGUI.this.account.sync(true);

					Common.tell(clickEvent.player, Translation.WITHDRAW.getString(account, "amount", withdrawAmount, "currency_auto_format", withdrawAmount > 1.0D ? currency.getPluralFormat() : currency.getSingularFormat()));
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
