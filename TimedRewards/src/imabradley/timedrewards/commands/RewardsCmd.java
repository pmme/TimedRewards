package imabradley.timedrewards.commands;

import imabradley.timedrewards.TimedRewards;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RewardsCmd implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{

		if (sender instanceof Player)
		{
			Player player = (Player) sender;

			player.openInventory(TimedRewards.getMenuHandler().getRewardsMenu().getInventory());
		}

		return false;
	}
}
