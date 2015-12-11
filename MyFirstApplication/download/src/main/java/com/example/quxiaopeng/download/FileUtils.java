package com.example.quxiaopeng.download;

import android.os.Environment;
import android.util.Log;

import com.ypy.eventbus.EventBus;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Created by quxiaopeng on 15/8/13.
 */
public class FileUtils {

    private String SDPath;
    int startpos = 0;
    boolean isPaused = false;


    private String path;
    private String fileName;
    private InputStream inputStream;
    private int size;

    public FileUtils() {
        SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFile/";
        EventBus.getDefault().register(this);
    }

    //在SD卡上创建文件
    public File createFile(String fileName) {
        File file = new File(SDPath + fileName);
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //在SD卡上创建目录
    public File createSDDir(String filePath) {
        File file = new File(SDPath + filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    //判断SD卡上文件是否存在
    public boolean isExist(String fileName) {
        File file = new File(SDPath + fileName);
        return file.exists();
    }

    //将一个inputStream里到数据写到SD卡中
    public int writeToSDFromInputStream(String path, String fileName, InputStream inputStream, int size, int startpos) {
        int downloadSize = startpos;
        int len = 0;
        File file;
        if (isExist(path + fileName)) {
            createSDDir(path);
            file = createFile(path + fileName);
        }
        else {
            file=new File(SDPath+path+fileName);
        }

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            byte[] bytes = new byte[1024];
            randomAccessFile.seek(startpos);
            while ((len = inputStream.read(bytes)) != -1) {
                randomAccessFile.write(bytes);
                downloadSize += len;
                Log.i("downloadSize", downloadSize + "");
                EventType eventType = new EventType();
                eventType.progress = (double) downloadSize / size;
                if (downloadSize == size) {
                    eventType.success = true;
                }
                EventBus.getDefault().post(eventType);
                if (isPaused) {
                    startpos = downloadSize + 1;
                    return downloadSize;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       /* OutputStream outputStream=null;
        try {
            outputStream = new FileOutputStream(file);
            byte[] bytes=new byte[1024];
            while ((len = inputStream.read(bytes))!=-1){
                outputStream.write(bytes);
                downloadSize+=len;
                Log.i("downloadSize", downloadSize + "");
                EventType eventType=new EventType();
                eventType.progress=(double)downloadSize/size;
                EventBus.getDefault().post(eventType);
            }
        }

        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                outputStream.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }*/
        return downloadSize;
    }

    public void onEvent(PauseEvent pauseEvent) {
        isPaused = true;
    }

    public void onEvent(GoonEvent goonEvent) {
        isPaused = false;
        writeToSDFromInputStream(path, fileName, inputStream, size, startpos);
    }

}
