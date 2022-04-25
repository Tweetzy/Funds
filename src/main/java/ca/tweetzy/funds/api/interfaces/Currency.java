package ca.tweetzy.funds.api.interfaces;

import ca.tweetzy.rose.comp.enums.CompMaterial;
import org.bukkit.inventory.ItemStack;

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

	String getSingularFormat();

	String getPluralFormat();

	boolean isWithdrawAllowed();

	boolean isPayingAllowed();

	double getStartingBalance();

	boolean isVaultCurrency();

	void setName(String name);

	void setDescription(String description);

	void setIcon(CompMaterial icon);

	void setSingularFormat(String format);

	void setPluralFormat(String format);

	void setWithdrawalAllowed(boolean allowed);

	void setPayingAllowed(boolean allowed);

	void setStartingBalance(double startingBalance);

	void setIsVaultCurrency(boolean isVaultCurrency);

	ItemStack buildPhysicalItem(double amount);
}
