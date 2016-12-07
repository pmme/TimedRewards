package imabradley.timedrewards.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Event fired when a player clicks with the RewardMenu open.
 *
 * @author ImABradley
 */
public class RewardsMenuClickEvent extends Event
{
	private final HandlerList handlerList = new HandlerList();

	private Player player;
	private Inventory inventory;
	private ItemStack item;

	/**
	 * @param player    The player who clicked.
	 * @param inventory The inventory open upon click.
	 * @param item      The ItemStack clicked.
	 */
	public RewardsMenuClickEvent(Player player, Inventory inventory, ItemStack item)
	{
		this.player = player;
		this.inventory = inventory;
		this.item = item;
	}

	/**
	 * @return player who clicked.
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * @return the inventory.
	 */
	public Inventory getInventory()
	{
		return inventory;
	}

	/**
	 * @return ItemStack clicked.
	 */
	public ItemStack getItemClicked()
	{
		return item;
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlerList;
	}
}
