package com.mike.baselib.utils


class AppConfig{
    companion object {
        val isPublish = false //是否发布
        private var BASE_URL=Constants.BASE_URL_TEST
        private var DEBUG=true
        fun init(){
            if(isPublish){
                DEBUG=false
                setBaseurl(Constants.DV_RELEASE)
                AppBusManager.setDev(Constants.DV_RELEASE)
                return
            }
            val dv=AppBusManager.getDev()
            setBaseurl(dv)
        }

        fun setBaseurl(dv:Int){
            when(dv){
                Constants.DV_TEST-> BASE_URL= Constants.BASE_URL_TEST
                Constants.DV_PRE_RELEASE-> BASE_URL= Constants.BASE_URL_PRE_RELEASE
                Constants.DV_RELEASE-> BASE_URL= Constants.BASE_URL_RELEASE
            }
        }

        fun getBaseurl():String{
            return BASE_URL
        }

        fun isDebug():Boolean{
            return DEBUG
        }
    }
}