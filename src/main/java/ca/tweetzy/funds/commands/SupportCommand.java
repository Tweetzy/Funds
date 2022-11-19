package ca.tweetzy.funds.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.flight.utils.ChatUtil;
import ca.tweetzy.flight.utils.Common;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Date Created: April 15 2022
 * Time Created: 1:25 p.m.
 *
 * @author Kiran Hart
 */
public final class SupportCommand extends Command {

	public SupportCommand() {
		super(AllowedExecutor.PLAYER, "support");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		Common.tellNoPrefix(sender,
				"&8&m-----------------------------------------------------",
				"",
				ChatUtil.centerMessage("&E&lFunds Support"),
				ChatUtil.centerMessage("&bhttps://discord.tweetzy.ca"),
				"&8&m-----------------------------------------------------"
		);
		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "funds.cmd.support";
	}

	@Override
	public String getSyntax() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}
}
