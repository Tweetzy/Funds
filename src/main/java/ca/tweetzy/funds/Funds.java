package ca.tweetzy.funds;

import ca.tweetzy.funds.settings.Settings;
import ca.tweetzy.rose.RosePlugin;
import ca.tweetzy.rose.gui.GuiManager;

/**
 * Date Created: April 08 2022
 * Time Created: 7:01 p.m.
 *
 * @author Kiran Hart
 */
public final class Funds extends RosePlugin {

	private final GuiManager guiManager = new GuiManager(this);

	@Override
	protected void onWake() {
		// settings & locale setup
		Settings.setup();
	}

	@Override
	protected void onFlight() {
		guiManager.init();
	}

	// instance
	public static Funds getInstance() {
		return (Funds) RosePlugin.getInstance();
	}
}
