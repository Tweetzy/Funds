package ca.tweetzy.funds.impl;

import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import lombok.AllArgsConstructor;

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
	private double startingBalance;

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
	public double getStartingBalance() {
		return this.startingBalance;
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
	public void setStartingBalance(double startingBalance) {
		this.startingBalance = startingBalance;
	}
}
