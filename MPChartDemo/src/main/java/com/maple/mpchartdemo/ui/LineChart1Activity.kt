package com.maple.mpchartdemo.ui

import android.os.Bundle
import com.maple.mpchartdemo.R
import com.maple.mpchartdemo.base.BaseActivity

class LineChart1Activity : BaseActivity() {

    override fun uiTitle(): String = getBundle()?.getString("title")?:""
    override fun onUILeft() { onFinish() }

    override fun getLayoutId(): Int = R.layout.activity_line_chart1

    override fun initData(savedInstanceState: Bundle?) {

    }
}