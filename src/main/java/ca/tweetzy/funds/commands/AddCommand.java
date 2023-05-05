package ca.tweetzy.funds.commands;

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.settings.Translations;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Date Created: April 13 2022
 * Time Created: 3:21 p.m.
 *
 * @author Kiran Hart
 */
public final class AddCommand extends Command {

	public AddCommand() {
		super(AllowedExecutor.BOTH, "add");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		if (args.length < 2) {
			return ReturnType.INVALID_SYNTAX;
		}

		// also run this async since looking up the player by name may make an api call
		Bukkit.getScheduler().runTaskAsynchronously(Funds.getInstance(), () -> {
			final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
			if (!offlinePlayer.hasPlayedBefore()) {
				Common.tell(sender, "&fThat player has never played on this server before &f: &e" + args[0]);
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

			if (offlinePlayer.isOnline()) {
				assert offlinePlayer.getPlayer() != null;
				tell(offlinePlayer.getPlayer(), TranslationManager.string(Translations.MONEY_RECEIVED,
						"amount", amount,
						"currency_auto_format", amount > 1.0 ? currency.getPluralFormat() : currency.getSingularFormat(),
						"payer_name", sender.getName()
				));
			}
		});

		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		if (args.length == 1)
			return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> !name.equalsIgnoreCase(sender.getName())).collect(Collectors.toList());
		if (args.length == 3)
			return Funds.getCurrencyManager().getCurrencies().stream().map(Currency::getId).collect(Collectors.toList());
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "funds.cmd.add";
	}

	@Override
	public String getSyntax() {
		return "add <player> <amount> [currency]";
	}

	@Override
	public String getDescription() {
		return "Add currency to a player's bank";
	}
}
