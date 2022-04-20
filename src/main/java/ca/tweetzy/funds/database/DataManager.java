package ca.tweetzy.funds.database;

import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.impl.FundAccount;
import ca.tweetzy.funds.impl.FundCurrency;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.database.Callback;
import ca.tweetzy.rose.database.DataManagerAbstract;
import ca.tweetzy.rose.database.DatabaseConnector;
import ca.tweetzy.rose.database.UpdateCallback;
import ca.tweetzy.rose.utils.Common;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Date Created: April 08 2022
 * Time Created: 7:18 p.m.
 *
 * @author Kiran Hart
 */
public final class DataManager extends DataManagerAbstract {

	public DataManager(DatabaseConnector databaseConnector, Plugin plugin) {
		super(databaseConnector, plugin);
	}

	// =================================================== //
	//					  ACCOUNT STUFF 				   //
	// =================================================== //

	public void createAccount(@NotNull final Account account, Callback<Account> callback) {
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			final String query = "INSERT INTO " + this.getTablePrefix() + "account (id, name, bal_top_blocked, currencies, preferred_language, created_at) VALUES (?, ?, ?, ?, ?, ?)";
			final String fetchQuery = "SELECT * FROM " + this.getTablePrefix() + "account WHERE id = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				final PreparedStatement fetch = connection.prepareStatement(fetchQuery);

				fetch.setString(1, account.getOwner().toString());

				preparedStatement.setString(1, account.getOwner().toString());
				preparedStatement.setString(2, account.getName());
				preparedStatement.setBoolean(3, account.isBalTopBlocked());
				preparedStatement.setString(4, account.getCurrencyJson());
				preparedStatement.setString(5, account.getPreferredLanguage().getFileName());
				preparedStatement.setLong(6, account.getCreatedAt());

				preparedStatement.executeUpdate();

				if (callback != null) {
					final ResultSet res = fetch.executeQuery();
					res.next();
					callback.accept(null, extractAccount(res));
				}

			} catch (Exception e) {
				e.printStackTrace();
				resolveCallback(callback, e);
			}
		}));
	}

	public void getAccounts(@NonNull final Callback<List<Account>> callback) {
		final List<Account> accounts = new ArrayList<>();
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + this.getTablePrefix() + "account")) {
				final ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					accounts.add(extractAccount(resultSet));
				}

				callback.accept(null, accounts);
			} catch (Exception e) {
				resolveCallback(callback, e);
			}
		}));
	}

	public void updateAccount(final boolean silent, @NonNull final Account currency, Callback<Boolean> callback) {
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			long begin = System.nanoTime();
			try (PreparedStatement statement = connection.prepareStatement("UPDATE " + this.getTablePrefix() + "account SET name = ?, bal_top_blocked = ?, currencies = ?, preferred_language = ? WHERE id = ?")) {

				statement.setString(1, currency.getName());
				statement.setBoolean(2, currency.isBalTopBlocked());
				statement.setString(3, currency.getCurrencyJson());
				statement.setString(4, currency.getPreferredLanguage().getFileName());
				statement.setString(5, currency.getOwner().toString());

				int result = statement.executeUpdate();

				if (!silent)
					Common.log(String.format("&fSynced user account to data file in &a%s&f ms", String.format("%,.3f", (System.nanoTime() - begin) / 1e+6)));

				if (callback != null)
					callback.accept(null, result > 0);

			} catch (Exception e) {
				resolveCallback(callback, e);
			}
		}));
	}

	public void updateAccounts(@NonNull final List<Account> accounts, Callback<Boolean> callback) {
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			long begin = System.nanoTime();
			SQLException err = null;
			connection.setAutoCommit(false);

			try (PreparedStatement statement = connection.prepareStatement("UPDATE " + this.getTablePrefix() + "account SET name = ?, bal_top_blocked = ?, currencies = ? WHERE id = ?")) {

				for (Account account : accounts) {
					try {
						statement.setString(1, account.getName());
						statement.setBoolean(2, account.isBalTopBlocked());
						statement.setString(3, account.getCurrencyJson());
						statement.setString(4, account.getOwner().toString());
						statement.addBatch();
					} catch (SQLException e) {
						err = e;
						break;
					}
				}

				int[] result = statement.executeBatch();
				Common.log(String.format("&fSynced user account(s) to data file in &a%s&f ms", String.format("%,.3f", (System.nanoTime() - begin) / 1e+6)));

				if (err == null)
					connection.commit();

				connection.setAutoCommit(true);

				if (callback != null) {
					callback.accept(null, result.length == accounts.size());
				}

			} catch (Exception e) {
				connection.rollback();
				resolveCallback(callback, e);
			}
		}));
	}

	// =================================================== //
	//					  CURRENCY STUFF 				   //
	// =================================================== //

	public void createCurrency(@NotNull final Currency currency, Callback<Currency> callback) {
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			final String query = "INSERT INTO " + this.getTablePrefix() + "currency (id, name, description, icon, singular_format, plural_format, starting_balance, withdraw_allowed, pay_allowed, is_vault_currency) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			final String fetchQuery = "SELECT * FROM " + this.getTablePrefix() + "currency WHERE id = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				final PreparedStatement fetch = connection.prepareStatement(fetchQuery);

				fetch.setString(1, currency.getId().toLowerCase());

				preparedStatement.setString(1, currency.getId().toLowerCase());
				preparedStatement.setString(2, currency.getName());
				preparedStatement.setString(3, currency.getDescription());
				preparedStatement.setString(4, currency.getIcon().name());
				preparedStatement.setString(5, currency.getSingularFormat());
				preparedStatement.setString(6, currency.getPluralFormat());
				preparedStatement.setDouble(7, currency.getStartingBalance());
				preparedStatement.setBoolean(8, currency.isPayingAllowed());
				preparedStatement.setBoolean(9, currency.isWithdrawAllowed());
				preparedStatement.setBoolean(10, currency.isVaultCurrency());

				preparedStatement.executeUpdate();

				if (callback != null) {
					final ResultSet res = fetch.executeQuery();
					res.next();
					callback.accept(null, extractCurrency(res));
				}

			} catch (Exception e) {
				e.printStackTrace();
				resolveCallback(callback, e);
			}
		}));
	}

	public void getCurrencies(@NonNull final Callback<List<Currency>> callback) {
		final List<Currency> currencies = new ArrayList<>();
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + this.getTablePrefix() + "currency")) {
				final ResultSet resultSet = statement.executeQuery();
				while (resultSet.next()) {
					currencies.add(extractCurrency(resultSet));
				}

				callback.accept(null, currencies);
			} catch (Exception e) {
				resolveCallback(callback, e);
			}
		}));
	}

	public void deleteCurrency(@NonNull final String id, Callback<Boolean> callback) {
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			try (PreparedStatement statement = connection.prepareStatement("DELETE FROM " + this.getTablePrefix() + "currency WHERE id = ?")) {
				statement.setString(1, id);

				int result = statement.executeUpdate();
				callback.accept(null, result > 0);

			} catch (Exception e) {
				resolveCallback(callback, e);
			}
		}));
	}

	public void updateCurrency(final boolean silent, @NonNull final Currency currency, Callback<Boolean> callback) {
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			long begin = System.nanoTime();
			try (PreparedStatement statement = connection.prepareStatement("UPDATE " + this.getTablePrefix() + "currency SET name = ?, description = ?, icon = ?, singular_format = ?, plural_format = ?, starting_balance = ?, withdraw_allowed = ?, pay_allowed = ?, is_vault_currency = ? WHERE id = ?")) {

				statement.setString(1, currency.getName());
				statement.setString(2, currency.getDescription());
				statement.setString(3, currency.getIcon().name());
				statement.setString(4, currency.getSingularFormat());
				statement.setString(5, currency.getPluralFormat());
				statement.setDouble(6, currency.getStartingBalance());
				statement.setBoolean(7, currency.isWithdrawAllowed());
				statement.setBoolean(8, currency.isPayingAllowed());
				statement.setBoolean(9, currency.isVaultCurrency());
				statement.setString(10, currency.getId());

				int result = statement.executeUpdate();
				if (!silent)
					Common.log(String.format("&fSynced &b%s &fcurrency to data file in &a%s&f ms", currency.getId(), String.format("%,.3f", (System.nanoTime() - begin) / 1e+6)));

				if (callback != null)
					callback.accept(null, result > 0);

			} catch (Exception e) {
				resolveCallback(callback, e);
			}
		}));
	}

	//update funds_currency SET is_vault_currency = false WHERE id != 'coins'
	public void updateVaultCurrency(@NonNull final Currency currency, Callback<Boolean> callback) {
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			try (PreparedStatement statement = connection.prepareStatement("UPDATE " + this.getTablePrefix() + "currency SET is_vault_currency = FALSE where id != ?")) {

				statement.setString(1, currency.getName());

				int result = statement.executeUpdate();

				if (callback != null)
					callback.accept(null, result > 0);

			} catch (Exception e) {
				resolveCallback(callback, e);
			}
		}));
	}

	// =================================================== //
	//					   EXTRACTIONS
	// =================================================== //

	private Account extractAccount(@NotNull final ResultSet resultSet) throws SQLException {
		return new FundAccount(
				UUID.fromString(resultSet.getString("id")),
				resultSet.getString("name"),
				FundAccount.getCurrencyMapFromJson(resultSet.getString("currencies")),
				resultSet.getBoolean("bal_top_blocked"),
				Locale.getLanague(resultSet.getString("preferred_language")),
				resultSet.getLong("created_at")

		);
	}

	private Currency extractCurrency(@NotNull final ResultSet resultSet) throws SQLException {
		return new FundCurrency(
				resultSet.getString("id"),
				resultSet.getString("name"),
				resultSet.getString("description"),
				CompMaterial.matchCompMaterial(resultSet.getString("icon")).orElse(CompMaterial.GOLD_INGOT),
				resultSet.getString("singular_format"),
				resultSet.getString("plural_format"),
				resultSet.getBoolean("withdraw_allowed"),
				resultSet.getBoolean("pay_allowed"),
				resultSet.getDouble("starting_balance"),
				resultSet.getBoolean("is_vault_currency")
		);
	}

	private void resolveUpdateCallback(@Nullable UpdateCallback callback, @Nullable Exception ex) {
		if (callback != null) {
			callback.accept(ex);
		} else if (ex != null) {
			ex.printStackTrace();
		}
	}

	private void resolveCallback(@Nullable Callback<?> callback, @NotNull Exception ex) {
		if (callback != null) {
			callback.accept(ex, null);
		} else {
			ex.printStackTrace();
		}
	}

	private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		for (int x = 1; x <= columns; x++) {
			if (columnName.equals(rsmd.getColumnName(x))) {
				return true;
			}
		}
		return false;
	}
}
