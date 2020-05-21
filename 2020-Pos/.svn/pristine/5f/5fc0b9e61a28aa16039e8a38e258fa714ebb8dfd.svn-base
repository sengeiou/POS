package com.epro.pos;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;
import com.mike.baselib.base.BaseApplicationLike;
import com.mike.baselib.net.RetrofitManager;
import com.mike.baselib.utils.AppContext;
import com.epro.pos.api.ApiService;
import com.epro.pos.ui.UpgradeActivity;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.upgrade.UpgradeListener;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;

public class PosApplicationLike extends BaseApplicationLike {

    private static ApiService apiService;

    public static ApiService getApiService() {
        return apiService;
    }


    public PosApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        // Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog
        // Beta.tipsDialogLayoutId = R.layout.upgrade_tips_dialog
        /*在application中初始化时设置监听，监听策略的收取*/
        /*在application中初始化时设置监听，监听策略的收取*/
        Beta.upgradeListener = new UpgradeListener() {
            @Override
            public void onUpgrade(int ret, UpgradeInfo strategy, boolean isManual, boolean isSilence) {
                if (strategy != null) {
                    Intent i = new Intent();
                    i.setClass(AppContext.getInstance().getContext(), UpgradeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    AppContext.getInstance().getContext().startActivity(i);
                } else {

                    Toast.makeText(AppContext.getInstance().getContext(), AppContext.getInstance().getString(R.string.not_to_update), Toast.LENGTH_LONG).show();
                }
            }
        };

        /* 设置更新状态回调接口 */
        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeSuccess(boolean isManual) {
                Toast.makeText(AppContext.getInstance().getContext(),"UPGRADE_SUCCESS",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpgradeFailed(boolean isManual) {
                Toast.makeText(AppContext.getInstance().getContext(),"UPGRADE_FAILED",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpgrading(boolean isManual) {
                Toast.makeText(AppContext.getInstance().getContext(),"UPGRADE_CHECKING",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadCompleted(boolean isManual) {

            }

            @Override
            public void onUpgradeNoVersion(boolean isManual) {
                Toast.makeText(AppContext.getInstance().getContext(),"UPGRADE_NO_VERSION",Toast.LENGTH_SHORT).show();
            }
        };

        super.onCreate();
        apiService = RetrofitManager.INSTANCE.getRetrofit().create(ApiService.class);
        //这里的json是字符串类型 = jsonArray.toString();
    }
}