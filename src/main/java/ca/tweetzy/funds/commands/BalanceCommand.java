package ca.tweetzy.funds.commands;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.guis.player.BalanceGUI;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.funds.settings.Settings;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
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
		final Account account = Funds.getAccountManager().getAccount(player);

		// for whatever reason if the payer account is not found, stop entirely
		if (account == null) return ReturnType.FAIL;

		if (Settings.USE_CHAT_BALANCE.getBoolean()) {

			Locale.tell(player, Translation.CURRENCY_BALANCE_CHAT_HEADER.getKey());

			account.getCurrencies().keySet().forEach(currency -> Translation.CURRENCY_BALANCE_CHAT_CURRENCY.getList(account,
					"currency_name", currency.getName(),
					"currency_balance", String.format("%,.2f", account.getCurrencies().getOrDefault(currency, 0D))
			).forEach(player::sendMessage));

			Locale.tell(player, Translation.CURRENCY_BALANCE_CHAT_FOOTER.getKey());
			return ReturnType.SUCCESS;
		}

		Funds.getGuiManager().showGUI(player, new BalanceGUI(null, account));
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
