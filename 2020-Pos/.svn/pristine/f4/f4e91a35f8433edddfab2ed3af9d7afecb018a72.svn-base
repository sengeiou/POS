package com.epro.pos.ui.fragment.settings;

import android.graphics.Rect
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.mvp.contract.BusinessInfoContract
import com.epro.pos.mvp.model.bean.BusinessBaseInfoBean
import com.epro.pos.mvp.model.bean.QualificationBaseBean
import com.epro.pos.mvp.presenter.BusinessInfoPresenter
import com.epro.pos.ui.fragment.HomeFragment
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.ext_loadImageWithDomain
import kotlinx.android.synthetic.main.activity_business.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.mike.baselib.utils.toast
import org.jetbrains.anko.textColor
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class BusinessInfoFragment : BaseTitleBarCustomFragment<BusinessInfoContract.View, BusinessInfoPresenter>(), BusinessInfoContract.View, View.OnClickListener {

    override fun lazyLoad() {
    }

    var bean :QualificationBaseBean.Result?=null
    override fun onQualificationInfoSuccess(result: QualificationBaseBean.Result) {
        registration.text = result.registration
        registrationValidity.text = result.registrationValidity.split(" ")[0]
        registrationNo.text = result.registrationNo
        legalName.text = result.legalName
        legalIdcar.text  = result.legalIdcar
        licenseNo.text = result.licenseNo
        license.text = result.license
        licenseValidity.text = result.licenseValidity.split(" ")[0]
        idcarValidity.text = result.idcarValidity.split(" ")[0]
        var visib = CalculationDate(result)
        var idcarNegativePath = result.idcarNegative  //省份证反面
        var idcarPositivePath = result.idcarPositive  //省份证正面
        var licensePath = result.licensePhoto  //行业资质
        var registPath = result.registrationPhoto //证件照
        if (visib){
            llPrompt.visibility = View.VISIBLE
        }else{
            llPrompt.visibility = View.GONE
        }
        registrationPhoto.ext_loadImageWithDomain(registPath,R.mipmap.icard_image)
        idcarPositive.ext_loadImageWithDomain(idcarPositivePath,R.mipmap.document)
        idcarNegative.ext_loadImageWithDomain(idcarNegativePath,R.mipmap.document)
        licensePhoto.ext_loadImageWithDomain(licensePath,R.mipmap.document)
        bean = result
        showTitleTips()
    }

    //资质信息是否到期前90天
    fun CalculationDate(result: QualificationBaseBean.Result):Boolean{
        var registrationValidity = result.registrationValidity.split(" ")[0]
        var licenseValidity = result.licenseValidity.split(" ")[0]
        var idcarValidity = result.idcarValidity.split(" ")[0]
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        var date = Date(System.currentTimeMillis())
        var time = simpleDateFormat.format(date)
        var dt1 = simpleDateFormat.parse(registrationValidity)
        var dt2 = simpleDateFormat.parse(licenseValidity)
        var dt3 = simpleDateFormat.parse(idcarValidity)
        var currentDate  = simpleDateFormat.parse(time)
        var diff1 = (dt1.time - currentDate.time)/(60*60*1000*24)
        var diff2 = (dt2.time - currentDate.time)/(60*60*1000*24)
        var diff3 = (dt3.time - currentDate.time)/(60*60*1000*24)
        if (diff1<=90 || diff2<=90 || diff3<= 90){
            return true
        }
        return false
    }

    var validityDate:Int=0
    var id:String?=null
    override fun onBusinessInfoSuccess(result: BusinessBaseInfoBean.Result) {
        shopId.text = result.shopId
        shopName.text = result.shopName
        shopType.text = result.shopType
        address.text = result.province+" "+result.city+" "+result.area+" "+result.address
        owner.text = result.owner
        mobile.text = result.mobile
        email.text = result.email
        tvTime.text = result.startTime+", "
        tvValidity.text = result.endTime+", "
        tvDate.text = getString(R.string.available_days)+" "+result.validityTime+getString(R.string.days)
        validityDate = result.validityTime.toInt()
        id = result.id
        if (validityDate<=30){
            btnRenew.visibility = View.VISIBLE
        }else{
            btnRenew.visibility = View.GONE
        }
        if ("1".equals(result.isRenew)){
            btnRenew.setBackgroundResource(R.drawable.renew_shape_btn_click)
            btnRenew.setTextColor(resources.getColor(R.color.thirdTextColor))
            tvRenewNotice.visibility = View.VISIBLE
            btnRenew.isEnabled = false
        }else{
            tvRenewNotice.visibility = View.GONE
            btnRenew.isEnabled = true
        }
    }

    private fun showTitleTips() {
        // 0:正常 1:提交审核  2:审核失败  审核通过0状态
        if ("0".equals(bean!!.shopinfoStatus)&&"0".equals(bean!!.isDelete)){
            showSuccessUI()
        }else if ("1".equals(bean!!.shopinfoStatus)){
            if (rlPrompt.visibility == View.GONE){
                showCommitUI()
            }
        }else if ("2".equals(bean!!.shopinfoStatus)){
            showFialedUI()
        }
    }

    //审核成功UI
    private fun showSuccessUI() {
        rlPrompt.visibility = View.VISIBLE
        rlPrompt.setBackgroundResource(R.drawable.shape_prompt_bg_green)
        promptImg.setImageResource(R.mipmap.success)
        promptText.text = getString(R.string.business_info_success)
        promptText.textColor = resources.getColor(R.color.qualification_pass)
        promptDelete.visibility = View.VISIBLE
        getRightView().setTextColor(resources.getColor(R.color.mainColor))
        getRightView().isEnabled = true
        promptDelete.setOnClickListener {
            mPresenter.deDeleteTips(PosConst.DELETE_TIPS,bean!!.shopinfoId)
        }
    }

    //提交审核
    private fun showCommitUI() {
        rlPrompt.visibility = View.VISIBLE
        rlPrompt.setBackgroundResource(R.drawable.shape_prompt_bg)
        promptImg.setImageResource(R.mipmap.prompt)
        promptText.text = getString(R.string.prompt_text_ing)
        promptText.textColor = resources.getColor(R.color.qualification_commit)
        promptDelete.visibility = View.GONE
        getRightView().setTextColor(resources.getColor(R.color.main_50))
        getRightView().isEnabled = false
    }

    //审核失败
    private fun showFialedUI() {
        rlPrompt.visibility = View.VISIBLE
        rlPrompt.setBackgroundResource(R.drawable.shape_prompt_bg_red)
        promptImg.setImageResource(R.mipmap.caution)
        promptText.text = getString(R.string.business_info_not_pass)+bean!!.shopinfoOpinions
        promptText.textColor = resources.getColor(R.color.mainColor)
        promptDelete.visibility = View.GONE
        getRightView().setTextColor(resources.getColor(R.color.mainColor))
        getRightView().isEnabled = true
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //不做数据拉取，跳转报错。
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onBackView(msg: FragmentChanageEvent){
        //基本资料编辑和资质信息编辑返回重新拉取
        if (msg.postion==13&&msg.level==3){
            mPresenter.BusinessBaseInfo(PosConst.BUSINESS_BASE_INFO)
            mPresenter.QualificationInfo(PosConst.MERCHANT_QUALIFICATION_INFO)
            logTools.t(" YB ").d("system  onClickItem : "+msg.from+" mLevel :"+msg.level+" postion :"+msg.postion)
        }
    }

    //续约接口
    override fun onRenewShopSuccess(result: Int) {
        if (200 == result){
            btnRenew.setBackgroundResource(R.drawable.renew_shape_btn_click)
            btnRenew.setTextColor(resources.getColor(R.color.thirdTextColor))
            tvRenewNotice.visibility = View.VISIBLE
            btnRenew.isEnabled = false
            toast(getString(R.string.commit_success))
        }else{
            toast(getString(R.string.commit_failed))
        }
    }

    override fun onDeleteSuccess() {
        rlPrompt.visibility = View.GONE
    }

    override fun onClick(v: View?) {
      when(v){
          //基本信息更新
          btnBupdate->{
              var msg = FragmentChanageEvent()
              msg.postion = 9
              EventBus.getDefault().postSticky(msg)
          }
          //资质信息更新
          btnQupdate->{
              var msg = FragmentChanageEvent()
              msg.postion = 14
              EventBus.getDefault().postSticky(msg)
          }
          //返回键
          getLeftView()->{
              var msg = FragmentChanageEvent()
              msg.postion = 13
              EventBus.getDefault().postSticky(msg)
          }
          //续约按钮
          btnRenew->{
              mPresenter.RenewShop(PosConst.RENEW_SHOP,AppBusManager.getShopId())
          }
      }
    }

    companion object {
        const val TAG = "BusinessInfo"
        const val BASE_URL_IMAGE = "https://shop.epro.com.hk/image/"
        const val EXTRA = "extra"
        fun newInstance(type: String, extra: String = ""): BusinessInfoFragment {
            val args = Bundle()
            args.putString(TAG, type)
            args.putString(EXTRA, extra)
            val fragment = BusinessInfoFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): BusinessInfoFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): BusinessInfoPresenter {
        return BusinessInfoPresenter()
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_business
    }

    override fun initData() {
    }

    var page = 1
    var status:String?=null
    override fun initView() {
        super.initView()
        val extra = arguments?.getString(EXTRA)
        logTools.t("YB").d("businessInfoFragment initView "+extra+"  page : "+page)
        mPresenter.BusinessBaseInfo(PosConst.BUSINESS_BASE_INFO)
        mPresenter.QualificationInfo(PosConst.MERCHANT_QUALIFICATION_INFO)
        getLeftTitleView().text= getString(R.string.system_settings)
        getTitleView().text =getString(R.string.business_information)
        setHaveBackView(true)
        if (!TextUtils.isEmpty(extra)){  //从首页证件更新跳转过来
            var extraStr = extra!!.split("_")
            if (extraStr[0]==HomeFragment.TAG&&"4".equals(extraStr[1])){
                qualifiInfo()
                viewFlipperBInfo.showNext()
                page = 2
            }
        }else if (!TextUtils.isEmpty(mFrom)){
            var mFromStr = mFrom!!.split("_")
            if (mFromStr[0]==HomeFragment.TAG&&"4".equals(mFromStr[1])){
                if (2==page){
                }else{
                    qualifiInfo()
                    viewFlipperBInfo.showNext()
                    page = 2
                }
            }
        }else{
            baseInfo()
            page = 1
        }
       // radioGroup.check(R.id.rbBInfo)
        initRadioListener()
    }

    //初始化RadioButton
    private fun initRadioListener() {
        logTools.t("YB").d("initRadioListener  ")
        try {
            radioGroup.setOnCheckedChangeListener { _, Id ->
                when(Id){
                    R.id.rbBInfo->{
                        baseInfo()
                        if (2 == page){
                            viewFlipperBInfo.showNext()
                            page = 1
                        }
                    }
                    R.id.rbQInfo->{
                        qualifiInfo()
                        viewFlipperBInfo.showNext()
                        page  = 2
                    }
                }
                logTools.t("YB").d("initRadioListener  "+"page : "+page)
            }
        }catch (e:Exception){
            logTools.t("YB").d("initRadioListener: "+e)
        }
    }

    fun baseInfo(){
         rbBInfo.setTextColor(resources.getColor(R.color.mainColor))
         rbBInfo.paint.isFakeBoldText = true
         rbQInfo.setTextColor(resources.getColor(R.color.mainTextColor))
         rbQInfo.paint.isFakeBoldText = false
         setCheckedPoninter(R.drawable.shape_info_bottom_bg,rbBInfo)
         setCheckedPoninter(R.drawable.shape_info_bottom_bg_tra,rbQInfo)
     }

    fun qualifiInfo(){
        rbBInfo.setTextColor(resources.getColor(R.color.mainTextColor))
        rbBInfo.paint.isFakeBoldText = false
        rbQInfo.setTextColor(resources.getColor(R.color.mainColor))
        rbQInfo.paint.isFakeBoldText = true
        setCheckedPoninter(R.drawable.shape_info_bottom_bg_tra,rbBInfo)
        setCheckedPoninter(R.drawable.shape_info_bottom_bg,rbQInfo)
    }

    private fun setCheckedPoninter(res: Int, radioButton: RadioButton) {
        val drawable = resources?.getDrawable(res)
        drawable?.bounds = Rect(0, 0, drawable?.minimumWidth!!, drawable?.minimumHeight!!)
        radioButton.setCompoundDrawables(null, null, null, drawable)
    }

    override fun initListener() {
        btnBupdate.setOnClickListener(this)
        btnQupdate.setOnClickListener(this)
        getLeftView().setOnClickListener(this)
        btnRenew.setOnClickListener(this)
    }

    var mFrom :String ?=null
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onClickItem(msg: FragmentChanageEvent){
        mFrom = msg.from
       if (!TextUtils.isEmpty(mFrom)){
            var mFromStr = mFrom!!.split("_")
            if (mFromStr[0]==HomeFragment.TAG&&"4".equals(mFromStr[1])){
                if (2==page){
                }else{
                    radioGroup.check(R.id.rbQInfo)
                    qualifiInfo()
                    viewFlipperBInfo.showNext()
                    page = 2
                }
            }
        }
        logTools.t("YB").d("onClickItem  "+"page : "+page)
        initRadioListener()
    }

}


