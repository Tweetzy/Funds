package ca.tweetzy.funds.commands;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.guis.player.AccountPickerGUI;
import ca.tweetzy.funds.guis.template.CurrencyPicker;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.rose.command.AllowedExecutor;
import ca.tweetzy.rose.command.Command;
import ca.tweetzy.rose.command.ReturnType;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.Replacer;
import ca.tweetzy.rose.utils.input.TitleInput;
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
				Funds.getGuiManager().showGUI(player, new AccountPickerGUI(payerAccount, (click, selectedAccount) -> click.manager.showGUI(click.player, new CurrencyPicker(payerAccount, null, true, (event, currency) -> {
					double currencyTotal = payerAccount.getCurrencies().get(currency);

					new TitleInput(player, Common.colorize(Translation.SEND_CURRENCY_AMT_TITLE.getString(payerAccount)), Common.colorize(Translation.SEND_CURRENCY_AMT_SUBTITLE.getString(payerAccount)), Common.colorize(
							String.format("&e%s %s", String.format("%,.2f", currencyTotal), currencyTotal > 1.0D ? currency.getPluralFormat() : currency.getSingularFormat())
					)) {

						@Override
						public boolean onResult(String string) {
							if (!NumberUtils.isNumber(string)) {
								// tell them to learn what a number is
								Common.tell(player, Replacer.replaceVariables(Locale.getString(Translation.NOT_A_NUMBER.getKey()), "value", string));
								return false;
							}

							final double transferAmount = Double.parseDouble(string);

							// does the player even have enough money
							// todo add method to interface
							if (payerAccount.getCurrencies().get(currency) < transferAmount) {
								Common.tell(player, Replacer.replaceVariables(Locale.getString(Translation.NOT_ENOUGH_MONEY.getKey()), "currency_plural_format", currency.getPluralFormat()));
								return false;
							}

							payerAccount.transferCurrency(selectedAccount, currency, transferAmount);
							// todo determine whether I should handle this within the Account#transferCurrency method
							Funds.getAccountManager().updateAccounts(Arrays.asList(payerAccount, selectedAccount), null);
							return true;
						}
					};
				}))));

				return ReturnType.FAIL;
			}

			final Player targetPlayer = Bukkit.getPlayerExact(args[0]);

			// check to see if lookup found anything
			if (targetPlayer == null) {
				Common.tell(player, Replacer.replaceVariables(Locale.getString(Translation.PLAYER_NOT_FOUND.getKey()), "player", args[0]));
				return ReturnType.FAIL;
			}

			final Account targetAccount = Funds.getAccountManager().getAccount(targetPlayer);
			// check if the target user even has an account
			if (targetAccount == null) {
				Common.tell(player, Replacer.replaceVariables(Locale.getString(Translation.PLAYER_DOES_NOT_HAVE_ACCOUNT.getKey()), "player", args[0]));
				return ReturnType.FAIL;
			}

			// check is arg 1 (transfer amt) is actually a number
			if (!NumberUtils.isNumber(args[1])) {
				// tell them to learn what a number is
				Common.tell(player, Replacer.replaceVariables(Locale.getString(Translation.NOT_A_NUMBER.getKey()), "value", args[1]));
				return ReturnType.FAIL;
			}

			final double transferAmount = Double.parseDouble(args[1]);

			Currency currency = Funds.getCurrencyManager().getVaultOrFirst();

			if (args.length == 3 && args[2] != null && Funds.getCurrencyManager().getCurrency(args[2]) != null)
				currency = Funds.getCurrencyManager().getCurrency(args[2]);

			if (currency == null) {
				Common.tell(player, Locale.getString(Translation.NO_CURRENCY_SET.getKey()));
				return ReturnType.FAIL;
			}

			if (!payerAccount.getCurrencies().containsKey(currency)) {
				Common.tell(player, Replacer.replaceVariables(Locale.getString(Translation.DOES_NOT_OWN_CURRENCY.getKey()), "currency_plural_format", currency.getPluralFormat()));
				return ReturnType.FAIL;
			}

			// does the player even have enough money
			// todo add method to interface
			if (payerAccount.getCurrencies().get(currency) < transferAmount) {
				Common.tell(player, Replacer.replaceVariables(Locale.getString(Translation.NOT_ENOUGH_MONEY.getKey()), "currency_plural_format", currency.getPluralFormat()));
				return ReturnType.FAIL;
			}

			// finally, transfer money
			payerAccount.transferCurrency(targetAccount, currency, transferAmount);
			// todo determine whether I should handle this within the Account#transferCurrency method
			Funds.getAccountManager().updateAccounts(Arrays.asList(payerAccount, targetAccount), null);

			return ReturnType.SUCCESS;
		}

		// is from console so infinite money
		// also run this async since looking up the player by name may make an api call
		Common.runAsync(() -> {
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
