package com.maple.mpchartdemo.app

import android.app.Application
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.github.gzuliyujiang.dialog.DialogColor

import com.github.gzuliyujiang.dialog.DialogConfig

import com.github.gzuliyujiang.dialog.DialogStyle
import com.maple.mpchartdemo.R
import com.maple.mpchartdemo.utils.UIUtils


class MyApplication: Application() {

    companion object {
        @JvmStatic
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this)
        SPUtils.getInstance(this.packageName)

        DialogConfig.setDialogStyle(DialogStyle.Default)
        DialogConfig.setDialogColor(
            DialogColor()
                .titleTextColor(UIUtils.getColor(R.color.black))
                .cancelTextColor(UIUtils.getColor(R.color.cancel))
                .okTextColor(UIUtils.getColor(R.color.confirm))
        )
    }
}