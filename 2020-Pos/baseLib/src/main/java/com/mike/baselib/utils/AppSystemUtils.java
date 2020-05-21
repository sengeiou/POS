package com.mike.baselib.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;

import com.mike.baselib.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class AppSystemUtils {

    public static String assetsFileToString(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /* 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return  */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 打开高德地图
     *
     * @author jack
     * created at 2017/8/2 15:01
     */
    public static void openGaoDeMap(Context context, double dlat, double dlon, String dname) {
        try {
            // APP_NAME  自己应用的名字
            String uri = getGdMapUri(context.getString(R.string.app_name), "", "", "",
                    String.valueOf(dlat),
                    String.valueOf(dlon),
                    dname);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage(Constants.MAP_GAODE);
            intent.setData(Uri.parse(uri));
            context.startActivity(intent); //启动调用
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取打开高德地图应用uri
     * style
     * 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5
     * 不走高速且避免收费；6 不走高速且躲避拥堵；
     * 7 躲避收费和拥堵；8 不走高速躲避收费和拥堵)
     */
    private static String getGdMapUri(String appName, String slat, String slon, String sname, String dlat, String dlon, String dname) {
        String newUri = "androidamap://navi?sourceApplication=%1$s&poiname=%2$s&lat=%3$s&lon=%4$s&dev=1&style=2";
        return String.format(newUri, appName, dname, dlat, dlon);
    }


    /**
     * 打开百度地图
     *
     * @param slat  开始地点 维度
     * @param slon  开始地点 经度
     * @param sname 开始地点 名字
     * @param dlat  终点地点 维度
     * @param dlon  终点地点 经度
     * @param dname 终点名字
     * @param city  所在城市- 动态获取 （例如：北京市）
     * @author jack
     * created at 2017/8/2 15:01
     */
    public static void openBaiduMap(Context context,
                                    double dlat, double dlon, String dname) {
        try {
            String uri = getBaiduMapUri("", "", "",
                    String.valueOf(dlat), String.valueOf(dlon), dname, "", "");
            Intent intent = Intent.parseUri(uri, 0);
            context.startActivity(intent); //启动调用
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String getBaiduMapUri(String originLat, String originLon, String originName, String desLat, String desLon, String destination, String region, String src) {
//        String uri = "intent://map/direction?origin=latlng:%1$s,%2$s|name:%3$s" +
//                "&destination=latlng:%4$s,%5$s|name:%6$s&mode=driving&region=%7$s&src=%8$s#Intent;" +
//                "scheme=bdapp;package=com.baidu.BaiduMap;end";
        String uri = "intent://map/direction?destination=latlng:%1$s,%2$s|name:%3$s&mode=driving&region=%4$s&src=%5$s#Intent;" +
                "scheme=bdapp;package=com.baidu.BaiduMap;end";
        return String.format(uri, desLat, desLon, destination, region, src);
    }


    public static boolean isExistActivity(Context context, Class<?> activity) {
        boolean flag = false;
        Intent intent = new Intent(context, activity);
        ComponentName cmpName = intent.resolveActivity(context.getPackageManager());
        if (cmpName != null) { // 说明系统中存在这个activity    
            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList =
                    am.getRunningTasks(10);  //获取从栈顶开始往下查找的10个activity
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了    
                    flag = true;
                    break;  //跳出循环，优化效率  
                }
            }
        }
        return flag;
    }

}
