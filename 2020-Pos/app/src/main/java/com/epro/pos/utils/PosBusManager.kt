package com.epro.pos.utils

import android.text.TextUtils
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mike.baselib.utils.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PosBusManager {

    companion object {
        fun setUser(user: User?) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.USER_JSON, if (user == null) "" else Gson().toJson(user))
        }

        fun getUser(): User? {
            var user_json: String = SPUtils.get(AppContext.getInstance().context, SPConstant.USER_JSON, "") as String
            if (TextUtils.isEmpty(user_json)) {
                return null
            }
            return Gson().fromJson(user_json, User::class.java)
        }

        fun getDevCheckId(): Int {
            var id = 0
            val dev = AppBusManager.getDev()
            when (dev) {
                Constants.DV_TEST -> id = R.id.rbTest
                Constants.DV_PRE_RELEASE -> id = R.id.rbPreRelease
                Constants.DV_RELEASE -> id = R.id.rbRelease
            }
            return id
        }

        fun setShopId(shopId: String) {
            SPUtils.put(AppContext.getInstance().context, PosConst.KEY_SHOP_ID, shopId)
        }

        fun getShopId(): String {
            return SPUtils.get(AppContext.getInstance().context, PosConst.KEY_SHOP_ID, "") as String
        }

        fun setRefund(isRefund: Boolean) {
            SPUtils.put(AppContext.getInstance().context, PosConst.KEY_IS_REFUND, isRefund)
        }

        fun isRefund(): Boolean {
            return SPUtils.get(AppContext.getInstance().context, PosConst.KEY_IS_REFUND, false) as Boolean
        }

        fun setCartProducts(cartProducts: ArrayList<CartProduct>?) {
            if (cartProducts == null) {
                SPUtils.put(AppContext.getInstance().context, if (isRefund()) PosConst.KEY_REFUND_CART_PRODUCTS else PosConst.KEY_CART_PRODUCTS, "")
                return
            }
            val map = HashMap<String, ArrayList<CartProduct>>()
            map.put(getShopId(), cartProducts)
            SPUtils.put(AppContext.getInstance().context, if (isRefund()) PosConst.KEY_REFUND_CART_PRODUCTS else PosConst.KEY_CART_PRODUCTS, AppBusManager.toJson(map)!!)
        }

        /**
         * 插入购物车数据，插到第一个
         */
        fun setOneCartProduct(cartProduct: CartProduct): Int {
            return setOneCartProduct(cartProduct, true)
        }

        /**
         * 保存或者更新一个购物车商品数据
         */
        fun setOneCartProduct(cartProduct: CartProduct, isAddToFirst: Boolean): Int {
            val cartProducts = getCartProducts()
            var position = -1
            for (i in cartProducts.indices) {
                if (cartProducts[i].product.productId == cartProduct.product.productId) {
                    if (cartProduct.num == 1 && isAddToFirst) {//这里表示由商品列表添加的购物车商品数据
                        cartProduct.num = cartProduct.num + cartProducts[i].num
                        if(cartProduct.num>PosConst.CART_MAX_NUM){
                            cartProduct.num=PosConst.CART_MAX_NUM
                        }
                    }else if(cartProduct.num == -1 && isAddToFirst){ //退
                        cartProduct.num = cartProduct.num + cartProducts[i].num
                        if(cartProduct.num<-PosConst.CART_MAX_NUM){
                            cartProduct.num=-PosConst.CART_MAX_NUM
                        }
                    }
                    cartProducts.removeAt(i)
                    position = i
                    break
                }
            }
            cartProducts.add(if (isAddToFirst || position < 0) 0 else position, cartProduct)
            setCartProducts(cartProducts)
            return position
        }

        /**
         * 删除一条购物车商品数据
         */
        fun deleteOneCartProduct(cartProduct: CartProduct): Int {
            val cartProducts = getCartProducts()
            var position = -1
            for (i in cartProducts.indices) {
                if (cartProducts[i].product.productId == cartProduct.product.productId) {
                    cartProducts.removeAt(i)
                    position = i
                    break
                }
            }
            setCartProducts(cartProducts)
            return position
        }

        /**
         * 获取购物车商品数据列表
         */
        fun getCartProducts(): ArrayList<CartProduct> {
            val json = SPUtils.get(AppContext.getInstance().context, if (isRefund()) PosConst.KEY_REFUND_CART_PRODUCTS else PosConst.KEY_CART_PRODUCTS, "") as String
            if (TextUtils.isEmpty(json)) {
                return ArrayList()
            }
            val type = object : TypeToken<HashMap<String, ArrayList<CartProduct>>>() {}.type
            val map = AppBusManager.fromJson<HashMap<String, ArrayList<CartProduct>>>(json, type)!!
            return map.get(getShopId()) ?: return ArrayList()
        }

        /**
         * 清除购物车商品数据
         */
        fun clearCartProducts() {
            setCartProducts(null)
        }

        fun shopIdChange(shopId: String) {
            if (getShopId() != shopId) {
                setShopId(shopId)
                clearCartProducts()
                setPendOrders(null)
            }
        }

        fun setPendOrders(pendOrders: ArrayList<PendOrder>?) {
            if (pendOrders == null) {
                SPUtils.put(AppContext.getInstance().context, PosConst.KEY_PEND_ORDERS, "")
                return
            }
            val map = HashMap<String, ArrayList<PendOrder>>()
            map.put(getShopId(), pendOrders)
            SPUtils.put(AppContext.getInstance().context, PosConst.KEY_PEND_ORDERS, AppBusManager.toJson(map)!!)
        }

        fun setOnePendOrder(pendOrder: PendOrder): Int {
            return setOnePendOrder(pendOrder, true)
        }

        fun updatePendOrder(cartProduct: CartProduct, pendOrder: PendOrder): Int {
            val cartProducts = pendOrder.cartProducts
            var position = -1
            for (i in cartProducts.indices) {
                if (cartProducts[i].product.productId == cartProduct.product.productId) {
                    cartProducts.removeAt(i)
                    position = i
                    break
                }
            }
            if (position >= 0) {
                cartProducts.add(position, cartProduct)
            }
            return setOnePendOrder(pendOrder, false)
        }

        fun setOnePendOrder(pendOrder: PendOrder, isAddToFirst: Boolean): Int {
            val pendOrders = getPendOrders()
            var position = -1
            for (i in pendOrders.indices) {
                if (pendOrders[i].id == pendOrder.id) {
                    pendOrders.removeAt(i)
                    position = i
                    break
                }
            }
            pendOrders.add(if (isAddToFirst || position < 0) 0 else position, pendOrder)
            setPendOrders(pendOrders)
            return position
        }

        fun deleteOnePendOrder(pendOrder: PendOrder): Int {
            val pendOrders = getPendOrders()
            var position = -1
            for (i in pendOrders.indices) {
                if (pendOrders[i].id == pendOrder.id) {
                    pendOrders.removeAt(i)
                    position = i
                    break
                }
            }
            setPendOrders(pendOrders)
            return position
        }

        fun getPendOrders(): ArrayList<PendOrder> {
            val json = SPUtils.get(AppContext.getInstance().context, PosConst.KEY_PEND_ORDERS, "") as String
            if (TextUtils.isEmpty(json)) {
                return ArrayList()
            }
            val type = object : TypeToken<HashMap<String, ArrayList<PendOrder>>>() {}.type
            val map = AppBusManager.fromJson<HashMap<String, ArrayList<PendOrder>>>(json, type)!!
            return map.get(getShopId()) ?: return ArrayList()
        }


        fun setShopProductCategorys(categorys: ArrayList<ProductCategory>?) {
            if (categorys == null) {
                SPUtils.put(AppContext.getInstance().context, PosConst.KEY_SHOP_PRODUCT_CATEGORYS, "")
                return
            }
            val map = HashMap<String, ArrayList<ProductCategory>>()
            map.put(getShopId(), categorys)
            SPUtils.put(AppContext.getInstance().context, PosConst.KEY_SHOP_PRODUCT_CATEGORYS, AppBusManager.toJson(map)!!)
        }

        fun getShopProductCategorys(): ArrayList<ProductCategory> {
            val json = SPUtils.get(AppContext.getInstance().context, PosConst.KEY_SHOP_PRODUCT_CATEGORYS, "") as String
            if (TextUtils.isEmpty(json)) {
                return ArrayList()
            }
            val type = object : TypeToken<HashMap<String, ArrayList<ProductCategory>>>() {}.type
            val map = AppBusManager.fromJson<HashMap<String, ArrayList<ProductCategory>>>(json, type)!!
            return map.get(getShopId()) ?: return ArrayList()
        }

        fun getSearchProducts(keyword: String, categorys: ArrayList<ProductCategory>): ArrayList<Product> {
            val products = ArrayList<Product>()
            // val categorys = getShopProductCategorys()
            for (c in categorys) {
                for (p in c.goodsProductDetailList) {
                    if (p.productBarCode.toUpperCase().contains(keyword.toUpperCase())
                            || p.goodsName.toUpperCase().contains(keyword.toUpperCase())) {
                        products.add(p)
                    }
                }
            }
            return products
        }

        fun searchProductWithBarcode(barCode: String, categorys: ArrayList<ProductCategory>): Product? {
            for (c in categorys) {
                for (p in c.goodsProductDetailList) {
                    if (p.productBarCode.equals(barCode)) {
                        return p
                    }
                }
            }
            return null
        }

        // Pos付款方式付款方式(1.现金 2.支付宝 3.微信 )
        fun getPosPayModeText(mode: Int): String {
            return switchText(mode - 1, R.array.pos_pay_type)
        }

        // 付款方式付款方式(1.现金 2.支付宝 3.paypal 4.微信 5.信用卡 6.银行卡)
        fun getPayModeText(mode: Int): String {
            return switchText(mode, R.array.filter_pay_type)
        }

        //交易类型(1.线上 2.线下)
        fun getTradeTypeText(type: Int): String {
            return switchText(type, R.array.filter_trade_type)
        }

        //订单类型(1.销售 2.退货)
        fun getOrderTypeText(type: Int): String {
            return switchText(type, R.array.filter_order_type)
        }

        // 订单状态(( 1.未支付 2.用户已取消3.待配送 4.待自提 5.配送中  6.交易完成  7.店家已取消)
        fun getOrderStatusText(mode: Int): String {
            return switchText(mode, R.array.filter_order_status)
        }

        fun getOrderStatusShowText(mode: Int): String {
            return switchText(mode-2, R.array.filter_order_status_show)
        }

        fun getOrderStatusTextColor(mode: Int): Int {
            when (mode) {
                PosConst.ONLINE_ORDER_STATUS_NOPAY -> {
                    return R.color.mainColor
                }
                PosConst.ONLINE_ORDER_STATUS_USER_CANCEL -> {
                    return R.color.mainColor
                }
                PosConst.ONLINE_ORDER_STATUS_WAIT_DISTRIBUTION -> {
                    return R.color.yellow_ffbe00
                }
                PosConst.ONLINE_ORDER_STATUS_WAIT_SELFTAKE -> {
                    return R.color.yellow_ffbe00
                }
                PosConst.ONLINE_ORDER_STATUS_DISTRIBUTION -> {
                    return R.color.yellow_ffbe00
                }
                PosConst.ONLINE_ORDER_STATUS_SUCCESS -> {
                    return R.color.prompt_text_pass
                }
                PosConst.ONLINE_ORDER_STATUS_CANCEL -> {
                    return R.color.mainColor
                }
            }
            return R.color.mainTextColor
        }

        //退款状态  1.退款中 2.退款成功 3.退款失败
        fun getRefundText(mode: Int): String {
            return switchText(mode, R.array.filter_refund_status)
        }

        fun getRefundTextColor(mode: Int): Int {
            when (mode) {
                PosConst.REFUND_ORDER_STATUS_DOING -> {
                    return R.color.yellow_ffbe00
                }
                PosConst.REFUND_ORDER_STATUS_SUCCESS -> {
                    return R.color.prompt_text_pass
                }
                PosConst.REFUND_ORDER_STATUS_FAILED -> {
                    return R.color.mainColor
                }
            }
            return R.color.mainTextColor
        }

        //<!--//商品类型(1 普通商品 0 组合商品)-->
        fun getGoodsTypeText(type: Int): String {
            return switchText(type, R.array.filter_goods_type_attr)
        }

        //商品状态(1.已入库 0.未入库
        fun getGoodsStatusText(type: Int): String {
            return switchText(type + 1, R.array.filter_goods_stock_status)
        }

        //盘点状态(1.已确认 0.未确认)
        fun getStockTakingText(type: Int): String {
            return switchText(type + 1, R.array.filter_stock_check_status)
        }

        // 出入库来源
        fun getStockSourceText(mode: Int): String {
            return switchText(mode, R.array.filter_stock_outin_source)
        }

        // !--配送方式(0自提 1配送)-->
        fun getDistributionTypeText(mode: Int): String {
            return switchText(mode, R.array.distribution_type)
        }

        // 0今日 1近一周 2近一月
        fun getSalesTimeText(mode: Int): String {
            return switchText(mode, R.array.sales_time_type)
        }

        // 0转化入库 1新增入库
        fun getStockAddTypeText(mode: Int): String {
            return switchText(mode, R.array.stock_add_type)
        }

        fun switchText(type: Int, res: Int): String {
            if (type < 0) {
                return ""
            }
            val arrays = AppContext.getInstance().context.resources.getStringArray(res)
            if (type > arrays.size - 1) {
                return ""
            }
            return arrays[type]
        }


        fun getTestData(list: List<String>): ArrayList<Item> {
            val items = ArrayList<Item>()
            for (i in list.indices) {
                val item = Item(i, list[i])
                items.add(item)
            }
            return items
        }

        /**
         * 获取已上架商品product 最高网店价和最低网店价
         * @param goods
         * @return
         */
        fun getUpperShelfGoodsMinMaxPrice(goods: UpperShelfGoods): String? {
            if (goods.productStatusResponseList.isEmpty()) {
                ToastUtil.showToast("goods info list is null")
                return null
            }
            val maxPriceProduct = Collections.max<UpperShelfGoods.Product>(goods.productStatusResponseList) { product1, product2 -> if (product1.onlineSalesPrice.toDouble() - product2.onlineSalesPrice.toDouble() > 0) 1 else -1 }
            val minPriceProduct = Collections.min<UpperShelfGoods.Product>(goods.productStatusResponseList) { product1, product2 -> if (product1.onlineSalesPrice.toDouble() - product2.onlineSalesPrice.toDouble() > 0) 1 else -1 }
            if (maxPriceProduct == null) {
                ToastUtil.showToast("max Product is null")
                return null
            }
            if (minPriceProduct == null) {
                ToastUtil.showToast("min Product is null")
                return null
            }
            return if (maxPriceProduct.onlineSalesPrice == minPriceProduct.onlineSalesPrice)
                maxPriceProduct.onlineSalesPrice else minPriceProduct.onlineSalesPrice + "-" + maxPriceProduct.onlineSalesPrice
        }


        fun getPayModeIcon(mode: Int): Int {
            when (mode) {
                PosConst.PAY_MODE_ZFB -> {
                    return R.mipmap.icon_pay_zfb
                }
                PosConst.PAY_MODE_PAYPAL -> {
                    return R.mipmap.icon_pay_paypal
                }
                PosConst.PAY_MODE_WX -> {
                    return R.mipmap.icon_pay_weixin
                }
            }
            return R.mipmap.icon_pay_zfb
        }

        fun getTradeTypeIcon(mode: Int): Int {
            when (mode) {
                PosConst.TRADE_TYPE_ONLINE -> {
                    return R.mipmap.icon_online_order
                }
                PosConst.TRADE_TYPE_OUTLINE -> {
                    return R.mipmap.icon_offline_order
                }
            }
            return R.mipmap.icon_online_order
        }

        /**
         * 线下商品活动
         */
       fun getProductActivity(cartGoods: Product): GoodsActivity? {
            if (!TextUtils.isEmpty(cartGoods.offlineActivityInfo)) {
                val type = object : TypeToken<ArrayList<GoodsActivity>>() {}.type
                val activityList = AppBusManager.fromJson<ArrayList<GoodsActivity>>(cartGoods.offlineActivityInfo, type)
                if (activityList != null && activityList.isNotEmpty()) {
                    for (a in activityList) {
                        var now = System.currentTimeMillis()
                        if(isGetSystemTimeSuccess()){
                            now += getTimeDifferenceValue().toLong()
                        }
                        if (a.status == "1" && DateUtils.dateToStamp(a.startTime + " 00:00:00") <= now && now <= DateUtils.dateToStamp(a.endTime + " 23:59:59")) {
                            return a
                        }
                    }
                }
            }
            return null
        }

        /**
         * 获取成交价格
         */
        fun getSalePrice(cartProduct: CartProduct):String{
            var salePrice=0.00.toString()
            val a= getProductActivity(cartProduct.product)
            if(a==null){
                if(cartProduct.discount==1F){
                    salePrice=cartProduct.product.retailPrice
                }else{
                    salePrice=(cartProduct.product.retailPrice.toDouble()*cartProduct.discount.ext_formatDiscount()).ext_formatAmount()
                }
            }else{
                salePrice=a.discountPrice
            }
            return salePrice
        }

        fun setTimeDifferenceValue(value: Long) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.DIFFERENCE_VALUE, value.toString())
        }

        fun getTimeDifferenceValue(): String {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.DIFFERENCE_VALUE, "#") as String
        }

        fun isGetSystemTimeSuccess(): Boolean {
            return getTimeDifferenceValue().ext_isPureNumber_orDecimal()
        }

    }
}