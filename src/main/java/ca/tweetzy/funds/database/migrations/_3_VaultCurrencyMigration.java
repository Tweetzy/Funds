package ca.tweetzy.funds.database.migrations;

import ca.tweetzy.rose.database.DataMigration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Date Created: April 13 2022
 * Time Created: 5:15 p.m.
 *
 * @author Kiran Hart
 */
public final class _3_VaultCurrencyMigration extends DataMigration {

	public _3_VaultCurrencyMigration() {
		super(3);
	}

	@Override
	public void migrate(Connection connection, String tablePrefix) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			statement.execute("ALTER TABLE " + tablePrefix + "currency ADD is_vault_currency BOOLEAN");
		}
	}
}
