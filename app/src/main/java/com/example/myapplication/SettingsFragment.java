package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class SettingsFragment extends Fragment {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    private EditText dailyRemind;
    private EditText maxDist;
    private EditText genderPref;
    private EditText accountVis;
    private EditText ageRangePref;

    private SettingsViewModel settingsViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        dailyRemind = view.findViewById(R.id.input_daily_reminder);
        maxDist = view.findViewById(R.id.input_max_dist);
        genderPref = view.findViewById(R.id.input_gender);
        accountVis = view.findViewById(R.id.input_acct_vis);
        ageRangePref = view.findViewById(R.id.input_age_range_desired);
        Button saveSettings = view.findViewById(R.id.save_settings_button);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        Intent intent = requireActivity().getIntent();
        Bundle b = intent.getExtras();

        final Observer<List<SettingsEntity>> getSettingsObserver = newSettings -> {
            if (newSettings == null || newSettings.size() <= 0) {
                return;
            }
            SettingsEntity setting = newSettings.get(0);

            if (setting == null) {
                return;
            }

            dailyRemind.setText(setting.getTime());
            maxDist.setText(setting.getMaxDist());
            genderPref.setText(setting.getGender());
            accountVis.setText(setting.getAcct_vis());
            ageRangePref.setText(setting.getAgeRange());
        };


        String[] emails = {getString(R.string.empty_string)};
        if(b != null) {
            if(b.containsKey(Constants.KEY_EMAIL)) {
                emails[0] = b.getString(Constants.KEY_EMAIL);
            }
        }


        saveSettings.setOnClickListener(v -> {

            Log.i(TAG, "click");

            SettingsEntity settingsEntity = new SettingsEntity();

            settingsEntity.setEmail(emails[0]);
            settingsEntity.setTime(dailyRemind.getText().toString());
            settingsEntity.setMaxDist(maxDist.getText().toString());
            settingsEntity.setGender(genderPref.getText().toString());
            settingsEntity.setAcct_vis(accountVis.getText().toString());
            settingsEntity.setAgeRange(ageRangePref.getText().toString());

            settingsViewModel.insertAll(view.getContext(), settingsEntity);
            settingsViewModel.updateSettings(getContext(), settingsEntity);

            dailyRemind.setText(settingsEntity.getTime());
            maxDist.setText(settingsEntity.getMaxDist());
            genderPref.setText(settingsEntity.getGender());
            accountVis.setText(settingsEntity.getAcct_vis());
            ageRangePref.setText(settingsEntity.getAgeRange());

            Toast toast = Toast.makeText(view.getContext(), getString(R.string.settings_saved), Toast.LENGTH_SHORT);
            toast.show();

        });



        settingsViewModel.loadAllByIds(getContext(), emails).observe(getViewLifecycleOwner(), getSettingsObserver);

        return view;
    }


}

