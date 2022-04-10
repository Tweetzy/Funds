package ca.tweetzy.funds.guis;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.guis.template.BaseGUI;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.utils.QuickItem;

/**
 * Date Created: April 10 2022
 * Time Created: 12:53 a.m.
 *
 * @author Kiran Hart
 */
public final class AdminMainGUI extends BaseGUI {

	public AdminMainGUI() {
		super("&eFunds &fv&7" + Funds.getInstance().getDescription().getVersion());

		draw();
	}

	private void draw() {
		reset();

		// Patron
		setButton(4, 7, QuickItem.of(CompMaterial.DIAMOND)
				.name("&e&lPatreon")
				.lore(
						"&8Support me on Patreon",
						"&7By supporting me on Patreon you will",
						"&7be helping me be able to continue updating",
						"&7and creating free plugins.",
						"",
						"&e&lClick &8» &7To view Patrons"
				)
				.glow(true)
				.make(), null);
	}
}
