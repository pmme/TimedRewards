package imabradley.timedrewards.commands;

import imabradley.timedrewards.TimedRewards;
import imabradley.timedrewards.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RewardsCmd implements CommandExecutor
{
    //
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;

			if (label.equalsIgnoreCase("rewards"))
			{
				TimedRewards.getMenuHandler().openRewardsMenu(player);
			}
			else
			{
				if (args.length >= 1)
				{
					if (args[0].equalsIgnoreCase("help"))
					{
						Util.messagePlayer(player, "&8-- &6TimedRewards &7Commands Help &8--");
						Util.messagePlayer(player, "&e/tr help &8- &7Displays this page.");
						Util.messagePlayer(player, "&e/rewards &8- &7Opens the rewards menu.");
					}
					else if (args[0].equalsIgnoreCase("open"))
					{
						TimedRewards.getMenuHandler().openRewardsMenu(player);
					}
				}
				else
				{
					Util.messagePlayer(player, TimedRewards.getYamlHandler()
							.getMessage("invalid-arguments")
							.replace("{prefix}", TimedRewards.getYamlHandler().getPrefix())
							.replace("{args}", "/tr help"));
				}
			}
		}
		else if (sender instanceof ConsoleCommandSender)
		{
			if (args.length >= 3)
			{
				if (args[0].equalsIgnoreCase("message") || args[0].equalsIgnoreCase("msg"))
				{
					Player player = Bukkit.getPlayer(args[1]);

					if (player != null)
					{
						StringBuilder msg = new StringBuilder();

						for (int i = 2; i < args.length; i++)
						{
							msg.append(args[i] + " ");
						}

						Util.messagePlayer(player, msg.toString());
					}
					else
					{
						Util.log("[Error] Could not find player: " + args[1]);
					}
				}
			}
			else
			{
				Util.log("-- TimedRewards Console Command Help --");
				Util.log("tr msg <player> <msg> - Sends a message without prefix to specified player.");
			}
		}

		return false;
	}
}
