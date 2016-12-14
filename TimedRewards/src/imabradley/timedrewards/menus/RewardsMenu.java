package imabradley.timedrewards.menus;

import imabradley.timedrewards.TimedRewards;
import imabradley.timedrewards.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

public class RewardsMenu
{
	private Inventory inventory;

	public RewardsMenu()
	{
		FileConfiguration config = TimedRewards.getPlugin().getConfig();

		inventory = Bukkit.createInventory(null, 23, config.getString("menus.rewards.title"));

		for (String s : config.getConfigurationSection("menus.rewards").getKeys(false))
		{
			try
			{

			}
			catch (NullPointerException e)
			{
				Util.log("[Exception] A NullPointerException occurred when creating the Rewards Menu:");
				e.printStackTrace();
			}
		}
	}

    public Inventory getInventory() {
        return inventory;
    }
}