package com.mike.baselib.activity


import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.mike.baselib.R
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.mike.baselib.listener.ShopInfoChange
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_base_titlebar.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


abstract class BaseTitleBarActivity<V: IBaseView,P: IPresenter<V>>: BaseActivity<V, P>(){
    override fun layoutId(): Int {
       return R.layout.activity_base_titlebar
    }

    override fun initView() {
        flContent.removeAllViews()
        if(customTitleBar()==0){
            rlLeft.setOnClickListener(View.OnClickListener {
                finish()
            })}
        else{
            rlTitleBar.removeAllViews()
            rlTitleBar.addView(LayoutInflater.from(this).inflate(customTitleBar(), rlTitleBar,false))
        }
        val customContentView=LayoutInflater.from(this).inflate(layoutCustomContentId(),null)
        val contentView=customContentView.findViewById<FrameLayout>(R.id.flContent)
        if(contentView!=null){
            contentView.removeAllViews()
            contentView.addView(LayoutInflater.from(this).inflate(layoutContentId(),null))
        }
        flContent.addView(customContentView)
    }

    open fun customTitleBar():Int{
        return 0
    }

    abstract fun layoutCustomContentId(): Int

    abstract fun layoutContentId(): Int

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        ToastUtil.showToast(errorMsg)
    }

    fun getTitleBar():RelativeLayout{
        return rlTitleBar
    }

    /**
     * 中间标题
     */
    fun getTitleView():TextView{
        return tvTitle
    }

    /**
     * 右边标题
     */
    fun getRightView():TextView{
        return tvRight
    }

    fun setRightImageResource(res:Int){
        tvRight.visibility= View.VISIBLE
        val rigntDrawable = resources?.getDrawable(res)
        rigntDrawable?.bounds = Rect(0, 0, rigntDrawable?.minimumWidth!!, rigntDrawable?.minimumHeight!!)
        tvRight.setCompoundDrawables(rigntDrawable, null, null, null)
    }

    /**
     * 左边标题
     */
    fun getLeftView():RelativeLayout{
        return rlLeft
    }

    /**
     * 左边文字
     */
    fun getLeftTitleView():TextView{
        return tvLeftTitle
    }

    /**
     * 左边返回键
     */

    fun getLeftBackView():ImageView{
        return ivLeft
    }


    fun getRootView():LinearLayout{
        return this.findViewById(R.id.lLRootView)
    }
    fun setHaveBackView(isHave: Boolean) {
        if (isHave) {
            rlLeft.visibility = View.VISIBLE
            rlLogo.visibility = View.VISIBLE
            llLogo.visibility = View.GONE
        } else {
            rlLeft.visibility = View.GONE
            rlLogo.visibility = View.GONE
            llLogo.visibility = View.VISIBLE
            tvShopName.text = AppBusManager.getShopName()
        }
    }

    fun setBackActivityView(){
        rlLeft.visibility = View.VISIBLE
        rlLogo.visibility = View.GONE
        llLogo.visibility = View.GONE
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun shopInfoChange(event:ShopInfoChange){
        tvShopName.text = AppBusManager.getShopName()
    }
}


