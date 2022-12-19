package com.maple.mpchartdemo.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.blankj.utilcode.util.TimeUtils
import com.github.gzuliyujiang.calendarpicker.CalendarPicker
import com.github.gzuliyujiang.calendarpicker.core.ColorScheme
import com.github.gzuliyujiang.calendarpicker.core.DateUtils
import com.maple.mpchartdemo.R
import com.maple.mpchartdemo.base.BaseActivity
import com.maple.mpchartdemo.utils.UIUtils
import com.maple.mpchartdemo.widget.picker.MyCalendarPicker
import com.maple.mpchartdemo.widget.picker.MyFestivalProvider
import java.text.DateFormat
import java.util.*


class PickerActivity : BaseActivity() {

    private var startTimeInMillis: Long? = null
    private var endTimeInMillis:Long? = null
    private var singleTimeInMillis:Long? = null

    private var btnPicker1: Button? = null

    override fun getLayoutId(): Int = R.layout.activity_picker

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        btnPicker1 = findViewById(R.id.btn_picker1)
        btnPicker1?.setOnClickListener {
            val picker = MyCalendarPicker(this)
            picker.setBackgroundResource(R.drawable.shape_picker_bg)
            if (singleTimeInMillis == null) {
                singleTimeInMillis = System.currentTimeMillis()
            }
            picker.setSelectedDate(singleTimeInMillis!!)

            val currentDate = Date(System.currentTimeMillis())
            val minCalendar: Calendar = DateUtils.calendar(currentDate)
            minCalendar.add(Calendar.MONTH, -12)
            val minData = minCalendar.getTime()
            minCalendar.set(Calendar.DAY_OF_MONTH, DateUtils.maxDaysOfMonth(minData))

            val maxCalendar: Calendar = DateUtils.calendar(currentDate)
            val maxData = maxCalendar.getTime()
            picker.setRangeDate(minData,maxData)
            picker.setFestivalProvider(MyFestivalProvider())
            val colorScheme = ColorScheme()
            colorScheme.daySelectBackgroundColor(UIUtils.getColor(R.color.colorPrimary))
            colorScheme.dayStressTextColor(UIUtils.getColor(R.color.black))
            picker.setTitle("请选择日期")
            picker.setColorScheme(colorScheme)

            picker.setOnSingleDatePickListener { date ->
                singleTimeInMillis = date.time
                Toast.makeText(
                    applicationContext,
                    DateFormat.getDateTimeInstance().format(date),
                    Toast.LENGTH_SHORT
                ).show()
            }
            picker.show()
        }
    }
    override fun initData(savedInstanceState: Bundle?) {

    }


}