package imabradley.timedrewards.listeners;

import imabradley.timedrewards.util.Playerdata;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener
{
	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		YamlConfiguration config = Playerdata.getYaml(player);
	}
}