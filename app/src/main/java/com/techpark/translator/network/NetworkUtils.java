package com.techpark.translator.network;

import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 05.10.14.
 */
public class NetworkUtils {

    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;

    private static final String LOG_TAG = NetworkUtils.class.getName();

    public static String httpGet(String url) throws IOException {
        return httpGet(url, null, null);
    }

    public static String httpGet(String url, HashMap<String, String> headers, HashMap<String, String> urlParams) throws IOException {

        if (urlParams != null) {
            StringBuilder builder = new StringBuilder(url);
            builder.append("?");
            for (Map.Entry<String, String> header : urlParams.entrySet()) {
                builder.append(header.getKey()).append('=').append(URLEncoder.encode(header.getValue(), "UTF-8")).append('&');
            }
            builder.deleteCharAt(builder.length() - 1);
            url = builder.toString();
        }
        URL link = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) link.openConnection();
        urlConnection.setRequestMethod("GET");
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                urlConnection.setRequestProperty(header.getKey(), header.getValue());
            }
        }
        urlConnection.setReadTimeout(READ_TIMEOUT);
        urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
        urlConnection.connect();

        int code = urlConnection.getResponseCode();

        String resp = null;
        if (code == 200) {
            try (InputStream inputStream = urlConnection.getInputStream()) {
                resp = handleInputStream(inputStream);
            }
        }
        return resp;
    }

    private static String handleInputStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (String resp; (resp = bufferedReader.readLine()) != null; builder.append(resp)) ;
        return builder.toString();
    }
}
