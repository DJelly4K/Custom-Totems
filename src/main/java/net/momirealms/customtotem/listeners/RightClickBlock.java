package net.momirealms.customtotem.listeners;

import net.momirealms.customtotem.configmanager.ConfigManager;
import net.momirealms.customtotem.CustomTotems;
import net.momirealms.customtotem.messagemanager.MessageManager;
import net.momirealms.customtotem.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;


public class RightClickBlock implements Listener{

    BukkitScheduler bukkitScheduler = Bukkit.getScheduler();

    private HashMap<Player, Long> coolDown;
    {
        coolDown = new HashMap<Player, Long>();
    }

    @EventHandler
    public void onRightClickBlock(PlayerInteractEvent event){

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!event.hasItem()) return;

        //判断点击方块是否为图腾方块
        Block block = event.getClickedBlock();
        int id = CheckBlock.getBlockID(block);
        if(id == 0) return;

        if(ConfigManager.CORES.contains(id)){

            ItemStack itemStack = event.getItem();
            Player player = event.getPlayer();

            //判断手中物品是否为对应图腾的激活物
            if (ConfigManager.TOTEMS.get(id).getItem().checkItem(itemStack)){

                long time = System.currentTimeMillis();
                //冷却时间判断
                if (time - (coolDown.getOrDefault(player, time - ConfigManager.Config.coolDownTime)) < ConfigManager.Config.coolDownTime) {
                    MessageManager.playerMessage(ConfigManager.Config.prefix+ConfigManager.Config.coolDown, player);
                    return;
                }
                //重置冷却时间
                coolDown.put(player, time);
                itemStack.setAmount(itemStack.getAmount()-1);
                bukkitScheduler.runTaskAsynchronously(CustomTotems.instance, ()->{

                    Totem totem = ConfigManager.TOTEMS.get(id);

                    Model model = totem.getModel();
                    CorePos corePos = totem.getCorePos();
                    int ro = Model.checkLocationModel(model, block.getLocation(), corePos);
                    if(ro != 0){

                        //不消耗物品
                        if(!totem.getcItem()){
                            Item.giveItem(totem.getItem(), player);
                        }
                        //移除图腾
                        if(totem.getcBlock()){
                            Model.removeModel(model, block.getLocation(), corePos, ro);
                        }else if(totem.getcCore()){
                            bukkitScheduler.callSyncMethod(CustomTotems.instance, ()->{
                                CheckBlock.removeBlock(block);
                                return null;
                            });
                        }

                        //执行指令
                        CommandSender commandSender = Bukkit.getConsoleSender();
                        totem.getCommands().forEach(command ->{
                            String finalCommand = command.replaceAll("\\{x}", String.valueOf(block.getX())).
                                    replaceAll("\\{y}", String.valueOf(block.getY())).
                                    replaceAll("\\{z}", String.valueOf(block.getZ())).
                                    replaceAll("\\{player}", event.getPlayer().getName()).
                                    replaceAll("\\{world}", player.getWorld().getName());
                            bukkitScheduler.callSyncMethod(CustomTotems.instance, ()->{
                                Bukkit.dispatchCommand(commandSender, finalCommand);
                                return null;
                            });
                        });

                    }else {

                        //错误的摆放方式
                        MessageManager.playerMessage(ConfigManager.Config.prefix+ConfigManager.Config.wrongTotem,player);
                        bukkitScheduler.callSyncMethod(CustomTotems.instance, ()->{

                            Item.giveItem(totem.getItem(), player);
                            return null;

                        });
                    }
                });

            }else {
                //非图腾激活物
                MessageManager.playerMessage(ConfigManager.Config.prefix+ConfigManager.Config.wrongItem, player);
            }
        }
    }
}
