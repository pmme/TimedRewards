package imabradley.timedrewards.listeners;

import imabradley.timedrewards.TimedRewards;
import imabradley.timedrewards.util.Util;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Listener
{

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInteract(PlayerInteractEntityEvent event)
	{
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();

		if (entity.getCustomName() != null)
		{
			for (String s : TimedRewards.getYamlHandler().getConfig().getStringList("npc-names"))
			{
				if (entity.getCustomName().equals(Util.colour(s)))
				{
					TimedRewards.getMenuHandler().openRewardsMenu(player);
				}
			}
		}
	}
}