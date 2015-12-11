package com.example.quxiaopeng.lrucachetest;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    private LruCache<String, Bitmap> mMemoryCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.iv_image);

        //获取可用的内存最大值,使用内存超过这个值会抛出OutOfMemory异常
        //LruCache通过构造函数传入缓存值,以KB为单位
        int maxMemory = (int) (Runtime.getRuntime().maxMemory());
        Log.e("maxMemory", maxMemory + "");
        //使用最大可用内存值的1/8作为缓存的大小
        int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (mMemoryCache.get(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }


    public void loadBitmap(int resId, ImageView imageView) {
        String key = String.valueOf(resId);
        Bitmap bitmap = mMemoryCache.get(key);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.mail);
            BitmapWorkerTask task = new BitmapWorkerTask();
            task.execute(resId);
        }
    }


    int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        int height = options.outHeight;
        int width = options.outWidth;
        Log.i("height and width", height + "  " + width);
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

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Integer... params) {
            Bitmap bitmap = decodeSampleBitmapFromResource(getResources(), params[0], dp2px(100), dp2px(100));
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }
}
