package ca.tweetzy.funds.impl;

import ca.tweetzy.funds.api.interfaces.Account;

public record TopBalanceRecord(Account account, double amount) {
}
