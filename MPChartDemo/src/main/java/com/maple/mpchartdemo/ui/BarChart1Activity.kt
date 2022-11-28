package com.maple.mpchartdemo.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.utils.Fill
import com.maple.mpchartdemo.R
import com.maple.mpchartdemo.base.BaseActivity
import com.maple.mpchartdemo.utils.UIUtils
import com.maple.mpchartdemo.widget.marker.LineChartMarkView


class BarChart1Activity : BaseActivity() {

    override fun uiTitle(): String = getBundle()?.getString("title")?:""
    override fun onUILeft() { onFinish() }

    private var chart: BarChart? = null
    private val weekList: List<String> = arrayListOf("周一","周二","周三","周四","周五","周六","周日")

    override fun getLayoutId(): Int = R.layout.activity_bar_chart1

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        chart = findViewById(R.id.chart)
        chart?.apply {
            initChart(this)
        }
    }

    private fun initChart(chart: BarChart) {
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
            //设置阴影
            this.setDrawBarShadow(false)
            //设置柱状图上数值
            this.setDrawValueAboveBar(true)
            //设置最大显示数量
            this.setMaxVisibleValueCount(60)
            this.setPinchZoom(true)
            //是否显示表格颜色
            this.setDrawGridBackground(false)
            //设置是否可以缩放
            this.setScaleEnabled(true)
            //设置是否可以触摸
            this.setTouchEnabled(true)
            //设置是否可以拖拽
            this.isDragEnabled = false
            //Y轴左侧隐藏
            this.axisRight.isEnabled = false
            //Y轴右侧隐藏
            this.axisLeft.isEnabled = true
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

            this.setFitBars(true)
            //动画失效可能原因 1.添加混淆文件 2.先调用invalidate方法刷新数据
            this.animateY(2000)

            this.xAxis.valueFormatter = object :IAxisValueFormatter{
                override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                    return weekList.get(value.toInt())
                }
            }
            setCustomMarkerView(this@BarChart1Activity)
        }

        val xAxis: XAxis = chart.xAxis
        xAxis.apply {
            //下标位置
            this.setPosition(XAxis.XAxisPosition.BOTTOM)
            //设置旋转角度
///        xl.setLabelRotationAngle(-45f);
            // 设置 x 坐标轴 宽度
 ///        xl.setAxisLineWidth(2f);
            // 设置 x轴 的刻度数量
            this.setLabelCount(weekList.size)

            this.textColor = Color.GRAY
            this.textSize = 10.0f
            this.gridColor = Color.parseColor("#30888888")
            // 设置x轴数据偏移量
            this.yOffset = 4.0f

            //是否显示轴线
            this.setDrawAxisLine(true)
            //是否显示网格线
            this.setDrawGridLines(false)
            //下标间隔
            this.granularity = 1.0f
            //文字居中
            this.setCenterAxisLabels(false)
            //最小值（小于文字宽度一半最佳）
            this.axisMinimum = -0.3f
        }

        // YAxis
        val yAxis: YAxis = chart.axisLeft
        yAxis.apply {
            // 不显示y轴
            this.setDrawAxisLine(false)
            // 设置y轴数据的位置
            this.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            // 不从y轴发出横向直线
            this.setDrawGridLines(false)
            this.textColor = Color.GRAY
            this.textSize = 10.0f
            // 设置y轴数据偏移量
            this.xOffset = 20.0f
            this.yOffset = -3.0f
            this.axisMinimum = 0.0f
        }
        chart.invalidate()
    }

    override fun initData(savedInstanceState: Bundle?) {
        val json = UIUtils.getAssetsJson("lineChart1/data.json")
        setData(json)
    }
    private fun setData(json: String) {
        chart?.let { chart ->
            val barDataSet: BarDataSet
            val values: MutableList<BarEntry> = mutableListOf()
            values.add(BarEntry(0.0f,3.0f))
            values.add(BarEntry(1.0f,6.0f))
            values.add(BarEntry(2.0f,3.0f))
            values.add(BarEntry(3.0f,4.0f))
            values.add(BarEntry(4.0f,6.0f))
            values.add(BarEntry(5.0f,5.0f))
            values.add(BarEntry(6.0f,2.9f))


            if(chart.data != null && chart.data.dataSetCount > 0) {
                // 刷新数据
                barDataSet = chart.data.getDataSetByIndex(0) as BarDataSet
                barDataSet.entries = values

                chart.notifyDataSetChanged()
            } else {
                // 设置数据1
                barDataSet = BarDataSet(values,"线111")
                // 设置曲线颜色
                barDataSet.color = Color.parseColor("#4e72b8")
                //设置折线图填充
                barDataSet.formLineWidth = 1.0f
                barDataSet.formSize = 12.0f
                // 显示坐标点的数据
                barDataSet.setDrawValues(true)
                // 显示定位线
                barDataSet.isHighlightEnabled = true
                //
                barDataSet.valueTextSize = 10.0f
                barDataSet.valueTextColor = Color.parseColor("#4e72b8")
                barDataSet.fills = arrayListOf(
                    Fill(Color.parseColor("#afb4db"),Color.parseColor("#4e72b8")),
                    Fill(Color.parseColor("#afb4db"),Color.parseColor("#4e72b8")),
                    Fill(Color.parseColor("#afb4db"),Color.parseColor("#4e72b8")),
                    Fill(Color.parseColor("#afb4db"),Color.parseColor("#4e72b8")),
                    Fill(Color.parseColor("#afb4db"),Color.parseColor("#4e72b8")),
                    Fill(Color.parseColor("#afb4db"),Color.parseColor("#4e72b8")),
                    Fill(Color.parseColor("#afb4db"),Color.parseColor("#4e72b8")))
                val data: BarData = BarData(barDataSet)
                data.barWidth = 0.32f
                chart.data = data
                chart.invalidate()
            }
        }
    }

    private fun setCustomMarkerView(context: Context) {
        chart?.let { chart ->
            LineChartMarkView(context, chart.xAxis.valueFormatter).apply {
                this.chartView = chart
                chart.marker = this
            }
        }
    }
}