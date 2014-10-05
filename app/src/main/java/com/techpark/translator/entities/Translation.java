package com.techpark.translator.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrew on 05.10.14.
 */
public class Translation {
    private int code;
    private String translated;
    public static void parseTranslation(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
