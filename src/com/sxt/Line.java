package com.sxt;

import java.awt.*;

public class Line {
    //线的起点坐标
    int x = 380;
    int y = 180;

    //终点坐标
    int endx = 500;
    int endy = 500;

    //线的长度
    double length = 100;
    //线长最小值
    double MIN_length = 100;
    //线长最大值
    double MAX_length = 750;
    double n = 0;
    //方向参数
    int dir = 1;
    //状态0摇摆  状态1抓取  状态2收回  状态3抓取返回
    int state;

    //钩爪图片
    Image hook = Toolkit.getDefaultToolkit().getImage("imgs/hook.png");


    //抓取判定方法
    GameWin frame;

    Line(GameWin frame){
        this.frame=frame;
    }

    //钩爪碰撞检测 检测物体是否被抓取
    void logic(){
        for (Object obj:this.frame.objectList){
            if(endx>obj.x && endx<obj.x+obj.width
                    && endy>obj.y && endy<obj.y+obj.height){
                state=3;
                obj.flag=true;
            }
        }


    }

    //绘制方法
    void lines(Graphics g){
        //endx和endy的变化
        endx = (int) (x + length*Math.cos(n*Math.PI));
        endy = (int) (y + length*Math.sin(n*Math.PI));
        g.setColor(Color.red);
        //加粗红线
        g.drawLine(x-1,y,endx-1,endy);
        g.drawLine(x,y,endx,endy);
        g.drawLine(x+1,y,endx+1,endy);
        //让钩爪中间位置 放在红线顶点
        g.drawImage(hook,endx-36,endy-2,null);
    }

    void paintSelf(Graphics g){

        logic();
        //线的状态
        switch (state){
            case 0:
                if(n<0.1){
                    dir = 1;
                }else if(n>0.9){
                    dir = -1;
                }
                n = n + 0.005*dir;
                lines(g);
                break;
            case 1:
                //线的长度给一个范围
                if(length<MAX_length){
                    //状态为1 即鼠标左键 长度变化
                    length = length+5;
                    lines(g);
                }else {
                    state = 2;
                }

                break;

            case 2:
                //收回线长变短
                if(length>MIN_length){
                    //状态为1 即鼠标左键 长度变化
                    length = length-5;
                    lines(g);
                }else {
                    state = 0;
                }
                break;
            case 3:
                int m=1;
                //让红线返回
                if(length>MIN_length){
                    length = length-5;
                    lines(g);

                    for (Object obj:this.frame.objectList){
                        //判断能否移动
                        if (obj.flag){
                            m=obj.m;
                            obj.x = endx-obj.getWidth()/2;
                            obj.y = endy;
                            if (length<=MIN_length){
                                obj.x=-150;
                                obj.y=-150;
                                obj.flag=false;
                                Bg.waterFlag=false;
                                //加分
                                Bg.count+=obj.count;
                                state = 0;
                            }
                            //抓取时判断是否使用药水
                            if (Bg.waterFlag){
                                if (obj.type==1){//如果抓到的是金块
                                    m=1;
                                }
                                if (obj.type==2){//如果抓到的是石块
                                    obj.x=-150;
                                    obj.y=-150;
                                    obj.flag=false;
                                    Bg.waterFlag=false;
                                    state = 2;
                                }
                            }
                        }

                    }

                }
                try {
                    Thread.sleep(m);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;

            default:
        }

    }
    //重置线元素
    void reGame(){
        n=0;
        length=100;
    }
}
