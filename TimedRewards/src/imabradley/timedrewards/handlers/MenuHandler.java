package imabradley.timedrewards.handlers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;


public class MenuHandler implements Listener
{

	private HashMap<String, Inventory> menus = new HashMap<>();

	public void MenuHandler(Plugin plugin)
	{
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public HashMap<String, Inventory> getMenus() { return menus; }

	public Inventory getMenu(String name) { return menus.get(name); }

	@EventHandler
	public void onClick(InventoryClickEvent event)
	{

	}
}