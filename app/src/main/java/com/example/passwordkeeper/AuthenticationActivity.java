package com.example.passwordkeeper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

	private AppCompatTextView mCode;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.authentication_activity);

		final Context context = this;

		mCode = findViewById(R.id.lblCode);
		mCode.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().equals("1234")) {
					Intent intent = new Intent(context, MainActivity.class);
					startActivity(intent);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		Button btnNum0 = findViewById(R.id.btnNum0);
		btnNum0.setOnClickListener(this);
		Button btnNum1 = findViewById(R.id.btnNum1);
		btnNum1.setOnClickListener(this);
		Button btnNum2 = findViewById(R.id.btnNum2);
		btnNum2.setOnClickListener(this);
		Button btnNum3 = findViewById(R.id.btnNum3);
		btnNum3.setOnClickListener(this);
		Button btnNum4 = findViewById(R.id.btnNum4);
		btnNum4.setOnClickListener(this);
		Button btnNum5 = findViewById(R.id.btnNum5);
		btnNum5.setOnClickListener(this);
		Button btnNum6 = findViewById(R.id.btnNum6);
		btnNum6.setOnClickListener(this);
		Button btnNum7 = findViewById(R.id.btnNum7);
		btnNum7.setOnClickListener(this);
		Button btnNum8 = findViewById(R.id.btnNum8);
		btnNum8.setOnClickListener(this);
		Button btnNum9 = findViewById(R.id.btnNum9);
		btnNum9.setOnClickListener(this);
		ImageButton btnDeleteLast = findViewById(R.id.btnDeleteLast);
		btnDeleteLast.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnNum0:
				mCode.append("0");
				break;

			case R.id.btnNum1:
				mCode.append("1");
				break;

			case R.id.btnNum2:
				mCode.append("2");
				break;

			case R.id.btnNum3:
				mCode.append("3");
				break;

			case R.id.btnNum4:
				mCode.append("4");
				break;

			case R.id.btnNum5:
				mCode.append("5");
				break;

			case R.id.btnNum6:
				mCode.append("6");
				break;

			case R.id.btnNum7:
				mCode.append("7");
				break;

			case R.id.btnNum8:
				mCode.append("8");
				break;

			case R.id.btnNum9:
				mCode.append("9");
				break;

			case R.id.btnDeleteLast:
				String text = "" + mCode.getText();
				if (text.length() > 0) {
					text = text.substring(0, text.length() - 1);
					mCode.setText(text);
				}
				break;
		}
	}
}