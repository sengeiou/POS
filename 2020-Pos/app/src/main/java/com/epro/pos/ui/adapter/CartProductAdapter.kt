package com.epro.pos.ui.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.TextureView
import android.view.View
import android.widget.*
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.CartProduct
import com.epro.pos.utils.PosBusManager
import com.epro.pos.utils.PosConst
import com.mike.baselib.listener.SoftKeyBoardListener
import com.mike.baselib.utils.*
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import kotlinx.android.synthetic.main.popup_checkout.*
import kotlin.math.absoluteValue

class CartProductAdapter(mContext: Context, list: ArrayList<CartProduct>, layoutId: Int = R.layout.item_cart_product) :
        CommonAdapter<CartProduct>(mContext, list, layoutId, false) {

    interface OnItemClickListener {
        fun onClick(item: CartProduct)
    }

    interface OnBuyNumChangeListener {
        fun onBuyNumChange(buyNum: Int, cartProduct: CartProduct)
    }

    interface OnDiscountChangeListener {
        fun onDiscountChange(discount: Float, cartProduct: CartProduct)
    }

    interface OnItemLongClickListener {
        fun onLongClick(item: CartProduct)
    }

    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null
    var onBuyNumChangeListener: OnBuyNumChangeListener? = null
    var onDiscountChangeListener: OnDiscountChangeListener? = null
    override fun bindData(holder: ViewHolder, data: CartProduct, position: Int) {
        holder.setText(R.id.tvGoodsName, data.product.goodsName)
        holder.setText(R.id.tvSpec, data.product.specificationsValueNames)
        holder.setText(R.id.tvBarcode, data.product.productBarCode)
        holder.setText(R.id.tvPrice, data.product.retailPrice.ext_formatAmount())
        val originalTotal = data.product.retailPrice.toDouble() * data.num
        holder.setText(R.id.tvOriginalTotal, originalTotal.ext_formatAmount())
        val tvOriginalTotal = holder.getView<TextView>(R.id.tvOriginalTotal)
        tvOriginalTotal.visibility = if (data.discount == 1F) View.GONE else View.VISIBLE
        tvOriginalTotal.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG //中划线
        val total = (data.product.retailPrice.toDouble() * data.discount.ext_formatDiscount()).ext_formatAmount().toDouble() * data.num
        //购买数量
        val etNum = holder.getView<EditText>(R.id.etNum)
        holder.setText(R.id.tvSubtotal, total.ext_formatAmount())
        etNum.setText(data.num.toString())
        if (etNum.tag != null) {
            val etNumTag = etNum.getTag() as BuyNumChangerWatcher
            etNum.removeTextChangedListener(etNumTag)
        }
        val buyNumChangerWatcher = BuyNumChangerWatcher(holder, position)
        etNum.addTextChangedListener(buyNumChangerWatcher)
        etNum.tag = buyNumChangerWatcher
        val etDiscount = holder.getView<EditText>(R.id.etDiscount)
        etDiscount.isEnabled=AppBusManager.isBargain()//有没打折权限
        //折扣
        etDiscount.setText((data.discount * 100).toInt().toString())
        if (etDiscount.tag != null) {
            val etDiscountTag = etDiscount.getTag() as DiscountChangerWatcher
            etDiscount.removeTextChangedListener(etDiscountTag)
        }
        val discountChangerWatcher = DiscountChangerWatcher(holder, position)
        etDiscount.addTextChangedListener(discountChangerWatcher)
        etDiscount.tag = discountChangerWatcher

        val tvLess = holder.getView<ImageView>(R.id.tvLess)
        val tvAdd = holder.getView<ImageView>(R.id.tvClickLeft)
        tvLess.setOnClickListener(View.OnClickListener {
            if(!etNum.text.toString().ext_isPureNumber_orDecimal()){
                return@OnClickListener
            }
            var num = Integer.valueOf(etNum.text.toString().trim())
            if (1 == num.absoluteValue) {
                ToastUtil.showToast(mContext.getString(R.string.quantity_cannot_be_zero))
                return@OnClickListener
            }
            if (PosBusManager.isRefund()) {
                num++
            } else {
                num--
            }
            etNum.setText(num.toString())
        })
        tvAdd.setOnClickListener(View.OnClickListener {
            if(!etNum.text.toString().ext_isPureNumber_orDecimal()){
                return@OnClickListener
            }
            var num = Integer.valueOf(etNum.text.toString().trim())
            logTools.d(num)
            if (PosBusManager.isRefund()) {
                num--
            } else {
                num++
            }
            logTools.d(num)
            etNum.setText(num.toString())
            if (PosConst.CART_MAX_NUM<num.absoluteValue) {
                ToastUtil.showToast(mContext.getString(R.string.exceed_the_limit))
                return@OnClickListener
            }
        })
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })

        holder.setOnItemLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener!!.onLongClick(data)
                }
                return true
            }
        })

        SoftKeyBoardListener.setListener(mContext as Activity, object : SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
            }

            override fun keyBoardHide(height: Int) {
                var p0 = etNum.text.toString()
                if (!(p0.ext_isPureNumber_orDecimal())) {
                    holder?.setText(R.id.etNum, (if (PosBusManager.isRefund()) -1 else 1).toString())
                }
                p0 = etDiscount.text.toString()
                if (!(p0.ext_isPureNumber_orDecimal())) {
                    holder?.setText(R.id.etDiscount, 100.toString())
                }
                etNum.clearFocus()
                etDiscount.clearFocus()
            }

        })

        //限时折扣活动
        //val llActivity = holder.getView<LinearLayout>(R.id.llActivity)
        //val tvOriginalPrice = holder.getView<TextView>(R.id.tvOriginalPrice)
        val a = PosBusManager.getProductActivity(data.product)
        if(a==null){
           // llActivity.visibility=View.GONE
           // etDiscount.isEnabled=true
        }else{
           // llActivity.visibility=View.VISIBLE
            tvOriginalTotal.visibility=View.VISIBLE
            holder.setText(R.id.tvPrice,a.discountPrice.ext_formatAmount())
            data.discount=(a.discountPrice.toDouble()/data.product.retailPrice.toDouble()).toFloat()
            val total = a.discountPrice.toDouble() * data.num
            holder.setText(R.id.tvSubtotal,total.ext_formatAmount())
            etDiscount.setText((data.discount*100).toInt().toString())
            etDiscount.isEnabled=false
            //tvOriginalPrice.ext_setTextWithHorizontalLine(data.product.retailPrice.ext_formatAmount())
        }

    }

    inner class BuyNumChangerWatcher(holder: ViewHolder, position: Int) : TextWatcher {
        var holder: ViewHolder? = null
        var cartProduct: CartProduct? = null

        init {
            this.holder = holder
            this.cartProduct = mData[position]
        }

        override fun afterTextChanged(p0: Editable?) {
            val etNum=holder?.getView<EditText>(R.id.etNum)!!
            etNum.setSelection(p0.toString().length)//将光标移至文字末尾
            var buyNum = 0
            if (p0.toString().ext_isPureNumber_orDecimal()) {//输入为数字
                if(p0.toString().toInt() != 0){
                    if (p0.toString().toInt()  in -PosConst.CART_MAX_NUM..PosConst.CART_MAX_NUM) {
                        buyNum = p0.toString().toInt()
                        if (buyNum > 0 && PosBusManager.isRefund()) {
                            holder?.setText(R.id.etNum, (-buyNum).toString())
                        }
                        logTools.d(p0.toString().toInt() / 100F)
                    } else {
                        buyNum = if (PosBusManager.isRefund()) -PosConst.CART_MAX_NUM else PosConst.CART_MAX_NUM
                        holder?.setText(R.id.etNum, buyNum.toString())
                        logTools.d("do 9999")
                    }
                }else{ //输入为零的处理
                    buyNum = if (PosBusManager.isRefund()) -1 else 1
                    holder?.setText(R.id.etNum, buyNum.toString())
                }
            } else {
                buyNum = if (PosBusManager.isRefund()) -1 else 1
                if(!TextUtils.isEmpty(p0.toString())&&!p0.toString().ext_isPureNumber_orDecimal()&&p0.toString()!="-"&&PosBusManager.isRefund()){ //输入为 例:5-1 的处理
                    holder?.setText(R.id.etNum, buyNum.toString())
                }
            }
            if (buyNum > 0 && PosBusManager.isRefund()) {
                buyNum = -buyNum
            }
            if (buyNum != cartProduct?.num) {
                cartProduct?.num = buyNum
                val originalTotal = cartProduct?.product?.retailPrice!!.toDouble() * cartProduct!!.num
                val total = (cartProduct?.product?.retailPrice!!.toDouble() *
                        (cartProduct?.discount!!.ext_formatDiscount())).ext_formatAmount().toDouble() * cartProduct!!.num
                holder?.setText(R.id.tvOriginalTotal, originalTotal.ext_formatAmount())
                holder?.setText(R.id.tvSubtotal, total.ext_formatAmount())
                onBuyNumChangeListener?.onBuyNumChange(buyNum, cartProduct!!)
                logTools.d(cartProduct?.product?.productId)
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }


    inner class DiscountChangerWatcher(holder: ViewHolder, position: Int) : TextWatcher {
        var holder: ViewHolder? = null
        var cartProduct: CartProduct? = null

        init {
            this.holder = holder
            this.cartProduct = mData[position]
        }

        override fun afterTextChanged(p0: Editable?) {
            var discount = 0F
            if (p0.toString().ext_isPureNumber_orDecimal()) {
                if (p0.toString().toInt() / 100F in 0.0..1.0) {
                    discount = p0.toString().toInt() / 100F
                } else {// 输入大于100的处理
                    discount = 1F
                    holder?.setText(R.id.etDiscount, 100.toString())
                }
            } else { //输入不为数字
                discount = 1.toFloat()
            }
            if (discount != cartProduct?.discount) {
                cartProduct?.discount = discount
                val originalTotal = cartProduct?.product?.retailPrice!!.toDouble() * cartProduct!!.num
                val tvOriginalTotal = holder!!.getView<TextView>(R.id.tvOriginalTotal)
                tvOriginalTotal.visibility = if (discount == 1F) View.GONE else View.VISIBLE
                tvOriginalTotal.text = originalTotal.ext_formatAmount()
                val total = (cartProduct?.product?.retailPrice!!.toDouble() *
                        (cartProduct?.discount!!.ext_formatDiscount())).ext_formatAmount().toDouble() * cartProduct!!.num
                holder?.setText(R.id.tvSubtotal, total.ext_formatAmount())
                onDiscountChangeListener?.onDiscountChange(discount, cartProduct!!)
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

}