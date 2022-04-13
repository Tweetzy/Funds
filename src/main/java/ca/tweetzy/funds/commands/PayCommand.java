package ca.tweetzy.funds.commands;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.rose.command.AllowedExecutor;
import ca.tweetzy.rose.command.Command;
import ca.tweetzy.rose.command.ReturnType;
import ca.tweetzy.rose.utils.Common;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Date Created: April 13 2022
 * Time Created: 3:21 p.m.
 *
 * @author Kiran Hart
 */
public final class PayCommand extends Command {

	public PayCommand() {
		super(AllowedExecutor.BOTH, "pay");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		if (sender instanceof final Player player) {

			return ReturnType.SUCCESS;
		}

		// is from console so infinite money
		// also run this async since looking up the player by name may make an api call
		Common.runAsync(() -> {
			final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
			if (!offlinePlayer.hasPlayedBefore()) return;

			final Account account = Funds.getAccountManager().getAccount(offlinePlayer);
			if (account == null) return;


		});

		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "funds.cmd.pay";
	}

	@Override
	public String getSyntax() {
		return "pay <player> <amount> [currency]";
	}

	@Override
	public String getDescription() {
		return "Pay a user from your balance.";
	}
}
