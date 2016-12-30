package imabradley.timedrewards.handlers;

import imabradley.timedrewards.TimedRewards;
import imabradley.timedrewards.util.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class YamlHandler
{
	private FileConfiguration config;
	private YamlConfiguration messages;
	private String pdfile;

	public YamlHandler(Plugin plugin)
	{
		this.config = plugin.getConfig();
		this.messages = YamlConfiguration.loadConfiguration(Util.loadResource("messages.yml"));
		this.pdfile = plugin.getDataFolder() + File.separator + "playerdata";
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

	public String getPrefix()
	{
		return this.getMessage("prefix");
	}

	public YamlConfiguration getPlayerYaml(Player player)
	{
		File file = new File(pdfile, player.getUniqueId().toString() + ".yml");
		YamlConfiguration pconfig = YamlConfiguration.loadConfiguration(file);
		String path = "menus.rewards.reward-items";

		if (!file.exists())
		{
			pconfig.set("name", player.getName());
			pconfig.set("uuid", player.getUniqueId().toString());

			for (String s : config.getConfigurationSection(path).getKeys(false))
			{
				if (config.getBoolean("first-time-claim"))
				{
					pconfig.set("rewards." + s + ".claim-time", 0);
				}
				else
				{
					pconfig.set("rewards." + s + ".claim-time", System.currentTimeMillis());
				}
			}

			try
			{
				pconfig.save(file);
			}
			catch (IOException e)
			{
				Util.log("[Error] An IOException occurred when saving " + Util.getNameEnding(player.getName()) + " config " + "file:");
				e.printStackTrace();
			}
		}

		return pconfig;
	}

	public void savePlayerYaml(Player player, YamlConfiguration pconfig)
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