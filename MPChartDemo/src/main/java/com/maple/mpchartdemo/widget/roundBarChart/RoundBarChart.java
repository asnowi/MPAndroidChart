package com.maple.mpchartdemo.widget.roundBarChart;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;

public class RoundBarChart extends BarChart {
    public RoundBarChart(Context context) {
        super(context);
    }

    public RoundBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        mRenderer = new RoundBarChartRenderer(this, mAnimator, mViewPortHandler);
//        mRenderer = new BarChartRenderer(this, mAnimator, mViewPortHandler);
    }
}
