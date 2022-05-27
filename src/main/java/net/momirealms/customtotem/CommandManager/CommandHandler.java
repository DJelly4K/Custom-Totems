package net.momirealms.customtotem.CommandManager;

import net.momirealms.customtotem.ConfigManager.ConfigManager;
import net.momirealms.customtotem.MessageManager.MessageManager;
import net.momirealms.customtotem.Utils.Item;
import net.momirealms.customtotem.Utils.Model;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

public class CommandHandler implements CommandExecutor {

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equalsIgnoreCase("test")){

            Model.magic();
        }
        //重载插件
        if (args[0].equalsIgnoreCase("reload")){

            ConfigManager.Config.ReloadConfig();
            if (sender instanceof Player){
                MessageManager.playerMessage(ConfigManager.Config.prefix+ConfigManager.Config.reload, (Player) sender);
            }else {
                MessageManager.consoleMessage(ConfigManager.Config.prefix+ConfigManager.Config.reload, Bukkit.getConsoleSender());
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("items")){
            if(args[1].equalsIgnoreCase("get")){
                if (sender instanceof Player){

                    Item.giveItem(args[2], (Player) sender);

                    MessageManager.playerMessage(ConfigManager.Config.prefix+ConfigManager.Config.getItem, (Player) sender);
                }else {
                    MessageManager.consoleMessage(ConfigManager.Config.prefix+ConfigManager.Config.noConsole, Bukkit.getConsoleSender());
                }
            }
            if(args[1].equalsIgnoreCase("give")){

                Player player = Bukkit.getPlayer(args[2]);
                if(player == null) return false;
                Item.giveItem(args[3], player);

                if (sender instanceof Player){
                    MessageManager.playerMessage(ConfigManager.Config.prefix+ConfigManager.Config.giveItem.replace("{player}", args[2]), (Player) sender);
                }else {
                    MessageManager.consoleMessage(ConfigManager.Config.prefix+ConfigManager.Config.giveItem.replace("{player}", args[2]), Bukkit.getConsoleSender());
                }
                return true;
            }
        }
        return false;
    }
}
