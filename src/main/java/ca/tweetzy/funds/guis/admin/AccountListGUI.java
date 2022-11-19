package ca.tweetzy.funds.guis.admin;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.guis.template.ConfirmGUI;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.gui.helper.InventoryBorder;
import ca.tweetzy.flight.gui.template.PagedGUI;
import ca.tweetzy.flight.utils.QuickItem;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Date Created: April 11 2022
 * Time Created: 11:28 p.m.
 *
 * @author Kiran Hart
 */
public final class AccountListGUI extends PagedGUI<Account> {

	private final Account account;

	public AccountListGUI(Gui parent, @NonNull final Account account) {
		super(parent, Translation.GUI_ACCOUNT_LIST_TITLE.getString(account), 6, Funds.getAccountManager().getAccounts());
		this.account = account;
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Account account) {
		final OfflinePlayer player = Bukkit.getOfflinePlayer(account.getOwner());
		return QuickItem.of(player)
				.name(Translation.GUI_ACCOUNT_LIST_ITEMS_ACCOUNT_NAME.getString(this.account, "account_name", player.getName()))
				.lore(Translation.GUI_ACCOUNT_LIST_ITEMS_ACCOUNT_LORE.getList(this.account))
				.make();
	}

	@Override
	protected void drawAdditional() {
		// wipe account currencies
		setButton(5, 7, QuickItem.of(CompMaterial.LAVA_BUCKET)
				.name(Translation.GUI_ACCOUNT_LIST_ITEMS_RESET_NAME.getString(this.account))
				.lore(Translation.GUI_ACCOUNT_LIST_ITEMS_RESET_LORE.getList(this.account))
				.make(), click -> click.manager.showGUI(click.player, new ConfirmGUI(null, this.account, confirmed -> {

			if (confirmed) {
				Funds.getAccountManager().resetPlayerAccountsBalances();
			}

			click.manager.showGUI(click.player, new AccountListGUI(new AdminMainGUI(this.account), this.account));
		})));
	}

	@Override
	protected void onClick(Account account, GuiClickEvent event) {
		event.manager.showGUI(event.player, new AccountViewGUI(account));
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
