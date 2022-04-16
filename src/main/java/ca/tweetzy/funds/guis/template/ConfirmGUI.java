package ca.tweetzy.funds.guis.template;

import ca.tweetzy.rose.comp.enums.CompMaterial;
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
		super(null, titleOverride == null ? "&cConfirm Action" : titleOverride, 3);
		this.confirmed = confirmed;
		draw();
	}

	@Override
	protected void draw() {
		Arrays.asList(10, 11, 12).forEach(i -> setButton(i, QuickItem.of(CompMaterial.RED_STAINED_GLASS_PANE).name("&c&lCancel").lore("", "&E&LClick &8» &7To cancel action").make(), click -> {
			confirmed.accept(false);
		}));

		Arrays.asList(14, 15, 16).forEach(i -> setButton(i, QuickItem.of(CompMaterial.LIME_STAINED_GLASS_PANE).name("&a&lConfirm").lore("", "&E&LClick &8» &7To confirm action").make(), click -> {
			confirmed.accept(true);
		}));
	}
}
