package com.epro.comp.login.api

import com.epro.comp.login.mvp.model.bean.FindPasswordBean
import com.epro.comp.login.mvp.model.bean.LoginBean
import com.epro.comp.login.mvp.model.bean.RegisterBean
import com.epro.comp.login.utils.LoginConst
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Api 接口
 */

interface ApiService{

    /**
     * 登录
     */
    //@Headers("Content-Type: application/json","Accept: application/json")//需要添加头
    @POST(LoginConst.LOGIN)
    fun  login(@Body params:LoginBean.Send):Observable<LoginBean>

    /**
     * 注册
     */
     //loginname string (query)  登录名
    // truename string (query)  姓名
    // details string (query)  详情
    @POST(LoginConst.REGISTER)
    @FormUrlEncoded
    fun register(@Field("loginname") loginname:String,
                 @Field("truename") truename:String,
                 @Field("details") details:String):Observable<RegisterBean>


    @POST("")
    @FormUrlEncoded
    fun FindPassword(): Observable<FindPasswordBean>

}