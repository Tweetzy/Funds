package ca.tweetzy.funds.listeners;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.impl.FundAccount;
import ca.tweetzy.rose.utils.Common;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Date Created: April 11 2022
 * Time Created: 11:03 p.m.
 *
 * @author Kiran Hart
 */
public final class AccessListeners implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(final PlayerJoinEvent event) {
		// create user account if not exists
		final Player player = event.getPlayer();

		final Account playerAccount = Funds.getAccountManager().getAccount(player);

		if (playerAccount == null) {
			Funds.getAccountManager().createAccount(new FundAccount(player.getUniqueId()), (created, account) -> {
				if (created)
					Common.log(String.format("&fCreated user account for player &e%s", player.getName()));
			});

			return;
		}

		// check and update player name if diff
		if (!playerAccount.getName().equalsIgnoreCase(player.getName())) {
			Common.log(String.format("&fUser &e%s &fhas changed their name to &e%s&f, updating account info.", playerAccount.getName(), player.getName()));
			playerAccount.setName(player.getName());
			playerAccount.sync(false);
		}
	}
}
