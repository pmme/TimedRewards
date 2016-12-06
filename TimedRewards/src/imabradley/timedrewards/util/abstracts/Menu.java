package imabradley.timedrewards.util.abstracts;

import imabradley.timedrewards.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class Menu implements InventoryHolder
{
	private Inventory inventory;
	private int size;

	public Menu(String title, int size)
	{
		inventory = Bukkit.createInventory(null, size, Util.colour(title));
		this.size = size;
	}

	public void show(Player player) { player.openInventory(inventory); }

	public abstract void click(Player player, ItemStack itemStack);

	public Inventory getInventory() { return inventory; }

	public int getSize() { return size; }
}