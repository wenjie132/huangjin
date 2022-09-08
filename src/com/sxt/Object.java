package com.sxt;

import java.awt.*;

public class Object {

    //公共属性
    //坐标
    int x;
    int y;
    //宽高
    int width;
    int height;
    //图片
    Image img;
    //标记 是否能移动
    boolean flag;
    //质量
    int m;

    //积分属性
    int count;
    //类型 1金块  2石块
    int type;

    void paintSelf(Graphics g){
        g.drawImage(img,x,y,null);
    }

    public int getWidth() {
        return width;
    }

    //获取矩形
    public Rectangle getRoc(){
        return new Rectangle(x,y,width,height);
    }
}
