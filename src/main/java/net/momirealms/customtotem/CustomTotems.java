package net.momirealms.customtotem;

import net.momirealms.customtotem.CommandManager.CommandHandler;
import net.momirealms.customtotem.CommandManager.CommandTab;
import net.momirealms.customtotem.ConfigManager.ConfigManager;
import net.momirealms.customtotem.MessageManager.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CustomTotems extends JavaPlugin {

    public static JavaPlugin instance;

    @Override
    public void onEnable() {

        instance = this;
        saveDefaultConfig();

        //加载配置文件
        ConfigManager.Config.ReloadConfig();

        //指令注册
        Objects.requireNonNull(Bukkit.getPluginCommand("customtotems")).setExecutor(new CommandHandler());
        Objects.requireNonNull(Bukkit.getPluginCommand("customtotems")).setTabCompleter(new CommandTab());

        //启动成功
        MessageManager.consoleMessage("&#EAE5C9-#6CC6CB&[CustomTotems] 自定义召唤阵插件已加载! 作者：小默米 QQ:3266959688", Bukkit.getConsoleSender());

    }

    @Override
    public void onDisable() {

        //卸载成功
        MessageManager.consoleMessage("&#EAE5C9-#6CC6CB&[CustomTotems] 自定义召唤阵插件已卸载! 作者：小默米 QQ:3266959688", Bukkit.getConsoleSender());
    }
}
