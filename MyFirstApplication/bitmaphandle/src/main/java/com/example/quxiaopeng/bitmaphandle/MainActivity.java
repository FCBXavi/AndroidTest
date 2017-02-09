package com.example.quxiaopeng.bitmaphandle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView1;
    private ImageView mImageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
//        mImageView1 = (ImageView) findViewById(R.id.iv_image1);
//        mImageView2 = (ImageView) findViewById(R.id.iv_image2);
//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.erzong);
//        mImageView1.setImageBitmap(bitmap1);
//        Bitmap bitmap2 = BitmapUtils.handleImageNegative(bitmap1);
//        mImageView2.setImageBitmap(bitmap2);
    }
}
