package com.example.quxiaopeng.bitmapfactorytest;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.iv_image);
        imageView.setImageBitmap(decodeSampleBitmapFromResource(getResources(), R.drawable.mail, dp2px(30), dp2px(30)));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        int height = options.outHeight;
        int width = options.outWidth;
        Log.i("height and width",height+"  "+width);
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            int heightRadio = Math.round((float) height / (float) reqHeight);
            int widthRaido = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRadio < widthRaido ? heightRadio : widthRaido;
        }
        return inSampleSize;

    }


    Bitmap decodeSampleBitmapFromResource(Resources resource, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resource, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        Log.i("inSampleSize", options.inSampleSize + "");
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resource, resId, options);

    }

    public int dp2px(int dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        Log.i("density", scale + "");
        return (int) (scale * dpValue + 0.5);
    }
}
