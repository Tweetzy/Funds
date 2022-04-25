package ca.tweetzy.funds.api;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.api.interfaces.FundAPI;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Date Created: April 13 2022
 * Time Created: 11:47 p.m.
 *
 * @author Kiran Hart
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FundsAPI implements FundAPI {

	@Getter
	private static final FundAPI instance = new FundsAPI();

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

	@Override
	public void addCurrency(@NonNull Currency currency) {
		Funds.getCurrencyManager().addCurrency(currency);
	}

	@Override
	public void removeCurrency(@NonNull Currency currency) {
		Funds.getCurrencyManager().removeCurrency(currency);
	}

	@Override
	public void removeCurrency(@NonNull String id) {
		Funds.getCurrencyManager().removeCurrency(id);
	}

	@Override
	public Currency getCurrency(@NonNull String id) {
		return Funds.getCurrencyManager().getCurrency(id);
	}

	@Override
	public void setVaultCurrency(@NonNull Currency currency) {
		Funds.getCurrencyManager().setVaultCurrency(currency);
	}

	@Override
	public Currency getVaultOrFirst() {
		return Funds.getCurrencyManager().getVaultOrFirst();
	}

	@Override
	public void createCurrency(@NonNull Currency currency, BiConsumer<Boolean, Currency> consumer) {
		Funds.getCurrencyManager().createCurrency(currency, consumer);
	}

	@Override
	public void deleteCurrency(@NonNull String id, Consumer<Boolean> wasDeleted) {
		Funds.getCurrencyManager().deleteCurrency(id, wasDeleted);
	}

	@Override
	public List<Currency> getCurrencies() {
		return Funds.getCurrencyManager().getCurrencies();
	}

	@Override
	public HashMap<Currency, Double> getDefaultValueMap() {
		return Funds.getCurrencyManager().getDefaultValueMap();
	}
}
