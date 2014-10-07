package com.techpark.translator.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrew on 05.10.14.
 */
public class Translation {
    private String translated = "";
    private int code;

    private Translation() {
    }

    public String getTranslated() {
        return translated;
    }

    public void setTranslated(String translated) {
        this.translated = translated;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static Translation parseTranslation(String json) {
        Translation translation = new Translation();
        try {
            if (json == null) {
                /*request failed */
                translation.setCode(-1);
                return translation;
            }
            JSONObject jsonObject = new JSONObject(json);
            translation.setCode(jsonObject.getInt("code"));
            translation.setTranslated((String) jsonObject.getJSONArray("text").get(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return translation;

    }
}
