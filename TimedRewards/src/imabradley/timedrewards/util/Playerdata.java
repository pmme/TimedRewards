package imabradley.timedrewards.util;

import imabradley.timedrewards.TimedRewards;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Playerdata
{
	private static String pdfile = TimedRewards.getPlugin().getDataFolder() + File.separator + "playerdata";
	private static FileConfiguration config = TimedRewards.getPlugin().getConfig();
	private static String path = "menus.rewards.reward-items";

	public static YamlConfiguration getYaml(OfflinePlayer player)
	{
		File file = new File(pdfile, player.getUniqueId() + ".yml");
		YamlConfiguration pconfig = YamlConfiguration.loadConfiguration(file);

		if (!file.exists())
		{


			try
			{
				pconfig.save(file);
			}
			catch (IOException e)
			{
				Util.log(
						"An error occurred when saving " + Util.getNameEnding(player.getName()) + " config " + "file:");
				e.printStackTrace();
			}
		}

		return pconfig;
	}

	public static void saveYaml(OfflinePlayer player, YamlConfiguration pconfig)
	{
		File file = new File(pdfile, player.getUniqueId() + ".yml");

		if (pconfig == null) pconfig = Playerdata.getYaml(player);

		try
		{
			pconfig.save(file);
		}
		catch (IOException e)
		{
			Util.log("An error occurred when saving " + Util.getNameEnding(player.getName()) + " config " + "file:");
			e.printStackTrace();
		}
	}
}