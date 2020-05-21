package com.epro.pos.mvp.model.bean

data class ProblemSolveBean(val code:Int,val message:String,val content:String)
/*data class ProblemSolveBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<ProblemSolveBean.Result> {
    data class Result(val productId: String?)
    data class OrderRecordSend(val productId: String)
}*/
