package com.example.passwordkeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class LocalDatabaseIO extends SQLiteOpenHelper {

	private static String DATABASE_NAME = "PasswordKeeper";

	private class TablesNames {
		static final String Passwords = "Password";
		static final String Settings = "Settings";
	}

	private class TablesCreateQueries {
		static final String TABLE_PASSWORDS = "CREATE TABLE " + TablesNames.Passwords + "(Id integer PRIMARY KEY AUTOINCREMENT, Data date, Site text, Email text, Password text)";
		static final String TABLE_SETTINGS = "CREATE TABLE " + TablesNames.Settings + "(Id integer PRIMARY KEY AUTOINCREMENT, AccessCode text)";
	}

	public LocalDatabaseIO(Context context) {
		super(context, DATABASE_NAME, null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TablesCreateQueries.TABLE_PASSWORDS);
		db.execSQL(TablesCreateQueries.TABLE_SETTINGS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/**
	 * Checks if this is the first time the app has been opened
	 */
	public boolean isFirstAccess() {
		try (SQLiteDatabase db = getReadableDatabase();
			 Cursor result = db.rawQuery("SELECT * FROM " + TablesNames.Settings, null)) {
			return result.getCount() == 0;
		}
	}

	/**
	 * This method gets all items inside the 'Password' table.
	 *
	 * @return Returns a List of 'Items'
	 */
	public List<Items> getAllData() {
		try (SQLiteDatabase db = getReadableDatabase()) {
			Cursor result = db.rawQuery("SELECT * FROM " + TablesNames.Passwords, null);

			List<Items> items = new ArrayList<>();

			while (result.moveToNext()) {
				items.add(new Items(result.getLong(0), result.getString(1), result.getString(2), result.getString(3), result.getString(4)));
			}

			result.close();
			return items;
		}
	}

	/**
	 * This method updates an item with the data the user as modified/insert.
	 *
	 * @param newPassword the item to modify
	 */
	public boolean updateRow(@NonNull Items newPassword) {
		try (SQLiteDatabase db = getWritableDatabase()) {
			ContentValues values = new ContentValues();

			values.put("Data", newPassword.getInsertionDate());
			values.put("Site", newPassword.getSite());
			values.put("Email", newPassword.getEmail());
			values.put("Password", newPassword.getPassword());

			int result = db.update(TablesNames.Passwords, values, "Id = " + newPassword.getID(), null);
			return result != -1;
		}
	}

	/**
	 * This method insert a new item in the 'Password' table.
	 *
	 * @param newPassword The item to insert
	 * @return true if successful, false otherwise
	 */
	public boolean insertRow(@NonNull Items newPassword) {
		try (SQLiteDatabase db = getWritableDatabase()) {
			ContentValues values = new ContentValues();

			values.put("Data", newPassword.getInsertionDate());
			values.put("Site", newPassword.getSite());
			values.put("Email", newPassword.getEmail());
			values.put("Password", newPassword.getPassword());

			long result = db.insertOrThrow(TablesNames.Passwords, null, values);

			if (result == -1) {
				return false;
			} else {
				newPassword.setID(result);
				return true;
			}
		}
	}

	/**
	 * This method delete a row given it's ID.
	 *
	 * @param ID Row's ID.
	 */
	public boolean deleteRow(long ID) {
		try (SQLiteDatabase db = getWritableDatabase()) {
			return db.delete(TablesNames.Passwords, "Id = " + ID, null) != -1;
		}
	}

	/**
	 * This method delete all the item in the 'Passwords' table.
	 */
	public boolean deleteAllItems() {
		try (SQLiteDatabase db = getWritableDatabase()) {
			long queryResult = db.delete(TablesNames.Passwords, null, null);
			return queryResult != -1;
		}
	}

	public String getAccessCode() {
		try (SQLiteDatabase db = getReadableDatabase();
			 Cursor result = db.rawQuery("SELECT * FROM " + TablesNames.Settings + " WHERE Id = 1", null)) {


			if (result.moveToNext())
				return result.getString(1);
			else
				return "";
		}
	}

	/**
	 * This method updates or inserts in the database the user access code
	 */
	public boolean updateAccessCode(String AccessCode) {
		try (SQLiteDatabase db = getWritableDatabase()) {
			ContentValues values = new ContentValues();
			values.put("AccessCode", AccessCode);

			if (db.update(TablesNames.Settings, values, "Id = 1", null) < 1) {
				long queryResult = db.insert(TablesNames.Settings, null, values);
				db.close();
				return queryResult != -1;
			} else {
				db.close();
				return true;
			}
		}
	}
}
