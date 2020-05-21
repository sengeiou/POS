package com.epro.pos.mvp.model.bean


data class GetResultListBean<T>(override val code: Int, override val message: String, override val result: ArrayList<T>) : BaseBean<ArrayList<T>> {

}