package net.downwithdestruction.dwdshop;

import org.bukkit.Location;

public class Shop {

	public String owner;
	public int itemID,itemDamage,amount,blockX,blockY,blockZ;
	public double buy,sell;

	
	public Shop(int itemID, Location location) {
		this.itemID = itemID;
		this.blockX = location.getBlockX();
		this.blockY = location.getBlockY();
		this.blockZ = location.getBlockZ();
	}
	
	
	
	
}
