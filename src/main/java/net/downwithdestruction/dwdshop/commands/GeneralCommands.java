package net.downwithdestruction.dwdshop.commands;

import net.downwithdestruction.dwdshop.DwDShopPlugin;
import net.downwithdestruction.dwdshop.Events;

import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;

public class GeneralCommands {

	@Command(aliases = { "shoprepair" },usage = "", flags = "", desc = "Repair ChestShops into DwDShops (Admin Shop only).",help = "Repair your existing Admin Chest Shops into DwDShops", min = 0, max = 0)
	@CommandPermissions("dwdshop.shoprepair")
	public static void shoprepair(CommandContext args, CommandSender sender) throws CommandException {
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
	
}
