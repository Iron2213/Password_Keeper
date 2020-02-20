package com.example.passwordkeeper;

import android.content.Context;

import java.util.List;

public class Utility {

	public static class Settings {
		private static boolean showLabelsOnItems = true;
		private static boolean PINAccessEnabled = true;

		public static boolean isShowLabelsOnItems() {
			return showLabelsOnItems;
		}

		public static void setShowLabelsOnItems(boolean showLabelsOnItems) {
			Settings.showLabelsOnItems = showLabelsOnItems;
		}

		public static boolean isPINAccessEnabled() {
			return PINAccessEnabled;
		}

		public static void setPINAccessEnabled(boolean PINAccessEnabled) {
			Settings.PINAccessEnabled = PINAccessEnabled;
		}
	}

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