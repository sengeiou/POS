package com.epro.comp.login.mvp.presenter

import android.text.TextUtils
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.comp.login.mvp.contract.LoginContract
import com.epro.comp.login.mvp.model.LoginModel
import com.mike.baselib.net.exception.ErrorStatus

class LoginPresenter:BasePresenter<LoginContract.View>(),LoginContract.Presenter {

    private val model by lazy { LoginModel() }

    override fun login(code:String,loginname:String,password:String,type:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.login(code,loginname,password)
                .subscribe({
                    bean->
                    mRootView?.dismissLoading(type, ErrorStatus.SUCCESS, ErrorStatus.SUCCESS_MSG)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    fun checkParam(mobile: String,password:String?):Boolean{
        if(TextUtils.isEmpty(mobile)){
            toast("账号不能为空")
            return false
        }
//        if(TextUtils.isEmpty(icon_login_password)){
//            toast(R.string.password_not_empty)
//            return false
//        }
        return true
    }
}