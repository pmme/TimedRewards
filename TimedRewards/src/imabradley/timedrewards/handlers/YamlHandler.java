package imabradley.timedrewards.handlers;

import imabradley.timedrewards.TimedRewards;
import imabradley.timedrewards.util.Util;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlHandler
{
	private FileConfiguration config;
	private YamlConfiguration messages;
	private String pdfile = TimedRewards.getPlugin().getDataFolder() + File.separator + "playerdata";

	public void Config()
	{
		this.config = TimedRewards.getPlugin().getConfig();
		this.messages = YamlConfiguration.loadConfiguration(Util.loadResource("messages.yml"));
	}

	public void reload()
	{
		TimedRewards.getPlugin().reloadConfig();
		this.config = TimedRewards.getPlugin().getConfig();
		this.messages = YamlConfiguration.loadConfiguration(Util.loadResource("messages.yml"));
	}

	public String getMessage(String msg)
	{
		Object result = messages.get(msg);

		if (result == null || !(result instanceof String))
		{
			Util.log("[Exception] A NullPointerException occurred when trying to get the message \"" + msg + "\":");
		}
		else
		{
			result = Util.colour((String) result);
		}

		return (String) result;
	}

	String path = "menus.rewards.reward-items";

	public YamlConfiguration getPlayerYaml(OfflinePlayer player)
	{
		File file = new File(pdfile, player.getUniqueId() + ".yml");
		YamlConfiguration pconfig = YamlConfiguration.loadConfiguration(file);

		if (!file.exists())
		{
			pconfig.set("current-name", player.getName());
			pconfig.set("uuid", player.getUniqueId());

			try
			{
				for (String s : config.getConfigurationSection(path).getKeys(false))
				{
					pconfig.set("rewards." + s + ".can-claim", true);
					pconfig.set("rewards." + s + ".next-claim", config.get(path + s + ".time"));
				}

				pconfig.save(file);
			}
			catch (IOException | NullPointerException e)
			{
				Util.log("[Exception] An exception occurred when saving " + Util.getNameEnding(
						player.getName()) + " config " + "file:");
				e.printStackTrace();
			}
		}

		return pconfig;
	}

	public void savePlayerYaml(OfflinePlayer player, YamlConfiguration pconfig)
	{
		File file = new File(pdfile, player.getUniqueId() + ".yml");

		if (pconfig == null) pconfig = getPlayerYaml(player);

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

	public FileConfiguration getConfig() { return config; }

	public YamlConfiguration getMessages() { return messages; }
}