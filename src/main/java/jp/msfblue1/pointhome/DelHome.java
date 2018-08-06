package jp.msfblue1.pointhome;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by msfblue1 on 2017/06/04.
 */
public class DelHome implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if("delhome".equalsIgnoreCase(command.getName())){
            if(sender instanceof Player){
                Player player = (Player)sender;
                if(args.length == 1){
                    List<String> datas = Util.getDataBaseListName(player.getUniqueId().toString());
                    if(datas.contains(args[0])){
                        Data deldata = new Data();
                        deldata.Name = args[0];
                        if(Util.deleteUserData(player.getUniqueId().toString(),deldata)){
                            PointHome.puts(sender,ChatColor.GREEN+"削除に成功しました！");
                        }else{
                            PointHome.puts(sender,ChatColor.YELLOW+"ポイント名が不正です。");
                        }

                    }else{
                        PointHome.puts(sender,ChatColor.YELLOW+"ポイント名が不正です。");
                    }
                }else{
                    Util.putsHelp(sender);
                }
            }
        }
        return true;
    }
}
