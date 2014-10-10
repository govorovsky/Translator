package com.techpark.translator.entities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by andrew on 05.10.14.
 */
public class LanguageList {

    public static volatile boolean isLoaded = false;


    public static class LanguageListEntry implements Comparable<LanguageListEntry> {
        String name;
        String shortcut;

        public LanguageListEntry(String name, String shortcut) {
            this.name = name;
            this.shortcut = shortcut;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getName() {
            return name;
        }

        public String getShortcut() {
            return shortcut;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof LanguageListEntry)) return false;

            LanguageListEntry entry = (LanguageListEntry) o;

            return shortcut.equals(entry.shortcut);

        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + shortcut.hashCode();
            return result;
        }

        @Override
        public int compareTo(LanguageListEntry another) {
            return name.compareTo(another.name);
        }
    }

    private static ArrayList<LanguageListEntry> mLanguageList = new ArrayList<>(); /* all langs */
    private static ArrayList<LanguageListEntry> mFilteredLanguageList = new ArrayList<>();
    private static HashMap<String, LanguageListEntry> mShortcutToName = new HashMap<>();
    private static HashMap<String, ArrayList<LanguageListEntry>> mDirectionsMap = new HashMap<>();

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
        }
    }


    public static void parseDirections(String json) {
        try {
            for (LanguageListEntry entry : mLanguageList) {
                mShortcutToName.put(entry.getShortcut(), entry);
            }

            JSONObject jsonObject = new JSONObject(json);
            JSONArray directions = jsonObject.getJSONArray("dirs");
            for (int i = 0; i < directions.length(); i++) {
                String direction = directions.getString(i);
                String from = direction.substring(0, 2);
                String to = direction.substring(3);
                ArrayList<LanguageListEntry> listEntries = mDirectionsMap.get(from);
                if (listEntries == null) {
                    listEntries = new ArrayList<>();
                }
                listEntries.add(mShortcutToName.get(to));
                mDirectionsMap.put(from, listEntries);
            }

            filterLanguageList();
            isLoaded = true;

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private static void filterLanguageList() {
        /* creates list only with languages from which we can translate */
        for (Map.Entry<String, ArrayList<LanguageListEntry>> entry : mDirectionsMap.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                mFilteredLanguageList.add(mShortcutToName.get(entry.getKey()));
            }
        }
        Collections.sort(mFilteredLanguageList);
    }


    public static ArrayList<LanguageListEntry> getDirectionsForLang(String shortcut) {
        return mDirectionsMap.get(shortcut);
    }

    public static ArrayList<LanguageListEntry> getDirectionsForLang(int currentTranslateFrom) {
        return mDirectionsMap.get(mFilteredLanguageList.get(currentTranslateFrom).getShortcut());
    }

    public static LanguageListEntry getEntry(int pos) {
        return mFilteredLanguageList.get(pos);
    }

    public static ArrayList<LanguageListEntry> getLanguageList() {
        return mFilteredLanguageList;
    }
}
