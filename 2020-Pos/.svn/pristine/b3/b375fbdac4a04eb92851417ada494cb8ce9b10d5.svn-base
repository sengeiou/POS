package com.mike.baselib.fragment

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.mike.baselib.R
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.mike.baselib.listener.ShopInfoChange
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_base_titlebar.*
import kotlinx.android.synthetic.main.layout_titlebar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseTitleBarFragment<V : IBaseView, P : IPresenter<V>> : BaseLazyLoadFragment<V, P>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_titlebar
    }

    protected fun removeThis() {
        parentFragment?.childFragmentManager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
        AppUtils.Companion.closeKeyboard(activity!!)
    }

    override fun initView() {
        flContent.removeAllViews()
        if (customTitleBar() == 0) {
            rlLeft.setOnClickListener(View.OnClickListener {
                removeThis()
            })
        } else {
            rlTitleBar.removeAllViews()
            rlTitleBar.addView(LayoutInflater.from(activity).inflate(customTitleBar(), rlTitleBar, false))
        }
        val customContentView = LayoutInflater.from(activity).inflate(layoutCustomContentId(), null)
        val contentView = customContentView.findViewById<FrameLayout>(R.id.flContent)
        if (contentView != null) {
            contentView.removeAllViews()
            contentView.addView(LayoutInflater.from(activity).inflate(layoutContentId(), null))
        }
        flContent.addView(customContentView)
        setHaveBackView(false)
    }

    open fun customTitleBar(): Int {
        return 0
    }

    abstract fun layoutCustomContentId(): Int

    abstract fun layoutContentId(): Int


    /**
     * 中间标题
     */
    fun getTitleView(): TextView {
        return tvTitle
    }

    /**
     * 右边标题
     */
    fun getRightView(): TextView {
        return tvRight
    }

    fun setRightImageResource(res: Int) {
        val rigntDrawable = resources?.getDrawable(res)
        rigntDrawable?.bounds = Rect(0, 0, rigntDrawable?.minimumWidth!!, rigntDrawable?.minimumHeight!!)
        tvRight.setCompoundDrawables(null, rigntDrawable, null, null)
    }

    /**
     * 左边标题
     */
    fun getLeftView(): RelativeLayout {
        return rlLeft
    }

    /**
     * 左边文字
     */
    fun getLeftTitleView(): TextView {
        return tvLeftTitle
    }

    /**
     * 左边返回键
     */

    fun getLeftBackView(): ImageView {
        return ivLeft
    }

    fun getTitleBar(): RelativeLayout {
        return rlTitleBar
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

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun shopInfoChange(event: ShopInfoChange){
        tvShopName.text = AppBusManager.getShopName()
    }
}


