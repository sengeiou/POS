package com.epro.pos.mvp.model.bean

data class SearchProductBean(override val code: Int, override val message: String, override val result: ArrayList<Product>) : BaseBean<ArrayList<Product>> {
    data class Send(val searchStr: String)
}