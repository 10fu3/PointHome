package jp.msfblue1.pointhome;

import me.konsolas.aac.api.AACAPIProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;

/**
 * Created by msfblue1 on 2017/06/04.
 */
public class Home implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            if(sender instanceof Player){
                Player player = (Player)sender;
                List<Data> homes = Util.getDataBaseList(player.getUniqueId().toString());
                player.sendMessage(ChatColor.GOLD+"===== HOME LIST =====");
                for(int i = 0; i < homes.size();i++){
                    player.sendMessage("["+String.valueOf(i+1)+"] "+ChatColor.GOLD+homes.get(i).Name+" "+ChatColor.YELLOW+homes.get(i).WorldName);
                }
                player.sendMessage(ChatColor.GOLD+"===== HOME LIST =====");
            }
        }else if(args.length == 1){
            if(sender instanceof Player){
                Player player = (Player)sender;
                List<Data> homes = Util.getDataBaseList(player.getUniqueId().toString());
                List<String> homenames = Util.getDataBaseListName(player.getUniqueId().toString());
                if(homenames.contains(args[0])){
                    for(Data data : homes){
                        if(args[0].equalsIgnoreCase(data.Name)){
                            if(Util.existWorld(data.WorldName)){
                                World world = Bukkit.getWorld(data.WorldName);
                                Location warp = new Location(world,data.x,data.y,data.z);
                                player.sendMessage(ChatColor.GOLD+"ワープします！");
                                if(PointHome.isCheckingAAC() && !AACAPIProvider.getAPI().isBypassed(player)){
                                    PermissionsEx.getPermissionManager().getUser(player).addPermission("AAC.bypath");
                                    player.teleport(warp);
                                    PermissionsEx.getPermissionManager().getUser(player).removePermission("AAC.bypath");
                                }else{
                                    player.teleport(warp);
                                }
                                break;
                            }
                        }
                    }
                }else{
                    player.sendMessage(ChatColor.RED +"ポイント名が不正です");
                }

            }
        }else {
            Util.putsHelp(sender);
        }
        return false;
    }
}
