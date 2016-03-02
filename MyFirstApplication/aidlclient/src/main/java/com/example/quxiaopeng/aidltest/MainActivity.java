package com.example.quxiaopeng.aidltest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.textview);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("startcalculate");
                intent.setPackage("com.example.quxiaopeng.aidltest");
                bindService(intent, mConn, Context.BIND_AUTO_CREATE);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                try {
                    i = myServiceAIDLinstance.add(2, 3);
                    Log.i("i", i + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textView.setText(i + "");
            }
        });
    }

    myServiceAIDL myServiceAIDLinstance;

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("client", "connect service start");
            myServiceAIDLinstance = myServiceAIDL.Stub.asInterface(service);
            Log.i("client", "connect service");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("client", "disconnect service");
            myServiceAIDLinstance = null;
        }
    };


}
