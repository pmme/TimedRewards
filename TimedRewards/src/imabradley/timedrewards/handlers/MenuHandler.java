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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MenuHandler implements Listener
{
	private static HashMap<UUID, RewardsMenu> rewardsMenus = new HashMap<>();
	private static ArrayList<UUID> runningTasks = new ArrayList<>();

	public MenuHandler(Plugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
    }

	public static void addRewardMenu(Player player, RewardsMenu menu) { rewardsMenus.put(player.getUniqueId(), menu); }

	public void openRewardsMenu(Player player) { player.openInventory(this.getRewardsMenu(player).getInventory()); }

	public RewardsMenu getRewardsMenu(Player player)
	{
		RewardsMenu rewardsMenu = rewardsMenus.get(player.getUniqueId());

		if (rewardsMenu != null)
		{
			rewardsMenu.update();
			return rewardsMenu;
		}

		return new RewardsMenu(player);
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

				try
				{
					if (itemStack.getItemMeta().getDisplayName() != null && itemStack.getItemMeta().getDisplayName()
							.equals(Util.colour(config.getString(path + ".name"))))
					{
						YamlConfiguration pconfig = TimedRewards.getYamlHandler().getPlayerYaml(player);

						if (pconfig.get("rewards." + s + ".claim-time") != null)
						{
							long next = pconfig.getLong("rewards." + s + ".claim-time");

							if (System.currentTimeMillis() > (next + (config.getLong(path + ".time") * 1000)))
							{
								if (config.get(path + ".permission") == null || player.hasPermission(config.getString(path + ".permission")))
								{
									pconfig.set("rewards." + s + ".claim-time", System.currentTimeMillis());
									if (config.getBoolean("close-inventory-on-claim"))
									{
										player.closeInventory();
									}

									for (String cmd : config.getStringList(path + ".claim-reward-cmds"))
									{
										Bukkit.getServer()
												.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("{player}", player
														.getName()).replace("{uuid}", player.getUniqueId().toString()));
									}

									Util.log("[Claim] " + player.getName() + " has claimed the reward " + s + ".");
								}
								else
								{
									if (config.getBoolean("close-inventory-on-claim"))
									{
										player.closeInventory();
									}

									Util.messagePlayer(player, TimedRewards.getYamlHandler()
											.getMessage("no-claim-permission")
											.replace("{prefix}", TimedRewards.getYamlHandler().getPrefix())
											.replace("{reward}", s));
								}
							}
							else
							{
								if (config.getBoolean("close-inventory-on-claim"))
								{
									player.closeInventory();
								}

								Util.messagePlayer(player, TimedRewards.getYamlHandler()
										.getMessage("already-claimed")
										.replace("{prefix}", TimedRewards.getYamlHandler().getPrefix()));
							}
						}
						else
						{
							pconfig.set("rewards." + s + ".claim-time", System.currentTimeMillis());
						}

						TimedRewards.getYamlHandler().savePlayerYaml(player, pconfig);
					}
				}
				catch (NullPointerException e)
				{
				}
			}
		}
	}

	@EventHandler public void onOpen(InventoryOpenEvent event)
	{
		Player player = (Player) event.getPlayer();
		Inventory inventory = event.getInventory();

		if (inventory.getName().equals(this.getRewardsMenu(player).getInventory().getName()))
		{
			RewardsMenu rewardsMenu = this.getRewardsMenu(player);
			runningTasks.add(player.getUniqueId());

			new BukkitRunnable()
			{
				@Override public void run()
				{
					if (player.isOnline() && runningTasks.contains(player.getUniqueId()))
					{
						rewardsMenu.update();
					}
					else cancel();
				}
			}.runTaskTimerAsynchronously(TimedRewards.getPlugin(), 0L, 20L);
		}
	}

	@EventHandler public void onClose(InventoryCloseEvent event)
	{
		Player player = (Player) event.getPlayer();

		if (runningTasks.contains(player.getUniqueId()))
		{
			runningTasks.remove(player.getUniqueId());
		}
	}
}