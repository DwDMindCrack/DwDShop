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
	
}
