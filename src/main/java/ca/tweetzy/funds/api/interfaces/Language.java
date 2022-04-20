package ca.tweetzy.funds.api.interfaces;

/**
 * Date Created: April 20 2022
 * Time Created: 4:22 p.m.
 *
 * @author Kiran Hart
 */
public interface Language {

	String getName();

	String getFileName();

	String getFlagTexture();

	double calculateTranslatePercentage();
}
