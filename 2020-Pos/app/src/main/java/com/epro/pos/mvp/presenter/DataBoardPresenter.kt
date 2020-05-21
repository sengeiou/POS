package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.epro.pos.mvp.contract.DataBoardContract
import com.epro.pos.mvp.model.DataBoardModel

class DataBoardPresenter: BasePresenter< DataBoardContract.View>() ,DataBoardContract.Presenter{
    private val model by lazy { DataBoardModel() }
}