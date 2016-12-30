package imabradley.timedrewards.menus;

import imabradley.timedrewards.TimedRewards;
import imabradley.timedrewards.handlers.MenuHandler;
import imabradley.timedrewards.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RewardsMenu
{
	private Inventory inventory;
	private int size;
	private String title;
	private Player player;

	private HashMap<String, ItemStack> rewardItems = new HashMap<>();

	public RewardsMenu(Player player)
	{
		FileConfiguration config = TimedRewards.getYamlHandler().getConfig();
		YamlConfiguration pconfig = TimedRewards.getYamlHandler().getPlayerYaml(player);
		String path = "menus.rewards.reward-items";

		this.size = config.getInt("menus.rewards.size");
		this.title = Util.colour(config.getString("menus.rewards.title"));
		this.inventory = Bukkit.createInventory(null, size, title);
		this.player = player;

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
					String format = getFormattedTime(s);
					lore.add(Util.colour(line).replace("{time}", format));
				}

				itemMeta.setLore(lore);
				itemStack.setItemMeta(itemMeta);
				inventory.setItem(config.getInt(ipath + ".slot"), itemStack);

				rewardItems.put(s, itemStack);
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

	public void update()
	{
		FileConfiguration config = TimedRewards.getYamlHandler().getConfig();
		YamlConfiguration pconfig = TimedRewards.getYamlHandler().getPlayerYaml(player);
		String path = "menus.rewards.reward-items";

		for (Map.Entry<String, ItemStack> entry : rewardItems.entrySet())
		{
			String key = entry.getKey();
			ItemStack itemStack = entry.getValue();
			ItemMeta itemMeta = itemStack.getItemMeta();

			String ipath = path + "." + key;

			ArrayList<String> lore = new ArrayList<>();

			for (String line : config.getStringList(ipath + ".lore"))
			{
				String format = getFormattedTime(key);
				lore.add(Util.colour(line).replace("{time}", format));
			}

			itemMeta.setLore(lore);
			itemStack.setItemMeta(itemMeta);
			this.inventory.setItem(config.getInt(ipath + ".slot"), itemStack);
		}
	}

	public String getFormattedTime(String key)
	{
		YamlConfiguration pconfig = TimedRewards.getYamlHandler().getPlayerYaml(player);
		long claimed = pconfig.getLong("rewards." + key + ".claim-time");
		long delay = TimedRewards.getYamlHandler().getConfig().getLong("menus.rewards.reward-items." + key + ".time");
		long result = claimed - (System.currentTimeMillis() - (delay * 1000L));

		if (result <= 0)
		{
			return "&aNow";
		}
		else
			return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(result), TimeUnit.MILLISECONDS.toMinutes(result) - TimeUnit.HOURS
					.toMinutes(TimeUnit.MILLISECONDS.toHours(result)), TimeUnit.MILLISECONDS.toSeconds(result) - TimeUnit.MINUTES
					.toSeconds(TimeUnit.MILLISECONDS.toMinutes(result)));
	}

    public Inventory getInventory() {
        return inventory;
    }
}