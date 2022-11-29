package com.maple.mpchartdemo.widget.roundBarChart;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Path;
import android.graphics.RectF;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class RoundBarChartRenderer extends BarChartRenderer {
    public RoundBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    private final RectF mBarShadowRectBuffer = new RectF();

    private float mRadius = 16.0f;

    public void setRadius(float radius) {
        this.mRadius = radius;
    }


    @Override
    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {
        // super.drawDataSet(c, dataSet, index);
        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        mBarBorderPaint.setColor(dataSet.getBarBorderColor());
        mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getBarBorderWidth()));

        final boolean drawBorder = dataSet.getBarBorderWidth() > 0.f;

        float phaseX = mAnimator.getPhaseX();
        float phaseY = mAnimator.getPhaseY();

        // draw the bar shadow before the values
        if (mChart.isDrawBarShadowEnabled()) {
            mShadowPaint.setColor(dataSet.getBarShadowColor());

            BarData barData = mChart.getBarData();

            final float barWidth = barData.getBarWidth();
            final float barWidthHalf = barWidth / 2.0f;
            float x;

            for (int i = 0, count = Math.min((int)(Math.ceil((float)(dataSet.getEntryCount()) * phaseX)), dataSet.getEntryCount());
                 i < count;
                 i++) {

                BarEntry e = dataSet.getEntryForIndex(i);

                x = e.getX();

                mBarShadowRectBuffer.left = x - barWidthHalf;
                mBarShadowRectBuffer.right = x + barWidthHalf;

                trans.rectValueToPixel(mBarShadowRectBuffer);

                if (!mViewPortHandler.isInBoundsLeft(mBarShadowRectBuffer.right))
                    continue;

                if (!mViewPortHandler.isInBoundsRight(mBarShadowRectBuffer.left))
                    break;

                mBarShadowRectBuffer.top = mViewPortHandler.contentTop();
                mBarShadowRectBuffer.bottom = mViewPortHandler.contentBottom();

               //  c.drawRect(mBarShadowRectBuffer, mShadowPaint);
//                c.drawRoundRect(mBarShadowRectBuffer,mRadius,mRadius,mShadowPaint);
                final float[] radii = {mRadius,mRadius,mRadius,mRadius,0f,0f,0f,0f};
                final Path path = new Path();
                path.addRoundRect(mBarShadowRectBuffer, radii, Path.Direction.CW);
                c.drawPath(path,mShadowPaint);
            }
        }

        // initialize the buffer
        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setDataSet(index);
        buffer.setInverted(mChart.isInverted(dataSet.getAxisDependency()));
        buffer.setBarWidth(mChart.getBarData().getBarWidth());

        buffer.feed(dataSet);

        trans.pointValuesToPixel(buffer.buffer);

        final boolean isSingleColor = dataSet.getColors().size() == 1;

        if (isSingleColor) {
            mRenderPaint.setColor(dataSet.getColor());
        }

        for (int j = 0; j < buffer.size(); j += 4) {

            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]))
                continue;

            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                break;

            if (!isSingleColor) {
                // Set the color for the currently drawn value. If the index
                // is out of bounds, reuse colors.
                mRenderPaint.setColor(dataSet.getColor(j / 4));
            }

            if (dataSet.getGradientColor() != null) {
                GradientColor gradientColor = dataSet.getGradientColor();
                mRenderPaint.setShader(
                        new LinearGradient(
                                buffer.buffer[j],
                                buffer.buffer[j + 3],
                                buffer.buffer[j],
                                buffer.buffer[j + 1],
                                gradientColor.getStartColor(),
                                gradientColor.getEndColor(),
                                android.graphics.Shader.TileMode.MIRROR));
            }

            if (dataSet.getGradientColors() != null) {
                mRenderPaint.setShader(
                        new LinearGradient(
                                buffer.buffer[j],
                                buffer.buffer[j + 3],
                                buffer.buffer[j],
                                buffer.buffer[j + 1],
                                dataSet.getGradientColor(j / 4).getStartColor(),
                                dataSet.getGradientColor(j / 4).getEndColor(),
                                android.graphics.Shader.TileMode.MIRROR));
            }

            // 源码
//            c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2], buffer.buffer[j + 3], mRenderPaint);
            // 上下都为圆角
//            c.drawRoundRect(buffer.buffer[j],buffer.buffer[j + 1],buffer.buffer[j + 2],buffer.buffer[j + 3],mRadius,mRadius,mRenderPaint);
            // 头部为圆角
            final float[] radii = {mRadius,mRadius,mRadius,mRadius,0f,0f,0f,0f};
            RectF rectF = new RectF(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2], buffer.buffer[j + 3]);
            final Path path = new Path();
            path.addRoundRect(rectF, radii, Path.Direction.CW);
            c.drawPath(path,mRenderPaint);
            if (drawBorder) {
                // 源码
//                 c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2], buffer.buffer[j + 3], mBarBorderPaint);
                // 上下都为圆角
//                c.drawRoundRect(buffer.buffer[j],buffer.buffer[j + 1],buffer.buffer[j + 2],buffer.buffer[j + 3],mRadius,mRadius,mBarBorderPaint);
                // 头部为圆角
                c.drawPath(path,mBarBorderPaint);
            }
        }
    }

    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {
       // super.drawHighlighted(c, indices);
        BarData barData = mChart.getBarData();

        for (Highlight high : indices) {

            IBarDataSet set = barData.getDataSetByIndex(high.getDataSetIndex());

            if (set == null || !set.isHighlightEnabled())
                continue;

            BarEntry e = set.getEntryForXValue(high.getX(), high.getY());

            if (!isInBoundsX(e, set))
                continue;

            Transformer trans = mChart.getTransformer(set.getAxisDependency());

            mHighlightPaint.setColor(set.getHighLightColor());
            mHighlightPaint.setAlpha(set.getHighLightAlpha());

            boolean isStack = (high.getStackIndex() >= 0  && e.isStacked()) ? true : false;

            final float y1;
            final float y2;

            if (isStack) {

                if(mChart.isHighlightFullBarEnabled()) {

                    y1 = e.getPositiveSum();
                    y2 = -e.getNegativeSum();

                } else {

                    Range range = e.getRanges()[high.getStackIndex()];

                    y1 = range.from;
                    y2 = range.to;
                }

            } else {
                y1 = e.getY();
                y2 = 0.f;
            }

            prepareBarHighlight(e.getX(), y1, y2, barData.getBarWidth() / 2f, trans);

            setHighlightDrawPos(high, mBarRect);
            // 源码
//            c.drawRect(mBarRect, mHighlightPaint);
            // 上下都为圆角
//            c.drawRoundRect(mBarRect,mRadius,mRadius,mHighlightPaint);
            // 头部为圆角
            final float[] radii={mRadius,mRadius,mRadius,mRadius,0f,0f,0f,0f};
            final Path path = new Path();
            path.addRoundRect(mBarRect, radii, Path.Direction.CW);
            c.drawPath(path,mHighlightPaint);
        }
    }
}
