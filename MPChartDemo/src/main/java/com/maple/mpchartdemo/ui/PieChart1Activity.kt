package com.maple.mpchartdemo.ui

import android.os.Bundle
import com.github.mikephil.charting.charts.PieChart
import com.maple.mpchartdemo.R
import com.maple.mpchartdemo.base.BaseActivity

class PieChart1Activity : BaseActivity() {

    override fun uiTitle(): String = getBundle()?.getString("title")?:""
    override fun onUILeft() { onFinish() }

    private var chart: PieChart? = null


    override fun getLayoutId(): Int = R.layout.activity_pie_chart1

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        chart = findViewById(R.id.chart)
        chart?.apply {

        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}