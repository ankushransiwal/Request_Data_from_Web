package com.example.android.restful;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.restful.services.MyService;
import com.example.android.restful.utils.NetworkHelper;

public class MainActivity extends AppCompatActivity {

    private static final String JSON_URL =
            "http://560057.youcanlearnit.net/services/json/itemsfeed.php";
    TextView output;

    private BroadcastReceiver mBroadcastReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(MyService.MY_SERVICE_PAYLOAD);
            output.append(message + "\n");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView) findViewById(R.id.output);

        //Create more  LocalboadcastManager and register for more number of messages which you want
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReciever,new IntentFilter(MyService.MY_SERVICE_MSG));
    }

    //Unregister the BroadcastManager to avoid memory leaks
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReciever);
    }

    public void runClickHandler(View view) {
        Intent i = new Intent(this,MyService.class);
        i.setData(Uri.parse(JSON_URL));
        startService(i);
        startService(i);
        startService(i);

    }

    public void clearClickHandler(View view) {
        output.setText("");
    }

}
