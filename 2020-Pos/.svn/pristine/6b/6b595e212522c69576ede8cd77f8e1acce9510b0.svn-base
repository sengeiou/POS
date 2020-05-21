package com.epro.pos.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.epro.pos.R
import com.epro.pos.mvp.contract.DataBoardContract
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.mvp.model.bean.SaleItem
import com.epro.pos.mvp.presenter.DataBoardPresenter
import com.epro.pos.ui.adapter.*
import com.epro.pos.ui.view.OrderMarkerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.mike.baselib.utils.DateUtils
import com.mike.baselib.utils.DisplayManager
import kotlinx.android.synthetic.main.fragment_data_board.*
import java.util.*
import kotlin.collections.ArrayList

class DataBoardFragment : BaseTitleBarCustomFragment<DataBoardContract.View, DataBoardPresenter>(), DataBoardContract.View {

    companion object {
        const val TAG = "DataBoardFragment"
        fun newInstance(str: String): DataBoardFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = DataBoardFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): DataBoardFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): DataBoardPresenter {
        return DataBoardPresenter()
    }

    override fun layoutContentId(): Int {
        return R.layout.fragment_data_board
    }

    override fun lazyLoad() {

    }

    override fun initView() {
        super.initView()
        getLeftView().visibility = View.GONE
        getTitleView().text = getString(R.string.data_board)
    }

    override fun initData() {
        rvSale.layoutManager = GridLayoutManager(activity!!, 4)
        rvSale.adapter = SaleStatisticsAdapter1(activity!!, ArrayList())
        var list = ArrayList<Item>()
        var titles = arrayOf(getString(R.string.to_be_listed), getString(R.string.it_has_been_added_to), getString(R.string.stock_alert), getString(R.string.all_products))
        var icons = arrayOf(R.mipmap.icon_goods_show1, R.mipmap.icon_goods_show2, R.mipmap.icon_goods_show3, R.mipmap.icon_goods_show4)
        for (i in titles.indices) {
            list.add(Item(icons[i], titles[i]))
        }
        rvGoods.layoutManager = GridLayoutManager(activity!!, 4)
        rvGoods.adapter = SaleStatisticsAdapter2(activity!!, list)

        list = ArrayList()
        titles = arrayOf(getString(R.string.added_today), getString(R.string.added_yesterday), getString(R.string.new_this_month))
        icons = arrayOf(R.mipmap.icon_online_user_show1, R.mipmap.icon_online_user_show2, R.mipmap.icon_online_user_show3)
        for (i in titles.indices) {
            list.add(Item(icons[i], titles[i]))
        }
        rvUsers.layoutManager = GridLayoutManager(activity!!, 3)
        rvUsers.adapter = SaleStatisticsAdapter2(activity!!, list)


        val titles2 = arrayOf(getString(R.string.last_month), getString(R.string.last_week))
        val tags = arrayOf(R.drawable.shape_tag_radius2_green, R.drawable.shape_tag_radius2_orange)
        icons = arrayOf(R.mipmap.icon_sales_total, R.mipmap.icon_sales_number)
        var saleList = ArrayList<SaleItem>()
        titles = arrayOf(getString(R.string.total_orders_in_the_past_month), getString(R.string.orders_in_the_last_week))
        for (i in titles.indices) {
            val item = SaleItem("", titles[i], icons[i], tags[i])
            item.valueContent2(titles2[i])
            saleList.add(item)
        }
        rvOrderTrend.layoutManager = LinearLayoutManager(activity!!)
        rvOrderTrend.adapter = SaleStatisticsAdapter3(activity!!, saleList)

        saleList = ArrayList()
        titles = arrayOf(getString(R.string.total_sales_in_the_past_month), getString(R.string.total_sales_in_the_past_week))
        for (i in titles.indices) {
            val item = SaleItem("", titles[i], icons[i], tags[i])
            item.valueContent2(titles2[i])
            saleList.add(item)
        }
        rvSaleTrend.layoutManager = LinearLayoutManager(activity!!)
        rvSaleTrend.adapter = SaleStatisticsAdapter3(activity!!, saleList)
        val values = arrayListOf(42, 30, 18, 12, 36, 25, 50)
        initLineChart(lineChart, values)
        initLineChart(lineChart2, values)
    }

    override fun initListener() {
    }

    val lineColors = arrayListOf(R.color.blue_6399fe, R.color.pink_ff9292, R.color.green_5dcbb1)
    val valueList = arrayListOf(arrayListOf(42, 30, 18, 12, 36, 25, 50), arrayListOf(12, 15, 8, 10, 11, 15, 30), arrayListOf(30, 15, 10, 2, 25, 10, 20))
    fun getLineDataSets(): ArrayList<ILineDataSet> {
        val lines = ArrayList<ILineDataSet>()

        for (index in valueList.indices) {

            //设置数据
            val entries = java.util.ArrayList<Entry>()
            var i = 0
            while (i < valueList[index].size) {
                entries.add(Entry(i * 1f, valueList[index][i] * 1f))
                i++
            }
            //一个LineDataSet就是一条线
            val lineDataSet = LineDataSet(entries, "")

            lineDataSet.highLightColor = resources.getColor(R.color.gray_cccccc)
            if (index != 0) {
                lineDataSet.isHighlightEnabled = false
            }
            //线颜色
            lineDataSet.color = resources.getColor(lineColors[index])
            //线宽度
            lineDataSet.lineWidth = DisplayManager.dip2px(1F)!!.toFloat()
            //不显示圆点
            lineDataSet.setDrawCircles(true)
            lineDataSet.setCircleColor(resources.getColor(lineColors[index]))
            //线条平滑
            // lineDataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            //设置折线图填充
            //  lineDataSet.setDrawFilled(true);
            lines.add(lineDataSet)
        }

        return lines
    }

    /**
     * 初始化曲线图表
     *
     * @param list 数据集
     */
    private fun initLineChart(lineChart: LineChart, list: List<Int>) {
        //显示边界
        lineChart.setDrawBorders(false)
        val data = LineData(getLineDataSets())
        //无数据时显示的文字
        lineChart.setNoDataText(getString(R.string.no_data))
        //折线图不显示数值
        data.setDrawValues(false)
        //得到X轴
        val xAxis = lineChart.getXAxis()
        //设置X轴的位置（默认在上方)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        //设置X轴坐标之间的最小间隔
        xAxis.setGranularity(1f)
        //设置X轴的刻度数量，第二个参数为true,将会画出明确数量（带有小数点），但是可能值导致不均匀，默认（6，false）
        xAxis.setLabelCount(6, false)
        //设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMinimum(0f)
        xAxis.setAxisMaximum(list.size.toFloat())
        //不显示网格线
        xAxis.setDrawGridLines(false)
        xAxis.textSize = 12F
        xAxis.textColor = resources.getColor(R.color.secondaryTextColor)
        // 标签倾斜
        //  xAxis.setLabelRotationAngle(45f)
        //设置X轴值为字符串
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val v = value.toInt()
                var day = DateUtils.formatDate(System.currentTimeMillis() - (6 - v) * 24 * 60 * 60 * 1000, DateUtils.DATE_FORMAT3)
                day = day.split("-")[2]
                return day + getString(R.string.day)
            }
        }
        //得到Y轴
        val yAxis = lineChart.getAxisLeft()
        val rightYAxis = lineChart.getAxisRight()
        //设置Y轴是否显示
        rightYAxis.setEnabled(false) //右侧Y轴不显示
        //设置y轴坐标之间的最小间隔
        //不显示网格线
        yAxis.setDrawGridLines(false)
        //设置Y轴坐标之间的最小间隔
        yAxis.setGranularity(10f)
        //设置y轴的刻度数量
        //+2：最大值n就有n+1个刻度，在加上y轴多一个单位长度，为了好看，so+2
        yAxis.setLabelCount(Collections.max(list) + 2, false)
        yAxis.textSize = 12F
        yAxis.textColor = resources.getColor(R.color.secondaryTextColor)
        //设置从Y轴值
        yAxis.setAxisMinimum(0f)
        //+1:y轴多一个单位长度，为了好看
        yAxis.setAxisMaximum((Collections.max(list) + 1).toFloat())

        //y轴
        yAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val v = value.toInt()
                return v.toString()
            }
        }

        //图例：得到Lengend
        val legend = lineChart.getLegend()
        //隐藏Lengend
        legend.setEnabled(false)
        //隐藏描述
        val description = Description()
        description.setEnabled(false)
        lineChart.setDescription(description)
        //折线图点的标记
        val mv = OrderMarkerView(activity!!)
        lineChart.setMarker(mv)
        //设置数据
        lineChart.setData(data)
        lineChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                //toast(""+e?.x)
            }

        })
        //图标刷新
        lineChart.invalidate()
    }


}