package com.techpark.translator.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import com.techpark.translator.R;
import com.techpark.translator.adapters.LanguageListAdapter;
import com.techpark.translator.entities.LanguageList;

/**
 * Created by andrew on 05.10.14.
 */
public class LanguagesListFragment extends Fragment {

    private OnLanguageSelected mCallBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.languages_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView list = (ListView) view.findViewById(R.id.languages_list);
        list.setAdapter(new LanguageListAdapter(getActivity(), R.layout.language_item, LanguageList.getLanguageList()));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.onSelect(position);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.promt_str);
        }
        if (activity instanceof OnLanguageSelected) {
            mCallBack = (OnLanguageSelected) activity;
        }
    }

    public interface OnLanguageSelected {
        void onSelect(int pos);

    }
}
