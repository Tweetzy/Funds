package ca.tweetzy.funds.api.events;

import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import lombok.Getter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Date Created: April 23 2022
 * Time Created: 1:52 p.m.
 *
 * @author Kiran Hart
 */
@Getter
public final class CurrencyWithdrawEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled;

	private final Account account;
	private final Currency currency;
	private final double amount;

	public CurrencyWithdrawEvent(boolean async, Account account, Currency currency, double amount) {
		super(async);
		this.account = account;
		this.currency = currency;
		this.amount = amount;
	}

	@NotNull
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
