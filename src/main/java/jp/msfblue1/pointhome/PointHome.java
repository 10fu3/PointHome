package jp.msfblue1.pointhome;

import me.konsolas.aac.api.AACAPIProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class PointHome extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginCommand("home").setExecutor(new Home());
        Bukkit.getPluginCommand("delhome").setExecutor(new DelHome());
        Bukkit.getPluginCommand("sethome").setExecutor(new SetHome());
        puts(ChatColor.GREEN+"有効化されました");
    }

    @Override
    public void onDisable() {
        puts(ChatColor.RED +"無効化されました");
    }

    public static void puts (String mes){
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"[PointHome] "+ChatColor.WHITE+mes);
    }
    public static void puts (CommandSender sender , String mes){
        sender.sendMessage(ChatColor.BLUE+"[PointHome] "+ChatColor.WHITE+mes);
    }

    public static boolean isCheckingAAC(){
        return Bukkit.getPluginManager().isPluginEnabled("AAC") && AACAPIProvider.isAPILoaded();
    }


}
