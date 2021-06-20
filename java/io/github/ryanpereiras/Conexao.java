package io.github.ryanpereiras;

import io.github.ryanpereiras.utils.ConfigUtils;
import org.bukkit.Bukkit;

import java.sql.*;

public class Conexao {
    public static Connection com = null;
    public static void openConnectionBans(){
        String HOST = ConfigUtils.host();
        int PORT = ConfigUtils.port();
        String USER = ConfigUtils.user();
        String PASS = ConfigUtils.pass();
        String DB = ConfigUtils.database();
        String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB;
        if(com != null){
            return;
        }
        try {
            com = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Sucesso");
            createTableBans();
            createTableMute();
        } catch (SQLException e) {
            System.out.println("Connection with mysql error");
            System.out.println(e.getSQLState()+ " " + e.getMessage() + " " + e.getErrorCode() + " " + e.getCause());
        }


    }
    public static void closeConectionBans(){
        if(com == null){
            return;
        }
        try {
            com.close();
        } catch (SQLException e) {
            System.out.println(e.getSQLState()+ " " + e.getMessage() + " " + e.getErrorCode() + " " + e.getCause());
        }
    }
    public static void createTableBans(){
        try {
            PreparedStatement ps = com.prepareStatement("CREATE TABLE IF NOT EXISTS bans (id_ban INT NOT NULL AUTO_INCREMENT , `nick` VARCHAR(24) NOT NULL , `staff` VARCHAR(24) NOT NULL , `motivo` VARCHAR(80) NOT NULL, PRIMARY KEY (`id_ban`))");
            ps.executeUpdate();
            ps.close();
            System.out.println("Tabela ban criada/carregada com sucesso");
            loadTable();
        } catch (SQLException e) {
            System.out.println(e.getSQLState()+ " " + e.getMessage() + " " + e.getErrorCode() + " " + e.getCause());
        }
    }
    public static void createTableMute(){
        try {
            PreparedStatement ps = com.prepareStatement("CREATE TABLE IF NOT EXISTS mutes (id_mute INT NOT NULL AUTO_INCREMENT , `nick` VARCHAR(24) NOT NULL , `staff` VARCHAR(24) NOT NULL , `motivo` VARCHAR(80) NOT NULL, PRIMARY KEY (`id_mute`))");
            ps.executeUpdate();
            ps.close();
            System.out.println("Tabela mute criada/carregada com sucesso");
        } catch (SQLException e) {
            System.out.println(e.getSQLState()+ " " + e.getMessage() + " " + e.getErrorCode() + " " + e.getCause());
        }
    }
    public static void loadTable(){
        try {

            PreparedStatement preparedStatement = Conexao.com.prepareStatement("SELECT * from bans;");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                String punido = rs.getString("nick");
                String[] value = new String[2];
                value[0] = rs.getString("staff");
                value[1] = rs.getString("motivo");

                rBans.bans.put(punido, value);
            }
        } catch (SQLException e) {
            Bukkit.getServer().getPluginManager().disablePlugin(rBans.getPlugin());
        }
        rBans.bans.forEach((punido, value) -> System.out.println(punido +": " +value[0]+": "+value[1])) ;
    }
}
