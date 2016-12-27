package imabradley.timedrewards.commands;

import imabradley.timedrewards.TimedRewards;
import imabradley.timedrewards.util.Util;
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
				player.openInventory(TimedRewards.getMenuHandler().getRewardsMenu(player).getInventory());
			}
			else
			{
				if (args.length >= 1)
				{
					if (args[0].equalsIgnoreCase("help"))
					{
						Util.messagePlayer(player, "&8-- &6TimedRewards &7Commands Help &8--");
						Util.messagePlayer(player, "&a/tr help &8- &7Displays this page.");

						return true;
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
