package com.ogau.photopicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;

import com.bumptech.glide.Glide;
import com.ogau.R;
import com.ogau.base.BaseActivity;
import com.ogau.photopicker.intent.PhotoPickerIntent;
import com.ogau.photopicker.intent.PhotoPreviewIntent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TestPhotoPickerActivity extends BaseActivity implements OnClickListener{

	private Context context = TestPhotoPickerActivity.this;
	private String TAG =this.getClass().getSimpleName();


	private Button bt_pic_single, bt_pic_multy, bt_pic_photo;
	private GridView gv_pic;
	private int columnWidth;
	private GridAdapter gridAdapter;

	private static final int REQUEST_CAMERA_CODE = 11;
	private static final int REQUEST_PREVIEW_CODE = 22;
	private ArrayList<String> imagePaths = null;
	private ImageCaptureManager captureManager; // 相机拍照处理类

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_photo_picker);

		initView();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		default:
			break;
		}
	}

	private void initView() {

		bt_pic_single = (Button) findViewById(R.id.bt_pic_single);
		bt_pic_multy = (Button) findViewById(R.id.bt_pic_multy);
		bt_pic_photo = (Button) findViewById(R.id.bt_pic_photo);
		gv_pic = (GridView) findViewById(R.id.gv_pic);

		bt_pic_single.setOnClickListener(this);
		bt_pic_multy.setOnClickListener(this);
		bt_pic_photo.setOnClickListener(this);

		int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
		cols = cols < 3 ? 3 : cols;
		gv_pic.setNumColumns(cols);

		// Item Width
		int screenWidth = getResources().getDisplayMetrics().widthPixels;
		int columnSpace = getResources().getDimensionPixelOffset(R.dimen.ogau_photopicker_space_size);
		columnWidth = (screenWidth - columnSpace * (cols - 1)) / cols;

		bt_pic_single.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PhotoPickerIntent intent = new PhotoPickerIntent(TestPhotoPickerActivity.this);
				intent.setSelectModel(SelectModel.SINGLE);
				intent.setShowCamera(true);
				// intent.setImageConfig(null);
				startActivityForResult(intent, REQUEST_CAMERA_CODE);
			}
		});

		bt_pic_multy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PhotoPickerIntent intent = new PhotoPickerIntent(TestPhotoPickerActivity.this);
				intent.setSelectModel(SelectModel.MULTI);
				intent.setShowCamera(true); // 是否显示拍照
				intent.setMaxTotal(9); // 最多选择照片数量，默认为9
				intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
				// ImageConfig config = new ImageConfig();
				// config.minHeight = 400;
				// config.minWidth = 400;
				// config.mimeType = new String[]{"image/jpeg", "image/png"};
				// config.minSize = 1 * 1024 * 1024; // 1Mb
				// intent.setImageConfig(config);
				
				startActivityForResult(intent, REQUEST_CAMERA_CODE);
			}
		});

		bt_pic_photo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (captureManager == null) {
						captureManager = new ImageCaptureManager(TestPhotoPickerActivity.this);
					}
					Intent intent = captureManager.dispatchTakePictureIntent();
					startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
				} catch (IOException e) {
					Toast.makeText(TestPhotoPickerActivity.this, "", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		});
		gv_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				gotoPreview(position);
			}
		});
	}

	private void gotoPreview(int position) {
		PhotoPreviewIntent intent = new PhotoPreviewIntent(context);
		intent.setCurrentItem(0);
		intent.setPhotoPaths(imagePaths);
//		startActivityForResult(intent, Constants.REQUEST_PREVIEW);
		startActivityForResult(intent, REQUEST_PREVIEW_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			// 选择照片
			case REQUEST_CAMERA_CODE:
				loadAdpater(data.getStringArrayListExtra(Constants.EXTRA_RESULT));
				break;
			// 预览
			case REQUEST_PREVIEW_CODE:
				loadAdpater(data.getStringArrayListExtra(Constants.EXTRA_RESULT));
				break;
			// 调用相机拍照
			case ImageCaptureManager.REQUEST_TAKE_PHOTO:
				if (captureManager.getCurrentPhotoPath() != null) {
					captureManager.galleryAddPic();

					ArrayList<String> paths = new ArrayList<String>();
					paths.add(captureManager.getCurrentPhotoPath());
					loadAdpater(paths);
				}
				break;

			}
		}
	}

	private void loadAdpater(ArrayList<String> paths) {
		if (imagePaths == null) {
			imagePaths = new ArrayList<String>();
		}
		imagePaths.clear();
		imagePaths.addAll(paths);

		try {
			JSONArray obj = new JSONArray(imagePaths);
			Log.e("--", obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (gridAdapter == null) {
			gridAdapter = new GridAdapter(imagePaths);
			gv_pic.setAdapter(gridAdapter);
		} else {
			gridAdapter.notifyDataSetChanged();
		}
	}

	private class GridAdapter extends BaseAdapter {
		private ArrayList<String> listUrls;

		public GridAdapter(ArrayList<String> listUrls) {
			this.listUrls = listUrls;
		}

		@Override
		public int getCount() {
			return listUrls.size();
		}

		@Override
		public String getItem(int position) {
			return listUrls.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.ogau_photopicker_item_image, null);
				imageView = (ImageView) convertView.findViewById(R.id.imageView);
				convertView.setTag(imageView);
				// 重置ImageView宽高
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(columnWidth, columnWidth);
				imageView.setLayoutParams(params);
			} else {
				imageView = (ImageView) convertView.getTag();
			}
			Glide.with(TestPhotoPickerActivity.this).load(new File(getItem(position))).placeholder(R.mipmap.default_error).error(R.mipmap.default_error).centerCrop().crossFade()
					.into(imageView);
			return convertView;
		}
	}

}
