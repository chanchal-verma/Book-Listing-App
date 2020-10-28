package com.chanchal.sindhubhawanshaadi.layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class bookSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_settings);
    }

        public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

            @Override
            public boolean onPreferenceChange(Preference preference, Object value) {
                String stringValue = value.toString();
                preference.setSummary(stringValue);
                return true;
            }

            private void bindPreferenceSummaryToValue(Preference preference) {
                preference.setOnPreferenceChangeListener(this);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
                String preferenceString = preferences.getString(preference.getKey(), "");
                onPreferenceChange(preference, preferenceString);
            }

            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                addPreferencesFromResource(R.xml.settings_preferences);

                Preference title = findPreference(getString(R.string.settings_book_key));
                bindPreferenceSummaryToValue(title);
            }

    }
}