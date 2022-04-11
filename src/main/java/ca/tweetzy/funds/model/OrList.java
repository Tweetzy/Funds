package ca.tweetzy.funds.model;

import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * Date Created: April 10 2022
 * Time Created: 4:02 p.m.
 *
 * @author Kiran Hart
 */
@UtilityClass
public final class OrList {

	public List<String> get(boolean expression, List<String> ifTrue, List<String> ifFalse) {
		return expression ? ifTrue : ifFalse;
	}
}
