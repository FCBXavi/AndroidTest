package com.example.quxiaopeng.frescotest;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class MainActivity extends Activity implements View.OnTouchListener {

    private SimpleDraweeView mSimpleDraweeView;
    private SimpleDraweeView mSimpleDraweeView2;
    private SimpleDraweeView mSimpleDraweeView3;
    private String imageUri1 = "https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/fresco-logo.png";
    private String imageUri2 = "http://ww1.sinaimg.cn/bmiddle/6aaeb4b8gw1evn5z29p4bg20b40b41kx.gif";
    private String imageUri3 = "http://p5.qhimg.com/t01d0e0384b952ed7e8.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        setImage();

    }

    /**
     * 设置自动播放图片 三张
     */
    private void setImage() {
        Uri uri = Uri.parse(imageUri1);
        Uri uri2 = Uri.parse(imageUri2);
        Uri uri3 = Uri.parse(imageUri3);

        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            //图片加载成功或者失败，会执行里面的方法，其中图片加载成功时会执行onFinalImageSet方法，图片加载失败时会执行onFailure方法，如果图片设置渐进式，onIntermediateImageFailed会被回调
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if (anim != null) {
                    anim.start();
                }
                Log.i("load picture", "success");
                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                FLog.d("Final image received! " +
                                "Size %d x %d",
                        "Quality level %d, good enough: %s, full quality: %s",
                        imageInfo.getWidth(),
                        imageInfo.getHeight(),
                        qualityInfo.getQuality(),
                        qualityInfo.isOfGoodEnoughQuality(),
                        qualityInfo.isOfFullQuality());
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                FLog.d("", "Intermediate image received");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                FLog.e(getClass(), throwable, "Error loading %s", id);
                Log.i("load picture", "failed");
            }

        };

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri).build();
        //设置DraweeView的属性
        //GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilder=new GenericDraweeHierarchyBuilder(getResources());
        //GenericDraweeHierarchy genericDraweeHierarchy = generucDraweeHierachy.build();
        //mSimpleDraweeView.setHierarchy(genericDraweeHierarchy);


        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.frsco_img1);
        DraweeController draweeController1 = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setControllerListener(controllerListener)
                .setAutoPlayAnimations(true)
                .setTapToRetryEnabled(true)
                .build();
        mSimpleDraweeView.setController(draweeController1);


        DraweeController draweeController2 = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setUri(uri2)
                .build();
        mSimpleDraweeView2 = (SimpleDraweeView) findViewById(R.id.frsco_img2);
        mSimpleDraweeView2.setController(draweeController2);
        RoundingParams mRoundParams2 = mSimpleDraweeView2.getHierarchy().getRoundingParams();
        mRoundParams2.setRoundAsCircle(true);
        mSimpleDraweeView2.getHierarchy().setRoundingParams(mRoundParams2);

        DraweeController draweeController3 = Fresco.newDraweeControllerBuilder()
                .setUri(uri3)
                .setControllerListener(controllerListener)
                .build();
        mSimpleDraweeView3 = (SimpleDraweeView) findViewById(R.id.frsco_img3);
        mSimpleDraweeView3.setController(draweeController3);
        RoundingParams mRoundParams3 = mSimpleDraweeView3.getHierarchy().getRoundingParams();
        mRoundParams3.setCornersRadius(100);
        mSimpleDraweeView3.getHierarchy().setRoundingParams(mRoundParams3);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mSimpleDraweeView.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                return true;
//                break;
            case MotionEvent.ACTION_UP:
                mSimpleDraweeView.clearColorFilter();
                return true;
//                break;
        }
        return super.onTouchEvent(event);
    }
}
