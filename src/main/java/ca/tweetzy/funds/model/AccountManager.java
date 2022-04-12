package ca.tweetzy.funds.model;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.rose.utils.Common;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * Date Created: April 11 2022
 * Time Created: 10:44 p.m.
 *
 * @author Kiran Hart
 */
public final class AccountManager {

	private final List<Account> accounts = Collections.synchronizedList(new ArrayList<>());

	public void addAccount(@NonNull final Account account) {
		synchronized (this.accounts) {
			if (this.accounts.contains(account)) return;
			this.accounts.add(account);
		}
	}

	public void removeAccount(@NonNull final UUID id) {
		synchronized (this.accounts) {
			this.accounts.removeIf(account -> account.getOwner().equals(id));
		}
	}

	public Account getAccount(@NonNull final OfflinePlayer player) {
		return getAccount(player.getUniqueId());
	}

	public Account getAccount(@NonNull final UUID id) {
		synchronized (this.accounts) {
			return this.accounts.stream().filter(account -> account.getOwner().equals(id)).findFirst().orElse(null);
		}
	}

	public List<Account> getAccounts() {
		synchronized (this.accounts) {
			return Collections.unmodifiableList(this.accounts);
		}
	}

	/*
	======================== DATA MODIFICATION ========================
	 */

	public void createAccount(@NonNull final Account currency, final BiConsumer<Boolean, Account> consumer) {
		Funds.getDataManager().createAccount(currency, (error, created) -> {
			if (error == null)
				this.addAccount(created);

			if (consumer != null)
				consumer.accept(error == null, created);
		});
	}

	/*
	======================== END ========================
	 */

	public void loadAccounts() {
		this.accounts.clear();

		Funds.getDataManager().getAccounts((error, found) -> {
			if (error != null) {

				return;
			}

			found.forEach(this::addAccount);
			Common.log(String.format("&aLoaded &f%d &auser accounts", this.accounts.size()));
		});
	}
}
