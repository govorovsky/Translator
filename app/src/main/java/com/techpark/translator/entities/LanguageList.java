package com.techpark.translator.entities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by andrew on 05.10.14.
 */
public class LanguageList {

    public static class LanguageListEntry {
        String name;
        String shortcut;

        public LanguageListEntry(String name, String shortcut) {
            this.name = name;
            this.shortcut = shortcut;
        }

        @Override
        public String toString() {
            return shortcut + ":" + name;
        }
    }

    private static ArrayList<LanguageListEntry> mLanguageList = new ArrayList<>();

    public static void parseList(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonLangs = jsonObject.getJSONObject("langs");
            Iterator<?> lang = jsonLangs.keys();
            while (lang.hasNext()) {
                String langShortcut = (String) lang.next();
                String langName = (String) jsonLangs.get(langShortcut);
                mLanguageList.add(new LanguageListEntry(langName, langShortcut));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

    }

    public static List<LanguageListEntry> getLanguageList() {
        return mLanguageList;
    }
}
