package com.example.quxiaopeng.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by quxiaopeng on 15/9/18.
 */
public class MyService extends Service{
    //@Nullable

    class MyServiceAIDLinstance extends myServiceAIDL.Stub{
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int add(int value1, int value2) throws RemoteException {
            return value1+value2;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("service","is start");
        return new MyServiceAIDLinstance();
    }


}
