package net.downwithdestruction.dwdshop;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Events implements Listener {

	public static Set<String> repairMode = new HashSet<String>();

	public Events(DwDShopPlugin plugin) {

	}

	public void onPlayerInteract(PlayerInteractEvent event) {
		Location location = event.getClickedBlock().getLocation();
		if (Shops.isShop(location)) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

				if (repairMode.contains(event.getPlayer().getName())) {
					// Convert from old format
					Sign sign = (Sign) event.getClickedBlock();
					if(sign.getLine(0).contains("admin") && sign.getLine(0).contains("shop")) {
						String amount = sign.getLine(1);
						location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY()+1, location.getBlockZ()); // block above
					}
					else {
						
					}
				} else {
					// Sell
				}
			}
			else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
				// Buy
				
			}
		}
	}
}
