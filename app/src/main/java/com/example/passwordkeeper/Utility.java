package com.example.passwordkeeper;

import java.util.List;

public class Utility {

	public static List<Items> DataSet;
	private static int AccessCode;

	public static int getAccessCode() {
		return AccessCode;
	}

	public static void setAccessCode(int accessCode) {
		AccessCode = accessCode;
	}
}
