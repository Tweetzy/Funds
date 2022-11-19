package ca.tweetzy.funds.model;

import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.impl.TopBalanceRecord;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

	public Account getAccount(@NonNull final String name) {
		return CompletableFuture.supplyAsync(() -> {
			final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
			return getAccount(offlinePlayer);
		}).join();
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

	public List<TopBalanceRecord> getHighestBalances(@NonNull Currency currency) {
		synchronized (this.accounts) {
			final List<TopBalanceRecord> map = new ArrayList<>();

			this.accounts.forEach(account -> {
				if (!account.getCurrencies().containsKey(currency)) return;
				map.add(new TopBalanceRecord(account, account.getCurrencies().get(currency)));
			});

			return map.stream().sorted(Comparator.comparing(TopBalanceRecord::amount).reversed()).collect(Collectors.toList());
		}
	}

	public void resetPlayerAccountsBalances() {
		Bukkit.getServer().getScheduler().runTaskAsynchronously(Funds.getInstance(), () -> {
			synchronized (this.accounts) {
				this.accounts.forEach(Account::resetCurrencies);
				updateAccounts(this.accounts, null);
			}
		});
	}

	/*
	======================== DATA MODIFICATION ========================
	 */

	public void createAccount(@NonNull final Account account, final BiConsumer<Boolean, Account> consumer) {
		Funds.getDataManager().createAccount(account, (error, created) -> {
			if (error == null)
				this.addAccount(created);

			if (consumer != null)
				consumer.accept(error == null, created);
		});
	}

	public void updateAccounts(@NonNull final List<Account> accounts, final Consumer<Boolean> consumer) {
		Funds.getDataManager().updateAccounts(accounts, (error, success) -> {
			if (consumer != null)
				consumer.accept(success);
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
