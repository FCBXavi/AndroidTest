package com.example.quxiaopeng.pulltorefreshlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by quxiaopeng on 15/8/12.
 */
public class MyListView extends ListView {

    private LayoutInflater mInflater;


    public final static int NONE = 0;
    public final static int PULL_TO_REFRESH = 1;
    public final static int RELEASE_TO_REFRESH = 2;
    public final static int REFRSHING = 3;

    //当前第一个可见的Item的位置
    private int mfirstVisibleItem=0;
    //标记第一个出现的Item
    boolean isRemark;
    //当前状态
    private int mState;
    private int mscrollState;

    private int mtotalItemCount;

    int startY;
    int headerHeight;





    //HeaderView
    private LinearLayout mHeaderView;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private TextView mTips;
    private TextView mTime;

    public MyListView(Context context) {
        super(context);
        init(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mHeaderView = (LinearLayout) mInflater.inflate(R.layout.header, null);
        headerHeight =mHeaderView.getHeight();


        measureView(mHeaderView);
        Log.i("height", headerHeight + "");
        setPaddingTop(-headerHeight);


        addHeaderView(mHeaderView, null, false);

        setOnScrollListener(new ScrollListener());


    }

    private void measureView(View view) {

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
        int height;
        int temp = layoutParams.height;
        if (temp > 0) {
            height=MeasureSpec.makeMeasureSpec(temp,MeasureSpec.EXACTLY);
        }
        else{
            height=MeasureSpec.makeMeasureSpec(temp,MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }

    public void setPaddingTop(int paddingTop){
        mHeaderView.setPadding(mHeaderView.getPaddingLeft(), paddingTop, mHeaderView.getPaddingRight(), mHeaderView.getPaddingBottom());
        mHeaderView.invalidate();
    }
    public class ScrollListener implements OnScrollListener{
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            mscrollState=scrollState;

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            mfirstVisibleItem=firstVisibleItem;
            mtotalItemCount=totalItemCount;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(mfirstVisibleItem==0){
                    isRemark=true;
                    startY=(int)ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if(mState==RELEASE_TO_REFRESH){
                    mState=REFRSHING;
                    refresh();
                }
                else if(mState==PULL_TO_REFRESH){
                    mState=NONE;
                    isRemark=false;
                    refresh();
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    public void onMove(MotionEvent ev){
        int tempY=(int)ev.getY();
        int space=tempY-startY;
        int topPadding=space- headerHeight;
        if(!isRemark){
            return;
        }
        switch (mState){
            case NONE:
                if(space>0){
                    mState=PULL_TO_REFRESH;
                    refresh();
                }
                break;
            case PULL_TO_REFRESH:
                setPaddingTop(topPadding);
                if(space>headerHeight+30){
                    mState=RELEASE_TO_REFRESH;
                    refresh();
                }
                break;
            case RELEASE_TO_REFRESH:
                setPaddingTop(topPadding);
                if(space<headerHeight+30){
                    mState=PULL_TO_REFRESH;
                    refresh();
                }
                else if(space<=0){
                    mState=NONE;
                    isRemark=false;
                    refresh();
                }

        }

    }

    public void refresh(){
        mImageView = (ImageView) mHeaderView.findViewById(R.id.iv_image);
        mProgressBar = (ProgressBar) mHeaderView.findViewById(R.id.pb_bar);
        mTips = (TextView) mHeaderView.findViewById(R.id.tv_tips);
        mTime = (TextView) mHeaderView.findViewById(R.id.tv_time);

        RotateAnimation anim = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        anim.setFillAfter(true);
        RotateAnimation anim1 = new RotateAnimation(180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim1.setDuration(500);
        anim1.setFillAfter(true);
        Log.i("height", headerHeight + "");
        switch (mState) {
            case NONE:
                setPaddingTop(-headerHeight);
                mImageView.clearAnimation();
                break;
            case PULL_TO_REFRESH:
                mImageView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mTips.setText("下拉可以刷新");
                mImageView.clearAnimation();
                mImageView.setAnimation(anim1);
                break;
            case RELEASE_TO_REFRESH:
                mImageView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mTips.setText("松开可以刷新");
                mImageView.clearAnimation();
                mImageView.setAnimation(anim);
                break;
            case REFRSHING:
                setPaddingTop(50);
                mImageView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                mImageView.clearAnimation();
                mTips.setText("正在刷新...");
                break;
        }
    }
}
