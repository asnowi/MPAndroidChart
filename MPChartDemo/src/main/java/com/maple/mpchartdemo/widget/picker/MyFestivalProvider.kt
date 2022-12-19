package com.maple.mpchartdemo.widget.picker

import com.github.gzuliyujiang.calendarpicker.core.FestivalProvider
import java.text.SimpleDateFormat
import java.util.*

class MyFestivalProvider: FestivalProvider{

    override fun provideText(date: Date?): String {
        if(date == null) return ""
        val monthDay = SimpleDateFormat("MMdd",Locale.PRC).format(date)
        when(monthDay ) {
            "0101" -> "元旦节"
            "0214" -> "情人节"
            "0308" -> "妇女节"
            "0312" -> "植树节"
            "0401" -> "愚人节"
            "0501" -> "劳动节"
            "0504" -> "青年节"
            "0601" -> "儿童节"
            "0701" -> "建党节"
            "0801" -> "建军节"
            "0910" -> "教师节"
            "1001" -> "国庆节"
            "1111" -> "光棍节"
            "1225" -> "圣诞节"
            else -> ""
        }
        return ""
    }
}