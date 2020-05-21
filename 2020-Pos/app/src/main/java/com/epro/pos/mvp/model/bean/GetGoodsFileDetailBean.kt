package com.epro.pos.mvp.model.bean

data class GetGoodsFileDetailBean(override val code: Int, override val message: String, override val result: GoodsFileDetail) : BaseBean<GoodsFileDetail> {
}