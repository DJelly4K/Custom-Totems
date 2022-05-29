package net.momirealms.customtotem.utils;

import java.util.List;

public class Totem {

    //核心与物品
    private String key;
    private final Item item;
    private List<String> commands;
    private final Model model;
    private final CorePos corePos;

    //消耗
    private boolean cCore;
    private boolean cBlock;
    private boolean cItem;


    public Totem(String key, Item item, List<String> commands, Model model, CorePos corePos) {

        this.commands = commands;
        this.item = item;
        this.model = model;
        this.corePos = corePos;
        this.key = key;

    }

    public Model getModel() {
        return this.model;
    }
    public Item getItem() {
        return this.item;
    }
    public CorePos getCorePos() {
        return this.corePos;
    }
    public List<String> getCommands(){return this.commands;}
    public String getKey(){return this.key;}
    public boolean getcCore(){return this.cCore;}
    public boolean getcItem(){return this.cItem;}
    public boolean getcBlock(){return this.cBlock;}

    public void setcCore(boolean b){
        this.cCore = b;
    }
    public void setcItem(boolean b){
        this.cItem = b;
    }
    public void setcBlock(boolean b){
        this.cBlock = b;
    }
}
