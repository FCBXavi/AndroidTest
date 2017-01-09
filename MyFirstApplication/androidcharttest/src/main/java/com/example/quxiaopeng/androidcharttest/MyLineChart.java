package com.example.quxiaopeng.androidcharttest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;

/**
 * Created by quxiaopeng on 2017/1/7.
 */

public class MyLineChart extends LineChart {
    public MyLineChart(Context context) {
        super(context);
    }

    public MyLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    @Override
//    protected float[] getMarkerPosition(Entry e, Highlight highlight) {
//        int dataSetIndex = highlight.getDataSetIndex();
//        float xPos = e.getXIndex();
//        float yPos = e.getVal();
//        float[] pts = new float[]{
//                xPos, yPos
//        };
//        getTransformer(mData.getDataSetByIndex(dataSetIndex).getAxisDependency())
//                .pointValuesToPixel(pts);
//        return pts;
//    }

    @Override
    protected void drawMarkers(Canvas canvas) {
        // if there is no marker view or drawing marker is disabled
        if (mMarkerView == null || !mDrawMarkerViews || !valuesToHighlight())
            return;

        for (int i = 0; i < mIndicesToHighlight.length; i++) {

            Highlight highlight = mIndicesToHighlight[i];
            int xIndex = highlight.getXIndex();
            int dataSetIndex = highlight.getDataSetIndex();

            float deltaX = mXAxis != null
                    ? mXAxis.mAxisRange
                    : ((mData == null ? 0.f : mData.getXValCount()) - 1.f);

            if (xIndex <= deltaX && xIndex <= deltaX * mAnimator.getPhaseX()) {

                Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);

                // make sure entry not null
                if (e == null || e.getXIndex() != mIndicesToHighlight[i].getXIndex())
                    continue;

                float[] pos = getMarkerPosition(e, highlight);

                // check bounds
                if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                    continue;

                // callbacks to update the content
                mMarkerView.refreshContent(e, highlight);

                // mMarkerView.measure(MeasureSpec.makeMeasureSpec(0,
                // MeasureSpec.UNSPECIFIED),
                // MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                // mMarkerView.layout(0, 0, mMarkerView.getMeasuredWidth(),
                // mMarkerView.getMeasuredHeight());
                // mMarkerView.draw(mDrawCanvas, pos[0], pos[1]);

                mMarkerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                mMarkerView.layout(0, 0, mMarkerView.getMeasuredWidth(),
                        mMarkerView.getMeasuredHeight());

//                if (pos[1] - mMarkerView.getHeight() <= 0) {
//                    float y = mMarkerView.getHeight() - pos[1];
//                    mMarkerView.draw(canvas, pos[0], pos[1] + y);
//                } else {
                    mMarkerView.draw(canvas, pos[0], pos[1]);
//                }
            }
        }
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
}
