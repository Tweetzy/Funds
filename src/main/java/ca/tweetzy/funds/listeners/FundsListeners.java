package ca.tweetzy.funds.listeners;

import ca.tweetzy.funds.api.events.CurrencyTransferEvent;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.Replacer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Date Created: April 23 2022
 * Time Created: 1:25 p.m.
 *
 * @author Kiran Hart
 */
public final class FundsListeners implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void onCurrencyTransferEvent(final CurrencyTransferEvent event) {
		final Account payee = event.getPayee();
		final Account payer = event.getPayer();

		final OfflinePlayer payerUser = Bukkit.getOfflinePlayer(payer.getOwner());
		final OfflinePlayer payeeUser = Bukkit.getOfflinePlayer(payee.getOwner());

		if (payerUser.isOnline()) {
			assert payerUser.getPlayer() != null;
			Common.tell(payerUser.getPlayer(), Replacer.replaceVariables(Locale.getString(payer, Translation.MONEY_PAID.getKey()),
					"amount", event.getAmount(),
					"currency_auto_format", event.getAmount() > 1.0 ? event.getCurrency().getPluralFormat() : event.getCurrency().getSingularFormat(),
					"payee_name", payee.getName()
			));
		}

		if (payeeUser.isOnline()) {
			assert payeeUser.getPlayer() != null;
			Common.tell(payeeUser.getPlayer(), Replacer.replaceVariables(Locale.getString(payee, Translation.MONEY_RECEIVED.getKey()),
					"amount", event.getAmount(),
					"currency_auto_format", event.getAmount() > 1.0 ? event.getCurrency().getPluralFormat() : event.getCurrency().getSingularFormat(),
					"payer_name", payee.getName()
			));
		}
	}
}
