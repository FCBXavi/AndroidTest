package com.example.quxiaopeng.download;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ypy.eventbus.EventBus;

public class MainActivity extends Activity {

    Handler handler;
    double progress;
    HttpDownloadUtil httpDownloadUtil;
    boolean isPaused=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.tv_text);
        final Button downloadButton = (Button) findViewById(R.id.btn_download);
        final Button pauseButton=(Button)findViewById(R.id.btn_pause);
        pauseButton.setEnabled(false);


        httpDownloadUtil=new HttpDownloadUtil("http://nchc.dl.sourceforge.net/project/sevenzip/7-Zip/9.20/7z920.exe","","file.txt",0);

        final ProgressBar downloadProgress=(ProgressBar)findViewById(R.id.pb_progress);
        downloadProgress.setMax(100);
        downloadProgress.setProgress(0);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        textView.setText("下载成功");
                        pauseButton.setEnabled(false);
                        break;
                    case 1:
                        textView.setText("文件已存在");
                        break;
                    case 2:
                        textView.setText("暂停");
                        break;
                    case -1:
                        textView.setText("下载失败");
                        break;
                    case 3:
                        EventType eventType=(EventType)msg.obj;
                        if(!eventType.success) {
                            textView.setText("正在下载");
                            pauseButton.setEnabled(true);
                        }
                        else {
                            textView.setText("下载成功");
                            pauseButton.setEnabled(false);
                        }
                        downloadProgress.setProgress((int)(eventType.progress*100));
                        break;
                }
                super.handleMessage(msg);
            }
        };

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPaused){
                    new Thread(){
                        public void run(){
                            httpDownloadUtil.continueDownloadFile();
                        }
                    }.start();
                    pauseButton.setText("暂停");
                    isPaused=false;
                }
                else {
                    httpDownloadUtil.pause();
                    pauseButton.setText("继续");
                    isPaused=true;
                }

            }
        });

        EventBus.getDefault().register(this);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownLoadThread().start();
                /*String sdpath= Environment.getExternalStorageDirectory().toString()+"/";
                Log.i("SDCard",sdpath);
                File file=new File(sdpath,"MyFile.txt");
                try {
                    file.createNewFile();
                }
                catch (Exception e){
                    e.printStackTrace();
                }*/
            }
        });

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

    class DownLoadThread extends Thread {

//        private String urlstr;
//        private String fileName;
//
//        public DownLoadThread(String urlstr, String fileName) {
//            this.urlstr = urlstr;
//            this.fileName = fileName;
//        }

        @Override
        public void run() {


            Message message = handler.obtainMessage();
            message.what=httpDownloadUtil.downloadFile();
            handler.sendMessage(message);
            super.run();
        }
    }
    public void onEvent(EventType eventType){
        Log.i("downloadSizeInMain",eventType.progress+"");
        progress=eventType.progress;
        Message message=handler.obtainMessage();
        message.what=3;
        message.obj=eventType;
        handler.sendMessage(message);
    }



}
