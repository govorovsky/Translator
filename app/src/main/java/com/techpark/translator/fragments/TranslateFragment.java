package com.techpark.translator.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.techpark.translator.R;
import com.techpark.translator.adapters.LanguageListAdapter;
import com.techpark.translator.entities.LanguageList;

/**
 * Created by andrew on 05.10.14.
 */
public class TranslateFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String LANG_BUNDLE = "lang";

    private TextView mTextViewToTransPromt;
    private TextView mTextViewTranslatedPromt;
    private Spinner mSpinner;
    private Button mTranslateButton;


    private int currentTranslateFrom;
    private int currentTranslateTo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.translate_fragment, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextViewTranslatedPromt = (TextView) view.findViewById(R.id.translated_promt);
        mTranslateButton = (Button) view.findViewById(R.id.translate_button);
        mTranslateButton.setOnClickListener(this);


        mSpinner = (Spinner) view.findViewById(R.id.translate_to_spinner);
        mSpinner.setAdapter(new LanguageListAdapter(getActivity(), R.layout.language_item, LanguageList.getLanguageList()));
        mSpinner.setOnItemSelectedListener(this);

        currentTranslateFrom = getArguments().getInt(LANG_BUNDLE);

        mTextViewToTransPromt = (TextView) view.findViewById(R.id.to_translate_promt);
        mTextViewToTransPromt.setText(LanguageList.getEntry(currentTranslateFrom).getName());

//        Log.d("LOG_TAG", Integer.toString(currentTranslateFrom));
//        Log.d("LOG_TAG", Integer.toString(currentTranslateTo));

    }

    public static TranslateFragment getInstance(int pos) {
        TranslateFragment transfrag = new TranslateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LANG_BUNDLE, pos);
        transfrag.setArguments(bundle);
        return transfrag;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mTextViewTranslatedPromt.setText(LanguageList.getEntry(position).getName());
        currentTranslateTo = position;
        Log.d("here", Integer.toString(mSpinner.getSelectedItemPosition()));
//        Log.d("LOG_TAG", Integer.toString(mSpinner.getSelectedItemPosition()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.translate_button:

                break;
        }
    }
}
