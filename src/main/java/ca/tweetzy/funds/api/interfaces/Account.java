package ca.tweetzy.funds.api.interfaces;

import java.util.Map;
import java.util.UUID;

/**
 * Date Created: April 08 2022
 * Time Created: 11:07 p.m.
 *
 * @author Kiran Hart
 */
public interface Account {

	UUID getOwner();

	Map<Currency, Double>  getCurrencies();

	long getCreatedAt();
}
