package imabradley.timedrewards;

import imabradley.timedrewards.commands.RewardsCmd;
import imabradley.timedrewards.handlers.MenuHandler;
import imabradley.timedrewards.util.Metrics;
import imabradley.timedrewards.util.Util;
import imabradley.timedrewards.util.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class TimedRewards extends JavaPlugin
{
	private static TimedRewards plugin;
	private static MenuHandler menuHandler;
	private static Config config;

	@Override
	public void onEnable()
	{
		Util.log("Plugin enabled (version: " + this.getDescription().getVersion() + ") by ImABradley.");
		plugin = this;
		menuHandler = new MenuHandler(this);
		config = new Config();

		Util.loadResource("config.yml");
		Util.loadResource("messages.yml");

		this.getCommand("timedrewards").setExecutor(new RewardsCmd());

		try
		{
			if (plugin.getConfig().getBoolean("metrics-enabled"))
			{
				final Metrics metrics = new Metrics(this);

				if (metrics.start())
				{
					Util.log("Metrics successfully enabled!");
				}
			}
		}
		catch (Exception e)
		{
			Util.log("[Exception] An exception occurred when trying to get the value of 'metrics-enabled':");
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable()
	{
		Util.log("Plugin disabled (version: " + this.getDescription().getVersion() + ") by ImABradley.");
	}

	public static void reloadPlugin()
	{
		Util.log("Reloading plugin..");

		menuHandler = new MenuHandler(TimedRewards.getPlugin());
		config.reload();

		Util.log("Plugin successfully reloaded.");
	}

	public static TimedRewards getPlugin() { return plugin; }

	public static MenuHandler getMenuHandler() { return menuHandler; }
}