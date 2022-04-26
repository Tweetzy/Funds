package ca.tweetzy.funds.commands;

import ca.tweetzy.funds.Funds;
import ca.tweetzy.funds.api.interfaces.Account;
import ca.tweetzy.funds.guis.player.LanguagePickerGUI;
import ca.tweetzy.funds.settings.Locale;
import ca.tweetzy.funds.settings.Translation;
import ca.tweetzy.rose.command.AllowedExecutor;
import ca.tweetzy.rose.command.Command;
import ca.tweetzy.rose.command.ReturnType;
import ca.tweetzy.rose.utils.Common;
import ca.tweetzy.rose.utils.Replacer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Date Created: April 10 2022
 * Time Created: 12:38 a.m.
 *
 * @author Kiran Hart
 */
public final class LanguageCommand extends Command {

	public LanguageCommand() {
		super(AllowedExecutor.PLAYER, "language");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		final Player player = (Player) sender;

		final Account account = Funds.getAccountManager().getAccount(player);

		Funds.getGuiManager().showGUI(player, new LanguagePickerGUI(null, account, (e, language) -> {
			if (account.getPreferredLanguage() != language) {
				account.setPreferredLanguage(language);
				account.sync(true);

				e.gui.exit();
				Common.tell(player, Replacer.replaceVariables(Locale.getString(Translation.UPDATED_LANGUAGE.getKey()), "language", language.getName()));
			}
		}));

		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "funds.cmd.language";
	}

	@Override
	public String getSyntax() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Brings up the language selector menu";
	}
}
