package com.maple.mpchartdemo.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.maple.mpchartdemo.R
import com.maple.mpchartdemo.base.BaseActivity
import com.maple.mpchartdemo.utils.ToastUtils
import com.maple.mpchartdemo.utils.UIUtils
import com.maple.mpchartdemo.widget.marker.LineChartMarkView


class LineChart1Activity : BaseActivity() {

    override fun uiTitle(): String = getBundle()?.getString("title")?:""
    override fun onUILeft() { onFinish() }

    private var chart: LineChart? = null
    private val weekList: List<String> = arrayListOf("周一","周二","周三","周四","周五","周六","周日")

    override fun getLayoutId(): Int = R.layout.activity_line_chart1

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

    private fun initChart(chart: LineChart) {
        // 描述信息
        val description = Description().apply {
            this.text = "图表描述"
            this.textColor = Color.CYAN
            this.yOffset = 20.0f
        }
        chart.apply {
            this.description = description
            this.setNoDataText("暂无数据")
            // 不显示表格颜色
            this.setDrawGridBackground(false)
            // 可以缩放
            this.setScaleEnabled(true)
            // 不显示y轴右边的值
            this.axisRight.isEnabled = false
            // 不显示图例
            val legend = chart.legend
            legend.isEnabled = false
            // 向左偏移15dp，抵消y轴向右偏移的30dp
            this.extraLeftOffset = -15.0f
            //是否可以根据最大值和最小值自动缩放--是
            this.isAutoScaleMinMaxEnabled = true
            this.isDragEnabled = true
            this.setPinchZoom(true)
            this.maxHighlightDistance = 300f
            this.setVisibleXRangeMaximum(10.0f)
            this.animateXY(2000,2000)
            // x周数据
            this.xAxis.valueFormatter = object :IAxisValueFormatter{
                override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                    return weekList.get(value.toInt())
                }
            }
            //选中点（数据）的监听事件
            this.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    e?.let {
                        ToastUtils.showToast(it.toString())
                    }
                }
                override fun onNothingSelected() {}
            })
            setCustomMarkerView(this@LineChart1Activity)
        }

        // XAxis
        val xAxis: XAxis = chart.xAxis
        xAxis.apply {
            // 不显示x轴
            this.setDrawAxisLine(false)
            // 设置x轴数据的位置
            this.position = XAxis.XAxisPosition.BOTTOM
            this.textColor = Color.GRAY
            this.textSize = 10.0f
            this.gridColor = Color.parseColor("#30888888")
            // 设置x轴数据偏移量
            this.yOffset = -12.0f
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

    private fun setData(json: String) {
        chart?.let { chart ->
            val lineDataSet1: LineDataSet
            val values1: MutableList<Entry> = mutableListOf()
            values1.add(Entry(0.0f,3.0f))
            values1.add(Entry(1.0f,6.0f))
            values1.add(Entry(2.0f,3.0f))
            values1.add(Entry(3.0f,4.0f))
            values1.add(Entry(4.0f,6.0f))
            values1.add(Entry(5.0f,5.0f))
            values1.add(Entry(6.0f,2.9f))

            val lineDataSet2: LineDataSet
            val values2: MutableList<Entry> = mutableListOf()
            values2.add(Entry(0.0f,0.0f))
            values2.add(Entry(1.0f,2.2f))
            values2.add(Entry(2.0f,2.0f))
            values2.add(Entry(3.0f,3.3f))
            values2.add(Entry(4.0f,4.1f))
            values2.add(Entry(5.0f,3.0f))
            values2.add(Entry(6.0f,0.0f))

            if(chart.data != null && chart.data.dataSetCount > 0) {
                // 刷新数据
                lineDataSet1 = chart.data.getDataSetByIndex(0) as LineDataSet
                lineDataSet1.entries = values1

                lineDataSet2 = chart.data.getDataSetByIndex(1) as LineDataSet
                lineDataSet2.entries = values2

                chart.notifyDataSetChanged()
            } else {
                // 设置数据1
                lineDataSet1 = LineDataSet(values1,"线111")
                // 设置曲线颜色
                lineDataSet1.color = Color.parseColor("#4e72b8")
                //设置折线图填充
                lineDataSet1.setDrawFilled(true)
                lineDataSet1.formLineWidth = 1.0f
                lineDataSet1.formSize = 12.0f
                // //设置曲线展示为圆滑曲线（如果不设置则默认折线）
                lineDataSet1.mode = LineDataSet.Mode.CUBIC_BEZIER
                // 显示坐标点的小圆点
                lineDataSet1.setDrawCircles(true)
                // 显示坐标点的数据
                lineDataSet1.setDrawValues(true)
                // 显示定位线
                lineDataSet1.isHighlightEnabled = true
                //
                lineDataSet1.lineWidth = 3.0f
                lineDataSet1.valueTextSize = 10.0f
                lineDataSet1.valueTextColor = Color.parseColor("#4e72b8")
                lineDataSet1.circleRadius = 5.0f
                //设置曲线值的圆点是实心还是空心
                lineDataSet1.setDrawCircleHole(true)
                lineDataSet1.setCircleColor(Color.parseColor("#4e72b8"))
                // 设置填充色
                lineDataSet1.fillColor = Color.parseColor("#4e72b8")

                // 设置数据2
                lineDataSet2 = LineDataSet(values2,"线222")
                // 设置曲线颜色
                lineDataSet2.color = Color.parseColor("#1d953f")
                //设置折线图填充
                lineDataSet2.setDrawFilled(true)
                lineDataSet2.formLineWidth = 1.0f
                lineDataSet2.formSize = 12.0f
                // //设置曲线展示为圆滑曲线（如果不设置则默认折线）
                lineDataSet2.mode = LineDataSet.Mode.CUBIC_BEZIER
                // 显示坐标点的小圆点
                lineDataSet2.setDrawCircles(true)
                // 显示坐标点的数据
                lineDataSet2.setDrawValues(true)
                // 显示定位线
                lineDataSet2.isHighlightEnabled = true
                //
                lineDataSet2.lineWidth = 3.0f
                lineDataSet2.valueTextSize = 10.0f
                lineDataSet2.valueTextColor = Color.parseColor("#1d953f")
                lineDataSet2.circleRadius = 5.0f
                //设置曲线值的圆点是实心还是空心
                lineDataSet2.setDrawCircleHole(true)
                lineDataSet2.setCircleColor(Color.parseColor("#1d953f"))
                // 设置填充色
                lineDataSet2.fillColor = Color.parseColor("#1d953f")

                val data: LineData = LineData(lineDataSet1,lineDataSet2)
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