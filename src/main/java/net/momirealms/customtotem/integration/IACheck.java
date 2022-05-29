package net.momirealms.customtotem.integration;

import dev.lone.itemsadder.api.CustomBlock;
import net.momirealms.customtotem.configmanager.ConfigManager;
import org.bukkit.block.Block;

public class IACheck {

    public static String getNI(Block block){

        if (CustomBlock.byAlreadyPlaced(block) != null){

            if(ConfigManager.BLOCKS.get(CustomBlock.byAlreadyPlaced(block).getNamespacedID()) != null){

                return CustomBlock.byAlreadyPlaced(block).getNamespacedID();
            }
        };
        return null;
    }

    public static boolean removeIABlock(Block block){

        if (CustomBlock.byAlreadyPlaced(block) != null){
            CustomBlock.remove(block.getLocation());
            return true;
        }
        return false;
    }
}
