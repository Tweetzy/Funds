package ca.tweetzy.funds.model;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.regex.Pattern;

/**
 * Date Created: April 09 2022
 * Time Created: 10:03 p.m.
 *
 * @author Kiran Hart
 */
@UtilityClass
public final class Matcher {

	public boolean match(String pattern, String sentence) {
		Pattern patt = Pattern.compile(ChatColor.stripColor(pattern), Pattern.CASE_INSENSITIVE);
		java.util.regex.Matcher matcher = patt.matcher(sentence);
		return matcher.find();
	}

}
