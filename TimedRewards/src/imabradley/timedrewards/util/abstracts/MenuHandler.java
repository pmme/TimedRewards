package imabradley.timedrewards.util.abstracts;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MenuHandler implements Listener
{
	private Map<String, Menu> menus = new HashMap<>();

	public MenuHandler(Plugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public void addMenu(String name, Menu menu) { menus.put(name, menu); }

	public Collection<Menu> getMenus() { return menus.values(); }

	public Menu getMenu(String name) { return menus.get(name); }

	@EventHandler
	public void onClick(InventoryDragEvent event)
	{
		Inventory inv = event.getWhoClicked().getOpenInventory().getTopInventory();
		for (Menu menu : menus.values())
		{
			if (menu.getInventory().getName().equals(inv.getName()))
			{
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		Inventory inv = player.getOpenInventory().getTopInventory();
		for (Menu menu : menus.values())
		{
			if (menu.getInventory().getName().equals(inv.getName()))
			{
				event.setCancelled(true);
				menu.click(player, event.getCurrentItem());
			}
		}
	}
}