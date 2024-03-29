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
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

			if (args.length == 0) {
				payerAccount.initiateTransfer(player, null);
				return ReturnType.SUCCESS;
			}

			final Player targetPlayer = Bukkit.getPlayerExact(args[0]);

			// check to see if lookup found anything
			if (targetPlayer == null) {
				Common.tell(player, TranslationManager.string(Translations.PLAYER_NOT_FOUND, "player", args[0]));
				return ReturnType.FAIL;
			}

			if (targetPlayer.getUniqueId().equals(player.getUniqueId())) return ReturnType.FAIL;

			final Account targetAccount = Funds.getAccountManager().getAccount(targetPlayer);
			// check if the target user even has an account
			if (targetAccount == null) {
				Common.tell(player, TranslationManager.string(Translations.PLAYER_DOES_NOT_HAVE_ACCOUNT, "player", args[0]));
				return ReturnType.FAIL;
			}

			if (args.length < 2) {
				return ReturnType.INVALID_SYNTAX;
			}

			// check is arg 1 (transfer amt) is actually a number
			if (!NumberUtils.isNumber(args[1])) {
				// tell them to learn what a number is
				Common.tell(player, TranslationManager.string(Translations.NOT_A_NUMBER, "value", args[1]));
				return ReturnType.FAIL;
			}

			final double transferAmount = Double.parseDouble(args[1]);

			Currency currency = Funds.getCurrencyManager().getVaultOrFirst();

			if (args.length == 3 && args[2] != null && Funds.getCurrencyManager().getCurrency(args[2]) != null)
				currency = Funds.getCurrencyManager().getCurrency(args[2]);

			if (currency == null) {
				Common.tell(player, TranslationManager.string(Translations.NO_CURRENCY_SET));
				return ReturnType.FAIL;
			}

			if (!currency.isPayingAllowed()) return ReturnType.FAIL;

			if (!payerAccount.getCurrencies().containsKey(currency)) {
				Common.tell(player, TranslationManager.string(Translations.DOES_NOT_OWN_CURRENCY, "currency_plural_format", currency.getPluralFormat()));
				return ReturnType.FAIL;
			}

			// does the player even have enough money
			// todo add method to interface
			if (payerAccount.getCurrencies().get(currency) < transferAmount) {
				Common.tell(player, TranslationManager.string(Translations.NOT_ENOUGH_MONEY, "currency_plural_format", currency.getPluralFormat()));
				return ReturnType.FAIL;
			}

			// finally, transfer money
			payerAccount.transferCurrency(targetAccount, currency, transferAmount, false);
			// todo determine whether I should handle this within the Account#transferCurrency method
			Funds.getAccountManager().updateAccounts(Arrays.asList(payerAccount, targetAccount), null);
			return ReturnType.SUCCESS;
		}

		// is from console so infinite money
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
				Common.tell(offlinePlayer.getPlayer(), TranslationManager.string(Translations.MONEY_RECEIVED,
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
