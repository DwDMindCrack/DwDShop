package net.downwithdestruction.dwdshop;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

public class Shops {

	public static Map<Location,Shop> shops = new HashMap<Location,Shop>();
	
	public void getShop() {
		
	}
	
	public static boolean isShop(Location loc) {
		if(shops.containsKey(loc)) {
			return true;
		}
		return false;
	}
	
	public static void loadShops() {
		
	}
	
	public static void saveShops() {
		
	}
	
	public static Shop createShop(Location blockLoc,int itemID, short itemDamage, double buyPrice, double sellPrice) {
		Shop shop = new Shop(blockLoc,itemID,itemDamage,buyPrice,sellPrice);
		Shops.shops.put(blockLoc, shop);
		return shop;
	}
	
}
