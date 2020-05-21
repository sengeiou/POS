package com.epro.pos.ui.fragment.settings

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.listener.AccountUnbindEvent
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.listener.UnLoginEvent
import com.epro.pos.mvp.contract.PersonCenterContract
import com.epro.pos.mvp.model.bean.PersonCenterBean
import com.epro.pos.mvp.model.bean.UpdateImageBean
import com.epro.pos.mvp.presenter.PersonCenterPresenter
import com.epro.pos.ui.MainActivity
import com.epro.pos.ui.activity.SelectImageActivity
import com.epro.pos.ui.fragment.BindEmailPopupFragment
import com.epro.pos.ui.fragment.BindPhonePopup
import com.epro.pos.ui.login.LoginActivity
import com.epro.pos.ui.view.PersonCenterPopup
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.mike.baselib.utils.*
import com.mike.baselib.view.CommonDialog
import kotlinx.android.synthetic.main.activity_person_center.*
import org.greenrobot.eventbus.EventBus
import com.mike.baselib.utils.toast
import org.jetbrains.anko.textColor
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.io.FileOutputStream


class PersonCenterFragment : BaseTitleBarCustomFragment<PersonCenterContract.View, PersonCenterPresenter>(), PersonCenterContract.View, View.OnClickListener {

    var avatar:String?=null
    override fun onUpdateImageSucess(result: UpdateImageBean) {
        avatar = result.result[0]
        mPresenter.EditAvatar(PosConst.UPDATE_AVATAR,avatar!!)
    }

    override fun onExitLogoutSuccess() {
        AppBusManager.setToken("")
        EventBus.getDefault().post(UnLoginEvent())
        toast(getString(R.string.logout_success))
        LoginActivity.launchForReslut(activity!!, MainActivity.REQUEST_CODE_FOR_LOGIN_RESULT)
    }

    override fun onPersonCenterSuccess(result: PersonCenterBean.Result) {
        if (TextUtils.isEmpty(result.phone)){
            phoneBind.text = getString(R.string.unbound)
            phoneBind.textColor = resources.getColor(R.color.mainColor)
        }else{
            phoneBind.text = result.phone
            phoneBind.textColor = resources.getColor(R.color.thirdTextColor)
        }
        if (TextUtils.isEmpty(result.email)){
            emailBind.text = getString(R.string.unbound)
            emailBind.textColor = resources.getColor(R.color.mainColor)
        }else{
            emailBind.text = result.email
            emailBind.textColor = resources.getColor(R.color.thirdTextColor)
        }
        personStyle.text = result.rolename
        personNum.text =result.account
        personName.text = result.name
        tvName.text = result.name
        tvAccount.text = result.account
        if (!TextUtils.isEmpty(result.avatar)){
            if (result.avatar.startsWith("/admin/")){
                ImageLoader.loadCircleImage(context, AppConfig.getBaseurl() + result.avatar, ivAvatar)
            }else{
                ivAvatar.ext_loadCircleImageWithDomain(result.avatar)
            }
        }else{
            ivAvatar.setImageResource(R.mipmap.head_portrait)
        }
    }

    override fun onEditAvatarSuccess() {
         toast(getString(R.string.update_image_avatar))
    }

    override fun lazyLoad() {
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
            mPresenter.PersonCenter(PosConst.GET_PERSON_INFO)
        }
    }

    override fun onClick(v: View?) {
       when(v){
           //头像点击选择相册
           ivAvatar->{
               showSelectPhoto()
           }

           //绑定手机
           llPhoneBind->{
               var phoneNum = phoneBind.text.toString().trim()
               if (TextUtils.isEmpty(phoneNum)||getString(R.string.unbound).equals(phoneNum)){
                   showBindDialog()
               }else{
                   var event = AccountUnbindEvent()
                   event.type = "phone"
                   event.account = phoneNum
                   EventBus.getDefault().postSticky(event)
                   //手机已经绑定跳转解绑
                   var msg = FragmentChanageEvent()
                   msg.postion = 8
                   EventBus.getDefault().postSticky(msg)
               }
           }

           //绑定手机
           phoneBind->{
               var phoneNum = phoneBind.text.toString().trim()
               if (TextUtils.isEmpty(phoneNum)||getString(R.string.unbound).equals(phoneNum)){
                   showBindDialog()
               }else{
                   var event = AccountUnbindEvent()
                   event.type = "phone"
                   event.account = phoneNum
                   EventBus.getDefault().postSticky(event)
                   //手机已经绑定跳转解绑
                   var msg = FragmentChanageEvent()
                   msg.postion = 8
                   EventBus.getDefault().postSticky(msg)
               }
           }

           //绑定邮箱
           llEmailBind->{
              //手机邮箱未绑定弹框
               var emailAddress = emailBind.text.toString().trim()
               if (TextUtils.isEmpty(emailAddress)||getString(R.string.unbound).equals(emailAddress)){
                   showBindEmailDialog()
               }else{
                   var event = AccountUnbindEvent()
                   event.type = "email"
                   event.account = emailAddress
                   EventBus.getDefault().postSticky(event)
                   //邮箱已经绑定跳转解绑
                   var msg = FragmentChanageEvent()
                   msg.postion = 8
                   EventBus.getDefault().postSticky(msg)
               }

           }
           emailBind->{
               //手机邮箱未绑定弹框
               var emailAddress = emailBind.text.toString().trim()
               if (TextUtils.isEmpty(emailAddress)||getString(R.string.unbound).equals(emailAddress)){
                   showBindEmailDialog()
               }else{
                   var event = AccountUnbindEvent()
                   event.type = "email"
                   event.account = emailAddress
                   EventBus.getDefault().postSticky(event)
                   //邮箱已经绑定跳转解绑
                   var msg = FragmentChanageEvent()
                   msg.postion = 8
                   EventBus.getDefault().postSticky(msg)
               }
           }
           //修改密码
           pswModify->{
               var msg = FragmentChanageEvent()
               msg.postion = 7
               EventBus.getDefault().postSticky(msg)
           }

           //退出登入
           personOut->{
               showLogoutDialog()
           }

           //返回键
           getLeftView()->{
               var msg = FragmentChanageEvent()
               msg.postion = 13
               EventBus.getDefault().postSticky(msg)
           }
       }
    }

    private fun showBindDialog() {
        val fragment = BindPhonePopup.newInstance("")
         fragment.show(childFragmentManager, "bind_phone")
    }

    private fun showBindEmailDialog() {
        val fragment = BindEmailPopupFragment.newInstance("")
        fragment.show(childFragmentManager, "bind_email")
    }
    var selectImg:ImageView?=null
    var imgRecommendSelect1:ImageView?=null
    var imgRecommendSelect2:ImageView?=null
    var imgRecommendSelect3:ImageView?=null
    var imgRecommendSelect4:ImageView?=null
    var imgRecommendSelect5:ImageView?=null
    var selectPhotoGo:ImageView?=null
    private fun showSelectPhoto() {
        val pop = PersonCenterPopup(activity!!)
        pop.width =  DisplayManager.getScreenWidth()!!*45/100
        pop.height = DisplayManager.getScreenHeight()!!*61/100
        pop.popupGravity = Gravity.CENTER
        pop.showPopupWindow()
        val cancel = pop.contentView.findViewById<TextView>(R.id.tvCancel)
        val confirm =  pop.contentView.findViewById<TextView>(R.id.tvRight)
        val selectPhoto = pop.contentView.findViewById<TextView>(R.id.selectTv)
        val imgRecommend1 = pop.contentView.findViewById<ImageView>(R.id.imgRecommend1)
        val imgRecommend2 = pop.contentView.findViewById<ImageView>(R.id.imgRecommend2)
        val imgRecommend3 = pop.contentView.findViewById<ImageView>(R.id.imgRecommend3)
        val imgRecommend4 = pop.contentView.findViewById<ImageView>(R.id.imgRecommend4)
        val imgRecommend5 = pop.contentView.findViewById<ImageView>(R.id.imgRecommend5)
        imgRecommendSelect1 = pop.contentView.findViewById<ImageView>(R.id.imgRecommendSelect1)
        imgRecommendSelect2 = pop.contentView.findViewById<ImageView>(R.id.imgRecommendSelect2)
        imgRecommendSelect3 = pop.contentView.findViewById<ImageView>(R.id.imgRecommendSelect3)
        imgRecommendSelect4 = pop.contentView.findViewById<ImageView>(R.id.imgRecommendSelect4)
        imgRecommendSelect5 = pop.contentView.findViewById<ImageView>(R.id.imgRecommendSelect5)
        selectPhotoGo = pop.contentView.findViewById<ImageView>(R.id.selectImgGo)
        selectImg = pop.contentView.findViewById<ImageView>(R.id.selectImg)
        var select =SPUtils.get(activity!!,"selectPhoto",0) as Int
        keepPhoto(select)
        if (!"".equals(path)){
            selectImg!!.ext_loadCircleImage(path!!)
        }

        imgRecommend1.setOnClickListener {
            imgRecommendSelect1!!.visibility = View.VISIBLE
            imgRecommendSelect2!!.visibility = View.GONE
            imgRecommendSelect3!!.visibility = View.GONE
            imgRecommendSelect4!!.visibility = View.GONE
            imgRecommendSelect5!!.visibility = View.GONE
            selectPhotoGo!!.visibility = View.GONE
        }
        imgRecommend2.setOnClickListener {
            imgRecommendSelect1!!.visibility = View.GONE
            imgRecommendSelect2!!.visibility = View.VISIBLE
            imgRecommendSelect3!!.visibility = View.GONE
            imgRecommendSelect4!!.visibility = View.GONE
            imgRecommendSelect5!!.visibility = View.GONE
            selectPhotoGo!!.visibility = View.GONE
        }
        imgRecommend3.setOnClickListener {
            imgRecommendSelect1!!.visibility = View.GONE
            imgRecommendSelect2!!.visibility = View.GONE
            imgRecommendSelect3!!.visibility = View.VISIBLE
            imgRecommendSelect4!!.visibility = View.GONE
            imgRecommendSelect5!!.visibility = View.GONE
            selectPhotoGo!!.visibility = View.GONE
        }
        imgRecommend4.setOnClickListener {
            imgRecommendSelect1!!.visibility = View.GONE
            imgRecommendSelect2!!.visibility = View.GONE
            imgRecommendSelect3!!.visibility = View.GONE
            imgRecommendSelect4!!.visibility = View.VISIBLE
            imgRecommendSelect5!!.visibility = View.GONE
            selectPhotoGo!!.visibility = View.GONE
        }
        imgRecommend5.setOnClickListener {
            imgRecommendSelect1!!.visibility = View.GONE
            imgRecommendSelect2!!.visibility = View.GONE
            imgRecommendSelect3!!.visibility = View.GONE
            imgRecommendSelect4!!.visibility = View.GONE
            imgRecommendSelect5!!.visibility = View.VISIBLE
            selectPhotoGo!!.visibility = View.GONE
        }

        selectImg!!.setOnClickListener {
            imgRecommendSelect1!!.visibility = View.GONE
            imgRecommendSelect2!!.visibility = View.GONE
            imgRecommendSelect3!!.visibility = View.GONE
            imgRecommendSelect4!!.visibility = View.GONE
            imgRecommendSelect5!!.visibility = View.GONE
            selectPhotoGo!!.visibility = View.VISIBLE
        }

        cancel.setOnClickListener {
            pop.dismiss()
        }
        confirm.setOnClickListener {
            var path =SPUtils.get(activity!!,"pathPhoto","") as String
            successSelectPhoto(path)
            pop.dismiss()
        }
        selectPhoto.setOnClickListener {
           // choosePhto()
            SelectImageActivity.launchWithType_Width_Height(this,SelectImageActivity.GALLERY_REQUEST_CODE, 300,300, FOR_IMAGE)
        }
    }

    private fun keepPhoto(select: Int) {
        when(select){
            1->{
                imgRecommendSelect1!!.visibility = View.VISIBLE
                imgRecommendSelect2!!.visibility = View.GONE
                imgRecommendSelect3!!.visibility = View.GONE
                imgRecommendSelect4!!.visibility = View.GONE
                imgRecommendSelect5!!.visibility = View.GONE
                selectPhotoGo!!.visibility = View.GONE
            }
            2->{
                imgRecommendSelect1!!.visibility = View.GONE
                imgRecommendSelect2!!.visibility = View.VISIBLE
                imgRecommendSelect3!!.visibility = View.GONE
                imgRecommendSelect4!!.visibility = View.GONE
                imgRecommendSelect5!!.visibility = View.GONE
                selectPhotoGo!!.visibility = View.GONE
            }
            3->{
                imgRecommendSelect1!!.visibility = View.GONE
                imgRecommendSelect2!!.visibility = View.GONE
                imgRecommendSelect3!!.visibility = View.VISIBLE
                imgRecommendSelect4!!.visibility = View.GONE
                imgRecommendSelect5!!.visibility = View.GONE
                selectPhotoGo!!.visibility = View.GONE
            }
            4->{
                imgRecommendSelect1!!.visibility = View.GONE
                imgRecommendSelect2!!.visibility = View.GONE
                imgRecommendSelect3!!.visibility = View.GONE
                imgRecommendSelect4!!.visibility = View.VISIBLE
                imgRecommendSelect5!!.visibility = View.GONE
                selectPhotoGo!!.visibility = View.GONE
            }
            5->{
                imgRecommendSelect1!!.visibility = View.GONE
                imgRecommendSelect2!!.visibility = View.GONE
                imgRecommendSelect3!!.visibility = View.GONE
                imgRecommendSelect4!!.visibility = View.GONE
                imgRecommendSelect5!!.visibility = View.VISIBLE
                selectPhotoGo!!.visibility = View.GONE
            }
        }
    }

    private fun successSelectPhoto(path:String) {
        when {
            imgRecommendSelect1!!.visibility == View.VISIBLE -> {
               var mFile= getFileImage(1,"head_portrait01.png")
                ivAvatar.setImageResource(R.mipmap.head_portrait01)
                SPUtils.put(activity!!,"selectPhoto", 1)
                mPresenter.updateImage(PosConst.UPDATE_IMAGE,mFile,0)
            }

            imgRecommendSelect2!!.visibility == View.VISIBLE -> {
                var mFile= getFileImage(2,"head_portrait02.png")
                ivAvatar.setImageResource(R.mipmap.head_portrait02)
                SPUtils.put(activity!!,"selectPhoto", 2)
                mPresenter.updateImage(PosConst.UPDATE_IMAGE,mFile,0)
            }
            imgRecommendSelect3!!.visibility == View.VISIBLE -> {
                var mFile= getFileImage(3,"head_portrait03.png")
                ivAvatar.setImageResource(R.mipmap.head_portrait03)
                SPUtils.put(activity!!,"selectPhoto", 3)
                mPresenter.updateImage(PosConst.UPDATE_IMAGE,mFile,0)
            }
            imgRecommendSelect4!!.visibility == View.VISIBLE -> {
                var mFile= getFileImage(4,"head_portrait04.png")
                ivAvatar.setImageResource(R.mipmap.head_portrait04)
                SPUtils.put(activity!!,"selectPhoto", 4)
                mPresenter.updateImage(PosConst.UPDATE_IMAGE,mFile,0)
            }
            imgRecommendSelect5!!.visibility == View.VISIBLE -> {
                var mFile= getFileImage(5,"head_portrait05.png")
                ivAvatar.setImageResource(R.mipmap.head_portrait05)
                SPUtils.put(activity!!,"selectPhoto", 5)
                mPresenter.updateImage(PosConst.UPDATE_IMAGE,mFile,0)
            }

            selectPhotoGo!!.visibility == View.VISIBLE->{
                SPUtils.put(activity!!,"selectPhoto", 6)
                if (!"".equals(path)){
                    ivAvatar.ext_loadCircleImage(path)
                    mPresenter.updateImage(PosConst.UPDATE_IMAGE,File(path),0)
                }else{
                    var mFile= getFileImage(6,"head_portrait.png")
                    ivAvatar.setImageResource(R.mipmap.head_portrait)
                    mPresenter.updateImage(PosConst.UPDATE_IMAGE,mFile,0)
                }
            }
        }
    }

    fun getFileImage(index:Int,fileName:String):File{
        var bitmapDrawable:BitmapDrawable?=null
        if (1 == index){
            bitmapDrawable  = resources.getDrawable(R.mipmap.head_portrait01) as BitmapDrawable
        }else if (2 == index){
            bitmapDrawable  = resources.getDrawable(R.mipmap.head_portrait02) as BitmapDrawable
        }else if (3 == index){
            bitmapDrawable  = resources.getDrawable(R.mipmap.head_portrait03) as BitmapDrawable
        } else if (4 == index){
            bitmapDrawable  = resources.getDrawable(R.mipmap.head_portrait04) as BitmapDrawable
        }else if (5 == index){
            bitmapDrawable  = resources.getDrawable(R.mipmap.head_portrait05) as BitmapDrawable
        }else if (6== index){
            bitmapDrawable  = resources.getDrawable(R.mipmap.head_portrait) as BitmapDrawable
        }
        var img = bitmapDrawable!!.bitmap
        var fn = fileName
        var path = activity!!.filesDir.absolutePath + File.separator + fn
        var os = FileOutputStream(path)
        img.compress(Bitmap.CompressFormat.PNG, 100, os)
        os.close()
        var mFile = File(path)
        return mFile
    }

    companion object {
        const val TAG = "PersonCenter"
        val FOR_IMAGE=12
        fun newInstance(str: String): PersonCenterFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = PersonCenterFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): PersonCenterFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): PersonCenterPresenter {
        return PersonCenterPresenter()
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_person_center
    }

    override fun initData() {

    }

    var path:String?=null
    override fun initView() {
        super.initView()
        mPresenter.PersonCenter(PosConst.GET_PERSON_INFO)
        setHaveBackView(true)
        path =SPUtils.get(activity!!,"pathPhoto","") as String
        getTitleView().text=getString(R.string.personal_center)
        getLeftTitleView().text = getString(R.string.system_settings)
    }

    override fun initListener() {
        ivAvatar.setOnClickListener(this)
        phoneBind.setOnClickListener(this)
        emailBind.setOnClickListener(this)
        pswModify.setOnClickListener(this)
        personOut.setOnClickListener(this)
        llPhoneBind.setOnClickListener(this)
        llEmailBind.setOnClickListener(this)
        getLeftView().setOnClickListener(this)
    }


    private fun showLogoutDialog() {
        CommonDialog.Builder(activity!!)
                .setTitle(getString(R.string.logout))
                .setContent(getString(R.string.confirm_logout))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        mPresenter.ExitLogout(PosConst.LOGOUT)
                        //LoginActivity.launchForReslut(activity!!,MainActivity.REQUEST_CODE_FOR_LOGIN_RESULT)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FOR_IMAGE->{
                    val file = data!!.getSerializableExtra("image") as File
                    selectImg!!.ext_loadCircleImage(file?.path)
                    SPUtils.put(activity!!,"pathPhoto", file?.path!!)
                    imgRecommendSelect1!!.visibility = View.GONE
                    imgRecommendSelect2!!.visibility = View.GONE
                    imgRecommendSelect3!!.visibility = View.GONE
                    imgRecommendSelect4!!.visibility = View.GONE
                    imgRecommendSelect5!!.visibility = View.GONE
                    selectPhotoGo!!.visibility = View.VISIBLE
                }
            }
        }
    }
}


