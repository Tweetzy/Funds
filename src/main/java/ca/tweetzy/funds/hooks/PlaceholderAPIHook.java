package ca.tweetzy.funds.hooks;

import ca.tweetzy.funds.Funds;
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
	public @NotNull String getIdentifier() {
		return "funds";
	}

	@Override
	public @NotNull String getAuthor() {
		return "Kiran Hart";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0.0";
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
		if (params.equalsIgnoreCase("total_currencies"))
			return String.valueOf(Funds.getCurrencyManager().getCurrencies().size());

		if (params.equalsIgnoreCase("total_accounts"))
			return String.valueOf(Funds.getAccountManager().getAccounts().size());

		return null;
	}
}
