package com.epro.comp.login;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCUtil;
import com.billy.cc.core.component.IComponent;
import com.mike.baselib.net.RetrofitManager;
import com.mike.baselib.utils.AppConfig;
import com.epro.comp.login.api.ApiService;
import com.epro.comp.login.ui.activity.LoginActivity;

public class CompLogin implements IComponent {
   private static ApiService apiService;
    public CompLogin(){
        AppConfig.Companion.init();
        apiService= RetrofitManager.INSTANCE.getRetrofit().create(ApiService.class);
    }

    public static ApiService getApiService() {
        return apiService;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case "showActivityLogin":
                openActivity(cc);
                break;
            default:
                //这个逻辑分支上没有调用CC.sendCCResult(...),是一种错误的示例
                //并且方法的返回值为false，代表不会异步调用CC.sendCCResult(...)
                //在LocalCCInterceptor中将会返回错误码为-10的CCResult
                break;
        }
        return true;
    }

    private void openActivity(CC cc) {
        CCUtil.navigateTo(cc, LoginActivity.class);
    }
}
