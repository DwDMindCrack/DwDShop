package net.downwithdestruction.dwdshop;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Shop {

	public String name,world;
	public int itemID,itemDamage,amount,blockX,blockY,blockZ;
	public double buy,sell;
	
	private File confFile = null;
	private FileConfiguration conf = null;

	public Shop(String name) {
		this.name = name;
	}
	
	public Shop(int itemID, Location location) {
		this.itemID = itemID;
		this.world  = location.getWorld().getName();
		this.blockX = location.getBlockX();
		this.blockY = location.getBlockY();
		this.blockZ = location.getBlockZ();
		this.name = world+"~=~"+blockX+"~=~"+blockY+"~=~"+blockZ;
	}


	public Shop(Location blockLoc, int itemID, short itemDamage, int amount,
			double buyPrice, double sellPrice) {
		this.itemID = itemID;
		this.itemDamage = itemDamage;
		this.amount = amount;
		
		this.buy = buyPrice;
		this.sell = sellPrice;

		this.world  = blockLoc.getWorld().getName();
		this.blockX = blockLoc.getBlockX();
		this.blockY = blockLoc.getBlockY();
		this.blockZ = blockLoc.getBlockZ();
		
		this.name = world+"~=~"+blockX+"~=~"+blockY+"~=~"+blockZ;
		
		DwDShopPlugin.debug("Created Shop: "+itemID+":"+itemDamage+" x"+amount+"@ B:"+buyPrice+" S:"+sellPrice+" - Admin Shop ("+blockX+","+blockY+","+blockZ+")");
		
	}


	public int getItemID() {
		return itemID;
	}


	public void setItemID(int itemID) {
		this.itemID = itemID;
	}


	public int getItemDamage() {
		return itemDamage;
	}


	public void setItemDamage(int itemDamage) {
		this.itemDamage = itemDamage;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public double getBuy() {
		return buy;
	}


	public double getSell() {
		return sell;
	}
	
	public Location getLocation() {
		return Bukkit.getServer().getWorld(world).getBlockAt(blockX, blockY, blockZ).getLocation();
	}
	
	public void save() {
		confFile = new File("plugins/DwDShop/shops/", name + ".yml");
		conf = YamlConfiguration.loadConfiguration(confFile);
		
		conf.set("location.world", world);
		conf.set("location.x", blockX);
		conf.set("location.y", blockY);
		conf.set("location.z", blockZ);
		conf.set("item.ID", itemID);
		conf.set("item.damage", itemDamage);
		conf.set("item.amount", amount);
		conf.set("price.buy", buy);
		conf.set("price.sell", sell);
		
		DwDShopPlugin.debug("World: "+world+", X:"+blockX+", Y:"+blockY+", Z:"+blockZ+", itemID:"+itemID+", itemDamage:"+itemDamage+", amount:"+amount+", buy:"+buy+", sell:"+sell);

		try {
			conf.save(confFile); // Save the file
		} catch (IOException ex) {
			DwDShopPlugin.log(Level.SEVERE, "Could not save config to "
					+ confFile);
		}
	}
	
	public void load() {
		confFile = new File("plugins/DwDShop/shops/", name + ".yml");
		conf = YamlConfiguration.loadConfiguration(confFile);

		// Set values
		this.world = conf.getString("location.world");
		this.blockX = conf.getInt("location.x");
		this.blockY = conf.getInt("location.y");
		this.blockZ = conf.getInt("location.z");
		
		this.itemID = conf.getInt("item.ID");
		this.itemDamage = conf.getInt("item.damage");
		this.amount = conf.getInt("item.amount");
		this.buy = conf.getDouble("price.buy");
		this.sell = conf.getDouble("price.sell");
		
		DwDShopPlugin.debug("World: "+world+", X:"+blockX+", Y:"+blockY+", Z:"+blockZ+", itemID:"+itemID+", itemDamage:"+itemDamage+", amount:"+amount+", buy:"+buy+", sell:"+sell);
		
	}
	
	public void delete() {
		confFile = new File("plugins/DwDShop/shops/", name + ".yml");
		confFile.delete();
	}

	public String getName() {
		return this.name;
	}
	
	
}
