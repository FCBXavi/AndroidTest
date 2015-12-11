package com.example.quxiaopeng.httpoktest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new OkHttpThread().start();
    }

    class OkHttpThread extends Thread {
        @Override
        public void run() {
            String str = "http://www.baidu.com";
            try {

                URL url = new URL(str);
                OkHttpClient client = new OkHttpClient();
                //Post提交Json数据
//                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//                JSONObject jsonObject=new JSONObject();
//                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
               // Request request = new Request.Builder().url(url).post(body).build();
                Request request = new Request.Builder().url(url).build();
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
}
