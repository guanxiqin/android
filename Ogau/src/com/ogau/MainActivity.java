package com.ogau;

import com.ogau.base.BaseActivity;
import com.ogau.longscreenshoot.LongScreenShootTestActivity;
import com.ogau.map_location.baidu.BaiduMapTestActivity;
import com.ogau.photocompress.PhotoCompressTestActivity;
import com.ogau.photopicker.TestPhotoPickerActivity;
import com.ogau.socialshare.SocialShareTestActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity implements OnClickListener {
	private Context context = MainActivity.this;
	private String TAG = this.getClass().getSimpleName();

	private Button bt_photo_picker,bt_photo_compress,bt_long_screen_shoot,bt_baidu_map,bt_share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_photo_picker:
			Intent intentPP=new Intent(context, TestPhotoPickerActivity.class);
			startActivity(intentPP);
			break;
		case R.id.bt_photo_compress:
			Intent intentPPr=new Intent(context, PhotoCompressTestActivity.class);
			startActivity(intentPPr);
			break;
		case R.id.bt_long_screen_shoot:
			Intent intentLss=new Intent(context, LongScreenShootTestActivity.class);
			startActivity(intentLss);
			break;
		case R.id.bt_baidu_map:
			Intent intentBmap=new Intent(context, BaiduMapTestActivity.class);
			startActivity(intentBmap);
			break;
		case R.id.bt_share:
			Intent intentShare=new Intent(context, SocialShareTestActivity.class);
			startActivity(intentShare);
			break;
		default:
			break;
		}

	}


	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		bt_photo_picker = (Button) findViewById(R.id.bt_photo_picker);
		bt_photo_compress = (Button) findViewById(R.id.bt_photo_compress);
		bt_long_screen_shoot = (Button) findViewById(R.id.bt_long_screen_shoot);
		bt_baidu_map = (Button) findViewById(R.id.bt_baidu_map);
		bt_share = (Button) findViewById(R.id.bt_share);
		
		
		bt_photo_picker.setOnClickListener(this);
		bt_photo_compress.setOnClickListener(this);
		bt_long_screen_shoot.setOnClickListener(this);
		bt_baidu_map.setOnClickListener(this);
		bt_share.setOnClickListener(this);
		
	}

	@Override
	protected void initActb() {
		// TODO Auto-generated method stub
		
	}
}
