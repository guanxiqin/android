package com.ogau;

import com.ogau.base.BaseActivity;
import com.ogau.photocompress.PhotoCompressTestActivity;
import com.ogau.photopicker.TestPhotoPickerActivity;

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

	private Button bt_photo_picker,bt_photo_compress;

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
		default:
			break;
		}

	}

	private void initViews() {
		bt_photo_picker = (Button) findViewById(R.id.bt_photo_picker);
		bt_photo_compress = (Button) findViewById(R.id.bt_photo_compress);
		
		
		bt_photo_picker.setOnClickListener(this);
		bt_photo_compress.setOnClickListener(this);
	}
}
