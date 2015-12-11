package com.example.quxiaopeng.asynctasktest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.tv_text);
        Button button=(Button)findViewById(R.id.btn_startAsyncTask);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAsyncTask task=new MyAsyncTask(getApplicationContext());
                task.execute("http://www.sina.com");
            }
        });
    }

    public class MyAsyncTask extends AsyncTask<String,Integer,String>{

        ProgressDialog progressDialog;

        public MyAsyncTask(Context context){
//            progressDialog = new ProgressDialog(context,0);
//            progressDialog.setButton("cancle", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    finish();
//                }
//            });
//            progressDialog.setCancelable(true);
//            progressDialog.setMax(100);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progressDialog.show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuilder sb=new StringBuilder();
            try {
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpGet httpGet = new HttpGet(params[0]);
//                HttpResponse response = httpClient.execute(httpGet);
//                HttpEntity entity=response.getEntity();
//                Log.i("length",entity.getContentLength()+"");
//                InputStream inputStream=entity.getContent();

                URL url=new URL(params[0]);
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=connection.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
                String line=br.readLine();
                while(line!=null){
                    sb.append(line);
                    line=br.readLine();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }


}
