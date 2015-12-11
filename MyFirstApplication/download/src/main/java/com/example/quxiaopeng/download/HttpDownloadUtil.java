package com.example.quxiaopeng.download;

import android.util.Log;

import com.ypy.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by quxiaopeng on 15/8/13.
 */
public class HttpDownloadUtil {

    int fileSize=0;
    int downloadSize=0;
    int startpos=0;

    String urlstr;
    String path;
    String fileName;

    //InputStream inputStream=null;

    public HttpDownloadUtil(String urlstr,String path,String fileName,int startpos){
        this.urlstr=urlstr;
        this.path=path;
        this.fileName=fileName;
        this.startpos=startpos;
        //inputStream=getInputStreamFromUrl(urlstr);
    }

    public String downloadFile(String urlstr){
        StringBuffer sb=new StringBuffer();
        try {
            InputStream inputStream=getInputStreamFromUrl(urlstr);
            if(inputStream!=null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    //函数返回整型, -1表示下载出错,0代表下载成功,1代表文件已经存在,2表示被暂停
    public int downloadFile(){

        Log.i("startpos",startpos+"");
        Log.i("fileSize",fileSize+"");
        FileUtils fileUtils=new FileUtils();
        InputStream inputStream=getInputStreamFromUrl(urlstr);
        if(inputStream==null&&fileSize==0){
            Log.i("dowloadFile","fail");
            return -1;
        }
        if (fileUtils.isExist(path+fileName)&&startpos==fileSize){
            Log.i("dowloadFile","exist");
            return 1;
        }
        else {
            downloadSize=fileUtils.writeToSDFromInputStream(path, fileName, inputStream,fileSize,startpos);
            startpos=downloadSize+1;
        }
        if(downloadSize==fileSize) {
            startpos=fileSize;
            Log.i("dowloadFile","success");
            return 0;
        }
        else {
            Log.i("dowloadFile","pause");
            return 2;
        }
    }

    public void continueDownloadFile(){
        this.downloadFile();
    }



    public InputStream getInputStreamFromUrl(String urlstr){

        InputStream inputStream=null;
        try {
            URL url = new URL(urlstr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept-Encoding", "identity");
            urlConnection.setRequestProperty("Range","bytes="+startpos+"-");
            if (startpos==0) {
                fileSize = urlConnection.getContentLength();
            }
            Log.i("size", fileSize + "");
            inputStream = urlConnection.getInputStream();
        }
        catch (Exception e){
            e.printStackTrace();
            Log.i("oh no ","get size Failed");
        }
        return inputStream;
    }

    public void pause(){
        EventBus.getDefault().post(new PauseEvent());
    }
    public void goon(){
        EventBus.getDefault().post(new GoonEvent());
    }
}
