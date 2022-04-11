package ca.tweetzy.funds.api.interfaces;

import ca.tweetzy.rose.comp.enums.CompMaterial;

/**
 * Date Created: April 08 2022
 * Time Created: 7:26 p.m.
 *
 * @author Kiran Hart
 */
public interface Currency extends DatabaseSynchronize {

	String getId();

	String getName();

	String getDescription();

	CompMaterial getIcon();

	double getStartingBalance();

	void setName(String name);

	void setDescription(String description);

	void setIcon(CompMaterial icon);

	void setStartingBalance(double startingBalance);
}
