package net.momirealms.customtotem.Listeners;

import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RightClickBlock implements Listener{

    public void onRightClickBlock(PlayerInteractEvent event){

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!event.hasItem()) return;

        Block block = event.getClickedBlock();



    }
}
