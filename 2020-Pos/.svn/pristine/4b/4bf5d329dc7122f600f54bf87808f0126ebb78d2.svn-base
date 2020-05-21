package com.epro.comp.login.utils

import android.text.TextUtils
import com.google.gson.Gson
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.SPConstant
import com.mike.baselib.utils.SPUtils
import com.epro.comp.login.mvp.model.bean.User

class LoginBusManager{

    companion object {
        fun setUser(user: User?){
            SPUtils.put(AppContext.getInstance().context, SPConstant.USER_JSON,if(user==null) "" else Gson().toJson(user))
        }
        fun getUser(): User? {
            var user_json:String= SPUtils.get(AppContext.getInstance().context, SPConstant.USER_JSON,"") as String
            if(TextUtils.isEmpty(user_json)){
                return null
            }
            return Gson().fromJson(user_json,User::class.java)
        }
    }

}