package com.epro.pos.ui.fragment.settings;

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.mvp.contract.UpdateVersionContract
import com.epro.pos.mvp.model.bean.UpdateVersionBean
import com.epro.pos.mvp.presenter.UpdateVersionPresenter
import com.epro.pos.ui.view.UpdateVersionDialog
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.mike.baselib.utils.AppUtils
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.view.CommonDialog
import kotlinx.android.synthetic.main.activity_update_version.*
import org.greenrobot.eventbus.EventBus


class UpdateVersionFragment : BaseTitleBarCustomFragment<UpdateVersionContract.View, UpdateVersionPresenter>(), UpdateVersionContract.View, View.OnClickListener {

    var versionBean:UpdateVersionBean.Result?=null
    override fun onUpdateVersionSuccess(result: UpdateVersionBean.Result) {
        versionBean = result
        logTools.t("YB").d("onUpdateVersionSuccess "+ result.version.substring(1))
        var versionNewArray = result.version.split(".")
        var currentVersion = AppUtils.getVerName(activity!!).split(".")
        var doUpdate = getCompared(currentVersion,versionNewArray)
        if (doUpdate){
            showUpdateDailog()
        }else{
            showNotUpdate()
        }
    }

    private fun showNotUpdate() {
        CommonDialog.Builder(activity!!)
                .setTitle(getString(R.string.alert))
                .setContent(getString(R.string.current_version_not_update))
                .setConfirmText(getString(R.string.dialog_confirm))
                .setCancelIsVisibility(false)
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                    }
                })
                .create()
                .show()
    }

    private fun getCompared(currentVersion: List<String>, versionNewArray: List<String>): Boolean {
        logTools.t("YB").d("getCompared currentVersion:"+currentVersion+ " versionNewArray :"+versionNewArray)
        if (versionNewArray[0].toInt()>currentVersion[0].toInt()){
            return true
        }else if (versionNewArray[0].toInt()==currentVersion[0].toInt()&&versionNewArray[1].toInt()>currentVersion[1].toInt()){
            return true
        }else if (versionNewArray[0].toInt()==currentVersion[0].toInt()&&versionNewArray[1].toInt()==currentVersion[1].toInt()&&versionNewArray[2].toInt()>currentVersion[2].toInt()){
            return true
        }
        return false
    }

    override fun lazyLoad() {

    }

    override fun onClick(v: View?) {
      when(v){
          btnUpdate->{
              mPresenter.UpdateVersion(PosConst.VERSION_UPDATE,"4")
          }
          //返回键
          getLeftView()->{
              var msg  = FragmentChanageEvent()
              msg.postion = 13
              msg.level = 1
              EventBus.getDefault().postSticky(msg)
          }
      }
    }

    private fun showUpdateDailog() {
        val pop = UpdateVersionDialog(activity!!)
        pop.setWidth(DisplayManager.getScreenWidth()!!*45/100)
        pop.setHeight(DisplayManager.getScreenHeight()!!*61/100)
        pop.contentView.findViewById<TextView>(R.id.tvPopCancel).setOnClickListener {
            pop.dismiss()
        }
        pop.contentView.findViewById<TextView>(R.id.tvPopRight).setOnClickListener {

        }
        var tvVersion = pop.contentView.findViewById<TextView>(R.id.tvVersion)
        var tvTime = pop.contentView.findViewById<TextView>(R.id.tvTime)
        var tvContent = pop.contentView.findViewById<TextView>(R.id.tvContent)
        tvContent.text = versionBean!!.content
        tvTime.text = versionBean!!.createTime
        tvVersion.text = getString(R.string.version_update_title_start)+versionBean!!.version+getString(R.string.version_update_title_end)
        pop.popupGravity = Gravity.CENTER
        pop.showPopupWindow()
    }

    companion object {
        const val TAG = "UpdateVersion"
        const val EXTRA = "extra"
        fun newInstance(type: String, extra: String = ""): UpdateVersionFragment {
            val args = Bundle()
            args.putString(TAG, type)
            args.putString(EXTRA, extra)
            val fragment = UpdateVersionFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): UpdateVersionFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): UpdateVersionPresenter {
        return UpdateVersionPresenter()
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_update_version
    }

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        setHaveBackView(true)
        tvVersion.text = "Version "+AppUtils.getVerName(activity!!)
        getLeftTitleView().text = getString(R.string.system_settings)
        getTitleView().text =getString(R.string.checkout_update)
    }

    override fun initListener() {
        btnUpdate.setOnClickListener(this)
        getLeftView().setOnClickListener(this)
    }

}


