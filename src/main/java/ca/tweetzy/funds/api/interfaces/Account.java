package ca.tweetzy.funds.api.interfaces;

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

	long getCreatedAt();

	String getCurrencyJson();

	void setBalTopBlocked(boolean blocked);

	void setName(String name);

	boolean transferCurrency(Account account, Currency currency, double amount);

	boolean withdrawCurrency(Currency currency, double amount);

	boolean depositCurrency(Currency currency, double amount);

	boolean setCurrency(Currency currency, double amount);

	boolean resetCurrencies(Currency... currencies);

	void deleteCurrency(Currency currency);
}
