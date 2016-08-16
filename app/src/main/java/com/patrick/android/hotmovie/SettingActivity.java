package com.patrick.android.hotmovie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity  {
private static final String KEY="pref_syncConnectionType";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals(KEY)){

               Preference preference=findPreference(KEY);
                preference.setSummary(sharedPreferences.getString(key, ""));

            }
        }
    }
}
