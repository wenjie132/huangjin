package com.sxt;

import java.awt.*;

public class Gold extends Object{

    public Gold() {
        this.x=(int)(Math.random()*700);
        this.y=(int)(Math.random()*550+300);
        this.width=52;
        this.height=52;

        this.flag=false;
        this.m=30;
        this.count=4;
        this.type =1;
        this.img = Toolkit.getDefaultToolkit().getImage("imgs/gold1.gif");
    }
}
//多种金块
class GoldMini extends Gold{
    public GoldMini() {
        this.width=36;
        this.height=36;
        this.m=15;
        this.count=2;
        this.img = Toolkit.getDefaultToolkit().getImage("imgs/gold0.gif");
    }
}

class GoldPlus extends Gold{
    public GoldPlus() {
        this.x=(int)(Math.random()*650);
        this.width=105;
        this.height=105;
        this.m=60;
        this.count=8;
        this.img = Toolkit.getDefaultToolkit().getImage("imgs/gold2.gif");
    }
}
class Ranchi extends Gold {
    public Ranchi() {
        this.x =(int)(Math.random()*650);
        this.width=102;
        this.height=95;
        this.m=70;
        this.count=10;
        this.img =Toolkit.getDefaultToolkit().getImage("imgs/zuanshi2.png");
    }
}

