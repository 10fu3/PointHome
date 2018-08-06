package jp.msfblue1.pointhome;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by msfblue1 on 2017/06/04.
 */
public class SetHome implements CommandExecutor {
    Integer count = Bukkit.getPluginManager().getPlugin("PointHome2").getConfig().getInt("HomeLimiter");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if("sethome".equalsIgnoreCase(command.getName())){
            if(args.length == 1){
                if(sender instanceof Player){
                    Player player = (Player)sender;
                    List<String> homenames = Util.getDataBaseListName(player.getUniqueId().toString());
                    if(homenames.contains(args[0])){
                        PointHome.puts(sender, ChatColor.RED +"すでに登録されています");
                    }else{
                        if(!(count <= -1)){
                            if(Util.getDataCount(player.getUniqueId().toString()) + 1 > count){
                                PointHome.puts(sender, ChatColor.RED +"登録上限数を超えています");
                                return true;
                            }
                        }
                        Data data = new Data();
                        data.Name = args[0];
                        data.WorldName = player.getWorld().getName();
                        data.x = (int)player.getLocation().getX();
                        data.y = player.getLocation().getY();
                        data.z = (int)player.getLocation().getZ();
                        if(Util.saveNewUserData(player.getUniqueId().toString(),data)){
                            PointHome.puts(sender, ChatColor.GREEN+"登録しました！");
                        }else {
                            PointHome.puts(sender, ChatColor.RED +"登録に失敗しました");
                        }

                    }
                }
            }else{
                Util.putsHelp(sender);
            }
        }
        return false;
    }
}
