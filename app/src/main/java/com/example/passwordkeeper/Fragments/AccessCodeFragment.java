package com.example.passwordkeeper.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.passwordkeeper.Activities.MainActivity;
import com.example.passwordkeeper.R;
import com.example.passwordkeeper.Utility;

public class AccessCodeFragment extends Fragment implements View.OnClickListener {

	private AppCompatTextView mLblCode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_access_code, container, false);

		mLblCode = view.findViewById(R.id.lblCode);
		mLblCode.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void onTextChanged(CharSequence text, int start, int before, int count) {
				if (text.toString().length() == Utility.getAccessCode().length()) {
					if (text.toString().equals(Utility.getAccessCode())) {
						Intent intent = new Intent(getContext(), MainActivity.class);
						startActivity(intent);
					} else {
						Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
						mLblCode.startAnimation(shake);
					}
				}
			}
		});

		AppCompatButton btnNum0 = view.findViewById(R.id.btnNum0);
		btnNum0.setOnClickListener(this);
		AppCompatButton btnNum1 = view.findViewById(R.id.btnNum1);
		btnNum1.setOnClickListener(this);
		AppCompatButton btnNum2 = view.findViewById(R.id.btnNum2);
		btnNum2.setOnClickListener(this);
		AppCompatButton btnNum3 = view.findViewById(R.id.btnNum3);
		btnNum3.setOnClickListener(this);
		AppCompatButton btnNum4 = view.findViewById(R.id.btnNum4);
		btnNum4.setOnClickListener(this);
		AppCompatButton btnNum5 = view.findViewById(R.id.btnNum5);
		btnNum5.setOnClickListener(this);
		AppCompatButton btnNum6 = view.findViewById(R.id.btnNum6);
		btnNum6.setOnClickListener(this);
		AppCompatButton btnNum7 = view.findViewById(R.id.btnNum7);
		btnNum7.setOnClickListener(this);
		AppCompatButton btnNum8 = view.findViewById(R.id.btnNum8);
		btnNum8.setOnClickListener(this);
		AppCompatButton btnNum9 = view.findViewById(R.id.btnNum9);
		btnNum9.setOnClickListener(this);
		AppCompatImageButton btnDeleteLast = view.findViewById(R.id.btnDeleteLast);
		btnDeleteLast.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {
		String text = "" + mLblCode.getText();

		if (text.length() < 12) {
			mLblCode.append(((AppCompatButton) v).getText());
		}

		if (v.getId() == R.id.btnDeleteLast) {
			if (text.length() > 0) {
				text = text.substring(0, text.length() - 1);
				mLblCode.setText(text);
			}
		}
	}
}
