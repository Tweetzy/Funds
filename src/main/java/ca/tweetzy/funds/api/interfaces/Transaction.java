package ca.tweetzy.funds.api.interfaces;

import ca.tweetzy.funds.api.TransactionType;

import java.util.UUID;

/**
 * Date Created: April 13 2022
 * Time Created: 3:34 p.m.
 *
 * @author Kiran Hart
 */
public interface Transaction {

	UUID getId();

	TransactionType getTransactionType();

	UUID getPayee();

	UUID getPayer();

	double getAmount();

	long getCompletionTime();
}
