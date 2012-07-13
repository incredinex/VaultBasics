package com.flobi.VaultBasics;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultBasics extends JavaPlugin {
	public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;

    @Override
    public void onDisable() {
        // Remove all Service Registrations
    	permission = null;
        economy = null;
        chat = null;
    }

    @Override
    public void onEnable() {
    	setupVault();
        getCommand("vbecon").setExecutor(this);
        getCommand("vbchat").setExecutor(this);
        getCommand("vbperm").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (command.getName().equalsIgnoreCase("vbecon")) {
        	return econCommand(sender, args);
        } else if (command.getName().equalsIgnoreCase("vbchat")) {
        	return chatCommand(sender, args);
        } else if (command.getName().equalsIgnoreCase("vbperm")) {
        	return permCommand(sender, args);
        }
        // Show help
        sender.sendMessage("VaultBasic Commands:");
        sender.sendMessage("  /vbecon - Display economy subfunctions");
        sender.sendMessage("  /vbchat - Display chat subfunctions");
        sender.sendMessage("  /vbperm - Display permission subfunctions");
        return true;
    }
    
    private boolean econCommand(CommandSender sender, String[] args) {
    	if (economy == null) {
            sender.sendMessage("  No economy detected.");
    	} else if (args.length == 0) {
            sender.sendMessage("VaultBasic Economy Commands:");
            sender.sendMessage("  /vbecon info - Display economy information");
            sender.sendMessage("  /vbecon deposit [username] [amount] - Deposit money to a player.");
    	} else if (args[0].equalsIgnoreCase("info")) {
            sender.sendMessage("Vault Economy Info:");
            sender.sendMessage("  Vault is using: " + economy.getName());
            sender.sendMessage("  Monetary Unit: " + economy.currencyNameSingular() + " (singular), " + economy.currencyNamePlural() + " (plural)");
            if (economy.hasBankSupport()) {
	            sender.sendMessage("  This currency supports banks.");
            } else {
	            sender.sendMessage("  This currency does not support banks.");
            }
    	} else if (args[0].equalsIgnoreCase("deposit")) {
    		if (args.length < 3) {
                sender.sendMessage("VaultBasic Deposit Usage:");
                sender.sendMessage("  /vbecon deposit [username] [amount] - Deposit money to a player.");
    		} else {
    			if (economy.hasAccount(args[1])) {
    				sender.sendMessage(args[1] + " has no account in " + economy.getName());
    			}
    			double amount = 0;
    			try {
    				amount = Double.parseDouble(args[2]);
    			} catch(NumberFormatException e) {
    				sender.sendMessage(args[2] + " is not a valid amount.");
    			}
    			
    			
    		}
    	}
		return true;
    }
    
    private boolean chatCommand(CommandSender sender, String[] args) {
		return true;
    }
    
    private boolean permCommand(CommandSender sender, String[] args) {
		return true;
    }
    
    private void setupVault()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
    }

}
