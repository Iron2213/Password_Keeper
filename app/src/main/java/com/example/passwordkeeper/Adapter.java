package com.example.passwordkeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
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
		holder.mCardView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.item_spawn_animation));
		holder.mInsertionDate.setText(Utility.DataSet.get(position).getInsertionDate());
		holder.mTxtSite.setText(Utility.DataSet.get(position).getSite());
		holder.mTxtEmail.setText(Utility.DataSet.get(position).getEmail());
		holder.mTxtPassword.setText(Utility.DataSet.get(position).getPassword());
	}
	
	@Override
	public int getItemCount() {
		return Utility.DataSet.size();
	}

	public class CardHolder extends RecyclerView.ViewHolder {

		public CardView mCardView;
		public AppCompatTextView mInsertionDate;
		public AppCompatTextView mTxtSite;
		public AppCompatTextView mTxtEmail;
		public AppCompatTextView mTxtPassword;

		public CardHolder(View itemView) {
			super(itemView);

			mInsertionDate = itemView.findViewById(R.id.lblInsertionDate);
			mCardView = itemView.findViewById(R.id.cardView);
			mTxtSite = itemView.findViewById(R.id.lblSite);
			mTxtEmail = itemView.findViewById(R.id.lblEmail);
			mTxtPassword = itemView.findViewById(R.id.lblPassword);
		}
	}
}