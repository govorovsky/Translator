package com.techpark.translator.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.techpark.translator.MainActivity;
import com.techpark.translator.R;
import com.techpark.translator.SplashActivity;
import com.techpark.translator.adapters.LanguageListAdapter;
import com.techpark.translator.entities.LanguageList;
import com.techpark.translator.services.ApiConstants;
import com.techpark.translator.services.TranslateFetcherService;

/**
 * Created by andrew on 05.10.14.
 */
public class TranslateFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String LANG_BUNDLE = "lang";
    private static final String TO_BUNDLE = "to";
    private static final String FROM_BUNDLE = "from";

    private TextView mTextViewFrom;
    private TextView mTextViewTo;
    private Spinner mSpinner;
    private Button mTranslateButton;
    private ImageButton mSwapButton;
    private EditText mSourceText;
    private EditText mTranslatedtext;

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

        if (savedInstanceState != null) {
            currentTranslateFrom = savedInstanceState.getInt(FROM_BUNDLE);
            currentTranslateTo = savedInstanceState.getInt(TO_BUNDLE);
        } else {
            currentTranslateFrom = getArguments().getInt(LANG_BUNDLE);
        }

        mTextViewTo = (TextView) view.findViewById(R.id.translated_promt);
        mTranslateButton = (Button) view.findViewById(R.id.translate_button);
        mTranslateButton.setOnClickListener(this);
        mSwapButton = (ImageButton) view.findViewById(R.id.swap_button);
        mSwapButton.setOnClickListener(this);


        mSpinner = (Spinner) view.findViewById(R.id.translate_to_spinner);
        mSpinner.setAdapter(new LanguageListAdapter(getActivity(), R.layout.language_item, LanguageList.getLanguageList()));
        mSpinner.setOnItemSelectedListener(this);
        mSpinner.setSelection(currentTranslateTo);


        mTextViewFrom = (TextView) view.findViewById(R.id.to_translate_promt);
        mTextViewFrom.setText(LanguageList.getEntry(currentTranslateFrom).getName());


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
        mTextViewTo.setText(LanguageList.getEntry(position).getName());
        currentTranslateTo = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(FROM_BUNDLE, currentTranslateFrom);
        outState.putInt(TO_BUNDLE, currentTranslateTo);
        super.onSaveInstanceState(outState);

    }

    private void translate() {
        Intent translateIntent = new Intent(TranslateFetcherService.TRANSLATE_FETCH);
        translateIntent.putExtra(TranslateFetcherService.SRC_LANGUAGE, currentTranslateFrom);
        translateIntent.putExtra(TranslateFetcherService.DEST_LANGUAGE, currentTranslateTo);
        translateIntent.putExtra(TranslateFetcherService.TEXT, mSourceText.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.translate_button:


                break;
            case R.id.swap_button:
                mTextViewTo.setText(LanguageList.getEntry(currentTranslateFrom).getName());
                mTextViewFrom.setText(LanguageList.getEntry(currentTranslateTo).getName());
                mSpinner.setSelection(currentTranslateFrom);
                currentTranslateFrom ^= currentTranslateTo;
                currentTranslateFrom ^= (currentTranslateTo ^= currentTranslateFrom);
                break;
        }
    }

    private class TranslateReceiver extends BroadcastReceiver {
        TranslateReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra(ApiConstants.RESPONSE_STATUS, 0) < 0) {
                Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show();
            } else {
                /*TODO*/

            }
        }
    }
}
