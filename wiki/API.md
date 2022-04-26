# Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>ca.tweetzy</groupId>
    <artifactId>funds</artifactId>
    <version>ENTER-RELEASE-VERSION</version>
</dependency>
```

# Gradle

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'ca.tweetzy:funds:-SNAPSHOT'
}
```

# Funds API

You can access the API from FundsAPI class.

```java
public interface FundAPI {

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

	/**
	 * Used to add a currency to the storage list
	 *
	 * @param currency is the {@link Currency} being added
	 */
	void addCurrency(@NonNull final Currency currency);

	/**
	 * Used to remove a currency for the storage list
	 *
	 * @param currency is the {@link Currency} being removed
	 */
	void removeCurrency(@NonNull final Currency currency);

	/**
	 * Used to remove a currency for the storage list by id
	 *
	 * @param id is the {@link Currency} id
	 */
	void removeCurrency(@NonNull final String id);

	/**
	 * Used to get a currency by its id
	 *
	 * @param id is the id of the currency {@link Currency}
	 * @return the found {@link Currency} or null
	 */
	Currency getCurrency(@NonNull final String id);

	/**
	 * Used to set the currency that plugin should use
	 * as the vault currency
	 *
	 * @param currency is {@link Currency} to be used as the vault/default
	 */
	void setVaultCurrency(@NonNull final Currency currency);

	/**
	 * Gets the vault currency or the first found
	 * currency within the storage list
	 *
	 * @return {@link Currency}, can be null if no currencies are made
	 */
	Currency getVaultOrFirst();

	/**
	 * Used to create a new currency, this will automatically call {@link #addCurrency}
	 *
	 * @param currency is the {@link Currency} to be created
	 * @param consumer returns true if the currency was created, also returns the created {@link Currency}
	 */
	void createCurrency(@NonNull final Currency currency, final BiConsumer<Boolean, Currency> consumer);

	/**
	 * Used to delete an existing currency, this will automatically call {@link #removeCurrency}
	 *
	 * @param id is the {@link Currency} id to be deleted
	 * @param wasDeleted returns true if the currency was deleted
	 */
	void deleteCurrency(@NonNull final String id, final Consumer<Boolean> wasDeleted);

	/**
	 * Get an unmodifiable list of known currencies
	 *
	 * @return an unmodifiable list of {@link Currency}
	 */
	List<Currency> getCurrencies();

	/**
	 * Get the default starting balance for every known currency
	 *
	 * @return the starting balance for each {@link Currency}
	 */
	HashMap<Currency, Double> getDefaultValueMap();
}
```