package ca.tweetzy.funds.commands;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.guis.player.BalanceGUI;
import ca.tweetzy.rose.command.AllowedExecutor;
import ca.tweetzy.rose.command.Command;
import ca.tweetzy.rose.command.ReturnType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Date Created: April 12 2022
 * Time Created: 10:42 p.m.
 *
 * @author Kiran Hart
 */
public final class BalanceCommand extends Command {

	public BalanceCommand() {
		super(AllowedExecutor.PLAYER, "balance");

	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		if (sender instanceof final Player player) {
			if (args.length == 0) {
				final Account account = Funds.getAccountManager().getAccount(player);

				// for whatever reason if the payer account is not found, stop entirely
				if (account == null) return ReturnType.FAIL;

				Funds.getGuiManager().showGUI(player, new BalanceGUI(null, account));
				return ReturnType.SUCCESS;
			}

			return ReturnType.SUCCESS;
		}

		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "funds.cmd.balance";
	}

	@Override
	public String getSyntax() {
		return "";
	}

	@Override
	public String getDescription() {
		return "Check your balance";
	}
}
