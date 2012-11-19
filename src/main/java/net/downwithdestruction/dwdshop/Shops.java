package net.downwithdestruction.dwdshop;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

public class Shops {

	public static Map<Location, Shop> shops = new HashMap<Location, Shop>();

	public static boolean isShop(Location loc) {
		if (shops.containsKey(loc)) {
			return true;
		}
		return false;
	}

	public static Shop getShop(Location loc) {
		if (isShop(loc)) {
			Shop shop = shops.get(loc);
			return shop;
		}
		return null;
	}

	public static void loadShops() {
		File[] files = new File("plugins/DwDShop/shops").listFiles();
		for (File file : files) {
			String name = file.getName();
			int pos = name.lastIndexOf('.');
			String ext = name.substring(pos + 1);
			if (ext.equalsIgnoreCase("yml")) {
				name = name.replaceAll(".yml", "");
				Shop shop = new Shop(name);
				shop.load();
				Location location = shop.getLocation();
				shops.put(location, shop);
				DwDShopPlugin.log(DwDShopPlugin.lang.get("plugin.loaded")
						+ ": " + name);
			}
		}
	}

	public static void saveShops() {
		Collection<Shop> ashops = shops.values();
		for (Shop shop : ashops) {
			shop.save();
			DwDShopPlugin.log(DwDShopPlugin.lang.get("plugin.saved") + ": "
					+ shop.getName());
		}
	}

	public static Shop createShop(Location blockLoc, int itemID,
			short itemDamage, int amount, double buyPrice, double sellPrice) {
		Shop shop = new Shop(blockLoc, itemID, itemDamage, amount, buyPrice,
				sellPrice);
		Shops.shops.put(blockLoc, shop);
		return shop;
	}

}
