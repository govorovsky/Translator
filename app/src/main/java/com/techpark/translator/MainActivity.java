package com.techpark.translator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.techpark.translator.fragments.LanguagesListFragment;
import com.techpark.translator.fragments.TranslateFragment;


public class MainActivity extends FragmentActivity implements LanguagesListFragment.OnLanguageSelected {

    private TranslateFragment mTranslateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LanguagesListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelect(int pos) {
        mTranslateFragment = TranslateFragment.getInstance(pos);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mTranslateFragment).addToBackStack(null)
                .commit();
    }
}

