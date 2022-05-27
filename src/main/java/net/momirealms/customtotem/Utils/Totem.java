package net.momirealms.customtotem.Utils;

import java.util.List;

public class Totem {

    //核心与物品
    private String name;
    private String core;
    private String item;
    private List<String> commands;

    //冷却时间
    private long coolDown;

    //消耗
    private boolean cCore;
    private boolean cBlock;
    private boolean cItem;

    public Totem(String key, String core, Item item, List<String> commands, Model model) {
        this.core = core;
        this.commands = commands;
    }
}
