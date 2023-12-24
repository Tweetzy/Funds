/*
 * Spawners
 * Copyright 2023 Kiran Hart
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package ca.tweetzy.funds.guis;

import ca.tweetzy.flight.gui.Gui;
import ca.tweetzy.flight.gui.template.BaseGUI;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.funds.settings.Settings;
import ca.tweetzy.funds.settings.Translations;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public abstract class FundsBaseGUI extends BaseGUI {

	public FundsBaseGUI(Gui parent, @NonNull String title, int rows) {
		super(parent, title, rows);
	}

	public FundsBaseGUI(Gui parent, @NonNull String title) {
		super(parent, title);
	}

	public FundsBaseGUI(@NonNull String title) {
		super(title);
	}

	@Override
	protected ItemStack getBackButton() {
		return QuickItem
				.of(Settings.GUI_SHARED_ITEMS_BACK_BUTTON.getItemStack())
				.name(TranslationManager.string(Translations.GUI_SHARED_ITEMS_BACK_BUTTON_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHARED_ITEMS_BACK_BUTTON_LORE, "left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK)))
				.make();
	}

	@Override
	protected ItemStack getExitButton() {
		return QuickItem
				.of(Settings.GUI_SHARED_ITEMS_EXIT_BUTTON.getItemStack())
				.name(TranslationManager.string(Translations.GUI_SHARED_ITEMS_EXIT_BUTTON_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHARED_ITEMS_EXIT_BUTTON_LORE, "left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK)))
				.make();
	}

	@Override
	protected ItemStack getPreviousButton() {
		return QuickItem
				.of(Settings.GUI_SHARED_ITEMS_PREVIOUS_BUTTON.getItemStack())
				.name(TranslationManager.string(Translations.GUI_SHARED_ITEMS_PREVIOUS_BUTTON_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHARED_ITEMS_PREVIOUS_BUTTON_LORE, "left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK)))
				.make();
	}

	@Override
	protected ItemStack getNextButton() {
		return QuickItem
				.of(Settings.GUI_SHARED_ITEMS_NEXT_BUTTON.getItemStack())
				.name(TranslationManager.string(Translations.GUI_SHARED_ITEMS_NEXT_BUTTON_NAME))
				.lore(TranslationManager.list(Translations.GUI_SHARED_ITEMS_NEXT_BUTTON_LORE, "left_click", TranslationManager.string(Translations.MOUSE_LEFT_CLICK)))
				.make();
	}

	@Override
	protected int getPreviousButtonSlot() {
		return 48;
	}

	@Override
	protected int getNextButtonSlot() {
		return 50;
	}
}
