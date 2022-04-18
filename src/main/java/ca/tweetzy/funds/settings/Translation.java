package ca.tweetzy.funds.settings;

import ca.tweetzy.funds.model.Helper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Date Created: April 18 2022
 * Time Created: 3:18 p.m.
 *
 * @author Kiran Hart
 */
@AllArgsConstructor
@Getter
public enum Translation {

	// TITLES
	GUI_MAIN_TITLE("gui.main.title", "&eFunds &fv&7%plugin_version%"),


	;

	final String key;
	final Object value;

	public String getString(Object... replacements) {
		return Helper.replaceVariables(Locale.getString(this.key), replacements);
	}

	public List<String> getList(Object... replacements) {
		return Helper.replaceVariables(Locale.getList(this.key), replacements);
	}
}
