package net.momirealms.customtotem.CommandManager;

import net.momirealms.customtotem.ConfigManager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandTab implements TabCompleter {

    @Override
    @ParametersAreNonnullByDefault
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(1 == args.length){
            return Arrays.asList("reload","items");
        }
        if(2 == args.length && args[0].equalsIgnoreCase("items")){
            return Arrays.asList("get","give");
        }
        if(3 == args.length && args[1].equalsIgnoreCase("get") && args[0].equalsIgnoreCase("items")){
            return items();
        }else if(3 == args.length && args[1].equalsIgnoreCase("give")){
            return online_players();
        }
        if(4 == args.length && args[1].equalsIgnoreCase("give") && args[0].equalsIgnoreCase("items")){
            return items();
        }
        return null;
    }

    private static List<String> online_players(){
        List<String> online = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach((player -> online.add(player.getDisplayName())));
        return online;
    }

    private static List<String> items(){
        return new ArrayList<>(ConfigManager.ITEMS.keySet());
    }
}
