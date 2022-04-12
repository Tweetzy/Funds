package ca.tweetzy.funds.guis;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.guis.template.PagedGUI;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.gui.helper.InventoryBorder;
import ca.tweetzy.rose.utils.QuickItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Date Created: April 11 2022
 * Time Created: 11:28 p.m.
 *
 * @author Kiran Hart
 */
public final class AccountListGUI extends PagedGUI<Account> {

	public AccountListGUI(Gui parent) {
		super(parent, "&eFunds &8> &7Account List", 6, Funds.getAccountManager().getAccounts());
	}

	@Override
	protected ItemStack makeDisplayItem(Account account) {
		return QuickItem.of(Bukkit.getOfflinePlayer(account.getOwner())).make();
	}

	@Override
	protected void onClick(Account object, GuiClickEvent clickEvent) {

	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
