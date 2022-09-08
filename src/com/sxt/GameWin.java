package com.sxt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GameWin extends JFrame { //继承JFrame才能监听鼠标键盘事件的功能

    //设置静态变量 表示游戏状态 0表示未开始 1运行中 2商店 3失败 4胜利
    static int state;
    //创建链表 存储金块 石块
    List<Object> objectList = new ArrayList<>();

    Bg bg = new Bg();//背景
    Line line = new Line(this);//线


    {
        //是否可以放置
        boolean isPlace = true;
        //循环创建金块
        for (int i = 0; i < 11; i++){
            double random = Math.random();
            Gold gold;//存放当前生成的金块

            if (random<0.3){
                gold = new GoldMini();
            }else if(random<0.7){
                gold = new Gold();
            }else if(random<0.9){
                gold = new GoldPlus();
            }else{
                gold=new Ranchi();
            }

            for (Object obj:objectList){
                if (gold.getRoc().intersects(obj.getRoc())) {
                    //发生重叠 不能放置 需要重新生成
                    isPlace=false;
                }
            }
            //循环完毕  判断是否可放置
            if (isPlace){
                objectList.add(gold);
            }else {
                isPlace=true;
                i--;
            }

        }
        //循环创建石块
        for (int i = 0; i < 5; i++){
            Rock rock = new Rock();
            for (Object obj:objectList){
                if (rock.getRoc().intersects(obj.getRoc())) {
                    isPlace = false;
                }
            }
            if (isPlace){
                objectList.add(rock);
            }else {
                isPlace=true;
                i--;
            }
        }
    }

    Image offScreenImage;

    void launch(){ //launch方法初始化窗口信息
        this.setVisible(true);//设置窗口可见
        this.setSize(768,1000);
        this.setLocationRelativeTo(null);//窗口位置：居中
        this.setTitle("黄金矿工");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//关闭窗口的方法

        //设置鼠标事件 改变状态
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //不同状态 发出不同指令
                switch (state){
                    case 0:
                        if (e.getButton()==3){
                            state=1;
                            bg.startTime = System.currentTimeMillis();
                        }
                        break;
                    case 1:
                        //左右摇摆 点击左键
                        if(e.getButton() == 1 && line.state==0){
                            line.state = 1;
                        }
                        //抓取返回 点击右键
                        if (e.getButton()==3 && line.state==3 && Bg.waterNum>0){
                            Bg.waterFlag=true;
                            Bg.waterNum--;
                        }
                        break;
                    case 2:
                        if (e.getButton()==1){
                            bg.shop=true;
                        }
                        if (e.getButton()==3){
                            state=1;
                            bg.startTime = System.currentTimeMillis();
                        }
                        break;
                    case 3:
                    case 4:
                        if (e.getButton()==1){
                            state=0;
                            bg.reGame();
                            line.reGame();
                        }
                        break;
                    default:

                }

            }
        });

        //使线不停摆动
        while (true){
            repaint();
            nextLevel();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //下一关
    public void nextLevel(){
        if (bg.gameTime() && state==1){
            if (Bg.count >= bg.goal){//如果当前积分大于等于目标积分
                if (Bg.level==5){
                    state=4;
                }else {
                    state=2;
                    Bg.level++;//关卡数加一
                }

            }else {
                state=3;
            }
            dispose();//释放已过关窗体
            GameWin gameWin = new GameWin();
            gameWin.launch();//调用launch方法绘制新窗口

        }

    }
    @Override
    public void paint(Graphics g) {
        //画布与窗体大小相同
        offScreenImage = this.createImage(768,1000);
        //给画布添加画笔
        Graphics gImage = offScreenImage.getGraphics();


        bg.paintSelf(gImage);
        if (state == 1){
            //先画物体
            for(Object obj:objectList){
                obj.paintSelf(gImage);
            }
            //后画线
            line.paintSelf(gImage);
        }
        //将画布绘制到窗口中
        g.drawImage(offScreenImage,0,0,null);
    }


}
