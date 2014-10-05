package com.techpark.translator.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.techpark.translator.entities.LanguageList;
import com.techpark.translator.network.NetworkUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by andrew on 05.10.14.
 */
public class TranslateFetcherService extends IntentService {

    private static final String LOG_TAG = TranslateFetcherService.class.getName();

    public static final String TEXT = "TEXT";
    public static final String SRC_LANGUAGE = "SRC_LANG";
    public static final String DEST_LANGUAGE = "DEST_LANG";


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TranslateFetcherService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String text = intent.getStringExtra(TEXT);
        String src = intent.getStringExtra(SRC_LANGUAGE);
        String dst = intent.getStringExtra(DEST_LANGUAGE);

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("key", ApiConstants.API_KEY);
        urlParams.put("text", text);
        urlParams.put("lang", src + '-' + dst);

        String response;
        try {
            response = NetworkUtils.httpGet(ApiConstants.TRANSLATE_URL, null, urlParams);
            Log.d(LOG_TAG, response);
            LanguageList.parseList(response);

        } catch (IOException e) {
//            responseStatus = -1;
        }

    }
}
