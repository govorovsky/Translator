package com.techpark.translator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.techpark.translator.entities.LanguageList;
import com.techpark.translator.services.ApiConstants;
import com.techpark.translator.services.LanguageListFetcherService;

import java.util.concurrent.TimeUnit;

/**
 * Created by andrew on 05.10.14.
 */
public class SplashActivity extends Activity {

    private LanguageListReceiver mLanguageListReceiver;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        IntentFilter intentFilter = new IntentFilter(LanguageListFetcherService.LIST_FETCH);
        mLanguageListReceiver = new LanguageListReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mLanguageListReceiver, intentFilter);
        if (savedInstanceState == null) {
            if (LanguageList.getLanguageList() == null) {
                startService(new Intent(this, LanguageListFetcherService.class));
            } else {
                launchMainActivity();
            }
        }
    }

    private void launchMainActivity() {
        Intent activityIntent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(activityIntent);
        SplashActivity.this.finish();
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
            if (intent.getIntExtra(ApiConstants.RESPONSE_STATUS, 0) < 0) {
                mProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show();
            } else {
                launchMainActivity();
            }
        }
    }
}
