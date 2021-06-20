package io.github.ryanpereiras.commands;

import io.github.ryanpereiras.Conexao;
import io.github.ryanpereiras.rBans;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BanExecutor {
    public static void setBan(String punido, String staff, String motivo) {
        try {
            PreparedStatement ps = Conexao.com.prepareStatement("INSERT INTO bans (nick, staff, motivo) VALUES (?,?,?);");
            ps.setString(1, punido.toLowerCase());
            ps.setString(2, staff);
            ps.setString(3, motivo);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var4) {
        }

    }
    public static boolean hasBan(String punido){
        boolean b = false;
        if(rBans.bans.containsKey(punido)){
            b=true;
        }

        return b;
    }
    public static String getStaff(String punido){
        String staff = " ";
        String[] ban = rBans.bans.get(punido);
        staff = ban[0];
        /*try {
            PreparedStatement ps = Conexao.com.prepareStatement("SELECT staff FROM bans WHERE nick=?;");
            ps.setString(1, punido.toLowerCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                staff = rs.getString("staff");
                rs.close();
                ps.close();
            } else {
                rs.close();
                ps.close();
            }
        } catch (SQLException var4) {
        }*/
        return staff;
    }
    public static String getMotivo(String punido){
        String motivo = " ";
        String[] ban = rBans.bans.get(punido);
        motivo = ban[1];
        /*try {
            PreparedStatement ps = Conexao.com.prepareStatement("SELECT motivo FROM bans WHERE nick=?;");
            ps.setString(1, punido.toLowerCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                motivo = rs.getString("motivo");
                rs.close();
                ps.close();
            } else {
                rs.close();
                ps.close();
            }
        } catch (SQLException var4) {
        }*/
        return motivo;
    }
    public static void removeBan(String punido) {
        rBans.bans.remove(punido);
        try {
            PreparedStatement ps = Conexao.com.prepareStatement("DELETE FROM bans WHERE nick=?;");
            ps.setString(1, punido.toLowerCase());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException var2) {
        }

    }
}
