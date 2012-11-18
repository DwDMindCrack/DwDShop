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


	public Shop(Location blockLoc, int itemID, short itemDamage,
			double buyPrice, double sellPrice) {
		this.itemID = itemID;
		this.itemDamage = itemDamage;
		this.amount = 1;
		
		this.buy = buyPrice;
		this.sell = sellPrice;
		this.owner = "Admin Shop";
		
		this.blockX = blockLoc.getBlockX();
		this.blockY = blockLoc.getBlockY();
		this.blockZ = blockLoc.getBlockZ();
		
		DwDShopPlugin.debug("Created Shop: "+itemID+":"+itemDamage+" x"+amount+"@ B:"+buyPrice+" S:"+sellPrice+" - Admin Shop ("+blockX+","+blockY+","+blockZ+")");
		
	}
	
	
	
	
}
