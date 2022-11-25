package com.maple.mpchartdemo.ui

import android.os.Bundle
import com.maple.mpchartdemo.R
import com.maple.mpchartdemo.base.BaseActivity

class SplashActivity : BaseActivity() {

    // 找一找用户有什么样的数据要展示
    // 用什么样的方式展示
    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initData(savedInstanceState: Bundle?) {
        onStartActivity(HomeActivity::class.java,isFinish = true)
    }


}