package imabradley.timedrewards.util.config;

import imabradley.timedrewards.TimedRewards;
import imabradley.timedrewards.util.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config
{
	private FileConfiguration config;
	private YamlConfiguration messages;

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

	public FileConfiguration getConfig() { return config; }

	public YamlConfiguration getMessages() { return messages; }
}