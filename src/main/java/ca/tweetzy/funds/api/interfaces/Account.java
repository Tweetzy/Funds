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

	Map<Currency, Double> getCurrencies();

	boolean isBalTopBlocked();

	long getCreatedAt();

	void setBalTopBlocked(boolean blocked);

	String getCurrencyJson();
}
