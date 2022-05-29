package net.momirealms.customtotem.utils;

import net.momirealms.customtotem.configmanager.ConfigManager;
import net.momirealms.customtotem.integration.IACheck;
import org.bukkit.Material;
import org.bukkit.block.Block;


public class CheckBlock {

    public static int getBlockID(Block block){

        String name = block.getType().name();

        if(ConfigManager.BLOCKS.get(name) != null){

            return ConfigManager.BLOCKS.get(name);

        }else if(ConfigManager.Config.ia){

            if(ConfigManager.BLOCKS.get(IACheck.getNI(block)) != null){

                return ConfigManager.BLOCKS.get(IACheck.getNI(block));
            }

        }
        return 0;
    }

    public static void removeBlock(Block block){

        if(getBlockID(block) == 0) return;

        if(ConfigManager.Config.ia){
            if(IACheck.removeIABlock(block)){
                return;
            }
        }
        block.setType(Material.AIR);
    }
}
