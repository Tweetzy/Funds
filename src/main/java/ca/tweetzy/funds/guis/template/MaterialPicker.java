package ca.tweetzy.funds.guis.template;

import ca.tweetzy.funds.api.Inflector;
import ca.tweetzy.funds.model.Matcher;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.gui.events.GuiClickEvent;
import ca.tweetzy.rose.gui.helper.InventoryBorder;
import ca.tweetzy.rose.gui.helper.InventorySafeMaterials;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.QuickItem;
import ca.tweetzy.rose.utils.input.TitleInput;
import lombok.NonNull;
import org.apache.commons.lang.WordUtils;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Date Created: April 09 2022
 * Time Created: 9:18 p.m.
 *
 * @author Kiran Hart
 */
public final class MaterialPicker extends BaseGUI {

	private String searchQuery;
	private final BiConsumer<GuiClickEvent, CompMaterial> selected;

	public MaterialPicker(final String titleOverride, final String searchQuery, @NonNull final BiConsumer<GuiClickEvent, CompMaterial> selected) {
		super(titleOverride == null ? "&eMaterial Selector" : titleOverride);
		this.searchQuery = searchQuery;
		this.selected = selected;
		draw();
	}

	private void draw() {
		reset();

		List<CompMaterial> validMaterials = InventorySafeMaterials.get();
		if (this.searchQuery != null) {
			validMaterials = validMaterials.stream().filter(mat -> Matcher.match(this.searchQuery, mat.name()) || Matcher.match(this.searchQuery, Inflector.getInstance().pluralize(mat.name()))).collect(Collectors.toList());
		}

		final List<CompMaterial> itemsToFill = validMaterials.stream().skip((page - 1) * (long) this.fillSlots().size()).limit(this.fillSlots().size()).collect(Collectors.toList());
		pages = (int) Math.max(1, Math.ceil(validMaterials.size() / (double) this.fillSlots().size()));

		setPrevPage(5, 3, QuickItem.of(CompMaterial.ARROW, "&ePrevious").make());
		setNextPage(5, 5, QuickItem.of(CompMaterial.ARROW, "&eNext").make());
		setOnPage(e -> draw());

		for (int i = 0; i < this.rows * 9; i++) {
			if (this.fillSlots().contains(i) && this.fillSlots().indexOf(i) < itemsToFill.size()) {
				final CompMaterial material = itemsToFill.get(this.fillSlots().indexOf(i));
				setButton(i, QuickItem.of(material)
						.name("&e&l" + WordUtils.capitalizeFully(material.name().replace("_", " ")))
						.lore("&7Click to select this material")
						.make(), click -> this.selected.accept(click, material));
			}
		}

		setButton(5, 4, QuickItem.of(CompMaterial.DARK_OAK_SIGN).name("&b&lSearch").lore("&7Click to search materials").make(), click -> {
			click.gui.exit();

			new TitleInput(click.player, Common.colorize("&eMaterial Search"), Common.colorize("&fType the search term into chat")) {
				@Override
				public boolean onResult(String string) {
					if (string.isEmpty()) return false;
					click.manager.showGUI(click.player, new MaterialPicker(null, string, MaterialPicker.this.selected));
					return true;
				}
			};
		});

		if (this.searchQuery != null)
			setButton(5, 7, QuickItem
					.of(CompMaterial.LAVA_BUCKET)
					.name("&c&lClear Search")
					.lore("&7Click to clear your search")
					.make(), click -> click.manager.showGUI(click.player, new MaterialPicker(null, null, this.selected)));
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
