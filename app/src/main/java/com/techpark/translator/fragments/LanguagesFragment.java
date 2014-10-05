package com.techpark.translator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techpark.translator.R;

/**
 * Created by andrew on 05.10.14.
 */
public class LanguagesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.languages_list_fragment, container, false);
    }
}
