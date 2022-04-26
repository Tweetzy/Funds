package ca.tweetzy.funds.guis.admin;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.gui.template.BaseGUI;
import ca.tweetzy.rose.gui.template.MaterialPickerGUI;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.QuickItem;
import ca.tweetzy.rose.utils.Replacer;
import ca.tweetzy.rose.utils.input.TitleInput;
import lombok.NonNull;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * Date Created: April 10 2022
 * Time Created: 11:18 p.m.
 *
 * @author Kiran Hart
 */
public final class CurrencyEditGUI extends BaseGUI {

	private final Gui parent;
	private final Currency currency;
	private final Account account;

	public CurrencyEditGUI(Gui parent, @NonNull final Account account, @NonNull final Currency currency) {
		super(parent, Translation.GUI_CURRENCY_EDIT_TITLE.getString(account, "currency_id", currency.getId()), 6);
		this.parent = parent;
		this.account = account;
		this.currency = currency;
		draw();
	}

	@Override
	protected void draw() {

		// icon selector
		setButton(1, 4, QuickItem.of(this.currency.getIcon())
				.name(Translation.GUI_CURRENCY_EDIT_ITEMS_ICON_NAME.getString(this.account))
				.lore(Translation.GUI_CURRENCY_EDIT_ITEMS_ICON_LORE.getList(this.account))
				.make(), click -> click.manager.showGUI(click.player, new MaterialPickerGUI(this, "&eFunds &8> &7" + this.currency.getId() + " &8> &7Select Icon", null, (e, selected) -> {

			this.currency.setIcon(selected);
			this.currency.sync(false);
			e.manager.showGUI(e.player, new CurrencyEditGUI(this.parent, this.account, this.currency));
		})));

		// formatting
		setButton(2, 1, QuickItem.of(CompMaterial.PAPER)
				.name(Translation.GUI_CURRENCY_EDIT_ITEMS_FORMATTING_NAME.getString(this.account))
				.lore(Translation.GUI_CURRENCY_EDIT_ITEMS_FORMATTING_LORE.getList(this.account,
						"currency_singular_format", currency.getSingularFormat(),
						"currency_plural_format", currency.getPluralFormat()
				))
				.make(), click -> new TitleInput(click.player, Common.colorize(Translation.CURRENCY_EDIT_FORMATTING_TITLE.getString(this.account)), Common.colorize(click.clickType == ClickType.LEFT ? Translation.CURRENCY_EDIT_FORMATTING_SUBTITLE_SINGULAR.getString(this.account) : Translation.CURRENCY_EDIT_FORMATTING_SUBTITLE_PLURAL.getString(this.account))) {

			@Override
			public boolean onResult(String string) {
				if (string.length() < 3) return false;

				if (click.clickType == ClickType.LEFT)
					CurrencyEditGUI.this.currency.setSingularFormat(string);
				if (click.clickType == ClickType.RIGHT)
					CurrencyEditGUI.this.currency.setPluralFormat(string);

				CurrencyEditGUI.this.currency.sync(false);
				click.manager.showGUI(click.player, new CurrencyEditGUI(CurrencyEditGUI.this.parent, account, CurrencyEditGUI.this.currency));
				return true;
			}

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, CurrencyEditGUI.this);
			}
		});

		// starting balance
		setButton(2, 2, QuickItem.of(CompMaterial.SUNFLOWER)
				.name(Translation.GUI_CURRENCY_EDIT_ITEMS_STARTING_BAL_NAME.getString(this.account))
				.lore(Translation.GUI_CURRENCY_EDIT_ITEMS_STARTING_BAL_LORE.getList(this.account,
						"currency_starting_balance", currency.getStartingBalance(),
						"currency_plural_format", currency.getPluralFormat()
				)).make(), click -> new TitleInput(click.player, Common.colorize(Translation.CURRENCY_EDIT_STARTING_BAL_TITLE.getString(this.account)), Common.colorize(Translation.CURRENCY_EDIT_STARTING_BAL_SUBTITLE.getString(this.account))) {

			@Override
			public boolean onResult(String string) {
				if (!NumberUtils.isNumber(string)) {
					Common.tell(click.player, Replacer.replaceVariables(Locale.getString(account, Translation.NOT_A_NUMBER.getKey()), "value", string));
					return false;
				}

				final double starting = Double.parseDouble(ChatColor.stripColor(string));

				CurrencyEditGUI.this.currency.setStartingBalance(starting < 0 ? 0 : starting);
				CurrencyEditGUI.this.currency.sync(false);
				click.manager.showGUI(click.player, new CurrencyEditGUI(CurrencyEditGUI.this.parent, account, CurrencyEditGUI.this.currency));
				return true;
			}

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, CurrencyEditGUI.this);
			}
		});

		// name
		setButton(2, 4, QuickItem.of(CompMaterial.DARK_OAK_SIGN)
				.name(Translation.GUI_CURRENCY_EDIT_ITEMS_DISPLAY_NAME_NAME.getString(this.account))
				.lore(Translation.GUI_CURRENCY_EDIT_ITEMS_DISPLAY_NAME_LORE.getList(this.account,
						"currency_name", currency.getName()
				))
				.make(), click -> new TitleInput(click.player, Common.colorize(Translation.CURRENCY_EDIT_NAME_TITLE.getString(this.account)), Common.colorize(Translation.CURRENCY_EDIT_NAME_SUBTITLE.getString(this.account))) {

			@Override
			public boolean onResult(String string) {
				if (string.length() < 3) return false;

				CurrencyEditGUI.this.currency.setName(string);
				CurrencyEditGUI.this.currency.sync(false);
				click.manager.showGUI(click.player, new CurrencyEditGUI(CurrencyEditGUI.this.parent, account, CurrencyEditGUI.this.currency));
				return true;
			}

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, CurrencyEditGUI.this);
			}
		});

		// description
		setButton(4, 4, QuickItem.of(CompMaterial.WRITABLE_BOOK)
				.name(Translation.GUI_CURRENCY_EDIT_ITEMS_DESC_NAME.getString(this.account))
				.lore(Translation.GUI_CURRENCY_EDIT_ITEMS_DESC_LORE.getList(this.account,
						"currency_description", currency.getDescription()
				))
				.make(), click -> new TitleInput(click.player, Common.colorize(Translation.CURRENCY_EDIT_DESC_TITLE.getString(this.account)), Common.colorize(Translation.CURRENCY_EDIT_DESC_SUBTITLE.getString(this.account))) {

			@Override
			public boolean onResult(String string) {
				if (string.length() < 3) return false;

				CurrencyEditGUI.this.currency.setDescription(string);
				CurrencyEditGUI.this.currency.sync(false);
				click.manager.showGUI(click.player, new CurrencyEditGUI(CurrencyEditGUI.this.parent, account, CurrencyEditGUI.this.currency));
				return true;
			}

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, CurrencyEditGUI.this);
			}
		});

		// withdrawal
		setButton(2, 6, QuickItem.of(CompMaterial.GOLD_NUGGET)
				.name(Translation.GUI_CURRENCY_EDIT_ITEMS_WITHDRAW_NAME.getString(this.account))
				.lore(Translation.GUI_CURRENCY_EDIT_ITEMS_WITHDRAW_LORE.getList(this.account,
						"is_allowed", currency.isWithdrawAllowed() ? Translation.MISC_IS_ALLOWED.getString(this.account) : Translation.MISC_IS_DISALLOWED.getString(this.account)

				))
				.glow(this.currency.isWithdrawAllowed())
				.make(), click -> {

			CurrencyEditGUI.this.currency.setWithdrawalAllowed(!CurrencyEditGUI.this.currency.isWithdrawAllowed());
			CurrencyEditGUI.this.currency.sync(false);
			draw();
		});

		// paying
		setButton(2, 7, QuickItem.of(CompMaterial.PRISMARINE_SHARD)
				.name(Translation.GUI_CURRENCY_EDIT_ITEMS_PAYING_NAME.getString(this.account))
				.lore(Translation.GUI_CURRENCY_EDIT_ITEMS_PAYING_LORE.getList(this.account,
						"is_allowed", currency.isPayingAllowed() ? Translation.MISC_IS_ALLOWED.getString(this.account) : Translation.MISC_IS_DISALLOWED.getString(this.account)
				))
				.glow(this.currency.isPayingAllowed())
				.make(), click -> {

			CurrencyEditGUI.this.currency.setPayingAllowed(!CurrencyEditGUI.this.currency.isPayingAllowed());
			CurrencyEditGUI.this.currency.sync(false);
			draw();
		});

		// vault currency
		setButton(4, 6, QuickItem.of(CompMaterial.FLINT)
				.name(Translation.GUI_CURRENCY_EDIT_ITEMS_VAULT_NAME.getString(this.account))
				.lore(Translation.GUI_CURRENCY_EDIT_ITEMS_VAULT_LORE.getList(this.account,
						"is_true", currency.isVaultCurrency() ? Translation.MISC_IS_TRUE.getString(this.account) : Translation.MISC_IS_FALSE.getString(this.account)
				)).make(), click -> {

			if (!CurrencyEditGUI.this.currency.isVaultCurrency()) {
				CurrencyEditGUI.this.currency.setIsVaultCurrency(true);
				Funds.getCurrencyManager().setVaultCurrency(this.currency);
				draw();
			}
		});

		applyBackExit();
	}
}
