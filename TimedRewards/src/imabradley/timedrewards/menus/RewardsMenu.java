package imabradley.timedrewards.menus;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class RewardsMenu
{
	private Inventory inventory;

	public RewardsMenu()
	{
        inventory = Bukkit.createInventory(null, 23, "");
    }

    public Inventory getInventory() {
        return inventory;
    }
}