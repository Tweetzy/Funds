package ca.tweetzy.funds.impl;

import ca.tweetzy.funds.api.interfaces.Language;

/**
 * Date Created: April 20 2022
 * Time Created: 4:27 p.m.
 *
 * @author Kiran Hart
 */
public record FundLanguage(String name, String fileName, String flagTexture) implements Language {

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getFileName() {
		return this.fileName;
	}

	@Override
	public String getFlagTexture() {
		return this.flagTexture;
	}

	@Override
	public double calculateTranslatePercentage() {
		return 0;
	}
}
