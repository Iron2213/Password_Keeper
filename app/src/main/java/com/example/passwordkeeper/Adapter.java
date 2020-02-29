package com.example.passwordkeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.CardHolder> {
	private Context mContext;

	@NonNull
	@Override
	public Adapter.CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view;
		mContext = parent.getContext();

		view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_cardview, parent, false);

		return new CardHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull Adapter.CardHolder holder, int position) {
		if (PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("labels_hide", false)) {
			holder.mLayLabels.setVisibility(View.GONE);
			holder.mDateLabel.setVisibility(View.GONE);
		}

		holder.mCardView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.item_spawn_animation));
		holder.mInsertionDate.setText(Utility.DataSet.get(position).getInsertionDate());
		holder.mSite.setText(Utility.DataSet.get(position).getSite());
		holder.mEmail.setText(Utility.DataSet.get(position).getEmail());
		holder.mPassword.setText(Utility.DataSet.get(position).getPassword());
	}
	
	@Override
	public int getItemCount() {
		return Utility.DataSet.size();
	}

	public class CardHolder extends RecyclerView.ViewHolder {

		public CardView mCardView;
		public androidx.appcompat.widget.LinearLayoutCompat mLayLabels;
		public AppCompatTextView mDateLabel;

		public AppCompatTextView mInsertionDate;
		public AppCompatTextView mSite;
		public AppCompatTextView mEmail;
		public AppCompatTextView mPassword;

		public CardHolder(View itemView) {
			super(itemView);

			mDateLabel = itemView.findViewById(R.id.lblDateLabel);
			mLayLabels = itemView.findViewById(R.id.layLabels);
			mInsertionDate = itemView.findViewById(R.id.lblInsertionDate);
			mCardView = itemView.findViewById(R.id.cardView);
			mSite = itemView.findViewById(R.id.lblSite);
			mEmail = itemView.findViewById(R.id.lblEmail);
			mPassword = itemView.findViewById(R.id.lblPassword);
		}
	}
}