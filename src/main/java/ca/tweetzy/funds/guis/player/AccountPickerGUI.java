package ca.tweetzy.funds.guis.player;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.gui.template.PagedGUI;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

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
		super(null, "&eSelect Account", 6, Funds.getAccountManager().getAccounts().stream().filter(acc -> !acc.getOwner().equals(player.getOwner())).collect(Collectors.toList()));
		this.player = player;
		this.selected = selected;
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Account object) {
		return null;
	}

	@Override
	protected void onClick(Account object, GuiClickEvent clickEvent) {

	}
}
