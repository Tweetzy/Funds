package ca.tweetzy.funds.guis.player;

import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.gui.helper.InventoryBorder;
import ca.tweetzy.flight.gui.template.PagedGUI;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.settings.Translations;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Date Created: April 20 2022
 * Time Created: 4:17 p.m.
 *
 * @author Kiran Hart
 */
public final class AccountPickerGUI extends PagedGUI<Account> {

	private final Account player;
	private final BiConsumer<GuiClickEvent, Account> selected;

	public AccountPickerGUI(@NonNull final Account player, @NonNull final BiConsumer<GuiClickEvent, Account> selected) {
		super(null, TranslationManager.string(Translations.GUI_ACCOUNT_PICKER_TITLE), 6, Funds.getAccountManager().getAccounts().stream().filter(acc -> !acc.getOwner().equals(player.getOwner())).collect(Collectors.toList()));
		this.player = player;
		this.selected = selected;
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Account account) {
		final OfflinePlayer accountOwner = Bukkit.getOfflinePlayer(account.getOwner());
		return QuickItem.of(accountOwner)
				.name(TranslationManager.string(Translations.GUI_ACCOUNT_PICKER_ITEMS_ACCOUNT_NAME, "account_name", accountOwner.getName()))
				.lore(TranslationManager.list(Translations.GUI_ACCOUNT_PICKER_ITEMS_ACCOUNT_LORE))
				.make();
	}

	@Override
	protected void onClick(Account account, GuiClickEvent clickEvent) {
		this.selected.accept(clickEvent, account);
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
