package ca.tweetzy.funds.hooks;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Date Created: April 08 2022
 * Time Created: 7:12 p.m.
 *
 * @author Kiran Hart
 */
public final class PlaceholderAPIHook extends PlaceholderExpansion {

	@Override
	public @NotNull
	String getIdentifier() {
		return "funds";
	}

	@Override
	public @NotNull
	String getAuthor() {
		return "Kiran Hart";
	}

	@Override
	public @NotNull
	String getVersion() {
		return "1.0.0";
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public @Nullable
	String onRequest(OfflinePlayer player, @NotNull String params) {
		if (params.equalsIgnoreCase("total_currencies"))
			return String.valueOf(Funds.getCurrencyManager().getCurrencies().size());

		if (params.equalsIgnoreCase("total_accounts"))
			return String.valueOf(Funds.getAccountManager().getAccounts().size());

		final String[] paramSplit = params.split("_");
		if (paramSplit.length >= 2) {
			final Currency currency = Funds.getCurrencyManager().getCurrency(paramSplit[0]);
			if (currency == null) return "0";

			final Account account = Funds.getAccountManager().getAccount(player);
			if (account == null) return "0";

			return switch (paramSplit[1].toLowerCase()) {
				case "pluralname" -> currency.getPluralFormat();
				case "singlename" -> currency.getSingularFormat();
				case "balance" -> String.valueOf(account.getCurrencies().get(currency));
				default -> null;
			};
		}

		return null;
	}
}
