package com.example.quxiaopeng.circularfloatingactionmenutest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFloatingButton();

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_pager);
        views = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.fragment1, null);
        View view2 = inflater.inflate(R.layout.fragment2, null);
        View view3 = inflater.inflate(R.layout.fragment3, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);

        viewPager.setAdapter(new MyAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);

    }

    public void setFloatingButton() {
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon));
        FloatingActionButton floatingActionButton = new FloatingActionButton.Builder(this).setContentView(imageView).build();
        //FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.fButton);
        //create menu items
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageDrawable(getResources().getDrawable(R.drawable.mail));
        SubActionButton itemButton1 = itemBuilder.setContentView(itemIcon).build();
        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageDrawable(getResources().getDrawable(R.drawable.mail));
        SubActionButton itemButton2 = itemBuilder.setContentView(itemIcon2).build();
        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageDrawable(getResources().getDrawable(R.drawable.mail));
        SubActionButton itemButton3 = itemBuilder.setContentView(itemIcon3).build();
        //create manu with items
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this).addSubActionView(itemButton1).addSubActionView(itemButton2).addSubActionView(itemButton3).attachTo(floatingActionButton).build();

        /*
        *  private static void centerHorizontal(View child, LayoutParams params, int myWidth) {
        int childWidth = child.getMeasuredWidth();
        int left = (myWidth - childWidth) / 2;

        params.mLeft = left;
        params.mRight = left + childWidth;
    }
        * */
//        measureView(floatingActionButton);
        int childWidth = floatingActionButton.getLayoutParams().width;
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) floatingActionButton.getLayoutParams();
        params.leftMargin = (screenWidth-childWidth)/2;
        params.rightMargin = params.leftMargin;
        floatingActionButton.setLayoutParams(params);
       // floatingActionButton.invalidate();
    }

    /**
     * 计算View的width以及height
     * 前提条件是child的LayoutParams不为null，否则可能会crash
     * @param child
     */
    public static void measureView(View child) {
        ViewGroup.LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0,
                params.width);
        int lpHeight = params.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight,
                    View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }




    class MyAdapter extends PagerAdapter {

        private List<View> views;

        public MyAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }
    }
}
