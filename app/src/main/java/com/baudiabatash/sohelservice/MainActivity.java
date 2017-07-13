package com.baudiabatash.sohelservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Intent intent;

    private MyService myService;
    private boolean isServiceBound;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        intent = new Intent(this,MyService.class);

        Log.d("MyService",Thread.currentThread().getId()+" "+Thread.currentThread().getName());
    }

    public void startService(View view) {
        startService(intent);
    }

    public void stopService(View view) {
        stopService(intent);
    }

    public void bindService(View view) {
        bindService();
    }


    public void unBindService(View view) {
        if(isServiceBound){
            unbindService(serviceConnection);
            isServiceBound= false;
        }
    }

    public void getRandomNumber(View view) {
        if(isServiceBound){
            textView.setText("The Random Number is "+myService.getRandomNumber());
        }else{
            textView.setText("Service is not Bound");
        }
    }


    private void bindService() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyService.MyServiceBinder binder = (MyService.MyServiceBinder) service;
                myService = binder.getService();
                isServiceBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isServiceBound= false;

            }
        };

        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

}
