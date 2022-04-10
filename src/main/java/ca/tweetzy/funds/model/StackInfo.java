package ca.tweetzy.funds.model;

import ca.tweetzy.rose.utils.Common;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.WordUtils;
import org.bukkit.inventory.ItemStack;

/**
 * Date Created: April 09 2022
 * Time Created: 10:02 p.m.
 *
 * @author Kiran Hart
 */
@UtilityClass
public final class StackInfo {

	public String getItemName(@NonNull final ItemStack stack) {
		return stack.getItemMeta().hasDisplayName() ? stack.getItemMeta().getDisplayName() : Common.colorize("&f" + WordUtils.capitalize(stack.getType().name().toLowerCase().replace("_", " ")));
	}

}
