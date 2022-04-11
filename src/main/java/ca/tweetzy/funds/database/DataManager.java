package ca.tweetzy.funds.database;

import ca.tweetzy.funds.api.interfaces.Currency;
import ca.tweetzy.funds.impl.FundCurrency;
import ca.tweetzy.rose.comp.enums.CompMaterial;
import ca.tweetzy.rose.database.Callback;
import ca.tweetzy.rose.database.DataManagerAbstract;
import ca.tweetzy.rose.database.DatabaseConnector;
import ca.tweetzy.rose.database.UpdateCallback;
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
	//					  CURRENCY STUFF
	// =================================================== //

	public void createCurrency(@NotNull final Currency currency, Callback<Currency> callback) {
		this.runAsync(() -> this.databaseConnector.connect(connection -> {
			final String query = "INSERT INTO " + this.getTablePrefix() + "currency (id, name, description, icon, starting_balance) VALUES (?, ?, ?, ?, ?)";
			final String fetchQuery = "SELECT * FROM " + this.getTablePrefix() + "currency WHERE id = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				final PreparedStatement fetch = connection.prepareStatement(fetchQuery);

				fetch.setString(1, currency.getId().toLowerCase());

				preparedStatement.setString(1, currency.getId().toLowerCase());
				preparedStatement.setString(2, currency.getName());
				preparedStatement.setString(3, currency.getDescription());
				preparedStatement.setString(4, currency.getIcon().name());
				preparedStatement.setDouble(5, currency.getStartingBalance());

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

			} catch(Exception e) {
				resolveCallback(callback, e);
			}
		}));
	}

	// =================================================== //
	//					   EXTRACTIONS
	// =================================================== //

	private Currency extractCurrency(@NotNull final ResultSet resultSet) throws SQLException {
		return new FundCurrency(
				resultSet.getString("id"),
				resultSet.getString("name"),
				resultSet.getString("description"),
				CompMaterial.matchCompMaterial(resultSet.getString("icon")).orElse(CompMaterial.GOLD_INGOT),
				resultSet.getDouble("starting_balance")
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
