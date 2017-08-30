package com.ogau.utils;

import android.os.Environment;

public class PhoneUtils {
	public static String getExternalPath(){
		return Environment.getExternalStorageDirectory().toString();
	}
}
