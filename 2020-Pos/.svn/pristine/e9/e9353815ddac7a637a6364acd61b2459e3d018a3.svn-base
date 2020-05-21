package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.BusinessBaseInfoBean
import com.epro.pos.mvp.model.bean.EditBaseInfoBean
import com.epro.pos.mvp.model.bean.SearchAddressBean
import com.epro.pos.mvp.model.bean.UpdateBusinessBaseBean
import io.reactivex.Observable

class EditBaseInfoModel : BaseModel() {
    //更新商户基本资料
    fun updateBusinessBaseInfo(address:String,area:String,areaId:String,city:String,cityId:String,email:String, id:String, latitude:String,longitude:String,
                                mobile:String,owner:String,province:String, provinceId:String,shopId:String, shopName:String, shopType:String,shopTypeId:String): Observable<UpdateBusinessBaseBean>{
        return getApiSevice().UpdateBusinessBaseInfo(UpdateBusinessBaseBean.Send(address, area, areaId, city, cityId, email, id, latitude, longitude, mobile, owner, province, provinceId, shopId, shopName, shopType, shopTypeId))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<UpdateBusinessBaseBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    //查询商户基本资料
    fun businessBaseInfo(): Observable<BusinessBaseInfoBean> {
        return getApiSevice().BusinessBaseInfo()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<BusinessBaseInfoBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }


    fun searchAddress(parentId:String):Observable<SearchAddressBean>{

        return getApiSevice().SearchAddress(parentId)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<SearchAddressBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}