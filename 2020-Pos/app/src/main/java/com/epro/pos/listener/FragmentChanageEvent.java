package com.epro.pos.listener;

public class FragmentChanageEvent {
    /**
       跳转界面传postion，0-6 系统设置条目界面
     level：系统设置fragment里面的层级，按返回键传
     type ：相同层级fragment里面的不同界面，按返回键传
     */
    public int postion; //fragment 界面
    public int level;   // fragment 层级
    public int type;    //相同层级不同条目
    public String from;  //首页跳转
}
