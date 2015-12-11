package com.example.quxiaopeng.palettetest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1= (ImageView) findViewById(R.id.iv_image1);
        imageView2= (ImageView) findViewById(R.id.iv_image2);
        imageView3= (ImageView) findViewById(R.id.iv_image3);
        imageView4= (ImageView) findViewById(R.id.iv_image4);
        textView1= (TextView) findViewById(R.id.tv_text1);
        textView2= (TextView) findViewById(R.id.tv_text2);
        textView3= (TextView) findViewById(R.id.tv_text3);
        textView4= (TextView) findViewById(R.id.tv_text4);

        Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.pic1);
        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(),R.drawable.pic2);
        Bitmap bitmap3= BitmapFactory.decodeResource(getResources(),R.drawable.pic3);
        Bitmap bitmap4= BitmapFactory.decodeResource(getResources(),R.drawable.pic4);


        //该方法不建议使用
//        Palette.generateAsync(bitmap1, new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                Palette.Swatch swatch=palette.getVibrantSwatch();
//                //Palette.Swatch swatch=palette.getLightMutedSwatch();
//                if (swatch!=null){
//                    textView1.setBackgroundColor(swatch.getRgb());
//                    textView1.setTextColor(swatch.getTitleTextColor());
//                }
//            }
//        });

        //同步
        //Palette palette=Palette.from(bitmap1).generate();
        //建议使用该方法
        Palette.from(bitmap1).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                if (swatch != null) {
                    textView1.setBackgroundColor(swatch.getRgb());
                    textView1.setTextColor(swatch.getTitleTextColor());
                }
            }
        });
        Palette.from(bitmap2).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                if (swatch != null) {
                    textView2.setBackgroundColor(swatch.getRgb());
                    textView2.setTextColor(swatch.getTitleTextColor());
                }
            }
        });
        Palette.from(bitmap3).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch=palette.getVibrantSwatch();
                if(swatch!=null){
                    textView3.setBackgroundColor(swatch.getRgb());
                    textView3.setTextColor(swatch.getTitleTextColor());
                }
            }
        });
        Palette.from(bitmap4).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch=palette.getVibrantSwatch();
                if(swatch!=null){
                    textView4.setBackgroundColor(swatch.getRgb());
                    textView4.setTextColor(swatch.getTitleTextColor());
                }
            }
        });
    }
}
