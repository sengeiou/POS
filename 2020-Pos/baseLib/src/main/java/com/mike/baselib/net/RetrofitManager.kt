package com.mike.baselib.net

import com.tencent.bugly.crashreport.BuglyLog
import com.mike.baselib.utils.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import retrofit2.adapter.rxjava2.Result.response
import okhttp3.ResponseBody
import android.R.id.message
import android.R.string
import android.util.Log
import com.mike.baselib.net.gson.LongDefault0Adapter
import com.mike.baselib.net.gson.DoubleDefault0Adapter
import com.mike.baselib.net.gson.IntegerDefault0Adapter
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import com.mike.baselib.net.gson.FloatDefault0Adapter
import java.io.IOException
import java.net.URLEncoder


object RetrofitManager {

    //    val service: ApiService by lazy (LazyThreadSafetyMode.SYNCHRONIZED){
//        getRetrofit().create(ApiService::class.java)
//    }
    val logTools = LogTools(this.javaClass.simpleName)
    private var token: String by Preference("token", "")


    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    // Provide your custom parameter here
                    // .addQueryParameter("udid", "d2807c895f0348a180148c9dfa6f2feeac0781b5")
                    //.addQueryParameter("deviceModel", AppUtils.getMobileModel())
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }
//    设备号: equipmentNo
//    唯一码: equipmentId
    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    // Provide your custom header here
                    .header("Authorization", "shiroCookie=" + AppBusManager.getToken())
                    .header("Cookie", "shiroCookie=" + AppBusManager.getToken())
                    .header("resource", "3")//ap 安卓
                    .header("language", AppBusManager.getAppLanguage())
                    //.header("itemName", android.os.Build.DEVICE)
                    .header("itemName", URLEncoder.encode(AppBusManager.getDeviceName(),"UTF-8"))
                    .header("itemId", AppBusManager.getUuid())
                    .header("x-requested-with", "XMLHttpRequest")
//                    .addHeader("Content type","application/json")
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * 设置缓存
     */
//    private fun addCacheInterceptor(): Interceptor {
//        return Interceptor { chain ->
//            var request = chain.request()
//            if (!NetworkUtil.isNetworkAvailable(AppContext.getInstance().context)) {
//                request = request.newBuilder()
//                        .cacheControl(CacheControl.FORCE_CACHE)
//                        .build()
//            }
//            val response = chain.proceed(request)
//            if (NetworkUtil.isNetworkAvailable(AppContext.getInstance().context)) {
//                val maxAge = 0
//                // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
//                response.newBuilder()
//                        .header("Cache-Control", "public, max-age=" + maxAge)
//                        .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                        .build()
//            } else {
//                // 无网络时，设置超时为4周  只对get有用,post没有缓冲
//                val maxStale = 60 * 60 * 24 * 28
//                response.newBuilder()
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .removeHeader("nyn")
//                        .build()
//            }
//
//        }
//    }

    /**
     * 有网时候的缓存
     */
    val NetCacheInterceptor: Interceptor = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val onlineCacheTimeStr=request.header(Constants.HEADER_ONLINE_CACHETIME)
            var onlineCacheTime = 0 //在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
            if(onlineCacheTimeStr!=null&&onlineCacheTimeStr.ext_isPureNumber_orDecimal()){
                onlineCacheTime=onlineCacheTimeStr.toInt()
            }
            logTools.d(onlineCacheTime)
            if (NetworkUtil.isNetworkAvailable(AppContext.getInstance().context)) {
                val response = chain.proceed(request)
                return response.newBuilder()
                        .header("Cache-Control", "public, max-age=$onlineCacheTime")
                        .removeHeader("Pragma")
                        .build()
            }
            return chain.proceed(request)
        }
    }
    /**
     * 没有网时候的缓存
     */
    val OfflineCacheInterceptor: Interceptor = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            var offlineCacheTime = 0//离线的时候的缓存的过期时间
            val offlineCacheTimeStr=request.header(Constants.HEADER_OFFLINE_CACHETIME)
            if(offlineCacheTimeStr!=null&&offlineCacheTimeStr.ext_isPureNumber_orDecimal()){
                offlineCacheTime=offlineCacheTimeStr.toInt()
            }
            logTools.d(offlineCacheTime)
            if (!NetworkUtil.isNetworkAvailable(AppContext.getInstance().context)) {
                request = request.newBuilder()
                        //                        .cacheControl(new CacheControl
                        //                                .Builder()
                        //                                .maxStale(60,TimeUnit.SECONDS)
                        //                                .onlyIfCached()
                        //                                .build()
                        //                        ) 两种方式结果是一样的，写法不同
                        .header("Cache-Control", "public, only-if-cached, max-stale=$offlineCacheTime")
                        .build()
            }
            return chain.proceed(request)
        }
    }


    /**
     * 服务器错误返回的是"" ,前端用对像接收 拦截器处理
     */
    private fun EmptyStringHanlderInterceptor(): Interceptor {
        return Interceptor { chain ->
            var response = chain.proceed(chain.request())
            var bodyString = response.body()!!.string()
            bodyString = bodyString.replace("\"result\":\"\"", "\"result\":{}")
            response = response.newBuilder().body(ResponseBody.create(response.body()!!.contentType(), bodyString)).build()
            response
        }
    }

    fun getRetrofit(): Retrofit {
        return getRetrofit(AppConfig.getBaseurl())
    }

    fun getRetrofit(url: String): Retrofit {
        // 获取retrofit的实例
        return Retrofit.Builder()
                .baseUrl(url)  //自己配置
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        //添加一个log拦截器,打印所有的log
        //可以设置请求过滤的水平,body,basic,headers

        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            logTools.d(it)
            BuglyLog.d("Bugly_Net", it)
        })

        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //设置 请求的缓存的大小跟位置
        val cacheFile = File(AppContext.getInstance().context.cacheDir, "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

        return OkHttpClient.Builder()
                .addInterceptor(addQueryParameterInterceptor())  //参数添加
                .addInterceptor(addHeaderInterceptor()) // token过滤
//              .addInterceptor(addCacheInterceptor())
                .addNetworkInterceptor(NetCacheInterceptor)
                .addInterceptor(OfflineCacheInterceptor)
                .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                .addInterceptor(EmptyStringHanlderInterceptor())
                .cache(cache)  //添加缓存
                .connectTimeout(15L, TimeUnit.SECONDS)
                .readTimeout(15L, TimeUnit.SECONDS)
                .writeTimeout(15L, TimeUnit.SECONDS)
                .build()
    }

    /**
     * 增加后台返回""和"null"的处理
     * 1.int=>0
     * 2.double=>0.00
     * 3.long=>0L
     * 4.flat=>0F
     *
     * @return
     */
    fun buildGson(): Gson {
        return GsonBuilder()
                .registerTypeAdapter(Int::class.java, IntegerDefault0Adapter())
                .registerTypeAdapter(Int::class.javaPrimitiveType, IntegerDefault0Adapter())
                .registerTypeAdapter(Double::class.java, DoubleDefault0Adapter())
                .registerTypeAdapter(Double::class.javaPrimitiveType, DoubleDefault0Adapter())
                .registerTypeAdapter(Long::class.java, LongDefault0Adapter())
                .registerTypeAdapter(Long::class.javaPrimitiveType, LongDefault0Adapter())
                .registerTypeAdapter(Float::class.java, FloatDefault0Adapter())
                .registerTypeAdapter(Float::class.javaPrimitiveType, FloatDefault0Adapter())
                .create()
    }

}
