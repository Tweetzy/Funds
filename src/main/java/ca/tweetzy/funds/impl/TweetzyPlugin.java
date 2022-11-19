package ca.tweetzy.funds.impl;

import ca.tweetzy.flight.comp.enums.CompMaterial;

import java.util.List;

/**
 * Date Created: April 25 2022
 * Time Created: 12:25 a.m.
 *
 * @author Kiran Hart
 */
public final record TweetzyPlugin(int spigotId, CompMaterial icon, String name, List<String> description, double price) {
}
