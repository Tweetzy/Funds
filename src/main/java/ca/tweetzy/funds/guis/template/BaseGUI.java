package ca.tweetzy.funds.guis.template;

import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.QuickItem;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Date Created: April 09 2022
 * Time Created: 9:22 p.m.
 *
 * @author Kiran Hart
 */
public abstract class BaseGUI extends Gui {

	private final Gui parent;

	public BaseGUI(final Gui parent, @NonNull final String title, final int rows) {
		this.parent = parent;
		setTitle(Common.colorize(title));
		setRows(rows);
		setDefaultSound(null);
		setDefaultItem(QuickItem.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make());
	}

	public BaseGUI(final Gui parent, @NonNull final String title) {
		this(parent, title, 6);
	}

	public BaseGUI(@NonNull final String title) {
		this(null, title, 6);
	}

	protected abstract void draw();


	protected void applyBackExit(Gui override) {
		setButton(this.rows - 1, 0, QuickItem.of(CompMaterial.DARK_OAK_DOOR).name("&aBack").lore("&7Click to go back").make(), click -> click.manager.showGUI(click.player, override));
	}

	protected void applyBackExit() {
		if (this.parent == null)
			setButton(this.rows - 1, 0, QuickItem.of(CompMaterial.BARRIER).name("&cExit").lore("&7Click to close menu").make(), click -> click.gui.exit());
		else
			setButton(this.rows - 1, 0, QuickItem.of(CompMaterial.DARK_OAK_DOOR).name("&aBack").lore("&7Click to go back").make(), click -> click.manager.showGUI(click.player, this.parent));
	}

	protected List<Integer> fillSlots() {
		return IntStream.rangeClosed(0, 44).boxed().collect(Collectors.toList());
	}
}
