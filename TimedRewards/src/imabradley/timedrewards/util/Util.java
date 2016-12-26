package imabradley.timedrewards.util;

import com.google.common.io.ByteStreams;
import imabradley.timedrewards.TimedRewards;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

public class Util
{
	private static Logger logger = Bukkit.getLogger();

	public static void messagePlayer(Player player, String msg)
	{
		player.sendMessage(Util.colour(msg));
	}

	public static void log(String msg) { logger.info("[TimedRewards] " + msg); }

	public static String colour(String msg)
	{
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public static String getNameEnding(String name)
	{
		if (name.endsWith("s") || name.endsWith("z")) return name + '\'';

		return name + "'s";
	}

	public static File loadResource(String resource)
	{
		File folder = TimedRewards.getPlugin().getDataFolder();
		if (!folder.exists()) folder.mkdir();

		File file = new File(folder, resource);

		try
		{
			if (!file.exists())
			{
				file.createNewFile();

				try (InputStream in = TimedRewards.getPlugin().getResource(
						resource); OutputStream out = new FileOutputStream(file))
				{
					ByteStreams.copy(in, out);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return file;
	}
}