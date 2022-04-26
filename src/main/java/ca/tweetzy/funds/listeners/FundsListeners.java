package ca.tweetzy.funds.listeners;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.events.CurrencyDepositEvent;
import ca.tweetzy.funds.api.events.CurrencyTransferEvent;
import ca.tweetzy.funds.api.events.CurrencyWithdrawEvent;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.funds.settings.Settings;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.rose.comp.NBTEditor;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.Replacer;
import net.minecraft.world.item.Items;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * Date Created: April 23 2022
 * Time Created: 1:25 p.m.
 *
 * @author Kiran Hart
 */
public final class FundsListeners implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
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

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCurrencyWithdrawEvent(final CurrencyWithdrawEvent event) {

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCurrencyDepositEvent(final CurrencyDepositEvent event) {

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCurrencyRedeemEvent(final PlayerInteractEvent event) {
		if (event.getItem() == null) return;
		if (event.getHand() != EquipmentSlot.HAND) return;

		final ItemStack item = event.getItem();
		if (!NBTEditor.contains(item, "Funds:CurrencyID")) return;

		final Player player = event.getPlayer();
		final Account account = Funds.getAccountManager().getAccount(player);

		if (account == null) return;

		final String currencyId = NBTEditor.getString(item, "Funds:CurrencyID");
		final Currency currency = Funds.getCurrencyManager().getCurrency(currencyId);

		if (currency == null) return;
		final double currencyAmount = Double.parseDouble(NBTEditor.getString(item, "Funds:CurrencyAmount"));

		final CurrencyDepositEvent currencyDepositEvent = new CurrencyDepositEvent(false, account, currency, currencyAmount);
		Funds.getInstance().getServer().getPluginManager().callEvent(currencyDepositEvent);
		if (currencyDepositEvent.isCancelled()) return;

		account.depositCurrency(currency, currencyAmount);
		account.sync(true);

		if (player.getInventory().getItemInMainHand().getAmount() >= 2) {
			player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
		} else {
			player.getInventory().setItemInMainHand(CompMaterial.AIR.parseItem());
		}

		player.updateInventory();
		Common.tell(player, Translation.DEPOSIT.getString(account, "amount", currencyAmount, "currency_auto_format", currencyAmount > 1.0D ? currency.getPluralFormat() : currency.getSingularFormat()));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCurrencyPickup(final EntityPickupItemEvent event) {
		if (!Settings.AUTO_DEPOSIT_PICKED_UP_CURRENCY.getBoolean()) return;
		if (!(event.getEntity() instanceof final Player player)) return;

		final ItemStack itemStack = event.getItem().getItemStack();
		if (!NBTEditor.contains(itemStack, "Funds:CurrencyID")) return;

		final Account account = Funds.getAccountManager().getAccount(player);
		if (account == null) return;

		final String currencyId = NBTEditor.getString(itemStack, "Funds:CurrencyID");
		final Currency currency = Funds.getCurrencyManager().getCurrency(currencyId);

		if (currency == null) return;
		final double currencyAmount = Double.parseDouble(NBTEditor.getString(itemStack, "Funds:CurrencyAmount"));

		final CurrencyDepositEvent currencyDepositEvent = new CurrencyDepositEvent(false, account, currency, currencyAmount);
		Funds.getInstance().getServer().getPluginManager().callEvent(currencyDepositEvent);
		if (currencyDepositEvent.isCancelled()) return;

		event.setCancelled(true);
		event.getItem().remove();

		account.depositCurrency(currency, currencyAmount);
		account.sync(true);

		Common.tell(player, Translation.DEPOSIT.getString(account, "amount", currencyAmount, "currency_auto_format", currencyAmount > 1.0D ? currency.getPluralFormat() : currency.getSingularFormat()));

	}
}
