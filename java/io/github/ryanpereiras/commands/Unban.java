package io.github.ryanpereiras.commands;

import io.github.ryanpereiras.rBans;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Unban implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("command.unban")) {
            sender.sendMessage("§c§lPUNISH §fVocê nao tem permissao");
            return false;
        } else {
            if (cmd.getName().equalsIgnoreCase("Unban")) {
                if (args.length != 1) {
                    sender.sendMessage("§c§lPUNISH §fUse somente §c/unban <jogador>");
                    return false;
                }

                String player = args[0].toLowerCase();
                if (!BanExecutor.hasBan(player)) {
                    sender.sendMessage("§C§LPUNISH §fEsse jogador nao está banido §c§lPERMANENTEMENTE");
                    return false;
                }
                rBans.bans.remove(player);
                BanExecutor.removeBan(player);
                if (sender instanceof Player) {
                    Player staff = (Player)sender;
                    Bukkit.broadcastMessage("§a§lPUNISH §fO jogador §a" + player + " §ffoi desbanido por §a" + staff.getName());
                } else {
                    Bukkit.broadcastMessage("§a§lPUNISH §fO jogador §a" + player + " §ffoi desbanido por §aConsole");
                }
            }

            return false;
        }
    }
}
