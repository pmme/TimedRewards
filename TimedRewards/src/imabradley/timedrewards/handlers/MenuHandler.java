package imabradley.timedrewards.handlers;

import imabradley.timedrewards.TimedRewards;
import imabradley.timedrewards.events.RewardsMenuClickEvent;
import imabradley.timedrewards.menus.RewardsMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class MenuHandler implements Listener
{
    private RewardsMenu rewardsMenu;

    public MenuHandler(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);

        rewardsMenu = new RewardsMenu();
    }

    public RewardsMenu getRewardsMenu() {
        return rewardsMenu;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event)
	{
        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null || itemStack.getItemMeta().getDisplayName() == null) return;

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if (inventory.getName().equals(this.rewardsMenu.getInventory().getName())) {


            final RewardsMenuClickEvent rEvent = new RewardsMenuClickEvent(player, inventory, itemStack);
            TimedRewards.getPlugin().getServer().getPluginManager().callEvent(rEvent);
        }
	}
}