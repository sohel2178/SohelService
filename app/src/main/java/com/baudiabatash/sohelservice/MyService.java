package com.baudiabatash.sohelservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by Genius 03 on 7/13/2017.
 */

public class MyService extends Service {
    private static final String LOG="MyService";

    private int mRandomNumber;
    private boolean mRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;

    private IBinder mBinder = new MyServiceBinder();

    class MyServiceBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG,"On Bind");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        mRandomGeneratorOn =true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                generateRandomNumber();
            }
        }).start();

        //Log.d(LOG,"In onStartCommand Thread Id "+Thread.currentThread().getId()+" "+Thread.currentThread().getName());
       // stopSelf();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(LOG,"Service is Stop From Activity");
        stopRandomGeneration();
        super.onDestroy();
    }


    private void generateRandomNumber(){
        while (mRandomGeneratorOn){
            try {
                Thread.sleep(1000);
                if(mRandomGeneratorOn){
                    mRandomNumber  = new Random().nextInt(MAX)+MIN;
                    Log.d(LOG,"Current Thread Id "+Thread.currentThread().getId()+" Random Number "+mRandomNumber);
                }
            } catch (InterruptedException e) {
               Log.d(LOG,"Thread Interrepted");
            }
        }
    }



    private void stopRandomGeneration(){
        mRandomGeneratorOn=false;
    }

    public int getRandomNumber(){
        return mRandomNumber;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(LOG,"On Unbind");
        return super.onUnbind(intent);
    }
}
