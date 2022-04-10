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

	public BaseGUI(@NonNull final String title, final int rows) {
		setTitle(Common.colorize(title));
		setRows(rows);
		setDefaultSound(null);
		setDefaultItem(QuickItem.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make());
	}

	public BaseGUI(@NonNull final String title) {
		this(title, 6);
	}

	protected List<Integer> fillSlots() {
		return IntStream.rangeClosed(0, 53).boxed().collect(Collectors.toList());
	}
}
