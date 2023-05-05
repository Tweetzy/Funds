package ca.tweetzy.funds.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.guis.admin.AdminMainGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Date Created: April 10 2022
 * Time Created: 12:38 a.m.
 *
 * @author Kiran Hart
 */
public final class FundsCommand extends Command {

	public FundsCommand() {
		super(AllowedExecutor.PLAYER, "funds");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		final Player player = (Player) sender;

		final Account account = Funds.getAccountManager().getAccount(player);

		Funds.getGuiManager().showGUI(player, new AdminMainGUI(account));
		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "funds.admin";
	}

	@Override
	public String getSyntax() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Brings up the admin menu";
	}
}
