package com.maple.mpchartdemo.ui

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.maple.mpchartdemo.R
import com.maple.mpchartdemo.base.BaseActivity
import com.maple.mpchartdemo.utils.ToastUtils

class HomeActivity : BaseActivity() {

    override fun isUILeft(): Boolean = false
    override fun uiTitle(): String = "首页"

    private var lvList: ListView? = null

    private val list: MutableList<String> = mutableListOf<String>().apply {
        this.add("折线图1")
        this.add("柱状图1")
        this.add("饼图1")
    }

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun initData(savedInstanceState: Bundle?) {
        lvList = findViewById(R.id.lv_list)
        lvList?.apply {
            this.setOnItemClickListener { parent, view, position, id ->
                onItemUI(position)
            }
            this.adapter = ArrayAdapter<String>(this@HomeActivity,R.layout.item_home,R.id.tv_name,list)
        }
        // openItemActivity(LineChart1Activity::class.java,list.get(0))
    }

    private fun onItemUI(position: Int) {
        when(position) {
            0 -> {
                openItemActivity(LineChart1Activity::class.java,list.get(position))
            }
            1 -> {
                openItemActivity(BarChart1Activity::class.java,list.get(position))
            }
            2 -> {
                openItemActivity(PieChart1Activity::class.java,list.get(position))
            }
            else -> {
                ToastUtils.showToast("<-${position}->")
            }
        }
    }


    private fun openItemActivity(clazz: Class<out Activity?>,title: String){
        onStartActivity(clazz, bundle = Bundle().apply {
            this.putString("title",title)
        })
    }
}