package com.patrick.android.hotmovie.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.patrick.android.hotmovie.R;

public class SettingActivity extends AppCompatActivity  {
//private static final String KEY="pref_syncConnectionType";
    private static final String KEY="pref_sortOrder";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState==null){
            getFragmentManager().beginTransaction().add(R.id.activity_setting_container,new SettingFragment()).commit();
        }
    }







   public static class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

        }

       @Override
       public void onResume() {
           super.onResume();
           getPreferenceScreen().getSharedPreferences()
                   .registerOnSharedPreferenceChangeListener(this);

       }

       @Override
       public void onPause() {
           super.onPause();
           getPreferenceScreen().getSharedPreferences()
                   .unregisterOnSharedPreferenceChangeListener(this);

       }

       @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals(KEY)){
                Preference preference=findPreference(KEY);
                Log.i("sp",sharedPreferences.getString(key, ""));
                preference.setSummary(sharedPreferences.getString(key, ""));

            }
        }
    }
}
