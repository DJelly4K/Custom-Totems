package net.momirealms.customtotem.messagemanager;

import net.momirealms.customtotem.Libs.mineDown.MineDown;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageManager {
    //发送控制台消息
    public static void consoleMessage(String s, CommandSender sender) {
        sender.spigot().sendMessage(MineDown.parse(s));
    }
    //发送玩家消息
    public static void playerMessage(String s, Player player){
        player.spigot().sendMessage(MineDown.parse(s));
    }
}
