package net.momirealms.customtotem.utils;

import net.momirealms.customtotem.configmanager.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class Item {

    private String name;
    private List<String> lore;
    private Material material;

    public Item(String name, List<String> lore, Material material){

        this.name = name;
        this.lore = lore;
        this.material = material;

    }

    public String getName() { return this.name; }
    public Material getMaterial() {
        return this.material;
    }
    public List<String> getLore() {
        return this.lore;
    }

    public boolean checkItem(ItemStack item) {

        if (item.getType() != this.material) return false;

        ItemMeta itemMeta = item.getItemMeta();

        if (!itemMeta.hasLore() || !itemMeta.hasDisplayName()) return false;

        if (!Objects.equals(itemMeta.getDisplayName(), this.name)) return false;

        return itemMeta.getLore() == this.lore;
    }

    public static void giveItem(String itemName, Player player){

        //从缓存中请求物品Item
        Item item = ConfigManager.ITEMS.get(itemName);

        //设置物品的各个属性
        ItemStack itemStack = new ItemStack(item.material);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(item.name);
        itemMeta.setLore(item.lore);

        itemStack.setItemMeta(itemMeta);

        //给予玩家物品
        player.getInventory().addItem(itemStack);
    }
}
