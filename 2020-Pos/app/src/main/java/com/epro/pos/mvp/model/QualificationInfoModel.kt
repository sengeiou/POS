package com.epro.pos.mvp.model

import android.util.Log
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.*
import com.mike.baselib.utils.AppBusManager
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class QualificationInfoModel : BaseModel() {
    fun QualificationInfo(idcarNegative:String,idcarPositive:String,idcarValidity:String,legalIdcar:String, legalName:String, license:String, licenseId:String,
                          licenseNo:String,licensePhoto:String,licenseValidity:String, registration:String, registrationId:String,registrationNo:String,registrationPhoto:String, registrationValidity:String,
                          shopId:String): Observable<UpdateQualificationBaseBean> {
        return getApiSevice().updateQualificationInfo(UpdateQualificationBaseBean.Send(idcarNegative, idcarPositive, idcarValidity, legalIdcar, legalName, license, licenseId, licenseNo, licensePhoto, licenseValidity, registration, registrationId, registrationNo, registrationPhoto, registrationValidity, shopId))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<UpdateQualificationBaseBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun qualificationBaseInfo(): Observable<QualificationBaseBean> {
        return getApiSevice().QualificationInfo(shopId = AppBusManager.getShopId())
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<QualificationBaseBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun updateImage(image:File,isCreateThumb:Int): Observable<UpdateImageBean> {
        val bodyMap = HashMap<String,RequestBody>()
        bodyMap!!.put("isCreateThumb", RequestBody.create(MediaType.parse("text/plain"),isCreateThumb.toString()))
        val requestBody = RequestBody.create(MediaType.parse("image/png"), image)
        val body = MultipartBody.Part.createFormData("image",image.name,requestBody)
        return getApiSevice().updateImage(bodyMap,body)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<UpdateImageBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun deDeleteTips(shopinfoId:String): Observable<DeleteTipsBean> {
        return getApiSevice().deleteTips(DeleteTipsBean.Send(shopinfoId))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<DeleteTipsBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}