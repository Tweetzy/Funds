package ca.tweetzy.funds.guis.template;

import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.gui.Gui;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.utils.QuickItem;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Date Created: April 10 2022
 * Time Created: 10:00 p.m.
 *
 * @author Kiran Hart
 */
public abstract class PagedGUI<T> extends BaseGUI {

	protected final Gui parent;
	private final List<T> items;

	public PagedGUI(final Gui parent, @NonNull final String title, final int rows, @NonNull final List<T> items) {
		super(parent, title, rows);
		this.parent = parent;
		this.items = items;
		draw();
	}

	public PagedGUI(@NonNull final String title, final int rows, @NonNull final List<T> items) {
		this(null, title, rows, items);
	}

	@Override
	protected void draw() {
		reset();

		if (this.items != null) {
			final List<T> itemsToFill = this.items.stream().skip((page - 1) * (long) this.fillSlots().size()).limit(this.fillSlots().size()).collect(Collectors.toList());
			pages = (int) Math.max(1, Math.ceil(this.items.size() / (double) this.fillSlots().size()));

			setPrevPage(5, 3, QuickItem.of(CompMaterial.ARROW, "&ePrevious").make());
			setNextPage(5, 5, QuickItem.of(CompMaterial.ARROW, "&eNext").make());
			setOnPage(e -> draw());

			for (int i = 0; i < this.rows * 9; i++) {
				if (this.fillSlots().contains(i) && this.fillSlots().indexOf(i) < itemsToFill.size()) {
					final T object = itemsToFill.get(this.fillSlots().indexOf(i));
					setButton(i, this.makeDisplayItem(object), click -> this.onClick(object, click));
				}
			}
		}

		drawAdditional();
		applyBackExit();
	}

	protected void drawAdditional() {
	}

	public Gui getGuiParent() {
		return this.parent;
	}

	protected abstract ItemStack makeDisplayItem(final T object);

	protected abstract void onClick(final T object, final GuiClickEvent clickEvent);
}
