package com.epro.comp.login.mvp.model

import com.mike.baselib.base.IBaseModel
import com.mike.baselib.utils.LogTools
import com.epro.comp.login.CompLogin
import com.epro.comp.login.api.ApiService


open class BaseModel: IBaseModel {
    val logTools= LogTools("BaseModel_"+this.javaClass.simpleName)
    fun getApiSevice(): ApiService {
        return CompLogin.getApiService()
    }
}

