package com.epro.pos.mvp.model.bean

data class GetProcuctDetailBean(override val code: Int, override val message: String, override val result: ProductDetail) : BaseBean<ProductDetail> {
}
