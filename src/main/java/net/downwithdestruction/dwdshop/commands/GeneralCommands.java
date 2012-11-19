package net.downwithdestruction.dwdshop.commands;

import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.NestedCommand;

public class GeneralCommands {
	@Command(aliases = { "ashop", "adminshop" },usage = "", flags = "", desc = "Adminshop commands.",help = "All commands related to Admin Shop.", min = 0, max = 0)
	@NestedCommand(value = { ShopCommands.class })
	public static void ashop(CommandContext args, CommandSender sender) throws CommandException {
	}
}
