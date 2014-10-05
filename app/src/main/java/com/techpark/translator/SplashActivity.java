package com.techpark.translator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.techpark.translator.services.LanguageListFetcherService;

/**
 * Created by andrew on 05.10.14.
 */
public class SplashActivity extends Activity {
    private LanguageListReceiver mLanguageListReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("HERERE", "RERRE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        IntentFilter intentFilter = new IntentFilter(LanguageListFetcherService.LIST_FETCH);
        mLanguageListReceiver = new LanguageListReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mLanguageListReceiver, intentFilter);
        if (savedInstanceState == null) {
            startService(new Intent(this, LanguageListFetcherService.class));
        }
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mLanguageListReceiver);
        super.onDestroy();
    }

    private class LanguageListReceiver extends BroadcastReceiver {
        LanguageListReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent activityIntent = new Intent(SplashActivity.this, MainActivity.class);

            SplashActivity.this.startActivity(activityIntent);
            SplashActivity.this.finish();
        }
    }
}
