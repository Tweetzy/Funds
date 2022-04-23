package ca.tweetzy.funds.guis.player;

import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.gui.helper.InventoryBorder;
import ca.tweetzy.rose.gui.template.PagedGUI;
import ca.tweetzy.rose.utils.QuickItem;
import lombok.NonNull;
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
				.name(Translation.GUI_BALANCES_ITEMS_CURRENCY_NAME.getString(this.account, "currency_name",currency.getName()))
				.lore(Translation.GUI_BALANCES_ITEMS_CURRENCY_LORE.getList(this.account, "currency_balance", this.account.getCurrencies().get(currency)))
				.make();
	}

	@Override
	protected void onClick(Currency currency, GuiClickEvent clickEvent) {
		if (currency.isPayingAllowed())
			this.account.initiateTransfer(clickEvent.player, currency);
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
