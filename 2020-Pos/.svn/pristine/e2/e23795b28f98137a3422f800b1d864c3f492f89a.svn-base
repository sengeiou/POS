package com.epro.pos.ui.fragment.settings

import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.mvp.model.bean.LanguageSelectBean
import com.epro.pos.ui.MainActivity
import com.epro.pos.ui.adapter.LanguageAdapter
import com.mike.baselib.base.BaseApplication
import com.mike.baselib.fragment.BaseTitleBarSimpleFragment
import com.mike.baselib.utils.LocaleManager
import com.mike.baselib.view.CommonDialog
import kotlinx.android.synthetic.main.language_change.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.textColor

class LanguageChangeFragment : BaseTitleBarSimpleFragment(), View.OnClickListener {
    override fun lazyLoad() {

    }

    override fun onClick(v: View?) {
        when (v) {
            getRightView() -> {
                showDialogConfirm(selectType, mAdapter!!)
            }

            //返回键
            getLeftView() -> {
                var msg = FragmentChanageEvent()
                msg.postion = 13
                EventBus.getDefault().postSticky(msg)
            }
        }
    }


    override fun layoutContentId(): Int {
        return R.layout.language_change
    }

    companion object {
        const val TAG = "Language"
        const val LANGUAGE_TRADITIONAL: Int = 0
        const val LANGUAGE_SIMPLE: Int = 1
        const val LANGUAGE_ENGLISH: Int = 2
        fun newInstance(str: String): LanguageChangeFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = LanguageChangeFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): LanguageChangeFragment {
            return newInstance("")
        }
    }

    var selectType: Int = 0
    var mAdapter: LanguageAdapter? = null
    override fun initView() {
        super.initView()
        setHaveBackView(true)
        getLeftTitleView().text = getString(R.string.system_settings)
        getTitleView().text = getString(R.string.language_change)
        getRightView().text = getString(R.string.language_save)
        getRightView().visibility = View.VISIBLE
        getRightView().textColor = resources.getColor(R.color.mainTextColor)
        val linearLayoutManager = LinearLayoutManager(activity!!)
        lvList.layoutManager = linearLayoutManager
        var mTitles = arrayOf(R.string.language_chinese_fan, R.string.language_chinese_simple, R.string.language_english)
        var list = ArrayList<LanguageSelectBean>()
        for (index in mTitles.indices) {
            list.add(LanguageSelectBean(getString(mTitles[index])))
        }

        mAdapter = LanguageAdapter(activity!!, list)
        lvList.adapter = mAdapter

        mAdapter!!.onItemClickListener = object : LanguageAdapter.OnItemClickListener {
            override fun onClick(item: LanguageSelectBean) {
                when (item.title) {
                    getString(R.string.language_chinese_fan) -> {

                        languageSelect(LANGUAGE_TRADITIONAL, mAdapter!!)
                    }

                    getString(R.string.language_chinese_simple) -> {
                        languageSelect(LANGUAGE_SIMPLE, mAdapter!!)
                    }

                    getString(R.string.language_english) -> {
                        languageSelect(LANGUAGE_ENGLISH, mAdapter!!)
                    }
                }
            }
        }
    }

    fun languageSelect(type: Int, adapter: LanguageAdapter) {
        this.selectType = type
        adapter.updateListView(type)
        when (type) {
            0 -> {
                BaseApplication.localeManager?.setNewLocale(activity!!, LocaleManager.LANGUAGE_TRADITIONAL)

            }
            1 -> {
               BaseApplication.localeManager?.setNewLocale(activity!!, LocaleManager.LANGUAGE_CHINESE)

            }
            2 -> {
                BaseApplication.localeManager?.setNewLocale(activity!!, LocaleManager.LANGUAGE_ENGLISH)

            }
        }
    }

    private fun showDialogConfirm(type: Int, adapter: LanguageAdapter) {
        var mString = arrayOf(getString(R.string.language_chinese_fan), getString(R.string.language_chinese_simple), getString(R.string.language_english))
        CommonDialog.Builder(activity!!)
                .setTitle(getString(R.string.change_language_dailog))
                .setContent(getString(R.string.change_language_content) + mString[type] + "?")
                .setCancelText(getString(R.string.delete_dialog_cancel))
                .setConfirmText(getString(R.string.dailog_confirm))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        adapter.updateSelect(type)
                        dialog.dismiss()
                        resetActivity()
                    }
                })
                .setOnCancelListener(object : CommonDialog.OnCancelListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                    }
                })
                .create()
                .show()
    }

    private fun resetActivity() {
        startActivity<MainActivity>()
        activity!!.finish()
    }

    override fun initData() {

    }

    override fun initListener() {
        getRightView().setOnClickListener(this)
        getLeftView().setOnClickListener(this)
    }

}