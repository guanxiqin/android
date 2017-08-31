package com.ogau.base;

import com.mob.MobSDK;

import cn.sharesdk.framework.ShareSDK;
import android.app.Application;

public class BaseApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		MobSDK.init(this, "20966e1a28932 ", "463357b21cf90cafc369d19a7eb768b6");
	}

}
