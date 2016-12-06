package imabradley.timedrewards.menus;

import imabradley.timedrewards.util.abstracts.Menu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RewardsMenu extends Menu
{


	public RewardsMenu(String title, int size)
	{
		super(title, size);
	}

	@Override
	public void click(Player player, ItemStack itemStack)
	{

	}

	@Override
	public Inventory getInventory()
	{
		return null;
	}
}
