package com.epro.pos.mvp.model

import android.util.Log
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.EditAvatarBean
import com.epro.pos.mvp.model.bean.LogoutBean
import com.epro.pos.mvp.model.bean.PersonCenterBean
import com.epro.pos.mvp.model.bean.UpdateImageBean
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PersonCenterModel : BaseModel() {
    fun PersonCenter(): Observable<PersonCenterBean> {
        return getApiSevice().PersonCenter()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<PersonCenterBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    //编辑头像
    fun EditAvatar(avatar:String):Observable<EditAvatarBean>{
        return getApiSevice().updateAvatar(EditAvatarBean.Send(avatar))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<EditAvatarBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    //退出登录
    fun Logout():Observable<LogoutBean>{
        return getApiSevice().logoutSystem()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<LogoutBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun updateImage(image: File, isCreateThumb:Int): Observable<UpdateImageBean> {
        val bodyMap = HashMap<String, RequestBody>()
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

}