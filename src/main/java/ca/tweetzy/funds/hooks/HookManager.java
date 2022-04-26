package ca.tweetzy.funds.hooks;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;

/**
 * Date Created: April 26 2022
 * Time Created: 11:58 a.m.
 *
 * @author Kiran Hart
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HookManager {

	@Getter
	private static final HookManager instance = new HookManager();

	@Getter
	private VaultHook vaultHook;

	public void registerVault() {
		if (this.vaultHook == null)
			this.vaultHook = new VaultHook();
	}

	public void unregisterVault() {
		if (this.vaultHook == null) return;
		this.vaultHook.unregister();
		this.vaultHook = null;
	}

	public void registerPlaceholders() {
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new PlaceholderAPIHook().register();
		}
	}
}
