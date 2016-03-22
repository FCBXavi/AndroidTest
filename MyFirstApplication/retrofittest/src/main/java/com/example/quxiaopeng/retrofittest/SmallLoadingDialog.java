package com.example.quxiaopeng.retrofittest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 小块加载样式对话框
 *
 * @author gaoxu
 * @version 15/3/14
 */
public class SmallLoadingDialog extends Dialog {

    /** 宿主 */
    private Activity mActivity;
    /** 实际显示的加载视图 */
    private SmallLoadingView mLoadingView;

    /**
     * 构造
     */
    public SmallLoadingDialog(Activity activity) {
        this(activity, false);
    }

    /**
     * 构造
     */
    public SmallLoadingDialog(Activity activity, boolean isFullScreen) {
        super(activity, R.style.CustomDialogTheme);

        if (isFullScreen) {
            // 设置无标题
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            // 设置全屏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        mActivity = activity;
        mLoadingView = new SmallLoadingView(activity);
        setContentView(mLoadingView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setCancelable(true);
    }

    @Override
    public void show() {
        super.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mLoadingView.show();
            }
        }, 100);
    }

    @Override
    public void dismiss() {
        mLoadingView.dismiss(new OnLoadingDismissListener() {
            @Override
            public void onFinish() {
                dismissImmediately();
            }
        });
    }

    /**
     * 立即关闭
     */
    public void dismissImmediately() {
        if (isShowing() && !mActivity.isFinishing()) {
            SmallLoadingDialog.super.dismiss();
        }
    }

    /**
     * 小型加载对话框
     */
    private class SmallLoadingView extends View {

        /** 动画执行时间 */
        private static final float ANIM_DUTAION_SHOW = 300;
        /** 动画执行时间 */
        private static final float ANIM_DUTAION_DISMISS = 300;
        /** 动画关闭延迟执行时间 */
        private static final float ANIM_DELAY = 0;
        /** 帧数 */
        private static final int FPS = 30;
        /** 背景透明度 */
        private static final int SHADOW_ALPHA = 120;
        /** 加载进度条进度值变化步长 */
        private static final float LOADING_PROGRESS_ANIM_STEP = 0.01f;

        /** 提示文字 */
        private static final String TEXT_LOADING = "加载中";

        /** 对话框背景板 */
        private Bitmap mLoadingBorad;
        /** 进度条 */
        private Bitmap mLoadingBar;

        /** 动画执行器 */
        private AnimRunnable mAnimRunnable;

        /** 背景透明度 */
        private int mShadowAlpha = 0;
        /** 加载进度条进度值，百分位 */
        private float mProgressBarProgress;

        /** 黑色底板变化矩阵 */
        private Matrix mBoardMatrix;
        /** Logo外圈底板变化矩阵 */
        private Matrix mBarMatrix;

        /** 提示文字画笔 */
        private Paint mHintTextPaint;
        /** 图片画笔 */
        private Paint mDrawablePaint;
        /** Canvas抗锯齿 */
        private PaintFlagsDrawFilter mCanvasPaintFlagsDrawFilter;

        /** 动画执行完毕的 */
        private OnLoadingDismissListener mDismissListener;

        public SmallLoadingView(Context context) {
            super(context);

            init();
        }

        public SmallLoadingView(Context context, AttributeSet attrs) {
            super(context, attrs);

            init();
        }

        /**
         * 初始化
         */
        private void init() {
            mAnimRunnable = new AnimRunnable();

            mLoadingBorad = BitmapFactory.decodeResource(getResources(),
                    R.drawable.img_loading_small_board);
            mLoadingBar = BitmapFactory.decodeResource(getResources(),
                    R.drawable.img_loading_small_bar);

            mLoadingBorad = getScaleBitmap(mLoadingBorad,
                    (int) (mLoadingBorad.getWidth() * 0.70),
                    (int) (mLoadingBorad.getHeight() * 0.70));
            mLoadingBar = getScaleBitmap(mLoadingBar,
                    (int) (mLoadingBar.getWidth() * 0.70),
                    (int) (mLoadingBar.getHeight() * 0.70));

            mBoardMatrix = new Matrix();
            mBarMatrix = new Matrix();

            mHintTextPaint = new Paint();
            mHintTextPaint.setAntiAlias(true);
            mHintTextPaint.setColor(Color.WHITE);
            mHintTextPaint.setTextSize(dip2px(getContext(), 16));

            mDrawablePaint = new Paint();
            mDrawablePaint.setAntiAlias(true);

            mCanvasPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0,
                    Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.setDrawFilter(mCanvasPaintFlagsDrawFilter);

            if (mAnimRunnable.getAnimState() == AnimState.NONE) {
                return;
            }

            // 绘制背景
            canvas.drawARGB(mShadowAlpha, 0, 0, 0);

            // 绘制提示文字
            String hintText = TEXT_LOADING;

            // 设置位移
            mBoardMatrix.setTranslate(getWidth() / 2 - mLoadingBorad.getWidth()
                    / 2, getHeight() / 2 - mLoadingBorad.getHeight() / 2);
            mBarMatrix.setTranslate(getWidth() / 2 - mLoadingBar.getWidth() / 2
                    - mHintTextPaint.measureText(hintText) / 3 * 2, getHeight()
                    / 2 - mLoadingBar.getHeight() / 2);
            // 旋转
            mBarMatrix.preRotate(360 * mProgressBarProgress,
                    mLoadingBar.getWidth() / 2, mLoadingBar.getHeight() / 2);

            canvas.drawBitmap(mLoadingBorad, mBoardMatrix, mDrawablePaint);
            canvas.drawBitmap(mLoadingBar, mBarMatrix, mDrawablePaint);
            canvas.drawText(hintText,
                    getWidth() / 2 - mHintTextPaint.measureText(hintText) / 3,
                    getHeight() / 2 + mLoadingBar.getHeight() / 3,
                    mHintTextPaint);
        }

        /**
         * Bitmap缩放，注意源Bitmap在缩放后将会被回收。
         *
         * @param origin 原始图片
         * @param width  缩放后的宽
         * @param height 缩放后的高
         * @return 缩放后的图片
         */
        public Bitmap getScaleBitmap(Bitmap origin, int width, int height) {
            float originWidth = origin.getWidth();
            float originHeight = origin.getHeight();

            Matrix matrix = new Matrix();
            float scaleWidth = ((float) width) / originWidth;
            float scaleHeight = ((float) height) / originHeight;

            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap scale = Bitmap.createBitmap(origin, 0, 0, (int) originWidth,
                    (int) originHeight, matrix, true);
            origin.recycle();
            return scale;
        }

        /**
         * 将dip转为px
         */
        public int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }


        /**
         * 显示加载
         */
        public void show() {
            mAnimRunnable.setAnimState(AnimState.SHOWING);
        }

        /**
         * 隐藏加载
         */
        public void dismiss(OnLoadingDismissListener listener) {
            mDismissListener = listener;
            mAnimRunnable.setAnimState(AnimState.DISMISSING);
        }

        /**
         * 动画执行器
         *
         * @author gaoxu
         *
         */
        private class AnimRunnable implements Runnable {

            /** 当前动画状态 */
            private AnimState mAnimState = AnimState.NONE;
            /** 下一步动画状态 */
            private AnimState mNextState = AnimState.NONE;

            /** 总动画帧数 */
            private int mTotalFrame;
            /** 当前执行到的帧数 */
            private int mCurrentFrame;

            /**
             * 更新当前动画状态
             */
            void setAnimState(AnimState animState) {
                if (mAnimState == AnimState.NONE
                        && animState == AnimState.SHOWING) {
                    reset();
                    mAnimState = AnimState.SHOWING;
                    mNextState = AnimState.NONE;

                    mTotalFrame = (int) (ANIM_DUTAION_SHOW / 1000 * FPS);
                    post(this);
                }

                if (mAnimState == AnimState.SHOWING
                        && animState == AnimState.PROGRESSING) {
                    reset();
                    mAnimState = AnimState.PROGRESSING;
                    post(this);
                }

                if (mAnimState == AnimState.SHOWING
                        && animState == AnimState.DISMISSING) {
                    mNextState = AnimState.DISMISSING;
                } else if (animState == AnimState.DISMISSING) {
                    reset();
                    mAnimState = AnimState.DISMISSING;

                    mTotalFrame = (int) ((ANIM_DELAY + ANIM_DUTAION_DISMISS) / 1000 * FPS);
                    post(this);
                }
            }

            /**
             * 获取当前动画执行状态
             */
            AnimState getAnimState() {
                return mAnimState;
            }

            void reset() {
                removeCallbacks(this);
                mAnimState = AnimState.NONE;
                mCurrentFrame = 0;
            }

            @Override
            public void run() {
                mProgressBarProgress += LOADING_PROGRESS_ANIM_STEP;
                mProgressBarProgress %= 1;

                if (mAnimState == AnimState.SHOWING) {
                    float progress = (float) mCurrentFrame / mTotalFrame;
                    mShadowAlpha = (int) (progress * SHADOW_ALPHA);

                }

                if (mAnimState == AnimState.DISMISSING) {
                    int delayFrame = (int) (ANIM_DELAY / 1000 * FPS);
                    int realFrame = mCurrentFrame - delayFrame;
                    if (realFrame > 0) {
                        float progress = (float) (mCurrentFrame - delayFrame)
                                / (mTotalFrame - delayFrame);
                        mShadowAlpha = (int) ((1 - progress) * SHADOW_ALPHA);
                    }
                }

                if (mAnimState == AnimState.PROGRESSING) {
                    mShadowAlpha = SHADOW_ALPHA;
                }

                if (mAnimState != AnimState.PROGRESSING) {
                    if (mCurrentFrame < mTotalFrame) {
                        mCurrentFrame++;
                    } else {
                        if (mAnimState == AnimState.SHOWING) {
                            if (mNextState == AnimState.NONE) {
                                setAnimState(AnimState.PROGRESSING);
                            } else {
                                reset();
                                setAnimState(AnimState.DISMISSING);
                            }
                        } else if (mAnimState == AnimState.DISMISSING) {
                            reset();
                            if (mDismissListener != null) {
                                mDismissListener.onFinish();
                            }
                        }
                    }
                }

                if (mAnimState != AnimState.NONE) {
                    invalidate();
                    postDelayed(this, 1000 / FPS);
                }
            }
        }

        @Override
        protected void onAttachedToWindow() {
            AnimState state = mAnimRunnable.getAnimState();
            mAnimRunnable.reset();
            mAnimRunnable.setAnimState(state);
            super.onAttachedToWindow();
        }

        @Override
        protected void onDetachedFromWindow() {
            mAnimRunnable.reset();
            super.onDetachedFromWindow();
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            return true;
        }

    }

    /**
     * 动画状态类型，驶入、驶离、运行中
     */
    private enum AnimState {
        SHOWING, DISMISSING, PROGRESSING, NONE
    }

    /**
     * 消失动画结束后的回调接口
     */
    private interface OnLoadingDismissListener {
        /** 动画执行完毕 */
        void onFinish();
    }
}

