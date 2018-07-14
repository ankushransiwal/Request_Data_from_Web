package com.example.android.restful.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.android.restful.utils.HttpHelper;

import java.io.IOException;

public class MyService extends IntentService {

    public static final String TAG = "MyService";



    public static final String MY_SERVICE_MSG = "myServiceMessage";
    public static final String MY_SERVICE_PAYLOAD = "myServicePayload";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @para m name(Removed) Used to name the worker thread, important only for debugging.
     */
    public MyService() {
        //Removing String arguments to make it simpler
        super("MyService");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        Uri uri = intent.getData();
        Log.i(TAG, "onHandleIntent: " + uri.toString());

        //Getting the response in background thread
        String response;
        try {
            response = HttpHelper.downloadUrl(uri.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Intent messageIntent = new Intent(MY_SERVICE_MSG);

        //Passing the response we got instead of dummy data
        messageIntent.putExtra(MY_SERVICE_PAYLOAD,response);

        //Make sure to include the one LocalBroadcastManager with the context one
        LocalBroadcastManager manager =
                LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);

    }

    //The instance of services is created whenever it is called and it is destroyed whenever onHandleIntent is served
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }
}
