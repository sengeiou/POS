package com.epro.pos.mvp.presenter

import android.text.TextUtils
import com.epro.pos.R
import com.mike.baselib.base.BasePresenter
import com.epro.pos.mvp.contract.RegisterContract
import com.epro.pos.mvp.model.RegisterModel
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.ValidateUtils
import org.jetbrains.anko.appcompat.v7.Appcompat

class RegisterPresenter:BasePresenter<RegisterContract.View>(),RegisterContract.Presenter {




    private val model by lazy { RegisterModel() }


    override fun getPhoneVerifyCode(dial:String,mobile:String,type:String) {
    }

    fun checkParam(mobile: String):Boolean{
        if(TextUtils.isEmpty(mobile)){
            toast(R.string.phone_not_empty)
            return false
        }
        if(!ValidateUtils.validatePhoneNo(mobile)){
            toast(R.string.phone_format_error)
            return false
        }
        return true
    }
    fun checkParam(loginname:String,name:String,details:String):Boolean{
        if(TextUtils.isEmpty(loginname)){
            toast(R.string.phone_not_empty)
            return false
        }
        if(!ValidateUtils.validatePhoneNo(loginname)){
            toast(R.string.phone_format_error)
            return false
        }
        if(TextUtils.isEmpty(name)){
            toast(AppContext.getInstance().getString(R.string.name_not_null))
            return false
        }
        if(TextUtils.isEmpty(details)){
            toast(AppContext.getInstance().getString(R.string.unit_name_not_null))
            return false
        }
        return true
    }


    override fun register(loginname:String,name:String,details:String,type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.register(loginname,name,details)
                .subscribe({
                    bean->
                    mRootView?.registerSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    fun checkParam(mobile:String,code:String,isGotVfcode:Boolean,password:String,repassword:String,agent_code:String,agree_terms :String):Boolean{

        if(TextUtils.isEmpty(mobile)){
            toast(R.string.phone_not_empty)
            return false
        }
        if(!ValidateUtils.validatePhoneNo(mobile)){
            toast(R.string.phone_format_error)
            return false
        }
        if(!isGotVfcode){
            toast(R.string.get_vfcode_first)
            return false
        }
        if(TextUtils.isEmpty(code)){
            toast(R.string.vfcode_not_empty)
            return false
        }
        if(!ValidateUtils.validateVfcode(code)){
            toast(R.string.vfcode_format_error)
            return false
        }
        if(TextUtils.isEmpty(password)){
            toast(R.string.password_not_empty)
            return false
        }
        if(!ValidateUtils.validatePassword(password)){
            toast(R.string.password_format_error)
            return false
        }
        if(TextUtils.isEmpty(repassword)){
            toast(R.string.repassword_not_empty)
            return false
        }
        if(!ValidateUtils.validatePassword(repassword)){
            toast(R.string.repassword_format_error)
            return false
        }
        if(!(password.equals(repassword))){
            toast(R.string.two_password_not_same)
            return false
        }
        if(TextUtils.isEmpty(agent_code)){
            toast(R.string.proxy_id_not_empty)
            return false
        }
        if(!("1".equals(agree_terms))){
            toast(R.string.agree_service_protocol)
            return false
        }
        return true
    }
}