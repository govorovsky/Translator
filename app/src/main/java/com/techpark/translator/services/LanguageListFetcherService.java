package com.techpark.translator.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.techpark.translator.entities.LanguageList;
import com.techpark.translator.network.NetworkUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by andrew on 05.10.14.
 */
public class LanguageListFetcherService extends IntentService {

    public static final String LIST_FETCH = "LIST_FETCH_BROADCAST";
    public static final String DATA = "data";

    private static final String LOG_TAG = LanguageListFetcherService.class.getName();


    public LanguageListFetcherService() {
        super("");
    }


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LanguageListFetcherService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("key", ApiConstants.API_KEY);
        urlParams.put("ui", "en");
        String response;
        int responseStatus = 0;
        try {
            response = NetworkUtils.httpGet(ApiConstants.LANGUAGE_LIST_URL, null, urlParams);
            Log.d(LOG_TAG, response);
            LanguageList.parseList(response);
            LanguageList.parseDirections(response);

        } catch (IOException e) {
            responseStatus = -1;
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent respIntent = new Intent(LIST_FETCH);
        respIntent.putExtra(ApiConstants.RESPONSE_STATUS, responseStatus);
        LocalBroadcastManager.getInstance(this).sendBroadcast(respIntent);
    }
}
