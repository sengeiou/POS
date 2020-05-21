package com.mike.baselib.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.LocaleManager
import com.mike.baselib.utils.LogTools

open class BaseApplication : Application(){
    val logTools=LogTools("BaseApplication_"+this.javaClass.simpleName)
    companion object{
        var localeManager: LocaleManager?=null
    }

    override fun onCreate() {
        super.onCreate()
        AppContext.getInstance().init(this)
    }

    override fun attachBaseContext(base: Context?) {
         localeManager = LocaleManager(base)
         super.attachBaseContext(localeManager?.setLocale(base))
      //  super.attachBaseContext(base)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager?.setLocale(this)
    }


}
