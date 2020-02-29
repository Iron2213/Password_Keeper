package com.example.passwordkeeper.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordkeeper.Adapter;
import com.example.passwordkeeper.Items;
import com.example.passwordkeeper.LocalDatabaseIO;
import com.example.passwordkeeper.R;
import com.example.passwordkeeper.Utility;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

	private Context mContextMainActivity = this;

	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private AppCompatTextView mTextView;
	private LocalDatabaseIO mDB_IO;

	private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
		@Override
		public void onChanged() {
			super.onChanged();

			if (Utility.DataSet.size() < 1) {
				mTextView.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onItemRangeInserted(int positionStart, int itemCount) {
			super.onItemRangeInserted(positionStart, itemCount);

			if (Utility.DataSet.size() > 0) {
				mTextView.setVisibility(View.GONE);
			}
		}

		@Override
		public void onItemRangeRemoved(int positionStart, int itemCount) {
			super.onItemRangeRemoved(positionStart, itemCount);

			if (Utility.DataSet.size() < 1) {
				mTextView.setVisibility(View.VISIBLE);
			}
		}
	};
	private ItemTouchHelper.SimpleCallback mSimpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
		private Drawable mIconDelete = null;
		private Drawable mIconEdit = null;

		@Override
		public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
			View itemView = viewHolder.itemView;

			int itemHeight = itemView.getBottom() - itemView.getTop();
			int mImageSize = 128;
			int deleteIconTop = itemView.getTop() + (itemHeight - mImageSize) / 2;
			int deleteIconMargin = (itemHeight - mImageSize) / 2;
			int deleteIconBottom = deleteIconTop + mImageSize;

			if (mIconDelete == null)
				mIconDelete = ContextCompat.getDrawable(viewHolder.itemView.getContext(), R.drawable.ic_delete_black);

			if (mIconEdit == null)
				mIconEdit = ContextCompat.getDrawable(viewHolder.itemView.getContext(), R.drawable.ic_edit_black);

			if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
				Paint p = new Paint();

				if (dX > 0) {
					// Color of the positive displacement's background
					p.setColor(Color.rgb(20, 200, 50));

					// Draw Rect with varying right side, equal to displacement dX
					c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX + 32, (float) itemView.getBottom(), p);

					int deleteIconLeft = itemView.getLeft() + deleteIconMargin;
					int deleteIconRight = itemView.getLeft() + deleteIconMargin + mImageSize;

					mIconEdit.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
					mIconEdit.setColorFilter(new PorterDuffColorFilter(Color.argb(255, 255, 255, 255), PorterDuff.Mode.SRC_ATOP));
					mIconEdit.draw(c);

				} else if (dX < 0) {
					// Color of the negative displacement's background
					p.setColor(Color.rgb(220, 20, 50));

					// Draw Rect with varying left side, equal to the item's right side plus negative displacement dX
					c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom(), p);

					int deleteIconLeft = itemView.getRight() - deleteIconMargin - mImageSize;
					int deleteIconRight = itemView.getRight() - deleteIconMargin;

					mIconDelete.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
					mIconDelete.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP));
					mIconDelete.draw(c);
				}
			}

			super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
		}

		@Override
		public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
			return false;
		}

		@Override
		public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int swipeDir) {
			// Check swipe direction
			switch (swipeDir) {
				case ItemTouchHelper.LEFT:
					//
					// If the user swiped left (REMOVE)
					//
					final int position = viewHolder.getAdapterPosition();
					final Items removedItem = Utility.DataSet.get(position);

					if (!mDB_IO.deleteRow(removedItem.getID())) {
						Toast.makeText(mContextMainActivity, "An error occurred, unable to remove the password", Toast.LENGTH_LONG).show();
					} else {
						// I delete the item in the dataset and local DB
						Utility.DataSet.remove(position);

						// I notify that an item as been removed from the dataset
						mAdapter.notifyItemRemoved(position);

						final Snackbar snackbar = Snackbar.make(findViewById(R.id.llMainLayout), "Item removed", 5000);
						snackbar.getView().setBackground(ContextCompat.getDrawable(mContextMainActivity, R.color.actionBarColor));
						snackbar.setAction("Undo", new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// If the user clicked the undo button i insert the removed item in the dataset and in the local DB
								Utility.DataSet.add(position, removedItem);
								if (!mDB_IO.insertRow(removedItem)) {
									Toast.makeText(mContextMainActivity, "An error occurred, unable to add a new password", Toast.LENGTH_LONG).show();
								}

								// I notify that an item as been inserted in the dataset
								mAdapter.notifyItemInserted(position);

								if (position == 0) {
									mRecyclerView.smoothScrollToPosition(0);
								}
							}
						});

						snackbar.show();
					}

					break;

				case ItemTouchHelper.RIGHT:
					//
					// If the user swiped right (EDIT)
					//
					final Dialog dialog = new Dialog(viewHolder.itemView.getContext());
					dialog.setContentView(R.layout.popup_window);

					try {
						dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
					} catch (NullPointerException ex) {
						Toast.makeText(viewHolder.itemView.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
					}

					// I get the item to edit
					final Items item = Utility.DataSet.get(viewHolder.getAdapterPosition());

					Button btnCancel = dialog.findViewById(R.id.btnCancel);
					Button btnSave = dialog.findViewById(R.id.btnSave);

					final EditText txtSite = dialog.findViewById(R.id.txtSite);
					final EditText txtEmail = dialog.findViewById(R.id.txtEmail);
					final EditText txtPassword = dialog.findViewById(R.id.txtPassword);
					final TextView lblError = dialog.findViewById(R.id.lblError);

					// I load the previous content
					if (item.getSite().equals("Not specified")) {
						txtSite.setText("");
					} else {
						txtSite.setText(item.getSite());
					}

					txtEmail.setText(item.getEmail());
					txtPassword.setText(item.getPassword());

					// Listener for the save button
					btnSave.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							String site = txtSite.getText().toString();
							String email = txtEmail.getText().toString();
							String password = txtPassword.getText().toString();

							// I check if the password and email are not empty
							if (!email.isEmpty() && !password.isEmpty()) {
								if (site.isEmpty()) {
									site = "Not specified";
								}

								String formattedDate = new SimpleDateFormat("dd-MMM-yy").format(Calendar.getInstance().getTime());

								item.setInsertionDate(formattedDate);
								item.setSite(site);
								item.setEmail(email);
								item.setPassword(password);

								mDB_IO.updateRow(item);

								mAdapter.notifyDataSetChanged();
								dialog.dismiss();
							} else {
								lblError.setText(R.string.error_email_password_empty);
							}

						}
					});

					// Listener for the cancel button
					btnCancel.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
							dialog.dismiss();
						}
					});

					// I notify that the items in the dataset have been changed
					mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());

					dialog.show();
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDB_IO = Utility.getDatabaseIO();

		mRecyclerView = findViewById(R.id.rlvPasswords);
		mRecyclerView.setHasFixedSize(true);

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layoutManager);

		mAdapter = new Adapter();
		mRecyclerView.setAdapter(mAdapter);

		mTextView = findViewById(R.id.lblEmptyMessage);

		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

		ItemTouchHelper touchHelper = new ItemTouchHelper(mSimpleItemTouchCallback);
		touchHelper.attachToRecyclerView(mRecyclerView);

		// If the data set has items inside it i hide the 'No passwords' message
		if (Utility.DataSet.size() > 0) {
			mTextView.setVisibility(View.GONE);
		}

		mAdapter.registerAdapterDataObserver(mDataObserver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {

		switch (item.getItemId()) {
			case R.id.mnuAddItem:
				final AppCompatDialog dialog = new AppCompatDialog(mContextMainActivity);
				dialog.setContentView(R.layout.popup_window);

				try {
					dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
				} catch (Exception ex) {
					Toast.makeText(mContextMainActivity, ex.getMessage(), Toast.LENGTH_LONG).show();
				}

				AppCompatButton btnCancel = dialog.findViewById(R.id.btnCancel);
				AppCompatButton btnSave = dialog.findViewById(R.id.btnSave);

				// Listener for the save button
				btnSave.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String site = ((AppCompatEditText) dialog.findViewById(R.id.txtSite)).getText().toString();
						String email = ((AppCompatEditText) dialog.findViewById(R.id.txtEmail)).getText().toString();
						String password = ((AppCompatEditText) dialog.findViewById(R.id.txtPassword)).getText().toString();
						AppCompatTextView lblError = dialog.findViewById(R.id.lblError);

						// I take the current time and date
						String formattedDate = new SimpleDateFormat("dd-MMM-yy").format(Calendar.getInstance().getTime());

						if (!email.isEmpty() && !password.isEmpty()) {
							if (site.isEmpty()) {
								site = "Not specified";
							}

							// I create the new item
							Items newItem = new Items(formattedDate, site, email, password);

							// I insert the item in my local SQLite DB
							if (mDB_IO.insertRow(newItem)) {
								// I add the new item to my local DataSet
								Utility.DataSet.add(newItem);

								// I update the RecyclerView to show the new item, and i play an animation to show it
								mAdapter.notifyItemInserted(Utility.DataSet.size() - 1);
								mRecyclerView.smoothScrollToPosition(Utility.DataSet.size() - 1);

								// I close the dialog
								dialog.dismiss();
							} else {
								lblError.setVisibility(View.VISIBLE);
								lblError.setText(R.string.error_add_item);
							}


						} else {
							lblError.setVisibility(View.VISIBLE);
							lblError.setText(R.string.error_email_password_empty);
						}
					}
				});

				// Listener for the cancel button
				btnCancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				dialog.show();
				break;

			case R.id.mnuSettings:
				Intent intent = new Intent(this, SettingsActivity.class);
				startActivity(intent);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mAdapter.unregisterAdapterDataObserver(mDataObserver);
		mDB_IO.close();
	}
}