package io.github.ryanpereiras;

import io.github.ryanpereiras.commands.Ban;
import io.github.ryanpereiras.commands.Unban;
import io.github.ryanpereiras.listeners.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.HashMap;

public class rBans extends JavaPlugin {

    private static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }
    public static HashMap<String, String[]> bans = new HashMap();



    @Override
    public void onEnable() {
        if (plugin == null) {
            plugin = this;
        }
        saveDefaultConfig();
        if(getConfig().getBoolean("MySQL.enable")) {
            Conexao.openConnectionBans();
            this.getCommand("ban").setExecutor(new Ban());
            this.getCommand("unban").setExecutor(new Unban());
            Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        }else{
            getPlugin().getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if(Conexao.com != null) {
            try {
                PreparedStatement ps = Conexao.com.prepareStatement("SELECT * FROM bans");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (rBans.bans.containsKey(rs.getString("nick"))) {
                        rBans.bans.remove(rs.getString("nick"));
                    } else {
                        PreparedStatement insert = Conexao.com.prepareStatement("INSERT INTO bans (nick, staff, motivo) VALUES (?,?,?);");

                        rBans.bans.forEach((punido, value) -> {
                            String nick, staff, motivo;
                            nick = punido;
                            staff = value[0];
                            motivo = value[1];

                            try {
                                insert.setString(1, nick);
                                insert.setString(2, staff);
                                insert.setString(2, motivo);
                                insert.executeUpdate();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });


                    }
                }
            } catch (SQLException e) {

            }
            Conexao.closeConectionBans();
        }

    }


}
