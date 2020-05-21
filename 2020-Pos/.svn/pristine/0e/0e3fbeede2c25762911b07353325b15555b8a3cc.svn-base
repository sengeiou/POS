package com.mike.baselib.service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;


import com.mike.baselib.activity.ProgressActivity;
import com.mike.baselib.listener.DownloadCallBack;
import com.mike.baselib.net.RetrofitHttp;
import com.mike.baselib.utils.AppContext;
import com.mike.baselib.utils.SPDownloadUtil;
import com.mike.baselib.utils.StorageUtils;

import java.io.File;


/**
 * 创建时间：2018/3/7
 * 编写人：damon
 * 功能描述 ：
 */

public class DownloadIntentService extends IntentService {

    private static final String TAG = "DownloadIntentService";
   // private NotificationManager mNotifyManager;
    private String mDownloadFileName;
   // private Notification mNotification;

    public DownloadIntentService() {
        super("InitializeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String downloadUrl = intent.getExtras().getString("download_url");
        final int downloadId = intent.getExtras().getInt("download_id");
        mDownloadFileName = intent.getExtras().getString("download_file");

        File dir = StorageUtils.getCacheDirectory(AppContext.getInstance().getContext());

        Log.d(TAG, "download_url --" + downloadUrl);
        Log.d(TAG, "download_file --" + mDownloadFileName);

        final File file = new File(dir, mDownloadFileName);
        long range = 0;
        int progress = 0;
        if (file.exists()) {
            range = SPDownloadUtil.getInstance().get(downloadUrl, 0);
            progress = (int) (range * 100 / file.length());
            if (range == file.length()) {
                installApp(file);
                return;
            }
        }

        Log.d(TAG, "range = " + range);

//        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_download);
//        remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
//        remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");
//
//        final NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this, AppContext.getInstance().getContext().getPackageName())
//                        .setContent(remoteViews)
//                        .setTicker("正在下载")
//                        .setSmallIcon(R.drawable.icon_launcher_logo);
//
//        mNotification = builder.build();
//
//        mNotifyManager = (NotificationManager)
//                getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotifyManager.notify(downloadId, mNotification);

        startActivity(new Intent(this, ProgressActivity.class));

        RetrofitHttp.getInstance().downloadFile(range, downloadUrl, mDownloadFileName, new DownloadCallBack() {
            @Override
            public void onProgress(int progress) {
//                remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
//                remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");
//                mNotifyManager.notify(downloadId, mNotification);
                sendBroadcast(new Intent().setAction("update_progress").putExtra("progress", progress));
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "下载完成" + file.getAbsolutePath());
              //  mNotifyManager.cancel(downloadId);
                installApp(file);
            }

            @Override
            public void onError(String msg) {
              //  mNotifyManager.cancel(downloadId);
                Log.d(TAG, "下载发生错误--" + msg);
            }
        });
    }

    private void installApp(File apkfile) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= 24) { //适配安卓7.0
            i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri apkFileUri = FileProvider.getUriForFile(this,
                    this.getPackageName() + ".fileProvider", apkfile);
            i.setDataAndType(apkFileUri, "application/vnd.android.package-archive");
        } else {
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                    "application/vnd.android.package-archive");// File.toString()会返回路径信息
        }
        startActivity(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RetrofitHttp.getInstance().cancel();
    }
}
