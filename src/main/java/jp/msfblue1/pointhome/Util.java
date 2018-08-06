package jp.msfblue1.pointhome;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by msfblue1 on 2017/06/04.
 */
public class Util {
    static String database = "plugins/PointHome2/database/";

    //Help
    public static void putsHelp(CommandSender sender){
        sender.sendMessage(ChatColor.BLUE + "[PointHome]" + ChatColor.GREEN + "=======PointHelp=======");
        sender.sendMessage(ChatColor.GOLD + "/sethome [NAME]" + ChatColor.GRAY + ": " + ChatColor.GREEN
                + "今いる場所を登録します。");
        sender.sendMessage(
                ChatColor.GOLD + "/home" + ChatColor.GRAY + ": " + ChatColor.GREEN + "登録したポイント一覧を表示します。");
        sender.sendMessage(ChatColor.GOLD + "/home [NAME]" + ChatColor.GRAY + ": " + ChatColor.GREEN
                + "指定したポイントにワープします。");
        sender.sendMessage(ChatColor.GOLD + "/delhome [Name]" + ChatColor.GRAY + ": " + ChatColor.GREEN
                + "指定したポイントを削除します。");
        sender.sendMessage(ChatColor.BLUE + "[PointHome]" + ChatColor.GREEN + "=======PointHelp=======");
    }
    //指定したデータを削除
    public static boolean deleteUserData(String UUID,Data data){
        File file = new File(database+UUID,data.Name);
        if(file.delete()){
            return true;
        }
        return false;
    }

    //指定したデータを保存
    public static boolean saveNewUserData(String UUID,Data data){
        if(!existsDataBase(UUID)){
            new File(database+UUID).mkdirs();
        }
        File file = new File(database+UUID,data.Name);
        try {
            file.createNewFile();
            FileConfiguration configuration = new YamlConfiguration();
            configuration.set("name",data.Name);
            configuration.set("world",data.WorldName);
            configuration.set("x",data.x);
            configuration.set("y",data.y);
            configuration.set("z",data.z);
            configuration.save(file);
            return true;
        } catch (IOException e) {
            PointHome.puts(ChatColor.RED+"エラー :"+e.getMessage());
            return false;
        }
    }

    //指定した名前のデータを取得
    public static Data getUserData(String UUID,String Name){
        if(getDataBaseListName(UUID).contains(Name)){
            for(Data parts : getDataBaseList(UUID)){
                if(Name.equalsIgnoreCase(parts.Name)){
                    return parts;
                }
            }
        }
        return null;
    }

    //指定したユーザーのポイントリストを取得
    public static List<String> getDataBaseListName(String UUID){
        String maindatabase = database+UUID;
        File database = new File(maindatabase);
        if(existsDataBase(UUID)){
            return Arrays.asList(database.list());
        }
        return new ArrayList();
    }

    //指定したユーザーのポイント登録数を取得
    public static Integer getDataCount(String UUID){
        if(existsDataBase(UUID)){
            String path = database+UUID;
            return new File(path).listFiles().length;
        }
        return 0;
    }

    //指定したユーザーのデータをリストで取得
    public static List<Data> getDataBaseList(String UUID){
        List<Data> datas = new ArrayList<>();
        Data data;
        String maindatabase = database+UUID;
        File database = new File(maindatabase);
        if(existsDataBase(UUID)){
            for(File file : database.listFiles()){
                data = new Data();
                FileConfiguration configuration = new YamlConfiguration().loadConfiguration(file);
                if(configuration.contains("name")){
                    data.Name = configuration.getString("name");
                }else{
                    PointHome.puts(ChatColor.RED+"UUID: "+UUID+"の読み込みに失敗しました。ファイル-Nameが壊れています。");
                }
                if(configuration.contains("world")){
                    data.WorldName = configuration.getString("world");
                }else{
                    PointHome.puts(ChatColor.RED+"UUID: "+UUID+"の読み込みに失敗しました。ファイル-WorldNameが壊れています。");
                    return null;
                }
                if(configuration.contains("x")){
                    if(configuration.isInt("x")){
                        data.x = configuration.getInt("x");
                    }
                    else{
                        PointHome.puts(ChatColor.RED+"UUID: "+UUID+"の読み込みに失敗しました。ファイル-Xが壊れています。");
                        return null;
                    }
                }else {
                    PointHome.puts(ChatColor.RED+"UUID: "+UUID+"の読み込みに失敗しました。ファイル-Xが壊れています。");
                    return null;
                }
                if(configuration.contains("y")){
                    if(configuration.isDouble("y")){
                        data.y = configuration.getDouble("y");
                    }
                    else{
                        PointHome.puts(ChatColor.RED+"UUID: "+UUID+"の読み込みに失敗しました。ファイル-Yが壊れています。");
                        return null;
                    }
                }else{
                    PointHome.puts(ChatColor.RED+"UUID: "+UUID+"の読み込みに失敗しました。ファイル-Yが壊れています。");
                    return null;
                }
                if(configuration.contains("z")){
                    if(configuration.isInt("z")){
                        data.z = configuration.getInt("z");
                    }
                    else{
                        PointHome.puts(ChatColor.RED+"UUID: "+UUID+"の読み込みに失敗しました。ファイル-Zが壊れています。");
                        return null;
                    }
                }else{
                    PointHome.puts(ChatColor.RED+"UUID: "+UUID+"の読み込みに失敗しました。ファイル-Zが壊れています。");
                    return null;
                }
                datas.add(data);
            }
        }
        return datas;
    }

    //指定したユーザーのディレクトリの有無
    public static boolean existsDataBase(String UUID){
        String maindatabase = database+UUID;
        File database = new File(maindatabase);
        if(database.exists() && database.isDirectory()){
            return true;
        }
        return false;
    }

    // 指定したユーザーのディレクトリを作成
    public static boolean createDataBase(String UUID){
        String maindatabase = database+UUID;
        File database = new File(maindatabase);
        if(!database.exists()){
            if(database.mkdirs()){
                return true;
            }
            return false;
        }else if(!database.isDirectory()){
            if(database.mkdirs()){
                return true;
            }
            return false;
        }else {
            return true;
        }
    }

    //指定したワールドの有無
    public static boolean existWorld(String world){
        if(Bukkit.getWorld(world) != null){
            return true;
        }
        return false;
    }


    //指定したユーザーのUUIDを取得
    public static String toUUID (CommandSender sender){
        if(sender != null){
            if(sender instanceof Player){
                Player player = (Player)sender;
                return player.getUniqueId().toString();
            }else{
                throw new IllegalAccessError();
            }
        }else {
            throw new NullPointerException();
        }
    }
}
