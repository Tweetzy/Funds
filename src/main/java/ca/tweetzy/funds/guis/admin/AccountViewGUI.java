package ca.tweetzy.funds.guis.admin;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.guis.template.ConfirmGUI;
import ca.tweetzy.funds.guis.template.CurrencyPicker;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.gui.helper.InventoryBorder;
import ca.tweetzy.rose.gui.template.PagedGUI;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.QuickItem;
import ca.tweetzy.rose.utils.Replacer;
import ca.tweetzy.rose.utils.input.TitleInput;
import lombok.NonNull;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
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
		super(new AccountListGUI(new AdminMainGUI(account), account), Translation.GUI_ACCOUNT_VIEW_TITLE.getString(account, "account_name", account.getName()), 6, new ArrayList<>(account.getCurrencies().keySet()));
		this.account = account;
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Currency currency) {
		return QuickItem.of(currency.getIcon())
				.name(Translation.GUI_ACCOUNT_VIEW_ITEMS_CURRENCY_NAME.getString(this.account, "currency_name", currency.getName()))
				.lore(Translation.GUI_ACCOUNT_VIEW_ITEMS_CURRENCY_LORE.getList(this.account,
						"currency_balance", this.account.getCurrencies().get(currency),
						"currency_description", currency.getDescription(),
						"currency_starting_balance", currency.getStartingBalance(),
						"currency_id", currency.getId()
				))
				.make();
	}

	@Override
	protected void drawAdditional() {

		// blacklist balance top
		setButton(5, 8, QuickItem.of(account.isBalTopBlocked() ? CompMaterial.RED_DYE : CompMaterial.LIME_DYE)
				.name(Translation.GUI_ACCOUNT_VIEW_ITEMS_BALTOP_BLACKLIST_NAME.getString(this.account))
				.lore(Translation.GUI_ACCOUNT_VIEW_ITEMS_BALTOP_BLACKLIST_LORE.getList(this.account,
						"is_true", account.isBalTopBlocked() ? Translation.MISC_IS_TRUE.getString(this.account) : Translation.MISC_IS_FALSE.getString(this.account)
				))
				.make(), click -> {

			account.setBalTopBlocked(!account.isBalTopBlocked());
			account.sync(true);
			draw();
		});

		// add currency player doesn't have
		if (this.account.getCurrencies().size() != Funds.getCurrencyManager().getCurrencies().size())
			setButton(5, 4, QuickItem.of(CompMaterial.SLIME_BALL)
					.name(Translation.GUI_ACCOUNT_VIEW_ITEMS_DEPOSIT_NAME.getString(this.account))
					.lore(Translation.GUI_ACCOUNT_VIEW_ITEMS_DEPOSIT_LORE.getList(this.account))
					.make(), click -> click.manager.showGUI(click.player, new CurrencyPicker(this.account, null, false, (e, selected) -> {

				new TitleInput(e.player, Common.colorize(Translation.CURRENCY_DEPOSIT_TITLE.getString(this.account)), Common.colorize(Translation.CURRENCY_DEPOSIT_SUBTITLE.getString(this.account))) {

					@Override
					public void onExit(Player player) {
						e.manager.showGUI(player, AccountViewGUI.this);
					}

					@Override
					public boolean onResult(String string) {
						if (!NumberUtils.isNumber(string)) {
							Common.tell(click.player, Replacer.replaceVariables(Locale.getString(account, Translation.NOT_A_NUMBER.getKey()), "value", string));
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
				.name(Translation.GUI_ACCOUNT_VIEW_ITEMS_RESET_NAME.getString(this.account))
				.lore(Translation.GUI_ACCOUNT_VIEW_ITEMS_RESET_LORE.getList(this.account))
				.make(), click -> click.manager.showGUI(click.player, new ConfirmGUI(null, this.account, confirmed -> {

			if (confirmed) {
				this.account.resetCurrencies();
			}

			click.manager.showGUI(click.player, new AccountViewGUI(this.account));
		})));
	}

	@Override
	protected void onClick(Currency currency, GuiClickEvent event) {
		if (event.clickType == ClickType.LEFT || event.clickType == ClickType.RIGHT) {
			new TitleInput(event.player, Common.colorize(event.clickType == ClickType.LEFT ? Translation.CURRENCY_SET_BAL_TITLE.getString(this.account) : Translation.CURRENCY_ADD_BAL_TITLE.getString(this.account)), Common.colorize(event.clickType == ClickType.LEFT ? Translation.CURRENCY_SET_BAL_SUBTITLE.getString(this.account) : Translation.CURRENCY_ADD_BAL_SUBTITLE.getString(this.account))) {

				@Override
				public void onExit(Player player) {
					event.manager.showGUI(player, AccountViewGUI.this);
				}

				@Override
				public boolean onResult(String string) {
					if (!NumberUtils.isNumber(string)) {
						Common.tell(event.player, Replacer.replaceVariables(Locale.getString(account, Translation.NOT_A_NUMBER.getKey()), "value", string));
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
			event.manager.showGUI(event.player, new ConfirmGUI(null, this.account, confirmed -> {

				if (confirmed) {
					this.account.resetCurrencies(currency);
					this.account.sync(true);
				}

				event.manager.showGUI(event.player, new AccountViewGUI(this.account));
			}));
		}
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
