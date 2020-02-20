package com.example.passwordkeeper.Activities;

import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.passwordkeeper.R;
import com.example.passwordkeeper.Utility;

public class SettingsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		SwitchCompat swItemLabels = findViewById(R.id.swItemLabels);
		swItemLabels.setChecked(Utility.Settings.isShowLabelsOnItems());
		swItemLabels.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Utility.Settings.setShowLabelsOnItems(isChecked);
			}
		});
	}
}