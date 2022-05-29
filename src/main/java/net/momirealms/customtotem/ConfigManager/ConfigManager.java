package net.momirealms.customtotem.configmanager;

import net.momirealms.customtotem.CustomTotems;
import net.momirealms.customtotem.messagemanager.MessageManager;
import net.momirealms.customtotem.utils.CorePos;
import net.momirealms.customtotem.utils.Item;
import net.momirealms.customtotem.utils.Model;
import net.momirealms.customtotem.utils.Totem;
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
        public static boolean ia;

        //语言文件
        public static String wrongItem;
        public static String reload;
        public static String prefix;
        public static String noConsole;
        public static String getItem;
        public static String giveItem;
        public static String coolDown;
        public static String wrongTotem;

        public static int coolDownTime;

        //重载插件
        public static void ReloadConfig(){

            //获取config.yml
            CustomTotems.instance.reloadConfig();
            FileConfiguration configuration = CustomTotems.instance.getConfig();

            //文件内的配置加载
            ConfigManager.ItemsLoad();
            ConfigManager.TotemsLoad();
            ConfigManager.BlocksLoad();

            //处理配置
            Config.ia = configuration.getBoolean("integration.ItemsAdder");

            Config.coolDownTime = configuration.getInt("cooldown");

            //处理消息
            Config.wrongItem = configuration.getString("messages.wrong-item");
            Config.reload = configuration.getString("messages.reload");
            Config.prefix = configuration.getString("messages.prefix");
            Config.noConsole = configuration.getString("messages.no-console");
            Config.getItem = configuration.getString("messages.get-item");
            Config.giveItem = configuration.getString("messages.give-item");
            Config.coolDown = configuration.getString("messages.cooldown");
            Config.wrongTotem = configuration.getString("messages.wrong-totem");

        }
    }

    //初始化存入缓存
    public static HashMap<String, Integer> BLOCKS;
    static {
        BLOCKS = new HashMap<String, Integer>(64);
    }
    public static List<Integer> CORES;
    static {
        CORES = new ArrayList<>(16);
    }
    public static HashMap<String, Item> ITEMS;
    static {
        ITEMS = new HashMap<String, Item>(16);
    }
    public static HashMap<Integer, Totem> TOTEMS;
    static {
        TOTEMS = new HashMap<Integer, Totem>(16);
    }

    /*
    根据文件名获取配置文件
     */
    public static YamlConfiguration getConfig(String configName) {

        File file = new File(CustomTotems.instance.getDataFolder(), configName);
        //文件不存在则生成默认配置
        if (!file.exists()) {
            CustomTotems.instance.saveResource(configName, false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    /*
    加载方块数据
    */
    public static void BlocksLoad() {
        try {
            BLOCKS.clear();
            YamlConfiguration blockConfig = ConfigManager.getConfig("TotemBlocks.yml");
            Set<String> keys = blockConfig.getConfigurationSection("blocks").getKeys(false);
            keys.forEach(key -> BLOCKS.put(key, (Integer) blockConfig.get("blocks." + key)));
            BLOCKS.put("ANY", 0);
        }
        catch (Exception e) {
            e.printStackTrace();
            MessageManager.consoleMessage("&c[CustomTotems] TotemBlocks.yml加载失败!",Bukkit.getConsoleSender());
        }
    }

    /*
    加载激活物数据
    */
    public static void ItemsLoad() {

        try {
            ITEMS.clear();
            YamlConfiguration itemConfig = ConfigManager.getConfig("TotemItems.yml");
            Set<String> keys = Objects.requireNonNull(itemConfig.getConfigurationSection("items")).getKeys(false);
            keys.forEach(key -> {

                Material material = Material.valueOf(itemConfig.getString("items." + key + ".material"));
                String name = Objects.requireNonNull(itemConfig.getString("items." + key + ".display.name")).replaceAll("&","§");
                List<String> lore = itemConfig.getStringList("items." + key + ".display.lore");
                int cmd = 0;
                if (itemConfig.contains("items." + key + ".custom-model-data")){
                    cmd = itemConfig.getInt("items." + key + ".custom-model-data");
                }

                for (int i = 0; i < lore.size(); ++i) {
                    lore.set(i, lore.get(i).replaceAll("&", "§"));
                }

                //创建物品并丢进缓存
                Item item = new Item(name, lore, material, cmd);
                ITEMS.put(key, item);
            });
        }
        catch (Exception e) {
            e.printStackTrace();
            MessageManager.consoleMessage("&c[CustomTotems] TotemItems.yml加载失败!",Bukkit.getConsoleSender());
        }
    }


    /*
    加载图腾数据
    */
    public static void TotemsLoad() {
        try {
            TOTEMS.clear();
            CORES.clear();
            YamlConfiguration totemConfig = ConfigManager.getConfig("Totems.yml");
            Set<String> keys = totemConfig.getConfigurationSection("totems").getKeys(false);
            for(String key : keys){

                Item item = ITEMS.get(totemConfig.getString("totems."+key+".item"));
                List<String> commands = totemConfig.getStringList("totems."+key+".commands");
                int core = totemConfig.getInt("totems."+key+".core");

                List<String> flat = totemConfig.getStringList("totems."+key+".layer.1");
                int length = flat.get(0).split("\\s+").length;
                int width = flat.size();
                int height = totemConfig.getConfigurationSection("totems."+key+".layer").getKeys(false).size();

                CorePos corePos = null;
                Model model = new Model(length,width,height);
                /*
                设置图腾模型
                */
                for(int k = 0; k < height; k++){
                    List<String> layer = totemConfig.getStringList("totems."+key+".layer."+(k+1));
                    for(int j = 0; j < width; j++){
                        String[] args = layer.get(j).split("\\s+");
                        for (int i = 0; i < length; i++) {
                            model.setElement(Integer.parseInt(args[i]), i, j, k);
                        }
                    }
                }

                for(int k = 0; k < height; k++){
                    for(int j = 0; j < width; j++){
                        for (int i = 0; i < length; i++) {
                            if (model.getElement(i, j, k) == core) {
                                corePos = new CorePos(i, j, k);
                            }
                        }
                    }
                }

                if(corePos == null) {
                    continue;
                }

                Totem totem = new Totem(key, item, commands, model, corePos);

                totem.setcItem(totemConfig.getBoolean("totems."+key+".consume-item"));
                totem.setcCore(totemConfig.getBoolean("totems."+key+".consume-core"));
                totem.setcBlock(totemConfig.getBoolean("totems."+key+".consume-totem"));

                TOTEMS.put(core, totem);
                CORES.add(core);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            MessageManager.consoleMessage("&c[CustomTotems] Totems.yml加载失败!",Bukkit.getConsoleSender());
        }
    }
}
