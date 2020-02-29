package com.example.passwordkeeper;

import android.content.Context;

import java.util.List;

public class Utility {

	public static List<Items> DataSet;
	private static String PINCode;
	private static LocalDatabaseIO DatabaseIO;

	public static String getAccessCode() {
		return PINCode;
	}

	public static void setAccessCode(String Code) {
		PINCode = Code;
	}

	public static void setDatabaseIO(Context context) {
		if (DatabaseIO == null) {
			DatabaseIO = new LocalDatabaseIO(context);
		}
	}

	public static LocalDatabaseIO getDatabaseIO() {
		return DatabaseIO;
	}
}