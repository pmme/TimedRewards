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

import java.util.Date;
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
			String shortPath = "menus.rewards.reward-items";

			for (String s : config.getConfigurationSection(shortPath).getKeys(false))
			{
				String path = shortPath + "." + s;
				Date date = new Date();

				try
				{
					if (itemStack.getItemMeta().getDisplayName() != null && itemStack.getItemMeta().getDisplayName()
							.equals(Util.colour(config.getString(path + ".name"))))
					{
						YamlConfiguration pconfig = TimedRewards.getYamlHandler().getPlayerYaml(player);

						if (pconfig.get("rewards." + s + ".claim-time") != null)
						{
							long next = pconfig.getLong("rewards." + s + ".claim-time");

							if (date.getTime() > (next + (config.getLong(path + ".time") * 1000)))
							{
								if (config.get(path + ".permission") == null || player.hasPermission(config.getString(path + ".permission")))
								{
									for (String cmd : config.getStringList(path + ".claim-reward-cmds"))
									{
										Bukkit.getServer()
												.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("{player}", player
														.getName()).replace("{uuid}", player.getUniqueId().toString()));
									}

									Util.log("[Claim] " + player.getName() + " has claimed the reward " + s + ".");
									pconfig.set("rewards." + s + "claim-time", date.getTime());
								}
								else
								{
									Util.messagePlayer(player, TimedRewards.getYamlHandler()
											.getMessage("no-claim-permission")
											.replace("{prefix}", TimedRewards.getYamlHandler().getPrefix())
											.replace("{reward}", s));
								}
							}
							else
							{
								Util.messagePlayer(player, "You cannot claim that at the moment ~TODO");
							}
						}
						else
						{
							pconfig.set("rewards." + s + ".claim-time", date.getTime());
						}
					}
				}
				catch (NullPointerException e)
				{
				}

				player.closeInventory();
			}
		}
	}
}