package com.example.passwordkeeper.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.passwordkeeper.R;
import com.example.passwordkeeper.Utility;

public class SettingsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.settings, new SettingsFragment())
				.commit();
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	public static class SettingsFragment extends PreferenceFragmentCompat {
		@Override
		public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
			setPreferencesFromResource(R.xml.main_preferences, rootKey);

			Preference changeCodePreference = findPreference("change_code");
			if (changeCodePreference != null)
				changeCodePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						Toast.makeText(getContext(), "[WIP]", Toast.LENGTH_SHORT).show();
						return true;
					}
				});

			Preference deleteAllPreference = findPreference("delete_all");
			if (deleteAllPreference != null)
				deleteAllPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {

						new AlertDialog.Builder(getContext())
								.setTitle("Delete all passwords")
								.setIcon(android.R.drawable.ic_dialog_alert)
								.setMessage("Are you sure you want to delete all the passwords?\n(This action is irreversible)")
								.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {

										// I empty the dataset and local DB
										Utility.DataSet.clear();
										Utility.getDatabaseIO().deleteAllItems();

										// I notify that the items in the dataset have been deleted
										Toast.makeText(getContext(), "All passwords have been deleted", Toast.LENGTH_LONG).show();
									}
								})
								.setNegativeButton("CANCEL", null)
								.show();

						return true;
					}
				});
		}
	}
}