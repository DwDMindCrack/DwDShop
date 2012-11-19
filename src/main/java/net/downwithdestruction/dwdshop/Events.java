package net.downwithdestruction.dwdshop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Events implements Listener {

	public static Set<String> repairMode = new HashSet<String>();

	public Events(DwDShopPlugin plugin) {

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			DwDShopPlugin.debug("Right click block");
			Location location = event.getClickedBlock().getLocation();
			Block block = location.getBlock();
			if (block.getType() == Material.SIGN
					|| block.getType() == Material.WALL_SIGN
					|| block.getType() == Material.SIGN_POST) {
				DwDShopPlugin.debug("Is Sign");
				if (repairMode.contains(event.getPlayer().getName())) {
					DwDShopPlugin.debug("Is in repair mode");
					// Convert from old format
					Sign sign = (Sign) block.getState();
					DwDShopPlugin.debug("Line 0:"+sign.getLine(0));
					if ((sign.getLine(0).toLowerCase().contains("admin")
							&& sign.getLine(0).toLowerCase().contains("shop")) || Shops.isAdminShop(location)) {
						DwDShopPlugin.debug("Line contains admin and shop");
						String amount = sign.getLine(1);

						int x, y, z;
						x = location.getBlockX();
						y = location.getBlockY();
						z = location.getBlockZ();

						Entity[] entities = location.getChunk().getEntities();
						for (Entity e : entities) {
							Location eLoc = e.getLocation();
							if ((eLoc.getBlockX() == x)
									&& (eLoc.getBlockZ()) == z
									&& (eLoc.getBlockY() > y)
									&& (eLoc.getBlockY() <= (y + 2))
									&& (e.getType() == EntityType.ITEM_FRAME)) {
								DwDShopPlugin.debug("Found item frame");
								// Found the entity

								ItemFrame frame = (ItemFrame) e;
								int item = frame.getItem().getTypeId();
								short damage = frame.getItem().getDurability();

								String itemID = (damage == 0) ? "" + item
										: item + ":" + damage;

								// Get Prices
								try {
									ResultSet results = DwDShopPlugin.db
											.query("SELECT `buy`,`sell`,`itemName` FROM `Items` WHERE `itemID`='"
													+ itemID + "' LIMIT 1");
									if (results.first()) {
										DwDShopPlugin.debug("Found price");
										double buy, sell;
										buy = results.getDouble("buy");
										sell = results.getDouble("sell");

										// Add the local storage
										Shops.createShop(location, item,
												damage, buy, sell);
										Shops.saveShops();

										// Change the sign \o/
										String line1, line2, line3, line4;

										line1 = results.getString("itemName");
										line2 = amount;

										// Lines 3-4 only go up to 10 chars (to
										// line up) - 7 + . + decimals, always
										// align to right!!
										String buy1 = Double.toString(buy);
										String sell1 = Double.toString(sell);

										String buyPrice = "";
										String sellPrice = "";

										if (buy1.length() > 10) {
											buyPrice = "999,999.00";
										} else {
											int spaces = 10 - buy1.length();
											for (x = 0; x < spaces; x++) {
												buyPrice += " ";
											}
											buyPrice += buy1;
										}

										if (sell1.length() > 10) {
											sell1 = "999,999.00";
										} else {
											int spaces = 10 - sell1.length();
											for (x = 0; x < spaces; x++) {
												sellPrice += " ";
											}
											sellPrice += sell1;
										}

										line3 = "Buy " + buyPrice;
										line4 = "Sell " + sellPrice;

										sign.setLine(0, line1);
										sign.setLine(1, line2);
										sign.setLine(2, line3);
										sign.setLine(3, line4);
										sign.update(true);
										DwDShopPlugin.debug("Set sign");
										event.getPlayer()
												.sendMessage(
														DwDShopPlugin.lang
																.get("exceptions.shopUpdated"));
									} else {
										// Item not found
										event.getPlayer()
												.sendMessage(
														DwDShopPlugin.lang
																.get("exceptions.itemNotFound"));
									}
								} catch (SQLException e1) {
									e1.printStackTrace();
								}

								break;
							}
						}
					} else {
						event.getPlayer().sendMessage(
								DwDShopPlugin.lang
										.get("exceptions.notAdminShop"));
					}
				} else {
					if (Shops.isShop(location)) {
						// Sell

					}
				}
			}
		} else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Location location = event.getClickedBlock().getLocation();
			if (Shops.isShop(location)) {
				// Buy

			}
		}
	}
}
