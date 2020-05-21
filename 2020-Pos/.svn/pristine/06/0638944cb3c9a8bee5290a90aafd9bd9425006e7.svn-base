package com.epro.pos.mvp.presenter

import android.text.TextUtils
import com.epro.pos.R
import com.epro.pos.mvp.contract.LoginContract
import com.epro.pos.mvp.model.LoginModel
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.ValidateUtils

class LoginPresenter:BasePresenter<LoginContract.View>(),LoginContract.Presenter {

    private val model by lazy { LoginModel() }

    override fun login(account:String,password:String,loginType:String,type:String) {
        if(TextUtils.isEmpty(AppBusManager.getUuid())){
            toast(AppContext.getInstance().getString(R.string.pls_open_device_info))
            return
        }
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.login(account,password,loginType)
                .subscribe({
                    bean->
                    mRootView?.loginSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    fun checkParams(mobile:String,password: String):Boolean{
        if(!ValidateUtils.validatePhoneNo(mobile)){

            return false
        }

        if(!ValidateUtils.validatePassword(password)){

            return false
        }
        return true
    }

    fun checkParams2(email:String,password: String):Boolean{
        if(!ValidateUtils.validateEmail(email)){

            return false
        }

        if(!ValidateUtils.validatePassword(password)){

            return false
        }
        return true
    }

    fun checkParams3(shopId:String,password: String):Boolean{
        if(!ValidateUtils.validatePassword(password)){

            return false
        }
        return true
    }



    //基本信息
    override fun PersonCenter(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.PersonCenter()
                .subscribe({ bean ->
                    mRootView?.onPersonCenterSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }
}