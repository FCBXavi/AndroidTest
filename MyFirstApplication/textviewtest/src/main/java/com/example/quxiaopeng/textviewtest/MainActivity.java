package com.example.quxiaopeng.textviewtest;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDisplaySize();
        TextView[] textViews = new TextView[5];
        textViews[0] = (TextView) findViewById(R.id.tv_textView1);
        textViews[1] = (TextView) findViewById(R.id.tv_textView2);
        textViews[2] = (TextView) findViewById(R.id.tv_textView3);
        textViews[3] = (TextView) findViewById(R.id.tv_textView4);
        textViews[4] = (TextView) findViewById(R.id.tv_textView5);
        for (int i = 0; i < textViews.length; i++) {
            measureView(textViews[i]);
        }
        Log.i("MainActivity", "textView width: " + textViews[0].getMeasuredWidth() + "");
        Log.i("MainActivity", "textSize: " + textViews[0].getTextSize() + "");
        TextPaint paint = new TextPaint();
        paint.setTextSize(textViews[0].getTextSize());
        String s = "你好你好你好你好你好你好你好你好你好你好你好你好你";
        Log.i("MainActivity", paint.measureText(s) + "");

        List<String> list = new ArrayList<>();
        list.add("你好");
        list.add("哈哈哈你好");
        list.add("撒手地撒地方你好请问热塔尔图俄塔尔图我热情为而且为台湾儿童");
        list.add("haajahaahahahahahahahhaa阿杜舒服阿萨德发撒地方dddd你好");
        list.add("adfaasdfasdfasfdsdafsafdAA阿萨德发第四十三的放放风你好纷纷反对似懂非懂所得税负担山东非法所得发达省份");

        String key = "你好";
        for (int i = 0; i < textViews.length; i++) {
            setText(list.get(i), textViews[i], key);
        }


    }

   private void setText(String string, TextView textView, String key) {
        int textViewLength = textView.getMeasuredWidth();
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(textView.getTextSize());
        int index = string.indexOf(key);
        //float textLength = textPaint.measureText(string);
        //需要高亮的所有部分都在显示范围内
        if (textPaint.measureText(string.substring(0, index + key.length())) <= textViewLength) {
            SpannableString spannableString = new SpannableString(string);
            spannableString.setSpan(new ForegroundColorSpan(Color.rgb(74, 144, 226)), index, index + key.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            textView.setText(spannableString);
        }
        //需要高亮的部分不能直接显示在范围内,需进行处理
        else {
            //高亮部分比能够显示的最大内容要长
            if (textPaint.measureText(key) >= textViewLength) {
                SpannableString spannableString = new SpannableString(key);
                spannableString.setSpan(new ForegroundColorSpan(Color.rgb(74, 144, 226)), 0, key.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString);
            } else {
                //高亮字段前面的部分
                char[] stringCharArray = string.substring(0, index).toCharArray();
                char[] keyCharArray = key.toCharArray();
                StringBuilder sb1 = new StringBuilder();//存储高亮字段
                //高亮字段不在末尾
                if (index+key.length()<string.length()){
                    sb1.append("...");
                }
                for (int i = keyCharArray.length - 1; i >= 0; i--) {
                    sb1.append(keyCharArray[i]);
                }
                sb1.append("...");
                StringBuilder sb2 = new StringBuilder();//存储剩余字段
                for (int i = 0; i <stringCharArray.length; i++) {
                    if (textPaint.measureText(sb1.toString()) + textPaint.measureText(sb2.toString()) +30 >= textViewLength) {
                        break;
                    }
                    sb2.append(stringCharArray[i]);
                }
                String result = sb2.toString()+sb1.reverse().toString();
                SpannableString spannableString = new SpannableString(result);
                spannableString.setSpan(new ForegroundColorSpan(Color.rgb(74, 144, 226)), result.indexOf(key), result.indexOf(key) + key.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString);
            }
        }
    }

    public void getDisplaySize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        Log.i("MainActivity", "width: " + width + " height: " + height);
    }

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
}
