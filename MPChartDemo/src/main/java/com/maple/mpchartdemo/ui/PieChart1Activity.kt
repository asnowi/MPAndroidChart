package com.maple.mpchartdemo.ui

import android.graphics.Color
import android.os.Bundle
import com.blankj.utilcode.util.TimeUtils
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.MPPointF
import com.maple.mpchartdemo.R
import com.maple.mpchartdemo.base.BaseActivity
import com.maple.mpchartdemo.utils.UIUtils
import kotlin.random.Random

class PieChart1Activity : BaseActivity() {

    override fun uiTitle(): String = getBundle()?.getString("title")?:""
    override fun onUILeft() { onFinish() }

    private var chart: PieChart? = null

    private val random: Random by lazy { Random.Default }

    private val dateFormat by lazy {
        TimeUtils.getSafeDateFormat("HH:mm:ss")
    }

    private val titles = mutableListOf<String>()
    private val colors = mutableListOf<Int>()
    private val entries = mutableListOf<PieEntry>().apply {
        titles.clear()
        this.clear()
        for (index in 1..5) {
            titles.add(TimeUtils.getNowString(dateFormat))
            val value = (Math.random() * 10 + 10 / 5).toFloat()
            val data = "数据${index}"
            val entry = PieEntry(value, data)
            this.add(entry)
            colors.add(Color.argb(255,random.nextInt(),random.nextInt(),random.nextInt()))
        }
    }
    override fun getLayoutId(): Int = R.layout.activity_pie_chart1

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        chart = findViewById(R.id.chart)
        chart?.apply {
            initChart(this)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        val json = UIUtils.getAssetsJson("lineChart1/data.json")
        setData(json)
    }

    private fun initChart(chart: PieChart) {
        // 描述信息
        val description = Description().apply {
            this.text = "图表描述"
            this.textColor = Color.RED
            this.yOffset = 10.0f
            this.isEnabled = false
        }
        chart.apply {
            this.description = description
            this.setNoDataText("暂无数据")
            //设置是否可以触摸
            this.setTouchEnabled(true)
            // 数据描述文本大小
            this.setEntryLabelTextSize(8.0f)
            // 不显示 label
            this.setDrawEntryLabels(true)
            // 数据描述文本颜色
            this.setEntryLabelColor(Color.GRAY)
            // 设置中心圆大小
//            this.holeRadius = 20.0f
            //示例隐藏
            // 不显示图例
            val legend = chart.legend
            legend.isEnabled = true
            // 设置legend显示位置
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            // 显示legend显示的排列方法
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            //是否绘制在图表里
            legend.setDrawInside(false)
            // legend显示形状
            legend.form = Legend.LegendForm.SQUARE
            legend.textSize = 10.0f
            legend.textColor = Color.BLACK
            // 向左偏移15dp，抵消y轴向右偏移的30dp
            this.extraLeftOffset = -15.0f
            // 向上偏移10dp 用来显示 legend
            this.extraTopOffset = 10.0f
            //动画失效可能原因 1.添加混淆文件 2.先调用invalidate方法刷新数据
            this.animateY(1000)
        }
    }
    private fun setData(json: String) {
        chart?.let { chart ->
            val pieDataSet: PieDataSet
            chart.centerText = "【饼图】"

            //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

            if(chart.data != null && chart.data.dataSetCount > 0) {
                // 刷新数据
                pieDataSet = chart.data.getDataSetByIndex(0) as PieDataSet
                pieDataSet.values = entries
                val data: PieData = PieData(pieDataSet)
                chart.data = data
                chart.data.notifyDataChanged()
                chart.notifyDataSetChanged()
            } else {
                // 设置数据1
                pieDataSet = PieDataSet(entries,"线111")
                pieDataSet.setDrawIcons(false)
                pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                pieDataSet.sliceSpace = 3.0f
                pieDataSet.iconsOffset = MPPointF(0.0f, 40.0f)
                pieDataSet.selectionShift = 5.0f
                pieDataSet.valueTextSize = 10.0f
                pieDataSet.valueTextColor = Color.BLACK
                pieDataSet.colors = colors
                val data: PieData = PieData(pieDataSet)
                chart.data = data
                chart.invalidate()
            }
        }
    }
}