package ca.tweetzy.funds.guis;

import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.guis.template.BaseGUI;
import ca.tweetzy.funds.guis.template.MaterialPicker;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.QuickItem;
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

	public CurrencyEditGUI(Gui parent, @NonNull final Currency currency) {
		super(parent, "&eFunds &8> &7Editing &8> &7" + currency.getId(), 6);
		this.parent = parent;
		this.currency = currency;
		draw();
	}

	@Override
	protected void draw() {

		// icon selector
		setButton(1, 4, QuickItem.of(this.currency.getIcon())
				.name("&b&lIcon")
				.lore(
						"&8Change currency's display icon",
						"&7This will be shown within guis.",
						"",
						"&e&lClick &8» &7To change icon"
				).make(), click -> click.manager.showGUI(click.player, new MaterialPicker(this, "&eFunds &8> &7" + this.currency.getId() + " &8> &7Select Icon", null, (e, selected) -> {

			this.currency.setIcon(selected);
			this.currency.sync();
			e.manager.showGUI(e.player, new CurrencyEditGUI(this.parent, this.currency));
		})));

		// formatting
		setButton(2, 1, QuickItem.of(CompMaterial.PAPER)
				.name("&b&lFormatting")
				.lore(
						"&8Change currency's singular/plural format",
						"&7These will be used depending on currency amount",
						"",
						"&7Singular&f: &e" + this.currency.getSingularFormat(),
						"&7Plural&f: &e" + this.currency.getPluralFormat(),
						"",
						"&e&lLeft Click &8» &7To change singular format",
						"&e&lRight Click &8» &7To change plural format"
				).make(), click -> new TitleInput(click.player, Common.colorize("&eCurrency Edit"), Common.colorize("&fEnter " + (click.clickType == ClickType.LEFT ? "singular" : "plural") + " format for currency")) {

			@Override
			public boolean onResult(String string) {
				if (string.length() < 3) return false;

				if (click.clickType == ClickType.LEFT)
					CurrencyEditGUI.this.currency.setSingularFormat(string);
				if (click.clickType == ClickType.RIGHT)
					CurrencyEditGUI.this.currency.setPluralFormat(string);

				CurrencyEditGUI.this.currency.sync();
				click.manager.showGUI(click.player, new CurrencyEditGUI(CurrencyEditGUI.this.parent, CurrencyEditGUI.this.currency));
				return true;
			}

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, CurrencyEditGUI.this);
			}
		});

		// starting balance
		setButton(2, 2, QuickItem.of(CompMaterial.SUNFLOWER)
				.name("&b&lStarting Balance")
				.lore(
						"&8Change currency's starting balance",
						"&7Reset/New accounts will start with this",
						"",
						"&7Current&f: &e" + this.currency.getStartingBalance() + " " + (this.currency.getStartingBalance() > 1 ? this.currency.getPluralFormat() : this.currency.getSingularFormat()),
						"",
						"&e&lClick &8» &7To change starting balance"
				).make(), click -> new TitleInput(click.player, Common.colorize("&eCurrency Edit"), Common.colorize("&fEnter starting balance for currency")) {

			@Override
			public boolean onResult(String string) {
				if (!NumberUtils.isNumber(string)) {
					Common.tell(click.player, Locale.NOT_A_NUMBER.getString().replace("%value%", string));
					return false;
				}

				final double starting = Double.parseDouble(ChatColor.stripColor(string));

				CurrencyEditGUI.this.currency.setStartingBalance(starting < 0 ? 0 : starting);
				CurrencyEditGUI.this.currency.sync();
				click.manager.showGUI(click.player, new CurrencyEditGUI(CurrencyEditGUI.this.parent, CurrencyEditGUI.this.currency));
				return true;
			}

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, CurrencyEditGUI.this);
			}
		});

		// name
		setButton(2, 4, QuickItem.of(CompMaterial.DARK_OAK_SIGN)
				.name("&b&lName")
				.lore(
						"&8Change currency's display name",
						"&7This will be shown within guis.",
						"",
						"&7Current&f: &e" + this.currency.getName(),
						"",
						"&e&lClick &8» &7To change display name"
				).make(), click -> new TitleInput(click.player, Common.colorize("&eCurrency Edit"), Common.colorize("&fEnter display name for currency")) {

			@Override
			public boolean onResult(String string) {
				if (string.length() < 3) return false;

				CurrencyEditGUI.this.currency.setName(string);
				CurrencyEditGUI.this.currency.sync();
				click.manager.showGUI(click.player, new CurrencyEditGUI(CurrencyEditGUI.this.parent, CurrencyEditGUI.this.currency));
				return true;
			}

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, CurrencyEditGUI.this);
			}
		});

		// description
		setButton(4, 4, QuickItem.of(CompMaterial.WRITABLE_BOOK)
				.name("&b&LDescription")
				.lore(
						"&8Change currency's description",
						"&7This will be shown within guis.",
						"",
						"&7Description&f:",
						"&e" + this.currency.getDescription(),
						"",
						"&e&lClick &8» &7To change description"
				).make(), click -> new TitleInput(click.player, Common.colorize("&eCurrency Edit"), Common.colorize("&fEnter description for currency")) {

			@Override
			public boolean onResult(String string) {
				if (string.length() < 3) return false;

				CurrencyEditGUI.this.currency.setDescription(string);
				CurrencyEditGUI.this.currency.sync();
				click.manager.showGUI(click.player, new CurrencyEditGUI(CurrencyEditGUI.this.parent, CurrencyEditGUI.this.currency));
				return true;
			}

			@Override
			public void onExit(Player player) {
				click.manager.showGUI(player, CurrencyEditGUI.this);
			}
		});

		// withdrawal
		setButton(2, 6, QuickItem.of(CompMaterial.GOLD_NUGGET)
				.name("&E&lWithdrawal")
				.lore(
						"&8Currency Withdrawal",
						"&7Enabling this will allow users to withdraw ",
						"&7the currency into a physical item",
						"",
						"&7Current&f: " + (this.currency.isWithdrawAllowed() ? "&aAllowed" : "&cDisallowed"),
						"",
						"&e&lClick &8» &7To toggle state"
				)
				.glow(this.currency.isWithdrawAllowed())
				.make(), click -> {

			CurrencyEditGUI.this.currency.setWithdrawalAllowed(!CurrencyEditGUI.this.currency.isWithdrawAllowed());
			CurrencyEditGUI.this.currency.sync();
			draw();
		});

		// paying
		setButton(2, 7, QuickItem.of(CompMaterial.PRISMARINE_SHARD)
				.name("&E&lPaying")
				.lore(
						"&8Currency Paying",
						"&7Enabling this will allow users to pay ",
						"&7other users with this currency",
						"",
						"&7Current&f: " + (this.currency.isPayingAllowed() ? "&aAllowed" : "&cDisallowed"),
						"",
						"&e&lClick &8» &7To toggle state"
				)
				.glow(this.currency.isPayingAllowed())
				.make(), click -> {

			CurrencyEditGUI.this.currency.setPayingAllowed(!CurrencyEditGUI.this.currency.isPayingAllowed());
			CurrencyEditGUI.this.currency.sync();
			draw();
		});

		// vault currency
		setButton(4,6, QuickItem.of(CompMaterial.FLINT)
				.name("&b&lMake V")
				.lore(
						"&8Change currency's description",
						"&7This will be shown within guis.",
						"",
						"&7Description&f:",
						"&e" + this.currency.getDescription(),
						"",
						"&e&lClick &8» &7To change description"
				).make(), click -> {

		});

		applyBackExit();
	}
}
