package com.example.passwordkeeper.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passwordkeeper.LocalDatabaseIO;
import com.example.passwordkeeper.Utility;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LocalDatabaseIO localDB = new LocalDatabaseIO(this);

		Utility.DataSet = localDB.getAllData();
		int accessCode = localDB.getAccessCode();

		if (accessCode != -1)
			Utility.setAccessCode(accessCode);
		else
			Toast.makeText(this, "An error occurred, unable to recover data. Please try again later...", Toast.LENGTH_LONG).show();

		Intent intent = new Intent(this, AuthenticationActivity.class);
		startActivity(intent);
	}
}
