package ca.tweetzy.funds.commands;

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
		final Player player = (Player) sender;

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
		return null;
	}

	@Override
	public String getDescription() {
		return "Check your balance";
	}
}
