package com.techpark.translator.entities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by andrew on 05.10.14.
 */
public class LanguageList {

    public static class LanguageListEntry implements  Comparable<LanguageListEntry>{
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

        public String getName() {
            return name;
        }

        public String getShortcut() {
            return shortcut;
        }

        @Override
        public int compareTo(LanguageListEntry another) {
            return name.compareTo(another.name);
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
            Collections.sort(mLanguageList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static LanguageListEntry getEntry(int pos) {
        return mLanguageList.get(pos);
    }

    public static List<LanguageListEntry> getLanguageList() {
        return mLanguageList;
    }
}
