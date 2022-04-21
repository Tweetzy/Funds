package ca.tweetzy.funds.guis.player;

import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Language;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.rose.comp.NBTEditor;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.gui.helper.InventoryBorder;
import ca.tweetzy.rose.gui.template.PagedGUI;
import ca.tweetzy.rose.utils.QuickItem;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * Date Created: April 20 2022
 * Time Created: 5:54 p.m.
 *
 * @author Kiran Hart
 */
public final class LanguagePickerGUI extends PagedGUI<Language> {

	private final Account account;
	private final BiConsumer<GuiClickEvent, Language> selected;

	public LanguagePickerGUI(Gui parent, @NonNull Account account, final BiConsumer<GuiClickEvent, Language> selected) {
		super(parent, Translation.GUI_SELECT_LANGUAGE_TITLE.getString(account), 6, Locale.getLanguages());
		this.account = account;
		this.selected = selected;
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Language language) {
		return QuickItem.of(NBTEditor.getHead(language.getFlagTexture()))
				.name(Translation.GUI_SELECT_LANGUAGE_ITEMS_LANG_NAME.getString(this.account, "language_name", language.getName()))
				.lore(Translation.GUI_SELECT_LANGUAGE_ITEMS_LANG_LORE.getList(this.account))
				.make();
	}

	@Override
	protected void onClick(Language language, GuiClickEvent click) {
		this.selected.accept(click, language);
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
