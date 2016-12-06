package imabradley.timedrewards;

import imabradley.timedrewards.util.Util;
import imabradley.timedrewards.util.abstracts.MenuHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class TimedRewards extends JavaPlugin
{
	private static TimedRewards plugin;
	private static MenuHandler menuHandler;


	@Override
	public void onEnable()
	{
		Util.log("Plugin enabled (version: " + this.getDescription().getVersion() + ") by ImABradley.");
		plugin = this;

		menuHandler = new MenuHandler(this);
	}

	@Override
	public void onDisable()
	{
		Util.log("Plugin disabled (version: " + this.getDescription().getVersion() + ") by ImABradley.");
	}

	public static TimedRewards getPlugin() { return plugin; }
}