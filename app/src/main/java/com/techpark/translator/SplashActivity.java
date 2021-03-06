package com.techpark.translator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private Button mRetryButton;
    private TextView mLoadingStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRetryButton = (Button) findViewById(R.id.retry_btn);
        mLoadingStr = (TextView) findViewById(R.id.loading);


        IntentFilter intentFilter = new IntentFilter(LanguageListFetcherService.LIST_FETCH);
        mLanguageListReceiver = new LanguageListReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mLanguageListReceiver, intentFilter);
        if (savedInstanceState == null) {
            if (!LanguageList.isLoaded) {
                startFetchingList();
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        launchMainActivity();
                    }
                }, 1000);
            }
        }
    }

    private void startFetchingList() {
        mProgressBar.setVisibility(View.VISIBLE);
        mLoadingStr.setVisibility(View.VISIBLE);
        startService(new Intent(this, LanguageListFetcherService.class));

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
                /* we cant fetch list, lets try to retry... */
                mProgressBar.setVisibility(View.INVISIBLE);
                mLoadingStr.setVisibility(View.INVISIBLE);
                mRetryButton.setVisibility(View.VISIBLE);
                mRetryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFetchingList();
                    }
                });
                Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show();
            } else {
                launchMainActivity();
            }
        }
    }
}
