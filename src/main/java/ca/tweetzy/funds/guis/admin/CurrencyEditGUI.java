package ca.tweetzy.funds.guis.admin;

import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.gui.template.BaseGUI;
import ca.tweetzy.flight.gui.template.MaterialPickerGUI;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.flight.utils.input.TitleInput;
import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.settings.Translations;
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
		super(parent, TranslationManager.string(Translations.GUI_CURRENCY_EDIT_TITLE, "currency_id", currency.getId()), 6);
		this.parent = parent;
		this.account = account;
		this.currency = currency;
		draw();
	}

	@Override
	protected void draw() {

		// icon selector
		setButton(1, 4, QuickItem.of(this.currency.getIcon())
				.name(TranslationManager.string(Translations.GUI_CURRENCY_EDIT_ITEMS_ICON_NAME))
				.lore(TranslationManager.list(Translations.GUI_CURRENCY_EDIT_ITEMS_ICON_LORE))
				.make(), click -> click.manager.showGUI(click.player, new MaterialPickerGUI(this, "&eFunds &8> &7" + this.currency.getId() + " &8> &7Select Icon", null, (e, selected) -> {

			this.currency.setIcon(selected);
			this.currency.sync(false);
			e.manager.showGUI(e.player, new CurrencyEditGUI(this.parent, this.account, this.currency));
		})));

		// formatting
		setButton(2, 1, QuickItem.of(CompMaterial.PAPER)
				.name(TranslationManager.string(Translations.GUI_CURRENCY_EDIT_ITEMS_FORMATTING_NAME))
				.lore(TranslationManager.list(Translations.GUI_CURRENCY_EDIT_ITEMS_FORMATTING_LORE,
						"currency_singular_format", currency.getSingularFormat(),
						"currency_plural_format", currency.getPluralFormat()
				))
				.make(), click -> new TitleInput(Funds.getInstance(), click.player, Common.colorize(TranslationManager.string(Translations.CURRENCY_EDIT_FORMATTING_TITLE)), Common.colorize(click.clickType == ClickType.LEFT ? TranslationManager.string(Translations.CURRENCY_EDIT_FORMATTING_SUBTITLE_SINGULAR) : TranslationManager.string(Translations.CURRENCY_EDIT_FORMATTING_SUBTITLE_PLURAL))) {

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
				.name(TranslationManager.string(Translations.GUI_CURRENCY_EDIT_ITEMS_STARTING_BAL_NAME))
				.lore(TranslationManager.list(Translations.GUI_CURRENCY_EDIT_ITEMS_STARTING_BAL_LORE,
						"currency_starting_balance", currency.getStartingBalance(),
						"currency_plural_format", currency.getPluralFormat()
				)).make(), click -> new TitleInput(Funds.getInstance(), click.player, Common.colorize(TranslationManager.string(Translations.CURRENCY_EDIT_STARTING_BAL_TITLE)), Common.colorize(TranslationManager.string(Translations.CURRENCY_EDIT_STARTING_BAL_SUBTITLE))) {

			@Override
			public boolean onResult(String string) {
				if (!NumberUtils.isNumber(string)) {
					Common.tell(click.player, TranslationManager.string(Translations.NOT_A_NUMBER, "value", string));
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
				.name(TranslationManager.string(Translations.GUI_CURRENCY_EDIT_ITEMS_DISPLAY_NAME_NAME))
				.lore(TranslationManager.list(Translations.GUI_CURRENCY_EDIT_ITEMS_DISPLAY_NAME_LORE,
						"currency_name", currency.getName()
				))
				.make(), click -> new TitleInput(Funds.getInstance(), click.player, Common.colorize(TranslationManager.string(Translations.CURRENCY_EDIT_NAME_TITLE)), Common.colorize(TranslationManager.string(Translations.CURRENCY_EDIT_NAME_SUBTITLE))) {

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
				.name(TranslationManager.string(Translations.GUI_CURRENCY_EDIT_ITEMS_DESC_NAME))
				.lore(TranslationManager.list(Translations.GUI_CURRENCY_EDIT_ITEMS_DESC_LORE,
						"currency_description", currency.getDescription()
				))
				.make(), click -> new TitleInput(Funds.getInstance(), click.player, Common.colorize(TranslationManager.string(Translations.CURRENCY_EDIT_DESC_TITLE)), Common.colorize(TranslationManager.string(Translations.CURRENCY_EDIT_DESC_SUBTITLE))) {

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
				.name(TranslationManager.string(Translations.GUI_CURRENCY_EDIT_ITEMS_WITHDRAW_NAME))
				.lore(TranslationManager.list(Translations.GUI_CURRENCY_EDIT_ITEMS_WITHDRAW_LORE,
						"is_allowed", currency.isWithdrawAllowed() ? TranslationManager.string(Translations.ALLOWED) : TranslationManager.string(Translations.DISALLOWED)

				))
				.glow(this.currency.isWithdrawAllowed())
				.make(), click -> {

			CurrencyEditGUI.this.currency.setWithdrawalAllowed(!CurrencyEditGUI.this.currency.isWithdrawAllowed());
			CurrencyEditGUI.this.currency.sync(false);
			draw();
		});

		// paying
		setButton(2, 7, QuickItem.of(CompMaterial.PRISMARINE_SHARD)
				.name(TranslationManager.string(Translations.GUI_CURRENCY_EDIT_ITEMS_PAYING_NAME))
				.lore(TranslationManager.list(Translations.GUI_CURRENCY_EDIT_ITEMS_PAYING_LORE,
						"is_allowed", currency.isPayingAllowed() ? TranslationManager.string(Translations.ALLOWED) : TranslationManager.string(Translations.DISALLOWED)
				))
				.glow(this.currency.isPayingAllowed())
				.make(), click -> {

			CurrencyEditGUI.this.currency.setPayingAllowed(!CurrencyEditGUI.this.currency.isPayingAllowed());
			CurrencyEditGUI.this.currency.sync(false);
			draw();
		});

		// vault currency
		setButton(4, 6, QuickItem.of(CompMaterial.FLINT)
				.name(TranslationManager.string(Translations.GUI_CURRENCY_EDIT_ITEMS_VAULT_NAME))
				.lore(TranslationManager.list(Translations.GUI_CURRENCY_EDIT_ITEMS_VAULT_LORE,
						"is_true", currency.isVaultCurrency() ? TranslationManager.string(Translations.ALLOWED) : TranslationManager.string(Translations.DISALLOWED)
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
