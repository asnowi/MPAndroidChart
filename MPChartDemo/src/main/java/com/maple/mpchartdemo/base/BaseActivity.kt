package com.maple.mpchartdemo.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ActivityUtils
import com.maple.mpchartdemo.R
import com.maple.mpchartdemo.ext.toGone
import com.maple.mpchartdemo.ext.toVisible

abstract class BaseActivity: AppCompatActivity() {
    /// 传递bundle 数据 Key
    private val KEY_BUNDLE_DATA: String = "BUNDLE_DATA"

    /// 布局
    abstract fun getLayoutId(): Int

    abstract fun initData(savedInstanceState: Bundle?): Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView(savedInstanceState)
        initData(savedInstanceState)
    }

    open fun initView(savedInstanceState: Bundle?) {
        findViewById<TextView>(R.id.tv_title_center)?.apply {
            if(isUITitle()) {
                this.toVisible()
                this.text = uiTitle()
            } else {
                this.toGone()
            }
        }
        findViewById<ImageButton>(R.id.ibtn_title_left)?.apply {
            if(isUILeft()) {
                this.toVisible()
                this.setOnClickListener { onUILeft() }
            } else {
                this.toGone()
            }
        }
    }

    open fun isUILeft(): Boolean = true

    open fun isUITitle(): Boolean = true

    open fun uiTitle(): String {
        return ""
    }

    open fun onUILeft() {}

    /// 获取封装 bundle
    open fun getBundle(): Bundle? {
        return this.intent?.getBundleExtra(KEY_BUNDLE_DATA)
    }

    fun onStartActivity(clazz: Class<out Activity?>,bundle: Bundle? = null,isFinish: Boolean = false) {
        if(ActivityUtils.isActivityExistsInStack(clazz)) {
            return
        }
        this.startActivity(Intent(this, clazz).apply {
            bundle?.let {
                this.putExtra(KEY_BUNDLE_DATA, it)
            }
        })
        if (isFinish) this.onFinish()
    }

    open fun onFinish() {
        this.finish()
    }
}