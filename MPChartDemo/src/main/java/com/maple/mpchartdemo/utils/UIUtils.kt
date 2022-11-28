package com.maple.mpchartdemo.utils

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.blankj.utilcode.util.*
import com.blankj.utilcode.util.StringUtils

class UIUtils {

    companion object {

        fun getString(@StringRes resId: Int): String {
            return StringUtils.getString(resId)
        }

        fun getColor(@ColorRes resId: Int): Int {
            return ColorUtils.getColor(resId)
        }

        fun getDrawable(@DrawableRes resId: Int): Drawable {
            return ResourceUtils.getDrawable(resId)
        }

        fun getSize(dp: Float): Int {
            return SizeUtils.dp2px(dp)
        }

        fun getAssetsJson(name: String): String {
            return ResourceUtils.readAssets2String(name)
        }
    }
}