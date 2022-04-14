package ca.tweetzy.funds.guis;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.guis.template.PagedGUI;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.gui.helper.InventoryBorder;
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
		super(parent, "&eFunds &8> &7Account List", 6, Funds.getAccountManager().getAccounts());
	}

	@Override
	protected ItemStack makeDisplayItem(Account account) {
		final OfflinePlayer player = Bukkit.getOfflinePlayer(account.getOwner());
		return QuickItem.of(player)
				.name("&b&l" + player.getName())
				.lore(
						"&8Player account info",
						"&7View information about this player's",
						"&7account (ie, balances, transactions)",
						"",
						"&e&lLeft Click &8» &7To view account"
				)
				.make();
	}

	@Override
	protected void drawAdditional() {
		// wipe account currencies
		setButton(5, 7, QuickItem.of(CompMaterial.LAVA_BUCKET)
				.name("&c&lReset Accounts")
				.lore(
						"&8Reset all player accounts",
						"&7By clicking this you will reset every single",
						"&7user account account's currency balance.",
						"",
						"&c&lClick &8» &7To reset accounts"
				)
				.make(), click -> {

			Funds.getAccountManager().resetPlayerAccountsBalances();
			draw();
		});
	}

	@Override
	protected void onClick(Account object, GuiClickEvent clickEvent) {

	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
