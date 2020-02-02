package com.example.passwordkeeper.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.passwordkeeper.Fragments.AccessCodeFragment;
import com.example.passwordkeeper.Fragments.FirstSetupFragment;
import com.example.passwordkeeper.LocalDatabaseIO;
import com.example.passwordkeeper.R;

public class Login_SetupActivity extends AppCompatActivity {


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final LocalDatabaseIO localDB = new LocalDatabaseIO(this);

		setContentView(R.layout.activity_authentication_setup);

		if (localDB.isFirstAccess()) {
			getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new FirstSetupFragment()).commit();
		} else {
			getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new AccessCodeFragment()).commit();
		}
	}
}