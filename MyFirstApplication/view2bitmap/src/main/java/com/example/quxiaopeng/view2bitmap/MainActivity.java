package com.example.quxiaopeng.view2bitmap;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private View layoutCard;
    private Button btnCreateBitmap;
    private ImageView ivBitmap;

    private static String TAG = "quxiaopeng";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutCard = findViewById(R.id.layout_share_card);
        btnCreateBitmap = findViewById(R.id.btn_create_bitmap);
        ivBitmap = findViewById(R.id.iv_bitmap);
//      Button默认elevation2dp，translationZ 4dp
        btnCreateBitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = convertViewToBitmap(layoutCard);
                ivBitmap.setImageBitmap(bitmap);
                saveBitmapToSDCard(bitmap);
                if (Build.VERSION.SDK_INT >= 21) {
                    Log.i(TAG, "button TranslationZ : " + btnCreateBitmap.getTranslationZ());
                }
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            Log.i(TAG, "button TranslationZ : " + btnCreateBitmap.getTranslationZ());
        }
        ImageView ivMask = findViewById(R.id.iv_mask);
        ivMask.bringToFront();
        ivMask.setElevation(5);

    }


    private Bitmap convertViewToBitmap(View view) {
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        }
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    /**
     * 保存图片到SD卡(该目录会显示图片)
     *
     * @param bitmap 图片的bitmap对象
     */
    public String saveBitmapToSDCard(Bitmap bitmap) {
        if (bitmap == null) return "";
        FileOutputStream fileOutputStream = null;

        String imagePath = Environment.getExternalStorageDirectory().getPath() + "/QXP/photo/";
        createDirFile(imagePath);

        String fileName = UUID.randomUUID().toString() + ".jpg";
        String newFilePath = imagePath + fileName;
        File file = createNewFile(newFilePath);
        if (file == null)  return "";
        try {
            fileOutputStream = new FileOutputStream(newFilePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fileOutputStream);
            galleryAddPic(newFilePath);
        } catch (FileNotFoundException e1) {
            newFilePath = "";
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                newFilePath = "";
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        return newFilePath;
    }

    /**
     * 创建根目录
     *
     * @param path 目录路径
     */
    public void createDirFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return 创建的文件
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public File createNewFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return file;
    }

    /** 发送广播,扫描图片,让系统知道增加了一张图片 */
    public void galleryAddPic(String path) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(new File(path)));
        this.sendBroadcast(mediaScanIntent);
    }

    /**
     * 获取屏幕高度，px
     */
    public int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取屏幕宽度，px
     */
    public int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }
}
