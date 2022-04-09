package ca.tweetzy.funds.database.migrations;

import ca.tweetzy.rose.database.DataMigration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Date Created: April 08 2022
 * Time Created: 7:18 p.m.
 *
 * @author Kiran Hart
 */
public final class _1_InitialMigration extends DataMigration {

	public _1_InitialMigration() {
		super(1);
	}

	@Override
	public void migrate(Connection connection, String tablePrefix) throws SQLException {

	}
}
