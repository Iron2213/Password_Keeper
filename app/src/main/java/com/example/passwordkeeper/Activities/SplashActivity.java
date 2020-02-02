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

		Utility.setDatabaseIO(this);
		LocalDatabaseIO localDB = Utility.getDatabaseIO();

		Utility.DataSet = localDB.getAllData();
		String accessCode = localDB.getAccessCode();

		if (!localDB.isFirstAccess()) {
			if (!accessCode.isEmpty())
				Utility.setAccessCode(accessCode);
			else
				Toast.makeText(this, "An error occurred, unable to recover data. Please try again later...", Toast.LENGTH_LONG).show();
		}

		Intent intent = new Intent(this, Login_SetupActivity.class);
		startActivity(intent);
	}
}
