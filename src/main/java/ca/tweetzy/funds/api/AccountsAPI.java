package ca.tweetzy.funds.api;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.api.AccountAPI;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Date Created: April 13 2022
 * Time Created: 11:58 p.m.
 *
 * @author Kiran Hart
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountsAPI implements AccountAPI {

	@Getter
	private static final AccountsAPI instance = new AccountsAPI();

	@Override
	public void createAccount(@NonNull Account account, BiConsumer<Boolean, Account> consumer) {
		Funds.getAccountManager().createAccount(account, consumer);
	}

	@Override
	public void updateAccounts(@NonNull List<Account> accounts, Consumer<Boolean> consumer) {
		Funds.getAccountManager().updateAccounts(accounts, consumer);
	}

	@Override
	public void addAccount(@NonNull Account account) {
		Funds.getAccountManager().addAccount(account);
	}

	@Override
	public void removeAccount(@NonNull UUID uuid) {
		Funds.getAccountManager().removeAccount(uuid);
	}

	@Override
	public Account getAccount(@NonNull String name) {
		return Funds.getAccountManager().getAccount(name);
	}

	@Override
	public Account getAccount(@NonNull OfflinePlayer player) {
		return Funds.getAccountManager().getAccount(player);
	}

	@Override
	public Account getAccount(@NonNull UUID id) {
		return Funds.getAccountManager().getAccount(id);
	}

	@Override
	public List<Account> getAccounts() {
		return Funds.getAccountManager().getAccounts();
	}

	@Override
	public void resetPlayerAccountsBalances() {
		Funds.getAccountManager().resetPlayerAccountsBalances();
	}
}
