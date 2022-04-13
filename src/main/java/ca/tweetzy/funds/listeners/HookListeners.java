package ca.tweetzy.funds.listeners;

import ca.tweetzy.funds.hooks.VaultHook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

/**
 * Date Created: April 12 2022
 * Time Created: 10:32 p.m.
 *
 * @author Kiran Hart
 */
public final class HookListeners implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPluginLoad(final PluginEnableEvent event) {
		final Plugin plugin = event.getPlugin();

		if (plugin.getName().equals("Vault"))
			new VaultHook();
	}
}
