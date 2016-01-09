package com.example.quxiaopeng.httpoktest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends Activity {

    String uriStr = "";
    SimpleDraweeView mSimpleDraweeView;
    EditText mEditText;
    Button confrimBtn;
    Button newsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        new OkHttpThread().start();
        initView();
    }

    class OkHttpThread extends Thread {
        @Override
        public void run() {
//            String str = "http://hq.sinajs.cn/list=sh601007";
            String str ="http://a.apix.cn/apixmoney/stockdata/stock?stockid=sz002230";
            try {

                URL url = new URL(str);
                OkHttpClient client = new OkHttpClient();
                //Post提交Json数据
//                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//                JSONObject jsonObject=new JSONObject();
//                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                // Request request = new Request.Builder().url(url).post(body).build();
                Request request = new Request.Builder().url(url).addHeader("apix-key", "54acc370057645d4722f78542800c0fe").build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    Log.i("response body:", response.body().string());
                    /*InputStream inputStream=response.body().byteStream();
                    StringBuilder sb=new StringBuilder();
                    BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
                    String line=br.readLine();
                    while (line!=null){
                        sb.append(line);
                        line=br.readLine();
                        Log.i("loop","dd");
                    }
                    Log.i("response bytestream",sb.toString());*/
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.img);
        mEditText = (EditText) findViewById(R.id.et_stock_num);
        confrimBtn = (Button) findViewById(R.id.btn_confirm);
        newsBtn = (Button) findViewById(R.id.btn_news);
        confrimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUri();
                mSimpleDraweeView.setImageURI(Uri.parse(uriStr));
            }
        });

        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this,WebActivity.class));
            }
        });
    }

    private void getUri() {
        String pre = "http://image.sinajs.cn/newchart/daily/n/";
        String num = mEditText.getText().toString();
        String after = ".gif";
        uriStr = pre + num + after;
    }
}
