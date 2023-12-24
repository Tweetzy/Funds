package ca.tweetzy.funds.guis.player;

import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.gui.helper.InventoryBorder;
import ca.tweetzy.flight.gui.template.PagedGUI;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.guis.FundsPagedGUI;
import ca.tweetzy.funds.impl.TopBalanceRecord;
import ca.tweetzy.funds.settings.Translations;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class BalanceTopGUI extends FundsPagedGUI<TopBalanceRecord> {

	private final Account player;
	private final Currency currency;

	public BalanceTopGUI(@NonNull final Account player, @NonNull final Currency currency) {
		super(null, TranslationManager.string(Translations.GUI_TOP_BALANCE_TITLE), 6, Funds.getAccountManager().getHighestBalances(currency));
		this.player = player;
		this.currency = currency;
		draw();
	}


	@Override
	protected ItemStack makeDisplayItem(TopBalanceRecord topBalanceRecord) {
		final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(topBalanceRecord.account().getOwner());

		return QuickItem
				.of(offlinePlayer)
				.name(TranslationManager.string(Translations.GUI_TOP_BALANCE_ITEMS_ACCOUNT_NAME, "account_name", offlinePlayer.getName()))
				.lore(TranslationManager.list(Translations.GUI_TOP_BALANCE_ITEMS_ACCOUNT_LORE,
						"currency_balance", String.format("%,.2f", topBalanceRecord.amount()),
						"currency_name", this.currency.getName()
				))
				.make();
	}

	@Override
	protected void onClick(TopBalanceRecord object, GuiClickEvent clickEvent) {
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
