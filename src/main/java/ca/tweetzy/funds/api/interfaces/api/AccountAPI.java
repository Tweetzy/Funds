package ca.tweetzy.funds.api.interfaces.api;

import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.api.interfaces.Currency;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Date Created: April 13 2022
 * Time Created: 11:45 p.m.
 *
 * @author Kiran Hart
 */
public interface AccountAPI {

	/**
	 * Used to create a new {@link Account}
	 *
	 * @param account  is the {@link Account} to be created
	 * @param consumer returns true if the account was created, along with the created account
	 */
	void createAccount(@NonNull final Account account, final BiConsumer<Boolean, Account> consumer);

	/**
	 * Used to update multiple {@link Account}s
	 *
	 * @param accounts is a list of all {@link Account}s to be updated
	 * @param consumer returns true if <b>ALL</b> updates were updated
	 */
	void updateAccounts(@NonNull final List<Account> accounts, final Consumer<Boolean> consumer);

	/**
	 * Add an {@link Account} to the account storage list
	 *
	 * @param account is the {@link Account} being added;
	 */
	void addAccount(@NonNull final Account account);

	/**
	 * Remove an {@link Account} from the account storage list
	 *
	 * @param uuid is the {@link Account} uuid being removed;
	 */
	void removeAccount(@NonNull final UUID uuid);

	/**
	 * Get a player {@link Account} by their name
	 *
	 * <b>
	 * Please don't use this, use {@link #getAccount(OfflinePlayer)} or {@link #getAccount(UUID)}
	 * </b>
	 * <p>
	 * It's already ran async if you need to use it for whatever reason.
	 *
	 * @param name is the name of the player
	 * @return the found {@link Account} or null
	 */
	Account getAccount(@NonNull final String name);

	/**
	 * Get the player {@link Account} by the player instance
	 *
	 * @param player is the {@link Player} being searched
	 * @return the found {@link Account} or null
	 */
	Account getAccount(@NonNull final OfflinePlayer player);

	/**
	 * Get the player {@link Account} by the player {@link UUID}
	 *
	 * @param id is the {@link Player#getUniqueId()} of the user
	 * @return the found {@link Account} or null
	 */
	Account getAccount(@NonNull final UUID id);

	/**
	 * Get an unmodifiable list of all loaded
	 * player {@link Account}s
	 *
	 * @return a list of {@link Account}s
	 */
	List<Account> getAccounts();

	/**
	 * Used to reset all the known player {@link Account}s
	 * back to the starting {@link Currency} balances
	 * <p>
	 * This is run asynchronously
	 */
	void resetPlayerAccountsBalances();
}
