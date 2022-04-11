package ca.tweetzy.funds.guis;

import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.guis.template.BaseGUI;
import ca.tweetzy.funds.guis.template.MaterialPicker;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.QuickItem;
import ca.tweetzy.rose.utils.input.TitleInput;
import lombok.NonNull;
import org.bukkit.entity.Player;

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

		applyBackExit();
	}
}
