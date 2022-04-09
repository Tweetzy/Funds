package ca.tweetzy.funds.database.migrations;

import ca.tweetzy.rose.database.DataMigration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Date Created: April 08 2022
 * Time Created: 7:18 p.m.
 *
 * @author Kiran Hart
 */
public final class _1_CurrencyTableMigration extends DataMigration {

	public _1_CurrencyTableMigration() {
		super(1);
	}

	@Override
	public void migrate(Connection connection, String tablePrefix) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			statement.execute("CREATE TABLE " + tablePrefix + "currency (" +
					"id VARCHAR(64) PRIMARY KEY, " +
					"name VARCHAR(100) NOT NULL, " +
					"description VARCHAR(160) NULL, " +
					"icon VARCHAR(48) NOT NULL, " +
					"starting_balance DOUBLE NOT NULL" +
					")");
		}
	}
}
