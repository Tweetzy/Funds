package ca.tweetzy.funds.model;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Date Created: April 10 2022
 * Time Created: 4:02 p.m.
 *
 * @author Kiran Hart
 */
@UtilityClass
public final class Helper {

	private final Pattern TOKEN_PATTERN = Pattern.compile("[\\{|%]([^}|%]+)[\\}|%]");

	public List<String> orList(boolean expression, List<String> ifTrue, List<String> ifFalse) {
		return expression ? ifTrue : ifFalse;
	}

	private String replaceTokens(String text, Map<String, Object> valuesByKey) {
		StringBuilder output = new StringBuilder();
		Matcher tokenMatcher = TOKEN_PATTERN.matcher(text);

		int cursor = 0;
		while (tokenMatcher.find()) {
			int tokenStart = tokenMatcher.start();
			int tokenEnd = tokenMatcher.end();
			int keyStart = tokenMatcher.start(1);
			int keyEnd = tokenMatcher.end(1);

			output.append(text.substring(cursor, tokenStart));

			String token = text.substring(tokenStart, tokenEnd);
			String key = text.substring(keyStart, keyEnd);

			if (valuesByKey.containsKey(key)) {
				Object value = valuesByKey.get(key);
				output.append(value);
			} else {
				output.append(token);
			}

			cursor = tokenEnd;
		}
		output.append(text.substring(cursor));

		return output.toString();
	}

	public String replaceVariables(String text, Object... replacements) {
		final Map<String, Object> map = new HashMap<>();

		for (int i = 0; i < replacements.length; i += 2) {
			map.put((String) replacements[i], replacements[i + 1]);
		}

		return replaceTokens(text, map);
	}

	public List<String> replaceVariables(@NonNull final List<String> list, Object... replacements) {
		return list.stream().map(item -> replaceVariables(item, replacements)).collect(Collectors.toList());
	}
}
