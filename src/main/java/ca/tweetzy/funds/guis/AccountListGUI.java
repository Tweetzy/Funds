package ca.tweetzy.funds.guis;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.guis.template.ConfirmGUI;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.gui.helper.InventoryBorder;
import ca.tweetzy.rose.gui.template.PagedGUI;
import ca.tweetzy.rose.utils.QuickItem;
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

	public AccountListGUI(Gui parent) {
		super(parent, Translation.GUI_ACCOUNT_LIST_TITLE.getString(), 6, Funds.getAccountManager().getAccounts());
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Account account) {
		final OfflinePlayer player = Bukkit.getOfflinePlayer(account.getOwner());
		return QuickItem.of(player)
				.name(Translation.GUI_ACCOUNT_LIST_ITEMS_ACCOUNT_NAME.getString("account_name", player.getName()))
				.lore(Translation.GUI_ACCOUNT_LIST_ITEMS_ACCOUNT_LORE.getList())
				.make();
	}

	@Override
	protected void drawAdditional() {
		// wipe account currencies
		setButton(5, 7, QuickItem.of(CompMaterial.LAVA_BUCKET)
				.name(Translation.GUI_ACCOUNT_LIST_ITEMS_RESET_NAME.getString())
				.lore(Translation.GUI_ACCOUNT_LIST_ITEMS_RESET_LORE.getList())
				.make(), click -> click.manager.showGUI(click.player, new ConfirmGUI(null, confirmed -> {

			if (confirmed) {
				Funds.getAccountManager().resetPlayerAccountsBalances();
			}

			click.manager.showGUI(click.player, new AccountListGUI(new AdminMainGUI()));
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
