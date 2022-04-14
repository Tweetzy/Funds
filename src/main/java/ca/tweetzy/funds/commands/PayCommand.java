package ca.tweetzy.funds.commands;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.rose.command.AllowedExecutor;
import ca.tweetzy.rose.command.Command;
import ca.tweetzy.rose.command.ReturnType;
import ca.tweetzy.rose.utils.Common;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
			final Account payerAccount = Funds.getAccountManager().getAccount(player);

			// for whatever reason if the payer account is not found, stop entirely
			if (payerAccount == null) return ReturnType.FAIL;

			final AtomicReference<OfflinePlayer> targetPlayer = new AtomicReference<>(Bukkit.getPlayerExact(args[0]));
			if (targetPlayer.get() == null) {
				// try looking them up
				Common.runAsync(() -> targetPlayer.set(Bukkit.getOfflinePlayer(args[0])));
			}

			// check again to see if lookup found anything
			if (targetPlayer.get() == null) {

				return ReturnType.FAIL;
			}

			// get target account *hopefully*
			final Account targetAccount = Funds.getAccountManager().getAccount(targetPlayer.get());
			// check if the target user even has an account
			if (targetAccount == null) {

				return ReturnType.FAIL;
			}

			// check is arg 1 (transfer amt) is actually a number
			if (!NumberUtils.isNumber(args[1])) {
				// todo tell them to learn what a number is
				return ReturnType.FAIL;
			}


			return ReturnType.SUCCESS;
		}

		// is from console so infinite money
		// also run this async since looking up the player by name may make an api call
		Common.runAsync(() -> {
			final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
			if (!offlinePlayer.hasPlayedBefore()) {
				Common.tell(sender, "&fThat player has never played on this server before &f: &e" + args[0]);
				;
				return;
			}

			final Account account = Funds.getAccountManager().getAccount(offlinePlayer);
			if (account == null) {
				Common.tell(sender, "&fFunds does not have a registered account for user&f: &e" + args[0]);
				return;
			}

			final double amount = Double.parseDouble(args[1]);

			Currency currency;

			if (args.length == 3) {
				currency = Funds.getCurrencyManager().getCurrency(args[2]);
				if (currency == null) {
					Common.tell(sender, "&fFunds does not recognize the currency&f: &e" + args[2]);
					return;
				}
			} else {
				if (Funds.getCurrencyManager().getVaultCurrency() != null)
					currency = Funds.getCurrencyManager().getVaultCurrency();
				else {
					Common.tell(sender, "&fFunds does not have a default vault currency specified!");
					return;
				}
			}

			account.depositCurrency(currency, amount);
			account.sync(true);
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
