package com.maple.mpchartdemo.widget.picker

import android.app.Activity
import android.graphics.Typeface
import com.github.gzuliyujiang.calendarpicker.CalendarPicker

class MyCalendarPicker(activity: Activity?) : CalendarPicker(activity) {

    override fun initData() {
        super.initData()
        cancelView?.let {
            it.text = "取消"
            it.textSize  = 14.0f
            it.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        }
        okView?.let {
            it.text = "确定"
            it.textSize  = 14.0f
            it.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        }

        titleView?.let {
            it.text = "请选择日期"
            it.textSize  = 14.0f
            it.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        }

    }

}