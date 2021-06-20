package io.github.ryanpereiras.commands;

import io.github.ryanpereiras.rBans;
import io.github.ryanpereiras.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ban implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("ban")){

            if (!(commandSender.hasPermission("rBans.command.ban"))) {
                commandSender.sendMessage("§cVocê não possui permissão para fazer isso!");
                return false;
            }
            if(args.length == 0){
                commandSender.sendMessage("§c§lPUNISH §fUse §c/ban <jogador> <motivo>");
                return false;
            }
            String player = args[0].toLowerCase();
            String motivo = "";

            for(int i = 1; i < args.length; ++i) {
                motivo = motivo + args[i] + " ";
            }
            if(motivo == ""){
                motivo = "Motivo não definido!";
            }
            if(BanExecutor.hasBan(player)){
                commandSender.sendMessage("§c§lPUNISH §fO jogador já esta banido §c§lPERMANENTEMENTE");
                return false;
            }
            Player p;
            if (commandSender instanceof Player) {
                p = (Player) commandSender;
                Player target = Bukkit.getPlayer(player);
                if (target != null) {
                    target.kickPlayer(ConfigUtils.kickBan(target.getName(), p.getName(), motivo));
                }
                String punido = player.toLowerCase();
                String[] value = new String[2];
                value[0] = p.getName();
                value[1] = motivo;

                rBans.bans.put(punido, value);
                //BanExecutor.setBan(player.toLowerCase(), p.getName(), motivo);
                Bukkit.broadcastMessage("§a§lPUNISH §fO Jogador §a" + player + " §ffoi banido §a§lPERMANENTEMENTE §fpor §a" + p.getName());
                Bukkit.broadcastMessage("§a§lPUNISH §fMotivo §a" + motivo);
            } else {
                p = Bukkit.getPlayer(player);
                if (p != null) {
                    p.kickPlayer(ConfigUtils.kickBan(p.getName(), "Console", motivo));
                }

                String punido = player.toLowerCase();
                String[] value = new String[2];
                value[0] = "Console";
                value[1] = motivo;

                rBans.bans.put(punido, value);

                BanExecutor.setBan(player.toLowerCase(), "Console", motivo);
                Bukkit.broadcastMessage("§a§lPUNISH §fO Jogador §a" + player + " §ffoi banido §a§lPERMANENTEMENTE §fpor §aConsole");
                Bukkit.broadcastMessage("§a§lPUNISH §fMotivo §a" + motivo);
            }
        }
        return false;
    }
}
