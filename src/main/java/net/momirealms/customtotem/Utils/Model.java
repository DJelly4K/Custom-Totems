package net.momirealms.customtotem.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model {

    private String[][][] space;

    private int length;
    private int width;
    private int height;

    public Model(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.space = new String[length][width][height];
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
    public void setElement(String element, int length, int width, int height) {
        this.space[length][width][height] = element;
    }
    public String getElement(int length, int width, int height) {
        return this.space[length][width][height];
    }

    /*
    设置空间与获取空间
    */
    public void setSpace(String[][][] space) {
        this.space = space;
    }
    public String[][][] getSpace() {
        return this.space;
    }

    public Model createSpace(int length, int width, int height) {
        Model space = new Model(length, width, height);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0 ; k < height; k++){
                    space.setElement(this.space[i][j][k], i, j, k);
                }
            }
        }
        return space;
    }

    /*
    用于在大量for循环的函数中清理缓存
    */
    public void cleanCache() {
        this.space = new String[this.length][this.width][this.height];
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Model)) return false;
        for (int i = 0; i < 4; ++i) {
            if (Arrays.deepEquals(this.space, ((Model)obj).getSpace())) {
                return true;
            }
        }
        return false;
    }

    /*
    方案一：先确定图腾核心在三维数组内的位置
    补全二维投影为正方形且图腾核心位于中心
    遍历三维空间的方块，并绘制三维元素模型
    与配置文件三维元素模型比较
    不可行：补齐区域的内的其他方块可能影响空间的判定
    */

    /*
    方案二：

    */
}
