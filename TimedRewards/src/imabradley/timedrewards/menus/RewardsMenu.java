package imabradley.timedrewards.menus;

import imabradley.timedrewards.TimedRewards;
import imabradley.timedrewards.handlers.MenuHandler;
import imabradley.timedrewards.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class RewardsMenu
{
	private Inventory inventory;
	private int size;
	private String title;

	public RewardsMenu(Player player)
	{
		FileConfiguration config = TimedRewards.getYamlHandler().getConfig();
		String path = "menus.rewards.reward-items";

		size = config.getInt("menus.rewards.size");
		title = Util.colour(config.getString("menus.rewards.title"));
		inventory = Bukkit.createInventory(null, size, title);

		try
		{
			for (String s : config.getConfigurationSection(path).getKeys(false))
			{
				String ipath = path + "." + s;
				String fullId = config.getString(ipath + ".id");
				int amount = config.getInt(ipath + ".amount");

				ItemStack itemStack;

				if (fullId.contains(":"))
				{
					String[] parts = fullId.split(":");
					itemStack = new ItemStack(Integer.parseInt(parts[0]), amount, (short) Integer.parseInt(parts[1]));
				}
				else
				{
					itemStack = new ItemStack(Integer.parseInt(fullId), amount);
				}

				ItemMeta itemMeta = itemStack.getItemMeta();
				itemMeta.setDisplayName(Util.colour(config.getString(ipath + ".name")));

				ArrayList<String> lore = new ArrayList<>();

				for (String line : config.getStringList(ipath + ".lore"))
				{
					lore.add(Util.colour(line));
				}

				itemMeta.setLore(lore);
				itemStack.setItemMeta(itemMeta);
				inventory.setItem(config.getInt(ipath + ".slot"), itemStack);
			}

			int slot = 0;

			for (ItemStack itemStack : inventory)
			{
				if (itemStack == null)
				{
					String fullId = config.getString("menus.rewards.other-items");

					if (fullId.contains(":"))
					{
						String[] parts = fullId.split(":");
						itemStack = new ItemStack(Integer.parseInt(parts[0]), 1, (short) Integer.parseInt(parts[1]));
					}
					else
					{
						itemStack = new ItemStack(Integer.parseInt(fullId), 1);
					}

					if (itemStack.getItemMeta() != null) // Air
					{
						ItemMeta itemMeta = itemStack.getItemMeta();
						itemMeta.setDisplayName(" ");
						itemStack.setItemMeta(itemMeta);
					}

					inventory.setItem(slot, itemStack);
				}

				slot++;
			}
		}
		catch (NullPointerException e)
		{
			Util.log("[Error] A NullPointerException occurred when creating the Rewards Menu for " + player.getName() + ":");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			Util.log("[Error] An unknown exception occurred when creating the Rewards Menu for " + player.getName() + ":");
			e.printStackTrace();
		}

		MenuHandler.addRewardMenu(player, this);
	}

    public Inventory getInventory() {
        return inventory;
    }
}