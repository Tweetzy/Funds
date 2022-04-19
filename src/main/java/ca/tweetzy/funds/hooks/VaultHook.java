package ca.tweetzy.funds.hooks;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.impl.FundAccount;
import ca.tweetzy.rose.utils.Common;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.ServicePriority;

import java.util.Collections;
import java.util.List;

/**
 * Date Created: April 08 2022
 * Time Created: 7:12 p.m.
 *
 * @author Kiran Hart
 */
public final class VaultHook implements Economy {

	public VaultHook() {
		Bukkit.getServer().getServicesManager().register(Economy.class, this, Funds.getInstance(), ServicePriority.Highest);
		Common.log("&aHooked into Vault");
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getName() {
		return "Funds";
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public int fractionalDigits() {
		return -1;
	}

	@Override
	public String format(double amount) {
		return String.format("%s %s", String.format("%,.2f", amount), amount == 1D ? Funds.getCurrencyManager().getVaultCurrency().getSingularFormat() : Funds.getCurrencyManager().getVaultCurrency().getPluralFormat());
	}

	@Override
	public String currencyNamePlural() {
		return Funds.getCurrencyManager().getVaultCurrency().getPluralFormat();
	}

	@Override
	public String currencyNameSingular() {
		return Funds.getCurrencyManager().getVaultCurrency().getSingularFormat();
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		return Funds.getAccountManager().getAccount(player) != null;
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String worldName) {
		return hasAccount(player);
	}

	@Override
	public boolean hasAccount(String playerName) {
		return Funds.getAccountManager().getAccount(playerName) != null;
	}

	@Override
	public boolean hasAccount(String playerName, String worldName) {
		return hasAccount(playerName);
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		final Account account = Funds.getAccountManager().getAccount(player);
		final Currency currency = Funds.getCurrencyManager().getVaultCurrency();
		return account != null && currency != null ? account.getCurrencies().get(currency) : 0D;
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) {
		return getBalance(player);
	}

	@Override
	public double getBalance(String playerName) {
		final Account account = Funds.getAccountManager().getAccount(playerName);
		final Currency currency = Funds.getCurrencyManager().getVaultCurrency();
		return account != null && currency != null ? account.getCurrencies().get(currency) : 0D;
	}

	@Override
	public double getBalance(String playerName, String world) {
		return getBalance(playerName);
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		return getBalance(player) >= amount;
	}

	@Override
	public boolean has(OfflinePlayer player, String worldName, double amount) {
		return getBalance(player) >= amount;
	}

	@Override
	public boolean has(String playerName, double amount) {
		return getBalance(playerName) >= amount;
	}

	@Override
	public boolean has(String playerName, String worldName, double amount) {
		return getBalance(playerName) >= amount;
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		final Account account = Funds.getAccountManager().getAccount(player);
		final Currency currency = Funds.getCurrencyManager().getVaultCurrency();

		if (account == null || currency == null)
			return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Funds vault currency not set or user account not found!");

		if (account.getCurrencies().get(currency) < amount) {
			return new EconomyResponse(0D, account.getCurrencies().get(currency), EconomyResponse.ResponseType.FAILURE, null);
		}

		final double currentBalance = account.getCurrencies().get(currency);
		account.getCurrencies().put(currency, currentBalance - amount);
		account.sync(true);

		return new EconomyResponse(amount, account.getCurrencies().get(currency), EconomyResponse.ResponseType.SUCCESS, null);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
		return withdrawPlayer(player, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
		return null;
	}


	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		final Account account = Funds.getAccountManager().getAccount(player);
		final Currency currency = Funds.getCurrencyManager().getVaultCurrency();

		if (account == null || currency == null)
			return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Funds vault currency not set or user account not found!");


		final double currentBalance = account.getCurrencies().get(currency);
		account.getCurrencies().put(currency, currentBalance + amount);
		account.sync(true);

		return new EconomyResponse(amount, account.getCurrencies().get(currency), EconomyResponse.ResponseType.SUCCESS, null);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
		return depositPlayer(player, amount);
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount) {
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
		return null;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		Funds.getAccountManager().createAccount(new FundAccount(player), (created, account) -> {
			if (created)
				Common.log(String.format("&fCreated user account for player &e%s", player.getName()));
		});

		return true;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
		return createPlayerAccount(player);
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		return false;
	}

	@Override
	public boolean createPlayerAccount(String playerName) {
		return false;
	}

	/*
	=========================== BANK SHIT ===========================
	 */

	@Override
	public EconomyResponse createBank(String name, String player) {
		return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not currently supported by Funds");
	}

	@Override
	public EconomyResponse createBank(String name, OfflinePlayer player) {
		return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not currently supported by Funds");

	}

	@Override
	public EconomyResponse deleteBank(String name) {
		return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not currently supported by Funds");
	}

	@Override
	public EconomyResponse bankBalance(String name) {
		return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not currently supported by Funds");
	}

	@Override
	public EconomyResponse bankHas(String name, double amount) {
		return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not currently supported by Funds");
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not currently supported by Funds");
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not currently supported by Funds");
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not currently supported by Funds");
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
		return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not currently supported by Funds");
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not currently supported by Funds");
	}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) {
		return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not currently supported by Funds");
	}

	@Override
	public List<String> getBanks() {
		return Collections.emptyList();
	}
}
