package ca.tweetzy.funds.api.interfaces;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

/**
 * Date Created: April 08 2022
 * Time Created: 11:07 p.m.
 *
 * @author Kiran Hart
 */
public interface Account extends DatabaseSynchronize {

	UUID getOwner();

	String getName();

	Map<Currency, Double> getCurrencies();

	boolean isBalTopBlocked();

	Language getPreferredLanguage();

	long getCreatedAt();

	String getCurrencyJson();

	void setBalTopBlocked(boolean blocked);

	void setPreferredLanguage(Language language);

	void setName(String name);

	boolean transferCurrency(Account account, Currency currency, double amount, boolean asyncEvent);

	boolean withdrawCurrency(Currency currency, double amount);

	boolean depositCurrency(Currency currency, double amount);

	boolean setCurrency(Currency currency, double amount);

	boolean resetCurrencies(Currency... currencies);

	void deleteCurrency(Currency currency);

	void initiateTransfer(Player player, Currency currency);
}
