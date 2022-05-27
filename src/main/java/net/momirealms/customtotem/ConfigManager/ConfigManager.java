package net.momirealms.customtotem.ConfigManager;

import net.momirealms.customtotem.CustomTotems;
import net.momirealms.customtotem.MessageManager.MessageManager;
import net.momirealms.customtotem.Utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class ConfigManager {

    //插件配置文件部分
    public static class Config {

        //配置文件
        public static boolean res;
        public static boolean ia;

        //语言文件
        public static String resInfo;
        public static String wrongItem;
        public static String reload;
        public static String prefix;
        public static String noConsole;
        public static String getItem;
        public static String giveItem;

        //重载插件
        public static void ReloadConfig(){

            //获取config.yml
            CustomTotems.instance.reloadConfig();
            FileConfiguration configuration = CustomTotems.instance.getConfig();

            //文件内的配置加载
            ConfigManager.BlocksLoad();
            ConfigManager.ItemsLoad();

            //处理配置
            Config.res = configuration.getBoolean("integration.Residence");
            Config.ia = configuration.getBoolean("integration.ItemsAdder");

            //处理消息
            Config.resInfo = configuration.getString("messages.res-info");
            Config.wrongItem = configuration.getString("messages.wrong-item");
            Config.reload = configuration.getString("messages.reload");
            Config.prefix = configuration.getString("messages.prefix");
            Config.noConsole = configuration.getString("messages.no-console");
            Config.getItem = configuration.getString("messages.get-item");
            Config.giveItem = configuration.getString("messages.give-item");

        }
    }

    //将数据存入缓存，无并发场景使用HashMap效率高
    public static HashMap<String, String> BLOCKS;
    static {
        BLOCKS = new HashMap<String, String>(16);
    }
    public static HashMap<String, Item> ITEMS;
    static {
        ITEMS = new HashMap<String, Item>(16);
    }

    public static HashMap<String, String> TOTEMS;
    private static List<String> CORES;

    //根据文件名获取配置文件
    public static YamlConfiguration getConfig(String configName) {

        File file = new File(CustomTotems.instance.getDataFolder(), configName);
        //文件不存在则生成默认配置
        if (!file.exists()) {
            CustomTotems.instance.saveResource(configName, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    /*
    重载或开服的时候先清空缓存数据再导入
    加载方块数据
    */
    public static void BlocksLoad() {
        try {
            BLOCKS.clear();
            YamlConfiguration blockConfig = ConfigManager.getConfig("TotemBlocks.yml");
            Set<String> keys = Objects.requireNonNull(blockConfig.getConfigurationSection("blocks")).getKeys(false);
            keys.forEach(key -> BLOCKS.put(blockConfig.getString(key), key));
            BLOCKS.put("ANY", "0");
        }
        catch (Exception e) {
            e.printStackTrace();
            MessageManager.consoleMessage("&c[CustomTotems] TotemBlocks.yml加载失败!",Bukkit.getConsoleSender());
        }
    }

    /*
    重载或开服的时候先清空缓存数据再导入
    加载激活物数据
    */
    public static void ItemsLoad() {

        try {
            ITEMS.clear();
            YamlConfiguration blockConfig = ConfigManager.getConfig("TotemItems.yml");
            Set<String> keys = Objects.requireNonNull(blockConfig.getConfigurationSection("items")).getKeys(false);
            keys.forEach(key -> {

                Material material = Material.valueOf(blockConfig.getString("items." + key + ".material"));
                String name = Objects.requireNonNull(blockConfig.getString("items." + key + ".display.name")).replaceAll("&","§");
                List<String> lore = blockConfig.getStringList("items." + key + ".display.lore");

                for (int i = 0; i < lore.size(); ++i) {
                    lore.set(i, lore.get(i).replaceAll("&", "§"));
                }

                //创建物品并丢进缓存
                Item item = new Item(name, lore, material);
                ITEMS.put(key, item);
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            MessageManager.consoleMessage("&c[CustomTotems] TotemItems.yml加载失败!",Bukkit.getConsoleSender());
        }
    }
}
