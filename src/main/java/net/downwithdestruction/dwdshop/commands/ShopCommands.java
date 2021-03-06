package net.downwithdestruction.dwdshop.commands;

import java.util.Collection;

import net.downwithdestruction.dwdshop.DwDShopPlugin;
import net.downwithdestruction.dwdshop.Events;
import net.downwithdestruction.dwdshop.Shop;
import net.downwithdestruction.dwdshop.Shops;

import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;

public class ShopCommands {

	@Command(aliases = { "repair", "r" },usage = "", flags = "", desc = "Repair ChestShops into DwDShops (Admin Shop only).",help = "Repair your existing Admin Chest Shops into DwDShops", min = 0, max = 0)
	@CommandPermissions("dwdshop.shop.repair")
	public static void repair(CommandContext args, CommandSender sender) throws CommandException {
		if(Events.repairMode.contains(sender.getName())) {
			Events.repairMode.remove(sender.getName());
			sender.sendMessage(DwDShopPlugin.lang.get("commands.repairModeDisabled"));
			DwDShopPlugin.debug(sender.getName()+"'s Repair Mode is now: Disabled");
		}
		else {
			Events.repairMode.add(sender.getName());
			sender.sendMessage(DwDShopPlugin.lang.get("commands.repairModeEnabled"));
			DwDShopPlugin.debug(sender.getName()+"'s Repair Mode is now: Enabled");
		}
	}
	
	@Command(aliases = { "create", "c" },usage = "", flags = "", desc = "Create a dwdshop (Admin Shop only).",help = "Creates a dwdshop", min = 0, max = 0)
	@CommandPermissions("dwdshop.shop.create")
	public static void create(CommandContext args, CommandSender sender) throws CommandException {
		if(Events.createMode.contains(sender.getName())) {
			Events.createMode.remove(sender.getName());
			sender.sendMessage(DwDShopPlugin.lang.get("commands.createModeDisabled"));
			DwDShopPlugin.debug(sender.getName()+"'s Create Mode is now: Disabled");
		}
		else {
			Events.createMode.add(sender.getName());
			sender.sendMessage(DwDShopPlugin.lang.get("commands.createModeEnabled"));
			DwDShopPlugin.debug(sender.getName()+"'s Create Mode is now: Enabled");
		}
	}
	
	@Command(aliases = { "updateall", "ua" },usage = "", flags = "", desc = "Create a dwdshop (Admin Shop only).",help = "Creates a dwdshop", min = 0, max = 0)
	@CommandPermissions("dwdshop.shop.updateall")
	public static void updateall(CommandContext args, CommandSender sender) throws CommandException {
		Collection<Shop> shops = Shops.shops.values();
		for(Shop shop : shops) {
			shop.update();
		}
	}
	
}
