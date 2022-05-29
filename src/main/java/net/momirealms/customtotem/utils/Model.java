package net.momirealms.customtotem.utils;

import net.momirealms.customtotem.CustomTotems;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Model {

    private int[][][] model;

    private final int length;
    private final int width;
    private final int height;

    public Model(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.model = new int[length][width][height];
    }

    /*
    一些基础方法
    */
    public int getLength() {
        return this.length;
    }
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }

    /*
    设置空间元素与获取空间元素
    */
    public void setElement(int element, int length, int width, int height) {
        this.model[length][width][height] = element;
    }
    public int getElement(int length, int width, int height) {
        return this.model[length][width][height];
    }

    /*
    4x2旋转镜像检测
    */
    public static int checkLocationModel(Model model, Location location, CorePos corePos){

        int xOffset = corePos.getX();
        int yOffset = corePos.getY();
        int zOffset = corePos.getZ();

        int height = model.getHeight();
        int length = model.getLength();
        int width = model.getWidth();

        //从第一层开始逐层扫描，只有一层满足要求才能扫描上一层，否则跳入下一个方向检测
        Location startLoc = location.clone().subtract(0, yOffset, 0);

        Label_1:
        {
            for(int i = 0; i< height; i++) {
                //起点定于左下角，向右上遍历
                Location loc = startLoc.clone().add(-xOffset, i, -zOffset);
                for (int z = 0; z < width; z++) {
                    for (int x = 0; x < length; x++) {
                        int id = CheckBlock.getBlockID(loc.clone().add(x, 0, z).getBlock());
                        if (model.getElement(x, z, i) == 0) continue;
                        if (id != model.getElement(x, z, i)) {
                            break Label_1;
                        }
                    }
                }
            }
            return 1;
        }
        Label_2:
        {
            for (int i = 0; i < height; i++) {
                //起点定于右上角，向左下遍历
                Location loc = startLoc.clone().add(xOffset, i, zOffset);
                for (int z = 0; z < width; z++) {
                    for (int x = 0; x < length; x++) {
                        int id = CheckBlock.getBlockID(loc.clone().subtract(x, 0, z).getBlock());
                        if (model.getElement(x, z, i) == 0) continue;
                        if (id != model.getElement(x, z, i)) {
                            break Label_2;
                        }
                    }
                }
            }
            return 2;
        }
        Label_3:{
            for (int i = 0; i < height; i++) {
                //起点定于左上角，向右下遍历
                Location loc = startLoc.clone().add(-zOffset, i, xOffset);
                for (int z = 0; z < width; z++) {
                    for (int x = 0; x < length; x++) {
                        int id = CheckBlock.getBlockID(loc.clone().subtract(-z, 0, x).getBlock());
                        if (model.getElement(x, z, i) == 0) continue;
                        if (id != model.getElement(x, z, i)) {
                            break Label_3;
                        }
                    }
                }
            }
            return 3;
        }
        Label_4:
        {
            for (int i = 0; i < height; i++) {
                //起点定于右下角，向左上遍历
                Location loc = startLoc.clone().add(zOffset, i, -xOffset);
                for (int z = 0; z < width; z++) {
                    for (int x = 0; x < length; x++) {
                        int id = CheckBlock.getBlockID(loc.clone().add(-z, 0, x).getBlock());
                        if (model.getElement(x, z, i) == 0) continue;
                        if (id != model.getElement(x, z, i)) {
                            break Label_4;
                        }
                    }
                }
            }
            return 4;
        }
        Label_5:
        {
            for (int i = 0; i < height; i++) {
                //起点定于左下角（镜像），向上左遍历
                Location loc = startLoc.clone().add(-zOffset, i, -xOffset);
                for (int z = 0; z < width; z++) {
                    for (int x = 0; x < length; x++) {
                        int id = CheckBlock.getBlockID(loc.clone().add(z, 0, x).getBlock());
                        if (model.getElement(x, z, i) == 0) continue;
                        if (id != model.getElement(x, z, i)) {
                            break Label_5;
                        }
                    }
                }
            }
            return 5;
        }
        Label_6:
        {
            for (int i = 0; i < height; i++) {
                //起点定于右上角（镜像），向下左遍历
                Location loc = startLoc.clone().add(zOffset, i, xOffset);
                for (int z = 0; z < width; z++) {
                    for (int x = 0; x < length; x++) {
                        int id = CheckBlock.getBlockID(loc.clone().subtract(z, 0, x).getBlock());
                        if (model.getElement(x, z, i) == 0) continue;
                        if (id != model.getElement(x, z, i)) {
                            break Label_6;
                        }
                    }
                }
            }
            return 6;
        }
        Label_7:
        {
            for (int i = 0; i < height; i++) {
                //起点定于左上角（镜像)，向右下遍历
                Location loc = startLoc.clone().add(-xOffset, i, zOffset);
                for (int z = 0; z < width; z++) {
                    for (int x = 0; x < length; x++) {
                        int id = CheckBlock.getBlockID(loc.clone().add(x, 0, -z).getBlock());
                        if (model.getElement(x, z, i) == 0) continue;
                        if (id != model.getElement(x, z, i)) {
                            break Label_7;
                        }
                    }
                }
            }
            return 7;
        }
        Label_8:
        {
            for (int i = 0; i < height; i++) {
                //起点定于右下角（镜像），向左上遍历
                Location loc = startLoc.clone().add(xOffset, i, -zOffset);
                for (int z = 0; z < width; z++) {
                    for (int x = 0; x < length; x++) {
                        int id = CheckBlock.getBlockID(loc.clone().add(-x, 0, z).getBlock());
                        if (model.getElement(x, z, i) == 0) continue;
                        if (id != model.getElement(x, z, i)) {
                            break Label_8;
                        }
                    }
                }
            }
            return 8;
        }
        return 0;
    }
    public static void removeModel(Model model, Location location, CorePos corePos, int id) {

        int xOffset = corePos.getX();
        int yOffset = corePos.getY();
        int zOffset = corePos.getZ();

        int height = model.getHeight();
        int length = model.getLength();
        int width = model.getWidth();

        //从第一层开始逐层扫描，只有一层满足要求才能扫描上一层，否则跳入下一个方向检测
        Location startLoc = location.clone().subtract(0, yOffset, 0);

        Bukkit.getScheduler().callSyncMethod(CustomTotems.instance, ()->{

            switch (id) {
                case 1:
                    for (int i = 0; i < height; i++) {
                        //起点定于左下角，向右上遍历
                        Location loc = startLoc.clone().add(-xOffset, i, -zOffset);
                        for (int z = 0; z < width; z++) {
                            for (int x = 0; x < length; x++) {
                                CheckBlock.removeBlock(loc.clone().add(x, 0, z).getBlock());
                            }
                        }
                    }
                case 2:
                    for (int i = 0; i < height; i++) {
                        //起点定于右上角，向左下遍历
                        Location loc = startLoc.clone().add(xOffset, i, zOffset);
                        for (int z = 0; z < width; z++) {
                            for (int x = 0; x < length; x++) {
                                CheckBlock.removeBlock(loc.clone().subtract(x, 0, z).getBlock());
                            }
                        }
                    }
                case 3:
                    for (int i = 0; i < height; i++) {
                        //起点定于左上角，向右下遍历
                        Location loc = startLoc.clone().add(-zOffset, i, xOffset);
                        for (int z = 0; z < width; z++) {
                            for (int x = 0; x < length; x++) {
                                CheckBlock.removeBlock(loc.clone().subtract(-z, 0, x).getBlock());
                            }
                        }
                    }
                case 4:
                    for (int i = 0; i < height; i++) {
                        //起点定于右下角，向左上遍历
                        Location loc = startLoc.clone().add(zOffset, i, -xOffset);
                        for (int z = 0; z < width; z++) {
                            for (int x = 0; x < length; x++) {
                                CheckBlock.removeBlock(loc.clone().add(-z, 0, x).getBlock());
                            }
                        }
                    }
                case 5:
                    for (int i = 0; i < height; i++) {
                        //起点定于左下角（镜像），向上左遍历
                        Location loc = startLoc.clone().add(-zOffset, i, -xOffset);
                        for (int z = 0; z < width; z++) {
                            for (int x = 0; x < length; x++) {
                                CheckBlock.removeBlock(loc.clone().add(z, 0, x).getBlock());
                            }
                        }
                    }
                case 6:
                    for (int i = 0; i < height; i++) {
                        //起点定于右上角（镜像），向下左遍历
                        Location loc = startLoc.clone().add(zOffset, i, xOffset);
                        for (int z = 0; z < width; z++) {
                            for (int x = 0; x < length; x++) {
                                CheckBlock.removeBlock(loc.clone().add(x, 0, -z).getBlock());
                            }
                        }
                    }
                case 7:
                    for (int i = 0; i < height; i++) {
                        //起点定于左上角（镜像)，向右下遍历
                        Location loc = startLoc.clone().add(-xOffset, i, zOffset);
                        for (int z = 0; z < width; z++) {
                            for (int x = 0; x < length; x++) {
                                CheckBlock.removeBlock(loc.getBlock());
                            }
                        }
                    }
                case 8:
                    for (int i = 0; i < height; i++) {
                        //起点定于右下角（镜像），向左上遍历
                        Location loc = startLoc.clone().add(xOffset, i, -zOffset);
                        for (int z = 0; z < width; z++) {
                            for (int x = 0; x < length; x++) {
                                CheckBlock.removeBlock(loc.clone().add(-x, 0, z).getBlock());
                            }
                        }
                    }
            }
            return null;
        });
    }
}
