package imabradley.timedrewards.commands;

import imabradley.timedrewards.TimedRewards;
import imabradley.timedrewards.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class RewardsCmd implements CommandExecutor
{

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
						Util.messagePlayer(player, "&a/tr help &8- &7Displays this page.");
						Util.messagePlayer(player, "&a/tr open&8|&a/rewards &8- &7Opens the rewards menu.");
						Util.messagePlayer(player, "&a/tr claim &7<reward-name> &8- &7Attempts to claim the reward.");
					}
					else if (args[0].equalsIgnoreCase("open"))
					{
						TimedRewards.getMenuHandler().openRewardsMenu(player);
					}
					else if (args[0].equalsIgnoreCase("reward"))
					{
						if (args.length == 2)
						{

						}
						else
						{
							Util.messagePlayer(player, TimedRewards.getYamlHandler()
									.getMessage("invalid-arguments")
									.replace("{prefix}", TimedRewards.getYamlHandler().getPrefix())
									.replace("{args}", "/tr claim <reward-name>"));
						}
					}
				}
				else
				{

				}
			}
		}
		else if (sender instanceof ConsoleCommandSender)
		{

		}

		return false;
	}
}
