package ca.tweetzy.funds.impl;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.utils.Inflector;
import ca.tweetzy.flight.utils.QuickItem;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

/**
 * Date Created: April 08 2022
 * Time Created: 11:13 p.m.
 *
 * @author Kiran Hart
 */
@AllArgsConstructor
public final class FundCurrency implements Currency {

	private final String id;
	private String name;
	private String description;
	private CompMaterial icon;
	private String singularFormat;
	private String pluralFormat;
	private boolean withdrawAllowed;
	private boolean payingAllowed;
	private double startingBalance;
	private boolean isVaultCurrency;

	public FundCurrency(final String id) {
		this(
				ChatColor.stripColor(id.toLowerCase()),
				"&b&l" + id,
				id + " currency",
				CompMaterial.EMERALD,
				Inflector.getInstance().singularize(id),
				Inflector.getInstance().pluralize(id),
				true,
				true,
				0D,
				false
		);
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public CompMaterial getIcon() {
		return this.icon;
	}

	@Override
	public String getSingularFormat() {
		return this.singularFormat;
	}

	@Override
	public String getPluralFormat() {
		return this.pluralFormat;
	}

	@Override
	public boolean isWithdrawAllowed() {
		return this.withdrawAllowed;
	}

	@Override
	public boolean isPayingAllowed() {
		return this.payingAllowed;
	}

	@Override
	public double getStartingBalance() {
		return this.startingBalance;
	}

	@Override
	public boolean isVaultCurrency() {
		return this.isVaultCurrency;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setIcon(CompMaterial icon) {
		this.icon = icon;
	}

	@Override
	public void setSingularFormat(String format) {
		this.singularFormat = format;
	}

	@Override
	public void setPluralFormat(String format) {
		this.pluralFormat = format;
	}

	@Override
	public void setWithdrawalAllowed(boolean allowed) {
		this.withdrawAllowed = allowed;
	}

	@Override
	public void setPayingAllowed(boolean payingAllowed) {
		this.payingAllowed = payingAllowed;
	}

	@Override
	public void setStartingBalance(double startingBalance) {
		this.startingBalance = startingBalance;
	}

	@Override
	public void setIsVaultCurrency(boolean isVaultCurrency) {
		this.isVaultCurrency = isVaultCurrency;
	}

	@Override
	public ItemStack buildPhysicalItem(double amount) {
		return QuickItem.of(this.icon)
				.name(Translation.PHYSICAL_CURRENCY_NAME.getString("total", amount, "currency_auto_format", amount > 1.0 ? getPluralFormat() : getSingularFormat()))
				.lore(Translation.PHYSICAL_CURRENCY_LORE.getList())
				.tag("Funds:CurrencyID", getId())
				.tag("Funds:CurrencyAmount", String.valueOf(amount))
				.make();
	}

	@Override
	public void sync(boolean silent) {
		Funds.getDataManager().updateCurrency(silent, this, null);
	}
}
