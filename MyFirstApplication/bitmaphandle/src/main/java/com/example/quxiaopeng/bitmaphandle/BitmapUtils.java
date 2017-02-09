package com.example.quxiaopeng.bitmaphandle;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

/**
 * Created by quxiaopeng on 2017/1/17.
 */

public class BitmapUtils {

    public static Bitmap handleImageNegative(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        Log.i("hahahaha", width + "," + height);
        int color;
        int r,g,b,a;
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            r = 255 - r;
            g = 255 - g;
            b = 255 - b;


            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }

            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }

            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }

            newPx[i] = Color.argb(a,r,g,b);
        }

        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    public static Bitmap handleImageOld(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color;
        int r,g,b,a;
        int r1, g1, b1, a1;
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            r1 = (int) (0.393 * r + 0.769 * g + 0.189 * b);
            g1 = (int) (0.349 * r + 0.686 * g + 0.168 * b);
            b1 = (int) (0.272 * r + 0.534 * g + 0.131 * b);
            a1 = a;

            if (r1 > 255) {
                r1 = 255;
            } else if (r1 < 0) {
                r1 = 0;
            }

            if (g1 > 255) {
                g1 = 255;
            } else if (g1 < 0) {
                g1 = 0;
            }

            if (b1 > 255) {
                b1 = 255;
            } else if (b1 < 0) {
                b1 = 0;
            }

            newPx[i] = Color.argb(a1,r1,g1,b1);
        }

        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    public static Bitmap handleImageFuDiao(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color1, color2;
        int r,g,b,a;
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 0; i < width * height - 1; i++) {
            color1 = oldPx[i];
            color2 = oldPx[i + 1];

            a = Color.alpha(color1);
            r = Color.red(color2) - Color.red(color1) + 127;
            g = Color.green(color2) - Color.green(color1) + 127;
            b = Color.blue(color2) - Color.blue(color1) + 127;

            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }

            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }

            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }

            newPx[i] = Color.argb(a,r,g,b);
        }

        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }
}
