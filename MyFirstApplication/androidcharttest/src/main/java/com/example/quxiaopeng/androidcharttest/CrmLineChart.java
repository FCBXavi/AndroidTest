package com.example.quxiaopeng.androidcharttest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;

/**
 * Created by quxiaopeng on 2017/1/4.
 */

public class CrmLineChart extends LineChart {

//    private TopIndicatorDivider mIndicator;
    public CrmLineChart(Context context) {
        super(context);
    }

    public CrmLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CrmLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    public void setIndicator(TopIndicatorDivider indicator) {
//        this.mIndicator = indicator;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawHighLightCircle(canvas);
    }

    private void drawHighLightCircle(Canvas canvas) {
        if (!valuesToHighlight() || mIndicesToHighlight == null || mIndicesToHighlight.length == 0)
            return;
        int count = getLineData().getDataSetCount();

        int xIndex = mIndicesToHighlight[0].getXIndex();
        for (int i = 0; i < count; i++) {

            Highlight highlight = new Highlight(xIndex,i);

            float deltaX = mXAxis.mAxisRange;
            if (xIndex <= deltaX && xIndex <= deltaX * mAnimator.getPhaseX()) {

                Entry e = mData.getEntryForHighlight(highlight);

                // make sure entry not null
                if (e == null || e.getXIndex() != xIndex)
                    continue;

                float[] pos = getMarkerPosition(e, highlight);

//                if (mIndicator != null) {
//                    mIndicator.updateIndicator(pos[0]);
//                }

                // check bounds
                if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                    continue;
                LineDataSet dataSet = (LineDataSet) mData.getDataSetByIndex(i);
                Paint mPaintRender = getRenderer().getPaintRender();
                float radius = dataSet.getCircleSize();
                float halfsize = radius / 2f;
                int circleColor = dataSet.getCircleColor(i);
                mPaintRender.setColor(Color.argb(33,Color.red(circleColor),Color.green(circleColor),Color.blue(circleColor)));
                canvas.drawCircle(pos[0], pos[1], radius+halfsize, mPaintRender);
                mPaintRender.setColor(circleColor);
                canvas.drawCircle(pos[0], pos[1], radius, mPaintRender);
                mPaintRender.setColor(dataSet.getCircleHoleColor());
                if (dataSet.isDrawCircleHoleEnabled()
                        && circleColor != mPaintRender.getColor())
                    canvas.drawCircle(pos[0], pos[1],
                            halfsize,
                            mPaintRender);
            }
        }
    }

    @Override
    protected void init() {
        super.init();
//        mRenderer = new CrmLineChartRenderer(this, mAnimator, mViewPortHandler);
//        mChartTouchListener = new CrmLineChartTouchListener(this, mViewPortHandler.getMatrixTouch());
    }

    @Override
    protected void calcMinMax() {
        super.calcMinMax();
        if (mAxisLeft.mAxisMaximum < mAxisLeft.getLabelCount()) {
            mAxisLeft.mAxisMaximum = mAxisLeft.getLabelCount();
        }
        mAxisLeft.mAxisRange = Math.abs(mAxisLeft.mAxisMaximum - mAxisLeft.mAxisMinimum);
    }

    public void setHighlightEnabled(boolean enabled) {
        if (mData != null)
            mData.setHighlightEnabled(enabled);
    }

    /**
     * 数据量较少时处理y轴显示问题,此方法必须在设置数据之后调用才生效
     * @param axisDependency
     */
    public void configureChartYAxisLabels(YAxis.AxisDependency axisDependency) {
        if(mData == null) return;
        YAxis yAxis = this.getAxis(axisDependency);

        // Minimum section is 1, could be 1.0f for only integer values
        float labelInterval = 1f / 2f;

        int sections;
        do {
            labelInterval *= 2;
            sections = ((int) Math.ceil(this.getYMax() / labelInterval));
        } while (sections > 6);

        // If the ymax lies on one of the top interval, add another one for some spacing
        if (this.getYMax() == sections * labelInterval) {
            sections++;
        }

        yAxis.setAxisMaxValue(labelInterval * sections);
        yAxis.setLabelCount(sections + 1, true);
    }
}