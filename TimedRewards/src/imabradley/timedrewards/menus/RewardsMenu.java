package imabradley.timedrewards.menus;

import imabradley.timedrewards.TimedRewards;
import imabradley.timedrewards.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class RewardsMenu
{
	private Inventory inventory;
	private int size;
	private String title;

	public RewardsMenu(OfflinePlayer player)
	{
		FileConfiguration config = TimedRewards.getYamlHandler().getConfig();
		String path = "menus.rewards.reward-items";

		size = config.getInt("menus.rewards.size");
		title = Util.colour(config.getString("menus.rewards.title"));
		inventory = Bukkit.createInventory(null, size, title);

		for (String s : config.getConfigurationSection(path).getKeys(false))
		{
			try
			{
				String ipath = path + "." + s;
				String fullId = config.getString(ipath + ".id");
				int amount = config.getInt(ipath + ".amount");

				ItemStack itemStack;

				if (fullId.contains(":"))
				{
					String[] parts = fullId.split(":");
					itemStack = new ItemStack(Integer.parseInt(parts[0]), amount, (short) Integer.parseInt(parts[2]));
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
			catch (NullPointerException e)
			{
				Util.log("[Exception] A NullPointerException occurred when creating the Rewards Menu:");
				e.printStackTrace();
			}
			catch (Exception e)
			{
				Util.log("[Exception] An unknown exception occurred when creating the Rewards Menu:");
				e.printStackTrace();
			}
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

				ItemMeta itemMeta = itemStack.getItemMeta();
				itemMeta.setDisplayName("");
				itemStack.setItemMeta(itemMeta);

				inventory.setItem(slot, itemStack);
			}

			slot++;
		}
	}

    public Inventory getInventory() {
        return inventory;
    }
}