package com.example.passwordkeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SQLite_IO extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "PasswordKeeper";

    public class TableNames {
        public static final String Passwords = "Password";
    }

    public SQLite_IO(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TableNames.Passwords + " (Id integer PRIMARY KEY AUTOINCREMENT, Data date, Site text, Email text, Password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * This method gets all items inside the 'Password' table.
     *
     * @return Returns a List of 'Items' data
     */
    public List<Items> getAllData() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TableNames.Passwords, null);

        List<Items> items = new ArrayList<>();

        while (result.moveToNext()) {
            items.add(new Items(result.getLong(0), result.getString(1), result.getString(2), result.getString(3), result.getString(4)));
        }

        result.close();
        return items;
    }

    /**
     * This method updates an item with the data the user as modified/insert.
     * @param newPassword the item to modify
     */
    public void updateRow(@NonNull Items newPassword) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Data", newPassword.getInsertionDate());
        values.put("Site", newPassword.getSite());
        values.put("Email", newPassword.getEmail());
        values.put("Password", newPassword.getPassword());

        int i = db.update(TableNames.Passwords, values, "Id = " + newPassword.getID(), null);
    }

    /**
     * This method insert a new item in the 'Password' table.
     *
     * @param newPassword The item to insert
     * @return true if successful, false otherwise
     */
    public boolean insertRow(@NonNull Items newPassword) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Data", newPassword.getInsertionDate());
        values.put("Site", newPassword.getSite());
        values.put("Email", newPassword.getEmail());
        values.put("Password", newPassword.getPassword());

        long result = db.insertOrThrow(TableNames.Passwords, null, values);

        if (result == -1) {
            return false;
        } else {
            newPassword.setID(result);
            return true;
        }
    }

    /**
     * This method delete a row given it's ID.
     *
     * @param ID Row's ID.
     */
    public void deleteRow(long ID) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TableNames.Passwords, "Id = " + ID, null);
    }

    /**
     * This method delete all the item in the 'Passwords' table.
     */
    public void deleteAllItems() {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TableNames.Passwords, null, null);
    }
}
