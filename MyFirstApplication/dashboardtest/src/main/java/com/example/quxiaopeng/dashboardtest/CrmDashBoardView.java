package com.example.quxiaopeng.dashboardtest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by quxiaopeng on 2016/12/26.
 */

public class CrmDashBoardView extends View {

    //默认宽高值
    private int defaultSize;

    //进度圆环和透明圆环的距离
    private int arcDistance;

    //view宽度
    private int width;

    //view高度
    private int height;

    //默认Padding值
    private  int defaultPadding;

    //圆环起始角度
    private final static float mStartAngle = 180 - 6;

    //圆环总共的角度
    private final static float mEndAngle = 180 + 6 + 6;

    //内层圆环画笔
    private Paint mInnerArcPaint;

    //数字文本画笔
    private Paint mNumberPaint;

    //文本画笔
    private Paint mTextPaint;

    //大刻度画笔
    private Paint mCalibrationPaint;

    //小刻度画笔
    private Paint mSmallCalibrationPaint;

    //小刻度画笔
    private Paint mCalibrationTextPaint;

    //进度圆环画笔
    private Paint mArcProgressPaint;

    //半径
    private int radius;

    //内层矩形
    private RectF mInnerRect;

    //进度矩形
    private RectF mProgressRect;

    //最小数字
    private int mMinNum = 0;

    //最大数字
    private int mMaxNum = 5;

    //当前进度对应角度
    private float mCurrentAngle = 0f;

    //总进度对应角度
    private float mTotalAngle;

    //分数下的提示语
    private String strHint = "成单指数较低";

    //小圆点
    private Bitmap bitmap;

    //当前点的实际位置
    private float[] pos;

    //当前点的tangent值
    private float[] tan;

    //矩阵
    private Matrix matrix;

    //小圆点画笔
    private Paint mBitmapPaint;

    //最外层弧线渐变色
    private int[] progressColors = {Color.rgb(255, 177, 167), Color.rgb(255, 255, 255)};

    //显示数字所用字体
    private Typeface mTypeFace;

    private int starSize;
    private Bitmap starSolidBitmap;
    private Drawable starHollowDrawable;
    private Paint starPaint;
    private Paint edgePaint;


    public CrmDashBoardView(Context context) {
        this(context, null);
    }

    public CrmDashBoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CrmDashBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        defaultPadding = dp2px(20);
        defaultSize = dp2px(200);
        arcDistance = dp2px(6);
        starSize = dp2px(11);

        edgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        edgePaint.setStrokeWidth(dp2px(1));
        edgePaint.setColor(Color.WHITE);
        edgePaint.setStyle(Paint.Style.STROKE);

        //初始化自定义字体
        mTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/DINCondensedC.otf");

        //内层圆环画笔
        mInnerArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerArcPaint.setStrokeWidth(dp2px(10));
        mInnerArcPaint.setColor(Color.WHITE);
        mInnerArcPaint.setAlpha(51);//20%
        mInnerArcPaint.setStyle(Paint.Style.STROKE);

        //数字画笔
        mNumberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumberPaint.setColor(Color.WHITE);
        mNumberPaint.setTextAlign(Paint.Align.CENTER);
        mNumberPaint.setTypeface(mTypeFace);

        //文本画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        //圆环大刻度画笔
        mCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCalibrationPaint.setStrokeWidth(dp2px(1));
        mCalibrationPaint.setStyle(Paint.Style.STROKE);
        mCalibrationPaint.setColor(Color.WHITE);
        mCalibrationPaint.setAlpha(120);

        //圆环小刻度画笔
        mSmallCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallCalibrationPaint.setStrokeWidth(dp2px(1));
        mSmallCalibrationPaint.setStyle(Paint.Style.STROKE);
        mSmallCalibrationPaint.setColor(Color.WHITE);
        mSmallCalibrationPaint.setAlpha(130);

        //圆环刻度文本画笔
        mCalibrationTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCalibrationTextPaint.setTextSize(sp2px(10));
        mCalibrationTextPaint.setColor(Color.argb(153,255,255,255));
        mCalibrationTextPaint.setTypeface(mTypeFace);

        //外层进度画笔，在onSizeChanged方法中设置其渐变色
        mArcProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcProgressPaint.setStrokeWidth(dp2px(2));
        mArcProgressPaint.setStyle(Paint.Style.STROKE);

        //小圆点画笔
        mBitmapPaint = new Paint();
        mBitmapPaint.setStyle(Paint.Style.FILL);
        mBitmapPaint.setAntiAlias(true);

        starHollowDrawable = getResources().getDrawable(R.drawable.icon_customer_insight_star_hollow);
        starSolidBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_customer_insight_star_solid);
//        starSolidBitmap = drawableToBitmap(getResources().getDrawable(R.drawable.icon_customer_insight_star_solid));

        starPaint = new Paint();
        starPaint.setAntiAlias(true);
        starPaint.setShader(new BitmapShader(starSolidBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));


        //初始化小圆点图片
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_circle);
        pos = new float[2];
        tan = new float[2];
        matrix = new Matrix();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveMeasure(widthMeasureSpec, defaultSize),
                resolveMeasure(heightMeasureSpec, defaultSize));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
        radius = width / 2;

        mProgressRect = new RectF(
                defaultPadding, defaultPadding,
                width - defaultPadding, height - defaultPadding);

        mInnerRect = new RectF(
                defaultPadding + arcDistance,
                defaultPadding + arcDistance,
                width - defaultPadding - arcDistance,
                height - defaultPadding - arcDistance);

        //进度线设置渐变色
        mArcProgressPaint.setShader(new LinearGradient(0, radius, radius * 2, radius, progressColors, null, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawEdge(canvas);
        drawInnerArc(canvas);
        drawSmallCalibration(canvas);
        drawCalibrationAndText(canvas);
        drawCenterText(canvas);
        drawRingProgress(canvas);
//        drawStar(canvas);
    }

    private void drawEdge(Canvas canvas) {
        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRect(rectF, edgePaint);
    }

    private void drawStar(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            int startX = radius + i * starSize;
            int startY = radius + dp2px(20);
            starHollowDrawable.setBounds(startX , startY, startX + starSize, startY + starSize);
            starHollowDrawable.draw(canvas);
        }

        int solidStartX = radius;
        int solidStartY = radius + dp2px(20);
        float temp = mMinNum;
        for (int i = 0; temp > 1; i++, temp--) {
            Rect src = new Rect(0, 0, starSolidBitmap.getWidth(), starSolidBitmap.getHeight());
            Rect dst = new Rect(solidStartX, solidStartY, solidStartX + starSize, solidStartY + starSize);
            canvas.drawBitmap(starSolidBitmap, src, dst, starPaint);
            solidStartX = radius + (i + 1) * starSize;
            solidStartY = radius + dp2px(20);
        }
        Rect src = new Rect(0, 0, (int) (starSolidBitmap.getWidth() * temp), starSolidBitmap.getHeight());
        Rect dst = new Rect(solidStartX, solidStartY, (int) (solidStartX + starSize * temp), solidStartY + starSize);
        canvas.drawBitmap(starSolidBitmap, src, dst, starPaint);

    }

    /**
     * 绘制内层小刻度
     *
     * @param canvas
     */
    private void drawSmallCalibration(Canvas canvas) {
        //旋转画布
        canvas.save();
        float degree = (float) (180 / 50.0);
        canvas.rotate(-90 - degree, radius, radius);
        //分别计算大刻度线和小刻度线的起点结束点
        int smallStartDst = (int) (defaultPadding + arcDistance + mInnerArcPaint.getStrokeWidth() / 2 - 1);
        int smallEndDst = smallStartDst + dp2px(3);
        int bigStartDst = defaultPadding + dp2px(7);
        int bigEndDst = bigStartDst + dp2px(8);
        for (int i = -1; i <= 51; i++) {
            //每旋转3.6度画一个小刻度线，每旋转36度画一个大刻度线
            if (i % 10 == 0) {
                canvas.drawLine(radius, bigStartDst, radius, bigEndDst, mCalibrationPaint);
            } else {
                canvas.drawLine(radius, smallStartDst, radius, smallEndDst, mSmallCalibrationPaint);
            }
            canvas.rotate(degree, radius, radius);
        }
        canvas.restore();

//        canvas.save();
//        float degree = (float) (180 / 50.0);
//        canvas.rotate(-degree, radius, radius);
//        //分别计算大刻度线和小刻度线的起点结束点
//        int Y = width/2;
//        int smallStartX = (int) (defaultPadding + arcDistance + mInnerArcPaint.getStrokeWidth() / 2);
//        int smallEndX = smallStartX + dp2px(3);
//        int bigStartX = defaultPadding + arcDistance;
//        int bigEndX = bigStartX + dp2px(8);
//        for (int i = -1; i <= 51; i++) {
//            //每旋转3.6度画一个小刻度线，每旋转36度画一个大刻度线
//            if (i % 10 == 0) {
//                canvas.drawLine(bigStartX, Y, bigEndX, Y, mCalibrationPaint);
//            } else {
//                canvas.drawLine(smallStartX, Y, smallEndX, Y, mSmallCalibrationPaint);
//
//            }
//            canvas.rotate(degree, radius, radius);
//        }
//        canvas.restore();
    }

    /**
     * 绘制外层圆环进度和小圆点
     *
     * @param canvas
     */
    private void drawRingProgress(Canvas canvas) {

        Path path = new Path();
        path.addArc(mProgressRect, mStartAngle, mCurrentAngle);
        canvas.drawPath(path, mArcProgressPaint);

        PathMeasure pathMeasure = new PathMeasure(path, false);
        pathMeasure.getPosTan(pathMeasure.getLength() * 1, pos, tan);
//        matrix.reset();
//        matrix.postTranslate(pos[0] - bitmap.getWidth() / 2, pos[1] - bitmap.getHeight() / 2);

        pathMeasure.getMatrix(pathMeasure.getLength(), matrix, PathMeasure.POSITION_MATRIX_FLAG);
        matrix.postTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2);


        //起始角度不为0时候才进行绘制小圆点
        if (mCurrentAngle == 0)
            return;
        canvas.drawBitmap(bitmap, matrix, mBitmapPaint);
//        mBitmapPaint.setColor(Color.WHITE);
//        canvas.drawCircle(pos[0], pos[1], 8, mBitmapPaint);
    }

    /**
     * 绘制外层文本
     *
     * @param canvas
     */
    private void drawCenterText(Canvas canvas) {
        mNumberPaint.setTextSize(sp2px(40));
        canvas.drawText(String.valueOf(mMinNum), radius - dp2px(8), radius - dp2px(17), mNumberPaint);

        mTextPaint.setTextSize(sp2px(15));
        mTextPaint.setColor(Color.WHITE);
        canvas.drawText("分", radius + dp2px(18), radius - dp2px(18), mTextPaint);

        mTextPaint.setTextSize(sp2px(14));
        mTextPaint.setColor(Color.argb(204, 244, 244, 244));
        canvas.drawText(strHint, radius, radius + dp2px(7), mTextPaint);

//        mTextPaint.setTextSize(sp2px(10));
//        canvas.drawText("今日推荐度", radius - dp2px(35), radius + dp2px(30), mTextPaint);
    }

    /**
     * 绘制外层刻度
     *
     * @param canvas
     */
    private void drawCalibrationAndText(Canvas canvas) {
        //旋转画布进行绘制对应的刻度
        canvas.save();
        canvas.rotate(-90, radius, radius);
        int rotateAngle = 180 / 5;
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                canvas.drawText(String.valueOf(i * 20), radius - dp2px(3), defaultPadding - dp2px(4), mCalibrationTextPaint);
            } else if (i == 5) {
                canvas.drawText(String.valueOf(i * 20), radius - dp2px(7), defaultPadding - dp2px(4), mCalibrationTextPaint);
            } else {
                canvas.drawText(String.valueOf(i * 20), radius - dp2px(5), defaultPadding - dp2px(4), mCalibrationTextPaint);
            }
            canvas.rotate(rotateAngle, radius, radius);
        }
        canvas.restore();

    }


    /**
     * 绘制内层圆环
     *
     * @param canvas
     */
    private void drawInnerArc(Canvas canvas) {
        canvas.drawArc(mInnerRect, mStartAngle, mEndAngle, false, mInnerArcPaint);
    }

    /**
     * 根据传入的值进行测量
     *
     * @param measureSpec
     * @param defaultSize
     */
    public int resolveMeasure(int measureSpec, int defaultSize) {
        int result = 0;
        int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (View.MeasureSpec.getMode(measureSpec)) {
            case View.MeasureSpec.UNSPECIFIED:
                result = defaultSize;
                break;
            case View.MeasureSpec.AT_MOST:
                //设置warp_content时设置默认值
                result = Math.min(specSize, defaultSize);
                break;
            case View.MeasureSpec.EXACTLY:
                //设置math_parent 和设置了固定宽高值
                break;
            default:
                result = defaultSize;
        }

        return result;
    }

    /**
     * 设置分数
     *
     * @param value
     */
    public void setMark(int value) {
        mMaxNum = value;
        mTotalAngle = 6 + (float)value / 100 * 180;

        if (value <= 60) {
            strHint = "成单指数较低";
        } else {
            strHint = "成单指数较高";
        }

        startAnim();
    }


    public void startAnim() {
        ValueAnimator mAngleAnim = ValueAnimator.ofFloat(mCurrentAngle, mTotalAngle);
        mAngleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAngleAnim.setDuration(1000);
        mAngleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mCurrentAngle = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mAngleAnim.start();

//        final DecimalFormat format = new DecimalFormat("0.0");
        // mMinNum = 350;
        ValueAnimator mNumAnim = ValueAnimator.ofInt(mMinNum, mMaxNum);
        mNumAnim.setDuration(1000);
        mNumAnim.setInterpolator(new LinearInterpolator());
        mNumAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mMinNum = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mNumAnim.start();
    }

    /**
     * dp2px
     *
     * @param values
     * @return
     */
    public int dp2px(int values) {

        float density = getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public int sp2px(float spValue) {
        float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable)
    {
        if (drawable == null)return null;
        Bitmap bitmap = Bitmap.createBitmap(starSize, starSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(radius, radius + dp2px(20), radius + starSize, radius + dp2px(20) + starSize);
//        Toast.makeText(getContext(), "" + radius + "," + (radius + dp2px(20)) +","+(radius + starSize)+","+ (radius + dp2px(20) + starSize), Toast.LENGTH_SHORT).show();
        drawable.setBounds(0,0,starSize,starSize);
        drawable.draw(canvas);
        return bitmap;
    }
}
