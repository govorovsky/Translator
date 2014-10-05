package com.techpark.translator.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.techpark.translator.entities.LanguageList;
import com.techpark.translator.network.NetworkUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by andrew on 05.10.14.
 */
public class LanguageListFetcherService extends IntentService {

    public static final String LIST_FETCH = "LIST_FETCH_BROADCAST";
    public static final String DATA = "data";

    private static final String LOG_TAG = LanguageListFetcherService.class.getName();


    public LanguageListFetcherService( ) {
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
        Log.d(LOG_TAG, "HERERRER");
        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("key", ApiConstants.API_KEY);
        urlParams.put("ui", "ru");
        String response;
        try {
            response = NetworkUtils.httpGet(ApiConstants.LANGUAGE_LIST_URL, null, urlParams);
            Log.d(LOG_TAG, response);
            LanguageList.parseList(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
        stopSelf(); // we fetch list only once
    }
}
