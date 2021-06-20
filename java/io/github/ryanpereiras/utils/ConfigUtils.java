package io.github.ryanpereiras.utils;

import io.github.ryanpereiras.commands.BanExecutor;
import io.github.ryanpereiras.rBans;

public class ConfigUtils {
    public static String kickBan(String punido, String staff, String motivo) {
        return rBans.getPlugin().getConfig().getString("message.ban_message").replace("&", "§").replace("<line>", "\n").replace("%staff%", staff).replace("%player%", punido).replace("%motivo%", motivo);
    }
    public static String joinBan(String punido) {
        return rBans.getPlugin().getConfig().getString("message.join_ban").replace("&", "§").replace("<line>", "\n").replace("%staff%", BanExecutor.getStaff(punido)).replace("%player%", punido).replace("%motivo%", BanExecutor.getMotivo(punido));
    }
    public static String host(){
        return rBans.getPlugin().getConfig().getString("MySQL.host");
    }
    public static String user(){
        return rBans.getPlugin().getConfig().getString("MySQL.username");
    }
    public static String pass(){
        return rBans.getPlugin().getConfig().getString("MySQL.password");
    }
    public static String database(){
        return rBans.getPlugin().getConfig().getString("MySQL.database");
    }
    public static int port(){
        return rBans.getPlugin().getConfig().getInt("MySQL.port");
    }



}
