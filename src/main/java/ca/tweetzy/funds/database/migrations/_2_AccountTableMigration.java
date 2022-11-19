package ca.tweetzy.funds.database.migrations;

import ca.tweetzy.flight.database.DataMigration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Date Created: April 11 2022
 * Time Created: 9:19 p.m.
 *
 * @author Kiran Hart
 */
public final class _2_AccountTableMigration extends DataMigration {

	public _2_AccountTableMigration() {
		super(2);
	}

	@Override
	public void migrate(Connection connection, String tablePrefix) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			statement.execute("CREATE TABLE " + tablePrefix + "account (" +
					"id VARCHAR(36) PRIMARY KEY, " +
					"name VARCHAR(16) NOT NULL, " +
					"bal_top_blocked BOOLEAN NOT NULL, " +
					"currencies TEXT NOT NULL, " +
					"created_at LONG NOT NULL" +
					")");
		}
	}
}
