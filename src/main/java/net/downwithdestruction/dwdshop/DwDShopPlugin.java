package net.downwithdestruction.dwdshop;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.downwithdestruction.dwdshop.commands.GeneralCommands;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.roguedraco.lang.Lang;
import net.roguedraco.player.RDEvents;
import net.roguedraco.player.RDPlayers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

public class DwDShopPlugin extends JavaPlugin {

	private CommandsManager<CommandSender> commands;

	public static Logger logger;

	public static DwDShopPlugin plugin = null;

	public static String pluginName;
	public static String pluginVersion;

	public static Lang lang;

	public static Permission permission = null;
	public static Economy economy = null;

	public void onEnable() {

		this.getConfig().options().copyDefaults(true);
		this.saveConfig();

		DwDShopPlugin.logger = Logger.getLogger("Minecraft");
		DwDShopPlugin.plugin = this;
		DwDShopPlugin.pluginName = this.getDescription().getName();
		DwDShopPlugin.pluginVersion = this.getDescription().getVersion();

		DwDShopPlugin.lang = new Lang(this);

		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			setupPermissions();
			setupEconomy();
		} else {
			log(ChatColor.RED
					+ "Missing dependency: Vault. Please install this for the plugin to work.");
			getServer().getPluginManager().disablePlugin(plugin);
			return;
		}

		// Create shops folder
		File theDir = new File(this.getDataFolder() + "/shops/"
				+ File.separatorChar);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			boolean result = theDir.mkdir();
			if (result) {
				log("Shops folder created.");
			}
		}

		// Create players folder
		theDir = new File(this.getDataFolder() + "/players/"
				+ File.separatorChar);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			boolean result = theDir.mkdir();
			if (result) {
				log("Players folder created.");
			}
		}
		
		setupCommands();
		
		PluginManager pm = getServer().getPluginManager();
		Listener events = new Events(this);
		pm.registerEvents(events, this);

		Listener RDEvents = new RDEvents();
		pm.registerEvents(RDEvents, this);
		RDPlayers.loadAll();
		//Shops.loadShops();

	}

	private void setupCommands() {
		this.commands = new CommandsManager<CommandSender>() {
			@Override
			public boolean hasPermission(CommandSender sender, String perm) {
				return sender.hasPermission(perm);
			}
		};

		CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(
				this, this.commands);
		cmdRegister.register(GeneralCommands.class);
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		try {
			this.commands.execute(cmd.getName(), args, sender, sender);
		} catch (CommandPermissionsException e) {
			sender.sendMessage(ChatColor.RED
					+ lang.get("exceptions.noPermission"));
		} catch (MissingNestedCommandException e) {
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (CommandUsageException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (WrappedCommandException e) {
			if (e.getCause() instanceof NumberFormatException) {
				sender.sendMessage(ChatColor.RED
						+ lang.get("exceptions.numExpected"));
			} else {
				sender.sendMessage(ChatColor.RED
						+ lang.get("exceptions.errorOccurred"));
				e.printStackTrace();
			}
		} catch (CommandException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		}

		return true;
	}

	public static void log(String message) {
		log(Level.INFO, message);
	}

	public static void log(Level level, String message) {
		if (plugin.getConfig().getBoolean("useFancyConsole") == true
				&& level == Level.INFO) {
			ConsoleCommandSender console = Bukkit.getServer()
					.getConsoleSender();
			console.sendMessage("[" + ChatColor.AQUA + pluginName + " v"
					+ pluginVersion + ChatColor.GRAY + "] " + message);
		} else {
			DwDShopPlugin.logger.log(level, "[" + pluginName + " v"
					+ pluginVersion + "] " + message);
		}
	}

	public static void debug(String message) {
		if (plugin.getConfig().getBoolean("debug")) {
			if (plugin.getConfig().getBoolean("useFancyConsole") == true) {
				ConsoleCommandSender console = Bukkit.getServer()
						.getConsoleSender();
				console.sendMessage("[" + ChatColor.AQUA + pluginName + " v"
						+ pluginVersion + " Debug" + ChatColor.GRAY + "] "
						+ message);
			} else {
				System.out.println("[" + pluginName + " v" + pluginVersion
						+ " Debug" + "] " + message);
			}
		}
	}

}
