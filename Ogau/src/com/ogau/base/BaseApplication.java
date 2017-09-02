package com.ogau.base;

import com.baidu.mapapi.SDKInitializer;
import com.mob.MobSDK;

import cn.sharesdk.framework.ShareSDK;
import android.app.Application;

public class BaseApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		MobSDK.init(this, "20a778c7eb7e0 ", "5d0d88a787ebce867c898ee1b8b8ecd5");
		SDKInitializer.initialize(getApplicationContext()); 
	}

}
