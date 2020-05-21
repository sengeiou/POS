package com.epro.pos.mvp.contract

import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface ProblemSolveContract {

    interface View : IBaseView {
        fun onProblemSolveSuccess()

    }


    interface Presenter : IPresenter<View> {
        fun ProblemSolve(type: String)
    }
}