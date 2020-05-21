package com.epro.pos.mvp.model

import android.accounts.NetworkErrorException
import com.epro.pos.PosApplication
import com.epro.pos.api.ApiService
import com.mike.baselib.base.IBaseModel
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.NoNetworkException
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.LogTools
import com.mike.baselib.utils.NetworkUtil

open class BaseModel: IBaseModel {
    val logTools= LogTools("BaseModel_"+this.javaClass.simpleName)
    fun getApiSevice(): ApiService {
        return PosApplication.apiService
    }
}

