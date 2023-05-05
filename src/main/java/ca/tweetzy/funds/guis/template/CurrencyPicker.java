package ca.tweetzy.funds.guis.template;

import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.flight.gui.events.GuiClickEvent;
import ca.tweetzy.flight.gui.helper.InventoryBorder;
import ca.tweetzy.flight.gui.template.PagedGUI;
import ca.tweetzy.flight.utils.QuickItem;
import ca.tweetzy.funds.settings.Translations;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Date Created: April 15 2022
 * Time Created: 11:25 p.m.
 *
 * @author Kiran Hart
 * not rlly a template but eh, who cares
 */
public final class CurrencyPicker extends PagedGUI<Currency> {

	private final Account account;
	private final BiConsumer<GuiClickEvent, Currency> selected;

	public CurrencyPicker(@NonNull final Account account, final String titleOverride, final boolean showHas, @NonNull final BiConsumer<GuiClickEvent, Currency> selected) {
		super(null, titleOverride == null ? TranslationManager.string(Translations.GUI_SELECT_CURRENCY_TITLE) : titleOverride, 6, Funds.getCurrencyManager().getCurrencies().stream().filter(currency -> showHas ? account.getCurrencies().containsKey(currency) && account.getCurrencies().get(currency) > 0 : !account.getCurrencies().containsKey(currency)).collect(Collectors.toList()));
		this.account = account;
		this.selected = selected;
		draw();
	}

	@Override
	protected ItemStack makeDisplayItem(Currency currency) {
		return QuickItem.of(currency.getIcon())
				.name(TranslationManager.string(Translations.GUI_SELECT_CURRENCY_ITEMS_CURRENCY_NAME, "currency_name", currency.getName()))
				.lore(TranslationManager.string(Translations.GUI_SELECT_CURRENCY_ITEMS_CURRENCY_LORE,
						"currency_id", currency.getId(),
						"currency_description", currency.getDescription()
				))
				.make();
	}

	@Override
	protected void onClick(Currency currency, GuiClickEvent click) {
		this.selected.accept(click, currency);
	}

	@Override
	protected List<Integer> fillSlots() {
		return InventoryBorder.getInsideBorders(5);
	}
}
