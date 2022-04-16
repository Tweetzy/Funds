package ca.tweetzy.funds.guis;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.guis.template.CurrencyPicker;
import ca.tweetzy.funds.guis.template.PagedGUI;
import ca.tweetzy.funds.model.Helper;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.gui.helper.InventoryBorder;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.QuickItem;
import ca.tweetzy.rose.utils.input.TitleInput;
import lombok.NonNull;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date Created: April 15 2022
 * Time Created: 1:36 p.m.
 *
 * @author Kiran Hart
 */
public final class AccountViewGUI extends PagedGUI<Currency> {

	private final Account account;

	public AccountViewGUI(@NonNull final Account account) {
		super(new AccountListGUI(new AdminMainGUI()), String.format("&eFunds &8> &7User &8> &7%s", account.getName()), 6, new ArrayList<>(account.getCurrencies().keySet()));
		this.account = account;
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Currency currency) {
		return QuickItem.of(currency.getIcon())
				.name(currency.getName())
				.lore(Helper.replaceVariables(Arrays.asList(
								"&7Identifier&F: &e" + currency.getId(),
								"&7Description&F:",
								"&f- " + currency.getDescription(),
								"",
								"&7Current Balance&F: &e%currency_balance%",
								"",
								"&e&lLeft Click &8» &7To set balance",
								"&b&lRight Click &8» &7To add balance",
								"&c&lPress 1 &8» &7To reset to &c%currency_starting_balance%"
						), "currency_balance", this.account.getCurrencies().get(currency),
						"currency_starting_balance", currency.getStartingBalance()
				))
				.make();
	}

	@Override
	protected void drawAdditional() {

		// blacklist balance top
		setButton(5,8, QuickItem.of(account.isBalTopBlocked() ? CompMaterial.RED_DYE : CompMaterial.LIME_DYE)
				.name("&e&lBalance Top Blocked")
				.lore(Helper.replaceVariables(Arrays.asList(
						"&8Blocks user from balance top",
						"&7If true, this player will be completed blacklisted",
						"&7from the balance top lists for each currency.",
						"",
						"&7Current&f: %account_balance_top_blocked%",
						"",
						"&e&lClick &8» &7To toggle state"
				), "account_balance_top_blocked", account.isBalTopBlocked() ? "&cBlocked" : "&aNot Blocked"))
				.make(), click -> {

			account.setBalTopBlocked(!account.isBalTopBlocked());
			account.sync(true);
			draw();
		});

		// add currency player doesn't have
		if (this.account.getCurrencies().size() != Funds.getCurrencyManager().getCurrencies().size())
			setButton(5, 4, QuickItem.of(CompMaterial.SLIME_BALL)
					.name("&a&lDeposit Currency")
					.lore(
							"&8Add another currency balance",
							"&7Click to add another available currency",
							"&7to this player's balance list.",
							"",
							"&e&lClick &8» &7To deposit currency"
					).make(), click -> click.manager.showGUI(click.player, new CurrencyPicker(this.account, null, (e, selected) -> {

				new TitleInput(e.player, Common.colorize("&eCurrency Deposit"), Common.colorize("&fEnter deposit amount for currency")) {

					@Override
					public void onExit(Player player) {
						e.manager.showGUI(player, AccountViewGUI.this);
					}

					@Override
					public boolean onResult(String string) {
						if (!NumberUtils.isNumber(string)) {
							Common.tell(e.player, "&4%value% &cis not a valid number!".replace("%value%", string));
							return false;
						}

						final double value = Double.parseDouble(ChatColor.stripColor(string));

						account.depositCurrency(selected, value);
						account.sync(true);
						e.manager.showGUI(e.player, new AccountViewGUI(account));
						return true;
					}
				};
			})));


		// reset all balances
		setButton(5, 7, QuickItem.of(CompMaterial.LAVA_BUCKET)
				.name("&c&lReset Balances")
				.lore(
						"&8Reset player balances",
						"&7By clicking this you will reset every single",
						"&7currency balance from this user's account",
						"",
						"&c&lClick &8» &7To reset balances"
				)
				.make(), click -> {

			this.account.resetCurrencies();
			draw();
		});
	}

	@Override
	protected void onClick(Currency currency, GuiClickEvent event) {
		if (event.clickType == ClickType.LEFT || event.clickType == ClickType.RIGHT) {
			new TitleInput(event.player, Common.colorize(event.clickType == ClickType.LEFT ? "&eSet Balance" : "&eAdd To Balance"), Common.colorize(event.clickType == ClickType.LEFT ? "&fEnter new balance total" : "&fEnter amount to add to balance")) {

				@Override
				public void onExit(Player player) {
					event.manager.showGUI(player, AccountViewGUI.this);
				}

				@Override
				public boolean onResult(String string) {
					if (!NumberUtils.isNumber(string)) {
						Common.tell(event.player, "&4%value% &cis not a valid number!".replace("%value%", string));
						return false;
					}

					final double value = Double.parseDouble(ChatColor.stripColor(string));

					if (event.clickType == ClickType.LEFT)
						account.setCurrency(currency, value);

					if (event.clickType == ClickType.RIGHT)
						account.depositCurrency(currency, value);

					account.sync(true);
					event.manager.showGUI(event.player, new AccountViewGUI(account));
					return true;
				}
			};
		}

		if (event.clickType == ClickType.NUMBER_KEY) {
			this.account.resetCurrencies(currency);
			this.account.sync(true);
			draw();
		}
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
