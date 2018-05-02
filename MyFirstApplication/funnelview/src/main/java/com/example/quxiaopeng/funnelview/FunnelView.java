package com.example.quxiaopeng.funnelview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quxiaopeng on 2017/5/16.
 */

public class FunnelView extends View {

    private List<Integer> mStatusColors = new ArrayList<>();
    private List<Paint> mStatusPaints = new ArrayList<>();
    private List<FunnelModel> mModelList = new ArrayList<>();
    private Paint mStatusPaint;
    private Paint mCountPaint;
    private Paint mPercentagePaint;
    private Paint mLinePaint;

    private float highlightLength;
    private float leftPadding;
    private int defaultWidth;
    private int totalHeight;

    private float dividerHeight;
    private float mFunnelMaxVisibleHeight;
    private float mFunnelMaxWidth;
    private float mFunnelMinWidth;

    private List<Float> mWidthList = new ArrayList<>();
    private float mStatusHeight;
    private float mMinStatusHeight;
    private OnSelectListener mOnSelectListener;
    private int highlightIndex;

    public FunnelView(Context context) {
        super(context);
        init();
    }

    public FunnelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FunnelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initColors();
        initPaints();
        dividerHeight = dp2px(2);
        mFunnelMaxVisibleHeight = dp2px(360);
        mFunnelMaxWidth = dp2px(200);
        mFunnelMinWidth = dp2px(120);
        defaultWidth = dp2px(300);
        mMinStatusHeight = dp2px(36);
        highlightLength = dp2px(3);
        leftPadding = dp2px(5);
    }

    private void initColors() {
        mStatusColors.add(Color.parseColor("#5F92A8"));
        mStatusColors.add(Color.parseColor("#5FA8AF"));
        mStatusColors.add(Color.parseColor("#61B8A4"));
        mStatusColors.add(Color.parseColor("#66C696"));
        mStatusColors.add(Color.parseColor("#62D076"));
        mStatusColors.add(Color.parseColor("#8FDF63"));
        mStatusColors.add(Color.parseColor("#9ED45F"));
        mStatusColors.add(Color.parseColor("#C5D35E"));
        mStatusColors.add(Color.parseColor("#D3CC5E"));
        mStatusColors.add(Color.parseColor("#D4BA5F"));
        mStatusColors.add(Color.parseColor("#D4A95F"));
        mStatusColors.add(Color.parseColor("#D3955F"));
        mStatusColors.add(Color.parseColor("#D4855E"));
        mStatusColors.add(Color.parseColor("#D4785F"));
        mStatusColors.add(Color.parseColor("#D46C5F"));
        mStatusColors.add(Color.parseColor("#CB6063"));
        mStatusColors.add(Color.parseColor("#CB6077"));
        mStatusColors.add(Color.parseColor("#C7668B"));
        mStatusColors.add(Color.parseColor("#C766AB"));
        mStatusColors.add(Color.parseColor("#C066C7"));
        mStatusColors.add(Color.parseColor("#AD66C6"));
        mStatusColors.add(Color.parseColor("#9066C6"));
        mStatusColors.add(Color.parseColor("#7D66C7"));
        mStatusColors.add(Color.parseColor("#656AC7"));
        mStatusColors.add(Color.parseColor("#667BC6"));
        mStatusColors.add(Color.parseColor("#698BBD"));
        mStatusColors.add(Color.parseColor("#6894B1"));
        mStatusColors.add(Color.parseColor("#5E8DA7"));
    }

    private void initPaints() {
        mStatusPaints.clear();
        int size = mModelList.size();
        for (int i = 0; i < size; i++) {
            Paint paint = new Paint();
            paint.setColor(mStatusColors.get(i % size));
            paint.setStyle(Paint.Style.FILL);
            paint.setDither(true);
            paint.setAntiAlias(true);
            mStatusPaints.add(paint);
        }

        //客户状态画笔
        mStatusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStatusPaint.setColor(Color.WHITE);
        mStatusPaint.setTextSize(sp2px(12));
        mStatusPaint.setTextAlign(Paint.Align.CENTER);

        //客户个数画笔
        mCountPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCountPaint.setColor(Color.parseColor("#333333"));
        mCountPaint.setTextSize(sp2px(12));
        mCountPaint.setTextAlign(Paint.Align.RIGHT);

        //转换率画笔
        mPercentagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPercentagePaint.setColor(Color.parseColor("#999999"));
        mPercentagePaint.setTextSize(sp2px(12));

        //转化率线的画笔
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.parseColor("#cccccc"));
        mLinePaint.setStrokeWidth(dp2px(1));
        mLinePaint.setStyle(Paint.Style.STROKE);
    }

    private void initWidthAndHeight() {
        int size = mModelList.size();
        if (size > 0) {
            //计算每个梯形的高度
            mStatusHeight = (mFunnelMaxVisibleHeight - dividerHeight * (size - 1)) / size;
            if (mStatusHeight < mMinStatusHeight) {
                mStatusHeight = mMinStatusHeight;
            }
            //计算每个梯形下底的宽度
            for (int i = 0; i < size - 1; i++) {
                if (size == 1) {
                    mWidthList.add(mFunnelMinWidth);
                } else {
                    float width = mFunnelMaxWidth - ((mFunnelMaxWidth - mFunnelMinWidth) / (size - 1) * i);
                    mWidthList.add(width);
                }
            }
        }
    }

    private void notifyDataSetChanged() {
        initPaints();
        initWidthAndHeight();
        totalHeight = (int) ((mStatusHeight + dividerHeight) * (mModelList.size() - 1) + mMinStatusHeight);
    }

    public void setFunnelList(List<FunnelModel> list) {
        mModelList.clear();
        mModelList.addAll(list);
        notifyDataSetChanged();
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(widthMeasureSpec), totalHeight);
    }

    private int getMeasuredWidth(int size) {
        int result = 0;
        int specMode = MeasureSpec.getMode(size);
        int specSize = MeasureSpec.getSize(size);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = defaultWidth;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFunnel(canvas);
    }

    private void drawFunnel(Canvas canvas) {
        int size = mModelList.size();
        for (int i = 0; i < size; i++) {
            FunnelModel model = mModelList.get(i);
            //画梯形
            if (i < size - 1) {
                Paint paint = mStatusPaints.get(i);
                Path path = new Path();

                if (i == highlightIndex) {
                    path.moveTo(leftPadding + (mFunnelMaxWidth - mWidthList.get(i)) / 2 - highlightLength, i * (mStatusHeight + dividerHeight));
                    path.lineTo(leftPadding + (mFunnelMaxWidth + mWidthList.get(i)) / 2 + highlightLength, i * (mStatusHeight + dividerHeight));
                    if (i < size - 2) {
                        path.lineTo(leftPadding + (mFunnelMaxWidth + mWidthList.get(i + 1)) / 2 + highlightLength, (i + 1) * mStatusHeight + i * dividerHeight);
                        path.lineTo(leftPadding + (mFunnelMaxWidth - mWidthList.get(i + 1)) / 2 - highlightLength, (i + 1) * mStatusHeight + i * dividerHeight);
                    } else {
                        path.lineTo(leftPadding + (mFunnelMaxWidth + mWidthList.get(i)) / 2 + highlightLength, (i + 1) * mStatusHeight + i * dividerHeight);
                        path.lineTo(leftPadding + (mFunnelMaxWidth - mWidthList.get(i)) / 2 - highlightLength, (i + 1) * mStatusHeight + i * dividerHeight);
                    }
                } else {
                    path.moveTo(leftPadding + (mFunnelMaxWidth - mWidthList.get(i)) / 2, i * (mStatusHeight + dividerHeight));
                    path.lineTo(leftPadding + (mFunnelMaxWidth + mWidthList.get(i)) / 2, i * (mStatusHeight + dividerHeight));
                    if (i < size - 2) {
                        path.lineTo(leftPadding + (mFunnelMaxWidth + mWidthList.get(i + 1)) / 2, (i + 1) * mStatusHeight + i * dividerHeight);
                        path.lineTo(leftPadding + (mFunnelMaxWidth - mWidthList.get(i + 1)) / 2, (i + 1) * mStatusHeight + i * dividerHeight);
                    } else {
                        path.lineTo(leftPadding + (mFunnelMaxWidth + mWidthList.get(i)) / 2, (i + 1) * mStatusHeight + i * dividerHeight);
                        path.lineTo(leftPadding + (mFunnelMaxWidth - mWidthList.get(i)) / 2, (i + 1) * mStatusHeight + i * dividerHeight);
                    }
                }

                path.close();
                canvas.drawPath(path, paint);
            }

            //画状态名
            Paint.FontMetricsInt fontMetrics = mStatusPaint.getFontMetricsInt();
            float baseline;
            if (i == highlightIndex) {
                mStatusPaint.setTextSize(sp2px(14));
            } else {
                mStatusPaint.setTextSize(sp2px(12));
            }
            if (i < size - 1) {
                mStatusPaint.setColor(Color.WHITE);
                baseline = i * (mStatusHeight + dividerHeight) + (mStatusHeight - fontMetrics.bottom - fontMetrics.top) * 0.5f;//为了让文字垂直居中
            } else {
                mStatusPaint.setColor(Color.parseColor("#999999"));
                baseline = i * (mStatusHeight + dividerHeight) + (mMinStatusHeight - fontMetrics.bottom - fontMetrics.top) * 0.5f;
            }
            canvas.drawText(model.name, leftPadding + mFunnelMaxWidth / 2, baseline, mStatusPaint);
            //画客户个数
            canvas.drawText(String.valueOf(model.count), mFunnelMaxWidth + dp2px(30), baseline, mCountPaint);


            if (i >= 1 && i < size - 1) {
                //画转换率
                String percentage = getPercentage(mModelList, i - 1);
                canvas.drawText(percentage, mFunnelMaxWidth + dp2px(60), i * (mStatusHeight + dividerHeight) + dp2px(4), mPercentagePaint);

                //画转化率曲线start
                //竖线
                float verticalLineStartX = mFunnelMaxWidth + dp2px(45);
                float verticalLineStartY = i * (mStatusHeight + dividerHeight) - mStatusHeight / 2;
                float verticalLineEndX = verticalLineStartX;
                float verticalLineEndY = verticalLineStartY + mStatusHeight - dp2px(2);
                Path path = new Path();
                path.moveTo(verticalLineStartX, verticalLineStartY);
                path.lineTo(verticalLineEndX, verticalLineEndY);
                canvas.drawPath(path, mLinePaint);

                //需要在第一个转化率上画一个横线和一个弧度
                if (i == 1) {
                    RectF rect = new RectF(verticalLineStartX - dp2px(5), verticalLineStartY - dp2px(3), verticalLineStartX, verticalLineStartY + dp2px(3));
                    canvas.drawArc(rect, 0, -90, false, mLinePaint);
                    path.moveTo(verticalLineStartX - dp2px(2), verticalLineStartY - dp2px(3));
                    path.lineTo(verticalLineStartX - dp2px(6), verticalLineStartY - dp2px(3));
                    canvas.drawPath(path, mLinePaint);
                }

                //左下角弧线
                RectF rect = new RectF(verticalLineEndX - dp2px(5), verticalLineEndY - dp2px(3), verticalLineEndX, verticalLineEndY + dp2px(3));
                canvas.drawArc(rect, 0, 90, false, mLinePaint);

                //下方横线
                float horizontalLineStartX = verticalLineStartX - dp2px(2);
                float horizontalLineStartY = verticalLineEndY + dp2px(3);
                float horizontalLineEndX = horizontalLineStartX - dp2px(5);
                float horizontalLineEndY = horizontalLineStartY;
                path.moveTo(horizontalLineStartX, horizontalLineStartY);
                path.lineTo(horizontalLineEndX, horizontalLineEndY);
                canvas.drawPath(path, mLinePaint);

                //箭头上半部分
                path.moveTo(horizontalLineEndX, horizontalLineEndY);
                path.lineTo(horizontalLineEndX + dp2px(3), horizontalLineEndY - dp2px(3));
                canvas.drawPath(path, mLinePaint);

                //箭头下半部分
                path.moveTo(horizontalLineEndX, horizontalLineEndY);
                path.lineTo(horizontalLineEndX + dp2px(3), horizontalLineEndY + dp2px(3));
                canvas.drawPath(path, mLinePaint);
                //画转化率曲线end
            }

        }
    }


    DecimalFormat format = new DecimalFormat("##0.00");

    private String getPercentage(List<FunnelModel> list, int index) {
        String result;
        int total = getTotalCount(list, index);
        if (total == 0) {
            result = "0.00%";
        } else {
            double r = (total - list.get(index).count + 0.0f) / total * 100;
            format.setMinimumFractionDigits(2);
            result = format.format(r) + "%";
        }
        return result;
    }

    private int getTotalCount(List<FunnelModel> list, int index) {
        int result = 0;
        int size = list.size();
        for (int i = index; i < size - 1; i++) {
            result += list.get(i).count;
        }
        return result;
    }

    int downIndex = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < mFunnelMaxWidth) {
                    Log.i("onTouchEvent down", "y :" + event.getY() + " totalHeight" + totalHeight);
                    int size = mModelList.size();
                    for (int i = 0; i < size; i++) {
                        float temp = event.getY() - (mStatusHeight + dividerHeight) * (i + 1);
                        if (temp < 0) {
                            downIndex = i;
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (event.getX() < mFunnelMaxWidth) {
                    Log.i("onTouchEvent up", "y :" + event.getY() + " totalHeight" + totalHeight);
                    int size = mModelList.size();
                    for (int i = 0; i < size; i++) {
                        float temp = event.getY() - (mStatusHeight + dividerHeight) * (i + 1);
                        if (temp < 0 && downIndex == i) {
                            highlightIndex = i;
                            if (mOnSelectListener != null) {
                                mOnSelectListener.onSelect(highlightIndex);
                            }
                            invalidate();
                            break;
                        }
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public int dp2px(int values) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }

    public int sp2px(float spValue) {
        float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public void setOnSelectListener(OnSelectListener listener) {
        this.mOnSelectListener = listener;
    }

    public interface OnSelectListener {
        void onSelect(int index);
    }
}
