package com.epro.pos.ui.fragment.settings;

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.CustomListener
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.mvp.contract.QualificationInfoContract
import com.epro.pos.mvp.model.bean.QualificationBaseBean
import com.epro.pos.mvp.model.bean.UpdateImageBean
import com.epro.pos.mvp.model.bean.UpdateQualificationBaseBean
import com.epro.pos.mvp.presenter.QualificationInfoPresenter
import com.epro.pos.ui.view.CameraSelectPop
import com.epro.pos.ui.view.GeneralModifyPop
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.mike.baselib.utils.*
import com.mike.baselib.view.CommonDialog
import kotlinx.android.synthetic.main.activity_qualification_info.*
import org.greenrobot.eventbus.EventBus
import com.mike.baselib.utils.toast
import org.jetbrains.anko.textColor
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class QualificationInfoFragment : BaseTitleBarCustomFragment<QualificationInfoContract.View, QualificationInfoPresenter>(), QualificationInfoContract.View, View.OnClickListener {

    override fun onClick(v: View?) {
        when(v){
            getLeftView()->{
                var status = getEditStatus()
                if (status){
                    showTipsDialog()
                }else{
                    var msg = FragmentChanageEvent()
                    msg.postion = 13
                    msg.level = 3
                    msg.type = 1
                    EventBus.getDefault().postSticky(msg)
                }
            }
            dateTime->{
                //日期选择器
                showDateTime(R.id.dateTime)
                pvCustomLunar!!.show()
            }
            etValidity->{
                //日期选择器
                showDateTime(R.id.etValidity)
                pvCustomLunar!!.show()
            }
            dateTime2->{
                //日期选择器
                showDateTime(R.id.dateTime2)
                pvCustomLunar!!.show()
            }
            //证件照片
            imgDoc->{ showPhotoPop(CAMERA_REQUEST_CODE,GALLERY_REQUEST_CODE) }
            //身份证正面
            imgCard->{ showPhotoPop(CAMERA_REQUEST_CODE_2,GALLERY_REQUEST_CODE_2)}
            //身份证反面
            imgReverse->{showPhotoPop(CAMERA_REQUEST_CODE_3,GALLERY_REQUEST_CODE_3)}
            //行业资质
            imgDoc2->{showPhotoPop(CAMERA_REQUEST_CODE_4,GALLERY_REQUEST_CODE_4)}
            //证件类型
            typeCard->{
                if (pvOptions2 != null) {
                    pvOptions2?.show()
                }
            }
            //注册号
            registerId->{showGeneralPop(getString(R.string.registration_number))}
            legalPerson->{showGeneralPop(getString(R.string.legal_representative))}
            idCard->{showGeneralPop(getString(R.string.identification_number))}
            licenseID->{showGeneralPop(getString(R.string.permit_number))}
            licenseType->{showGeneralPop(getString(R.string.type_of_certificate))}

            //提交审核
            getRightView()->{
               if (postion.size!=0){
                   for (i in postion){
                       Thread.sleep(300)
                       if (REGISTRATION_PHOTO == i){
                           mPresenter.updateImage(PosConst.UPDATE_IMAGE,mFile1!!,0,REGISTRATION_PHOTO)
                       }else if (IDCAR_POSITIVE == i){
                           mPresenter.updateImage(PosConst.UPDATE_IMAGE,mFile2!!,0,IDCAR_POSITIVE)
                       }else if (IDCAR_NEGATIVE == i){
                           mPresenter.updateImage(PosConst.UPDATE_IMAGE,mFile3!!,0,IDCAR_NEGATIVE)
                       }else if (LICENSE_PHOTO == i){
                           mPresenter.updateImage(PosConst.UPDATE_IMAGE,mFile4!!,0,LICENSE_PHOTO)
                       }
                   }
               }else {
                   var typeCard =typeCard.text.toString()
                   var registerId = registerId.text.toString()
                   var legalPerson = legalPerson.text.toString()
                   var idCard = idCard.text.toString()
                   var dateTime = dateTime.text.toString()
                   var etValidity = etValidity.text.toString()
                   var licenseID = licenseID.text.toString()
                   var licenseType = licenseType.text.toString()
                   var dateTime2 = dateTime2.text.toString()
                   mPresenter.updateQualificationInfo(PosConst.MERCHANT_QUALIFICATION_INFO,  bean!!.idcarNegative,  bean!!.idcarPositive,etValidity,idCard,legalPerson,licenseType,bean!!.licenseId,licenseID,
                           bean!!.licensePhoto,dateTime2,
                           typeCard,bean!!.registrationId,registerId,bean!!.registrationPhoto,dateTime,bean!!.shopId)
               }
            }
        }
    }

    private fun getEditStatus() :Boolean{
        var typeCard =typeCard.text.toString()
        var registerId = registerId.text.toString()
        var legalPerson = legalPerson.text.toString()
        var idCard = idCard.text.toString()
        var dateTime = dateTime.text.toString()
        var etValidity = etValidity.text.toString()
        var licenseID = licenseID.text.toString()
        var licenseType = licenseType.text.toString()
        var dateTime2 = dateTime2.text.toString()
        if (!typeCard.equals(bean!!.registration)||!registerId.equals(bean!!.registrationNo)||!legalPerson.equals(bean!!.legalName)||!idCard.equals(bean!!.legalIdcar)||!dateTime.equals(bean!!.registrationValidity.split(" ")[0])
                || !etValidity.equals(bean!!.idcarValidity.split(" ")[0])||!licenseID.equals(bean!!.licenseNo)||!licenseType.equals(bean!!.license)||!dateTime2.equals(bean!!.licenseValidity.split(" ")[0])||mFile1!=null||mFile2!=null||mFile3!=null||mFile4!=null){
                return true
        }
        return false
    }

    private fun showNewUI() {
        rlPrompt.visibility = View.VISIBLE
        rlPrompt.setBackgroundResource(R.drawable.shape_prompt_bg)
        promptText.text = getString(R.string.prompt_text_ing)
        promptText.textColor = resources.getColor(R.color.qualification_commit)
        promptDelete.visibility = View.GONE
        getRightView().setTextColor(resources.getColor(R.color.main_50))
        getRightView().isEnabled = false
    }

    companion object {
        const val TAG = "QualificationInfo"
        const val BASE_URL_IMAGE = "https://shop.epro.com.hk/image/"
        // 拍照回传码
        val CAMERA_REQUEST_CODE = 100
        // 相册选择回传吗
        val GALLERY_REQUEST_CODE = 101
        //裁剪
        val CROP_REQUEST_CODE = 102

        // 拍照回传码
        val CAMERA_REQUEST_CODE_2 = 103
        // 相册选择回传吗
        val GALLERY_REQUEST_CODE_2 = 104
        //裁剪
        val CROP_REQUEST_CODE_2 = 105

        // 拍照回传码
        val CAMERA_REQUEST_CODE_3 = 106
        // 相册选择回传吗
        val GALLERY_REQUEST_CODE_3 = 107
        //裁剪
        val CROP_REQUEST_CODE_3= 108

        // 拍照回传码
        val CAMERA_REQUEST_CODE_4 = 109
        // 相册选择回传吗
        val GALLERY_REQUEST_CODE_4 = 110
        //裁剪
        val CROP_REQUEST_CODE_4= 111

        private const val RC_READ_AND_WRITE_EXTERNAL_STORAGE = 10
        private const val RC_CAMERA = 11

        private const val IDCAR_NEGATIVE = 1 //身份证反面
        private const val IDCAR_POSITIVE = 2 //身份证正面
        private const val REGISTRATION_PHOTO = 3 //证件照
        private const val LICENSE_PHOTO= 4   //行业照
        //相片路径
        var imageUri: Uri?=null
        //裁剪后路径
        var cropUri: Uri?=null
        fun newInstance(str: String): QualificationInfoFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = QualificationInfoFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): QualificationInfoFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): QualificationInfoPresenter {
        return QualificationInfoPresenter()
    }

    override fun onUpdateQualificationInfoSuccess(result:UpdateQualificationBaseBean.Result) {
        toast(getString(R.string.commit_success))
        showNewUI()
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_qualification_info
    }

    override fun initData() {

    }

/*  private const val IDCAR_NEGATIVE = 1 //身份证反面
    private const val IDCAR_POSITIVE = 2 //身份证正面
    private const val REGISTRATION_PHOTO = 3 //证件照
    private const val LICENSE_PHOTO= 4   //行业照*/

    var mList=HashMap<Int ,String>()
    var mSize = ArrayList<Int>()
    override fun onUpdateImageSucess(result: UpdateImageBean,photoType:Int) {
        mList[photoType] = result.result[0]
        mSize.add(photoType)
        if (mSize.equals(postion)){
            var typeCard =typeCard.text.toString()
            var registerId = registerId.text.toString()
            var legalPerson = legalPerson.text.toString()
            var idCard = idCard.text.toString()
            var dateTime = dateTime.text.toString()
            var etValidity = etValidity.text.toString()
            var licenseID = licenseID.text.toString()
            var licenseType = licenseType.text.toString()
            var dateTime2 = dateTime2.text.toString()
            var icarNegative = mList.get(IDCAR_NEGATIVE)
            var icarPositive = mList.get(IDCAR_POSITIVE)
            var licensePhoto = mList.get(LICENSE_PHOTO)
            var registrationPhot = mList.get(REGISTRATION_PHOTO)
            mPresenter.updateQualificationInfo(PosConst.MERCHANT_QUALIFICATION_INFO, icarNegative
                    ?: bean!!.idcarNegative, icarPositive
                    ?: bean!!.idcarPositive,etValidity,idCard,legalPerson,licenseType,bean!!.licenseId,licenseID,licensePhoto
                    ?: bean!!.licensePhoto,dateTime2,
                    typeCard,bean!!.registrationId,registerId,registrationPhot?:bean!!.registrationPhoto,dateTime,bean!!.shopId)
            mList.clear()
            mSize.clear()
        }
    }

    var bean:QualificationBaseBean.Result?=null
    override fun onQualificationInfoSuccess(result: QualificationBaseBean.Result) {
        typeCard.text = result.registration
        registerId.text = result.registrationNo
        legalPerson.text = result.legalName
        idCard.text = result.legalIdcar
        dateTime.text = result.registrationValidity.split(" ")[0]
        etValidity.text = result.idcarValidity.split(" ")[0]
        licenseID.text = result.licenseNo
        licenseType.text = result.license
        dateTime2.text = result.licenseValidity.split(" ")[0]
        var idcarNegativePath = result.idcarNegative  //省份证反面
        var idcarPositivePath = result.idcarPositive  //省份证正面
        var licensePath = result.licensePhoto  //行业资质
        var registPath = result.registrationPhoto //证件照
        imgPhotoCard.ext_loadImageWithDomain(registPath,R.mipmap.icon_placeholder)
        imgPositive.ext_loadImageWithDomain(idcarPositivePath,R.mipmap.icon_placeholder)
        idCardReverse.ext_loadImageWithDomain(idcarNegativePath,R.mipmap.icon_placeholder)
        imgDocument2.ext_loadImageWithDomain(licensePath,R.mipmap.icon_placeholder)
        bean = result
        showTitleTips()
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

    private fun showCommitUI() {
        logTools.t("YB").d("showCommitUI "+imgPhotoCard.isEnabled )
        rlPrompt.visibility = View.VISIBLE
        rlPrompt.setBackgroundResource(R.drawable.shape_prompt_bg)
        promptImg.setImageResource(R.mipmap.prompt)
        promptText.text = getString(R.string.prompt_text_ing)
        promptText.textColor = resources.getColor(R.color.qualification_commit)
        promptDelete.visibility = View.GONE
        getRightView().setTextColor(resources.getColor(R.color.main_50))
        getRightView().isEnabled = false
        forBidView()
    }

    //审核中的view禁止
    private fun forBidView() {
        typeCard.isEnabled = false
        registerId.isEnabled = false
        legalPerson.isEnabled = false
        idCard.isEnabled = false
        dateTime.isEnabled = false
        etValidity.isEnabled = false
        licenseID.isEnabled = false
        licenseType.isEnabled = false
        dateTime2.isEnabled = false
        typeCard.setTextColor(resources.getColor(R.color.mainText_50))
        registerId.setTextColor(resources.getColor(R.color.mainText_50))
        legalPerson.setTextColor(resources.getColor(R.color.mainText_50))
        etValidity.setTextColor(resources.getColor(R.color.mainText_50))
        idCard.setTextColor(resources.getColor(R.color.mainText_50))
        dateTime.setTextColor(resources.getColor(R.color.mainText_50))
        licenseID.setTextColor(resources.getColor(R.color.mainText_50))
        licenseType.setTextColor(resources.getColor(R.color.mainText_50))
        dateTime2.setTextColor(resources.getColor(R.color.mainText_50))
        //相片
        imgPositive.setOnClickListener(null)
        imgPhotoCard.setOnClickListener(null)
        idCardReverse.setOnClickListener(null)
        imgDocument2.setOnClickListener(null)
        imgPositive.setAlpha(80)
        imgPhotoCard.setAlpha(80)
        idCardReverse.setAlpha(80)
        imgDocument2.setAlpha(80)
    }

    //审核成功UI
    private fun showSuccessUI() {
        rlPrompt.visibility = View.VISIBLE
        showForBidView()
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

    //禁止的UI重新打开
    private fun showForBidView() {
        typeCard.isEnabled = true
        registerId.isEnabled = true
        legalPerson.isEnabled = true
        idCard.isEnabled = true
        dateTime.isEnabled = true
        etValidity.isEnabled = true
        licenseID.isEnabled = true
        licenseType.isEnabled = true
        dateTime2.isEnabled = true
        typeCard.setTextColor(resources.getColor(R.color.mainTextColor))
        registerId.setTextColor(resources.getColor(R.color.mainTextColor))
        legalPerson.setTextColor(resources.getColor(R.color.mainTextColor))
        etValidity.setTextColor(resources.getColor(R.color.mainTextColor))
        idCard.setTextColor(resources.getColor(R.color.mainTextColor))
        dateTime.setTextColor(resources.getColor(R.color.mainTextColor))
        licenseID.setTextColor(resources.getColor(R.color.mainTextColor))
        licenseType.setTextColor(resources.getColor(R.color.mainTextColor))
        dateTime2.setTextColor(resources.getColor(R.color.mainTextColor))
        //相片
        imgPositive.setOnClickListener(this)
        imgPhotoCard.setOnClickListener(this)
        idCardReverse.setOnClickListener(this)
        imgDocument2.setOnClickListener(this)
        imgPositive.setAlpha(0)
        imgPhotoCard.setAlpha(0)
        idCardReverse.setAlpha(0)
        imgDocument2.setAlpha(0)
    }

    override fun onDeleteSuccess() {
        rlPrompt.visibility = View.GONE
    }

    //审核失败
    private fun showFialedUI() {
        rlPrompt.visibility = View.VISIBLE
        showForBidView()
        rlPrompt.setBackgroundResource(R.drawable.shape_prompt_bg_red)
        promptImg.setImageResource(R.mipmap.caution)
        promptText.text = getString(R.string.business_info_not_pass)+bean!!.shopinfoOpinions
        promptText.textColor = resources.getColor(R.color.mainColor)
        promptDelete.visibility = View.GONE
        getRightView().setTextColor(resources.getColor(R.color.mainColor))
        getRightView().isEnabled = true
    }



    override fun initView() {
        super.initView()
        setHaveBackView(true)
        mPresenter.QualificationInfo(PosConst.MERCHANT_QUALIFICATION_INFO)
        getLeftTitleView().text = getString(R.string.business_information)
        getTitleView().text = getString(R.string.Qualification_info)
        getRightView().visibility = View.VISIBLE
        getRightView().text = getString(R.string.submit_review)
        getRightView().setTextColor(resources.getColor(R.color.mainColor))
        getOptionData()
        initOptionPicker2()
    }

    /**
     * 证件类型
     */
    var pvOptions2: OptionsPickerView<String>? = null
    private fun initOptionPicker2() {
        pvOptions2 = OptionsPickerBuilder(activity!!, OnOptionsSelectListener { options1, options2, options3, v ->
            //返回的分别是三个级别的选中位置
            var tx = options1Items[options1]
            typeCard.setText(tx)
        })
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.parseColor("#e5e5e5"))//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.parseColor("#333333"))
                .setCancelColor(Color.parseColor("#333333"))
                .setSubmitColor(Color.parseColor("#E52020"))
                .setTextColorCenter(Color.parseColor("#333333"))
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setOutSideColor(Color.WHITE) //设置外部遮罩颜色
                .setSubmitText(getString(R.string.dialog_done))
                .setCancelText(getString(R.string.delete_dialog_cancel))
                .setTitleSize(18)
                .setBackgroundId(Color.parseColor("#ffffff"))
                .setOptionsSelectChangeListener { options1, options2, options3 ->
                    val str = "options1: $options1\noptions2: $options2\noptions3: $options3"
                    //   Toast.makeText(this@MainActivity, str, Toast.LENGTH_SHORT).show()
                }
                .build<String>()
        pvOptions2!!.setPicker(options1Items);//一级选择器*/
    }

    var options1Items = ArrayList<String>()
    private fun getOptionData() {
        val res = resources
        val defaultStringArray_0 = res.getTextArray(R.array.type_of_certificate)
        //选项1
        options1Items.clear()
        for (index in defaultStringArray_0.indices) {
            options1Items.add(defaultStringArray_0[index].toString())
        }
    }

    override fun initListener() {
        getLeftView().setOnClickListener(this)
        getRightView().setOnClickListener(this)
        //证件类型
        typeCard.setOnClickListener(this)
        //证件照片
        imgDoc.setOnClickListener(this)
        //注册号：
        registerId.setOnClickListener(this)
        //法定代表人姓名
        legalPerson.setOnClickListener(this)
        //身份证号
        idCard.setOnClickListener(this)
        //主体证件有效期
        dateTime.setOnClickListener(this)
        //身份证正面
        imgCard.setOnClickListener(this)
        //身份证反面
        imgReverse.setOnClickListener(this)
        //身份证有效期
        etValidity.setOnClickListener(this)
        //许可编号
        licenseID.setOnClickListener(this)
        //行业资质证件类型
        licenseType.setOnClickListener(this)
        //行业资质证件照片
        imgDoc2.setOnClickListener(this)
        //行业资质证件有效期
        dateTime2.setOnClickListener(this)
    }

    override fun lazyLoad() {

    }

    var pop:CameraSelectPop?=null
    private fun showPhotoPop(a:Int,b:Int) {
        pop = CameraSelectPop(activity!!)
        pop!!.popupGravity = Gravity.CENTER
        pop!!.setWidth(DisplayManager.getScreenWidth()!!*32/100)
        pop!!.setHeight(DisplayManager.getScreenHeight()!!*23/100)
        pop!!.showPopupWindow()
        pop!!.contentView.findViewById<TextView>(R.id.tvTakePhoto).setOnClickListener {
            takePhoto(a)
        }
        pop!!.contentView.findViewById<TextView>(R.id.tvSelectPhoto).setOnClickListener {
            choosePhto(b)
        }
        pop!!.contentView.findViewById<TextView>(R.id.tvCancel).setOnClickListener {
            pop!!.dismiss()
        }

    }

    @AfterPermissionGranted(RC_CAMERA)
    private fun takePhoto(a:Int) {
        if (EasyPermissions.hasPermissions(activity!!, Manifest.permission.CAMERA,  Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Already have permission, do the thing
            imageUri = ext_takePhoto(a)
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.pls_permission_storage),
                    RC_CAMERA, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    @AfterPermissionGranted(RC_READ_AND_WRITE_EXTERNAL_STORAGE)
    private fun choosePhto(b:Int) {
        if (EasyPermissions.hasPermissions(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Already have permission, do the thing
            ext_choosePhoto(b)
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(activity!!, getString(R.string.pls_permission_open_camera),
                    RC_READ_AND_WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

/*    //证件照片
    imgDoc->{ showPhotoPop(CAMERA_REQUEST_CODE,GALLERY_REQUEST_CODE) }
    //身份证正面
    imgCard->{ showPhotoPop(CAMERA_REQUEST_CODE_2,GALLERY_REQUEST_CODE_2)}
    //身份证反面
    imgReverse->{showPhotoPop(CAMERA_REQUEST_CODE_3,GALLERY_REQUEST_CODE_3)}
    //行业资质
    imgDoc2->{showPhotoPop(CAMERA_REQUEST_CODE_4,GALLERY_REQUEST_CODE_4)}*/

    private var mFile1 :File?=null
    private var mFile2 :File?=null
    private var mFile3 :File?=null
    private var mFile4 :File?=null
    var postion=ArrayList<Int>()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    cropUri = ext_startPhotoSquareCrop(imageUri!!, CROP_REQUEST_CODE)
                }
                GALLERY_REQUEST_CODE -> {
                    cropUri = ext_startPhotoSquareCrop(data?.data!!, CROP_REQUEST_CODE)
                }
                CROP_REQUEST_CODE -> {
                val bitmap = BitmapFactory.decodeStream(activity!!.contentResolver.openInputStream(cropUri!!))
                imgPhotoCard.ext_loadImage(cropUri?.path)
                Luban.with(activity!!).load(cropUri?.path).ignoreBy(100).setTargetDir("").setCompressListener(object :OnCompressListener {
                        override fun onSuccess(file: File?) {
                            mFile1 = file
                            if (postion.size ==0){
                                postion.add(REGISTRATION_PHOTO)
                            }else{
                                for (i in 0 until  postion.size){
                                    if (!postion.contains(REGISTRATION_PHOTO)){
                                        postion.add(REGISTRATION_PHOTO)
                                    }
                                }
                            }
                        }
                        override fun onError(e: Throwable?) {
                        }
                        override fun onStart() {
                        }
                }).launch()
                pop!!.dismiss()
            }
                CAMERA_REQUEST_CODE_2->{
                    cropUri = ext_startPhotoSquareCrop(imageUri!!, CROP_REQUEST_CODE_2)
                }
                GALLERY_REQUEST_CODE_2 -> {
                    cropUri = ext_startPhotoSquareCrop(data?.data!!, CROP_REQUEST_CODE_2)
                }
                CROP_REQUEST_CODE_2 -> {
                    val bitmap = BitmapFactory.decodeStream(activity!!.contentResolver.openInputStream(cropUri!!))
                    //imgPhotoCard.ext_loadCircleImage(cropUri?.path)
                    imgPositive.ext_loadImage(cropUri?.path)
                    Luban.with(activity!!).load(cropUri?.path).ignoreBy(100).setTargetDir("").setCompressListener(object :OnCompressListener {
                        override fun onSuccess(file: File?) {
                            mFile2 = file
                            if (postion.size == 0){
                                postion.add(IDCAR_POSITIVE)
                            }else{
                                for (i in 0 until  postion.size){
                                    if (!postion.contains(IDCAR_POSITIVE)){
                                        postion.add(IDCAR_POSITIVE)
                                    }
                                }
                            }
                        }
                        override fun onError(e: Throwable?) {
                        }
                        override fun onStart() {
                        }
                    }).launch()
                    pop!!.dismiss()
                }
                CAMERA_REQUEST_CODE_3->{
                    cropUri = ext_startPhotoSquareCrop(imageUri!!, CROP_REQUEST_CODE_3)
                }
                GALLERY_REQUEST_CODE_3 -> {
                    cropUri = ext_startPhotoSquareCrop(data?.data!!, CROP_REQUEST_CODE_3)
                }
                CROP_REQUEST_CODE_3 -> {
                    val bitmap = BitmapFactory.decodeStream(activity!!.contentResolver.openInputStream(cropUri!!))
                    //imgPhotoCard.ext_loadCircleImage(cropUri?.path)
                    idCardReverse.ext_loadImage(cropUri?.path)
                    Luban.with(activity!!).load(cropUri?.path).ignoreBy(100).setTargetDir("").setCompressListener(object :OnCompressListener {
                        override fun onSuccess(file: File?) {
                            mFile3 = file
                            if (postion.size == 0){
                                postion.add( IDCAR_NEGATIVE)
                            }else{
                                for (i in 0 until  postion.size){
                                    if (!postion.contains(IDCAR_NEGATIVE)){
                                        postion.add(IDCAR_NEGATIVE)
                                    }
                                }
                            }
                        }
                        override fun onError(e: Throwable?) {
                        }
                        override fun onStart() {
                        }
                    }).launch()
                    pop!!.dismiss()
                }
                CAMERA_REQUEST_CODE_4->{
                    cropUri = ext_startPhotoSquareCrop(imageUri!!, CROP_REQUEST_CODE_4)
                }
                GALLERY_REQUEST_CODE_4 -> {
                    cropUri = ext_startPhotoSquareCrop(data?.data!!, CROP_REQUEST_CODE_4)
                }
                CROP_REQUEST_CODE_4 -> {
                    val bitmap = BitmapFactory.decodeStream(activity!!.contentResolver.openInputStream(cropUri!!))
                    //imgPhotoCard.ext_loadCircleImage(cropUri?.path)
                    imgDocument2.ext_loadImage(cropUri?.path)
                    Luban.with(activity!!).load(cropUri?.path).ignoreBy(100).setTargetDir("").setCompressListener(object :OnCompressListener {
                        override fun onSuccess(file: File?) {
                            mFile4 = file
                            if (postion.size == 0 ){
                                postion.add(LICENSE_PHOTO)
                            }else{
                                for (i in 0 until  postion.size){
                                    if (!postion.contains(LICENSE_PHOTO)){
                                        postion.add(LICENSE_PHOTO)
                                    }
                                }
                            }
                        }
                        override fun onError(e: Throwable?) {
                        }
                        override fun onStart() {
                        }
                    }).launch()
                    pop!!.dismiss()
                }
            }
        }
    }

    //日期选择器
    var pvCustomLunar: TimePickerView?=null
    private fun showDateTime(a:Int) {
        val selectedDate = Calendar.getInstance()//系统当前时间
        val startDate = Calendar.getInstance()
        startDate.set(2014, 1, 23)
        val endDate = Calendar.getInstance()
        endDate.set(2069, 2, 28)

        pvCustomLunar = TimePickerBuilder(activity!!, OnTimeSelectListener { date, v ->
            //选中事件回调
            var date= getTime(date)
            when(a){
                R.id.dateTime->{
                    dateTime.text = date
                }
                R.id.dateTime2->{
                    dateTime2.text = date
                }
                R.id.etValidity->{
                    etValidity.text =date
                }
            }

        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, CustomListener{ v->
                    val tvSubmit = v.findViewById<View>(R.id.tv_finish) as TextView
                    val ivCancel = v.findViewById<View>(R.id.iv_cancel) as TextView
                    tvSubmit.setOnClickListener {
                        pvCustomLunar!!.returnData()
                        pvCustomLunar!!.dismiss()
                    }
                    ivCancel.setOnClickListener{ pvCustomLunar!!.dismiss()}
                })
                .setType(booleanArrayOf(true,true,true,false,false,false))
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build()

    }

    private fun getTime(date: Date): String {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.time)
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    //其他弹框修改
    private fun showGeneralPop(title:String) {
        val pop = GeneralModifyPop(activity!!)
        pop.popupGravity = Gravity.CENTER
        pop.setWidth(DisplayManager.getScreenWidth()!!*1/2)
        pop.setHeight(DisplayManager.getScreenHeight()!!*1/2)
        pop.showPopupWindow()
        pop.contentView.findViewById<TextView>(R.id.tvPopTitle).text = title.substring(0,title.length-1)
        pop.contentView.findViewById<TextView>(R.id.tvName).text = title
        pop.contentView.findViewById<TextView>(R.id.tvPopCancel).setOnClickListener {
            pop.dismiss()
        }
        //保存按钮随输入框变换
        pop.contentView.findViewById<TextView>(R.id.tvInput).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var flag = true
                val tvRight =pop.contentView.findViewById<TextView>(R.id.tvPopRight)
                val tvInput = pop.contentView.findViewById<TextView>(R.id.tvInput)
                flag = TextUtils.isEmpty(tvInput.text.toString().trim())
                tvRight.setTextColor(if (!flag)resources.getColor(R.color.mainColor) else resources.getColor(R.color.main_50) )
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        /*//注册号
        registerId->{showGeneralPop(getString(R.string.registration_number))}
        legalPerson->{showGeneralPop(getString(R.string.legal_representative))}
        idCard->{showGeneralPop(getString(R.string.identification_number))}
        licenseID->{showGeneralPop(getString(R.string.permit_number))}
        licenseType->{showGeneralPop(getString(R.string.type_of_certificate))}*/

        pop!!.contentView.findViewById<TextView>(R.id.tvPopRight).setOnClickListener {
            pop.dismiss()
            var mText =  pop.contentView.findViewById<TextView>(R.id.tvInput).text.toString().trim()
            when(title){
                getString(R.string.registration_number)->{
                    registerId.text = mText
                }
                getString(R.string.legal_representative)->{
                    legalPerson .text = mText
                }
                getString(R.string.identification_number)->{
                    idCard .text = mText
                }
                getString(R.string.permit_number)->{
                    licenseID .text = mText
                }
                getString(R.string.type_of_certificate)->{
                    licenseType .text = mText
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mList.clear()
        mSize.clear()
        postion.clear()
        mFile1 = null
        mFile2 = null
        mFile3 = null
        mFile4 = null
    }

    private fun showTipsDialog() {
        CommonDialog.Builder(activity!!)
                .setTitle(getString(R.string.alert))
                .setContent(getString(R.string.edit_base_tips_content))
                .setConfirmText(getString(R.string.out_this_page))
                .setCancelText(getString(R.string.delete_dialog_cancel))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        var msg = FragmentChanageEvent()
                        msg.postion = 13
                        msg.level = 3
                        msg.type = 1
                        EventBus.getDefault().postSticky(msg)
                    }
                })
                .setOnCancelListener(object : CommonDialog.OnCancelListener{
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                    }
                })
                .create()
                .show()
    }

}


