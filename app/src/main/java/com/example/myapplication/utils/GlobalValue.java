package com.example.myapplication.utils;

import android.graphics.Color;

public class GlobalValue {

    /**
     * 是否可以启动android页面的轮询线程(其实是当前的状态是否有网络。).
     */
    public static boolean canRunThread = true;
    // 后台服务器是否为启动状态
    public static boolean isServerRunning = true;
    //net_resume是解决当前如果在下单页面，如果网络恢复不要到下单主页面
    public static boolean net_resumed = false;



    // 当前是否可以更新apk，必须同时满足2个条件：1.版本 2.网络
    public static boolean apkCanUpdate = false;

    //超价tick设置i
    public static int SUPER_SELL_TICK = 1;
    public static int SUPER_BUY_TICK = 1;
    //持仓界面跳转到下单界面标识
    //public static boolean POSITION_TO_NORMAL_ORDER = false;

    public static long rBits; // 某一个进程的总接收量
    public static long sBits; // 某一个进程的总发送量

    // 通知相关信息。
    public static int sNotificationID = 0;
    public static final String NOTIFICATION_CHANNEL_ID = "0X1234";
    public static final CharSequence NOTIFICATION_CHANNEL_NAME = "消息通知";
    public static final String NOTIFICATION_CHANNEL_DESCRIPTION = "TradeNow消息通知";

    public static final String BASICDATA_NAME = "basicData.json";

    // 0：表示红涨绿跌 1:绿跌红涨
    // public static int UP_DOWN_COLOR = 0;
    // 绿色
    public static int DOWN_COLOR = Color.parseColor("#1fba55");
    public static int PANEL_DOWN_COLOR = Color.parseColor("#2bcd9b");

    // 红色
    public static int UP_COLOR = Color.parseColor("#ff3d01");
    public static int PANEL_UP_COLOR = Color.parseColor("#f15352");

    /**
     * 行情刷新频率，当前可以设置的1/3/5/10秒。
     */
    public static long FRESH_INTERVAL = 1000;

    // 屏幕宽
    public static int SCREEN_WIDTH = -1;
    // 屏幕高
    public static int SCREEN_HEIGHT = -1;

    // http://218.202.237.33:8088/mdapi
    public static String brokerURL;
    public static String brokerURLWithoutMdapi;
    public static boolean isPlatformControl = false;

    // 新版api的认证所需信息
    public static final String AuthCode = "0000000000000000";
    public static final String AppID = "simnow_client_test";
    //指纹登录设置
    public static boolean fingerLogin = false;
}
