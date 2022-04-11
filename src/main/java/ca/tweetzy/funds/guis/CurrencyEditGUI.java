package ca.tweetzy.funds.guis;

import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.guis.template.BaseGUI;
import ca.tweetzy.rose.gui.Gui;
import lombok.NonNull;

/**
 * Date Created: April 10 2022
 * Time Created: 11:18 p.m.
 *
 * @author Kiran Hart
 */
public final class CurrencyEditGUI extends BaseGUI {

	public CurrencyEditGUI(Gui parent, @NonNull final Currency currency) {
		super(parent, "&eFunds &8> &7Editing &8> &7" + currency.getId(), 6);
	}

	@Override
	protected void draw() {

		applyBackExit();
	}
}
