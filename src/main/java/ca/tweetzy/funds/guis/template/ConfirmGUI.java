package ca.tweetzy.funds.guis.template;

import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.gui.template.BaseGUI;
import ca.tweetzy.rose.utils.QuickItem;
import lombok.NonNull;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Date Created: April 16 2022
 * Time Created: 12:02 a.m.
 *
 * @author Kiran Hart
 */
public final class ConfirmGUI extends BaseGUI {

	private final Consumer<Boolean> confirmed;

	public ConfirmGUI(final String titleOverride, @NonNull final Consumer<Boolean> confirmed) {
		super(null, titleOverride == null ? Translation.GUI_CONFIRM_TITLE.getString() : titleOverride, 3);
		this.confirmed = confirmed;
		draw();
	}

	@Override
	protected void draw() {
		Arrays.asList(10, 11, 12).forEach(i -> setButton(i, QuickItem.of(CompMaterial.RED_STAINED_GLASS_PANE).name(Translation.GUI_CONFIRM_ITEMS_CANCEL_NAME.getString()).lore(Translation.GUI_CONFIRM_ITEMS_CANCEL_LORE.getList()).make(), click -> {
			confirmed.accept(false);
		}));

		Arrays.asList(14, 15, 16).forEach(i -> setButton(i, QuickItem.of(CompMaterial.LIME_STAINED_GLASS_PANE).name(Translation.GUI_CONFIRM_ITEMS_CONFIRM_NAME.getString()).lore(Translation.GUI_CONFIRM_ITEMS_CONFIRM_LORE.getList()).make(), click -> {
			confirmed.accept(true);
		}));
	}
}
