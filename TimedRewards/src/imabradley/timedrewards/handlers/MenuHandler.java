package imabradley.timedrewards.handlers;

import imabradley.timedrewards.TimedRewards;
import imabradley.timedrewards.events.RewardsMenuClickEvent;
import imabradley.timedrewards.menus.RewardsMenu;
import imabradley.timedrewards.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class MenuHandler implements Listener
{
	private static HashMap<UUID, RewardsMenu> rewardsMenus = new HashMap<>();

	public MenuHandler(Plugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
    }

	public static void addRewardMenu(Player player, RewardsMenu menu) { rewardsMenus.put(player.getUniqueId(), menu); }

	public void openRewardsMenu(Player player) { player.openInventory(this.getRewardsMenu(player).getInventory()); }

	public RewardsMenu getRewardsMenu(Player player)
	{
		return rewardsMenus.get(player.getUniqueId()) != null ? rewardsMenus.get(player.getUniqueId()) : new RewardsMenu(player);
	}

    @EventHandler
    public void onClick(InventoryClickEvent event)
	{
        ItemStack itemStack = event.getCurrentItem();

		if (itemStack == null) return;

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

		if (inventory.getName().equals(this.getRewardsMenu(player).getInventory().getName()))
		{
			event.setCancelled(true);

			final RewardsMenuClickEvent rEvent = new RewardsMenuClickEvent(player, inventory, itemStack);
            TimedRewards.getPlugin().getServer().getPluginManager().callEvent(rEvent);

			FileConfiguration config = TimedRewards.getYamlHandler().getConfig();
			String path = "menus.rewards.reward-items";

			for (String s : config.getConfigurationSection(path).getKeys(false))
			{
				String ipath = path + "." + s;

				try
				{
					if (itemStack.getItemMeta().getDisplayName() != null && itemStack.getItemMeta().getDisplayName().equals(
							Util.colour(config.getString(ipath + ".name"))))
					{
						//TODO check if permission, check time remaining, reward.
						YamlConfiguration pconfig = TimedRewards.getYamlHandler().getPlayerYaml(player);
					}
				}
				catch (NullPointerException e) {}
			}
		}
	}
}