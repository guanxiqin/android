package com.ogau.photopicker;

import java.util.ArrayList;

import com.ogau.R;
import com.ogau.base.BaseActivity;
import com.ogau.photopicker.PhotoPagerAdapter.PhotoViewClickLinstener;
import com.ogau.photopicker.widget.ViewPagerFixed;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class PhotoPreviewActivity extends BaseActivity implements PhotoViewClickLinstener, OnClickListener {

	private ViewPagerFixed mViewPager;
	private PhotoPagerAdapter mPagerAdapter;
	private ArrayList<String> paths;
	private int currentItem = 0;

	private TextView tv_title, tv_right, tv_left;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ogau_photopicker_activity_image_preview);
		initViews();
	}
	
	private void initViews() {
		mViewPager = (ViewPagerFixed) findViewById(R.id.vp_photos);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_left = (TextView) findViewById(R.id.tv_left);

		tv_title.setText("图片预览");
		tv_left.setText("");
		tv_right.setText("删除");
		tv_right.setOnClickListener(this);

		paths = new ArrayList<String>();
		ArrayList<String> pathArr = getIntent().getStringArrayListExtra(Constants.EXTRA_PHOTOS);
		if (pathArr != null) {
			paths.addAll(pathArr);
		}
		currentItem = getIntent().getIntExtra(Constants.EXTRA_CURRENT_ITEM, 0);

		mPagerAdapter = new PhotoPagerAdapter(this, paths);
		mPagerAdapter.setPhotoViewClickListener(this);

		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setCurrentItem(currentItem);
		mViewPager.setOffscreenPageLimit(5);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				updateActionBarTitle();
			}

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		updateActionBarTitle();
	}

	private void updateActionBarTitle() {
		tv_left.setText(mViewPager.getCurrentItem() + 1 + "/" + paths.size());
	}

	@Override
	public void OnPhotoTapLinstener(View view, float v, float v1) {
		// TODO Auto-generated method stub
		// tv_left.setText(mViewPager.getCurrentItem()+1+"/"+paths.size());
		tv_left.setText(getString(R.string.ogau_photopicker_image_index, mViewPager.getCurrentItem() + 1, paths.size()));

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_right:
			doDelete();
			break;
		default:
			break;
		}

	}

	private void doDelete() {
		final int index = mViewPager.getCurrentItem();
		if (paths.size() <= 1) {
			// 最后一张照片弹出删除提示
			// show confirm dialog
			new AlertDialog.Builder(this).setTitle(R.string.ogau_photopicker_confirm_to_delete).setPositiveButton(R.string.ogau_photopicker_yes, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					dialogInterface.dismiss();
					paths.remove(index);
					onBackPressed();
				}
			}).setNegativeButton(R.string.ogau_photopicker_cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					dialogInterface.dismiss();
				}
			}).show();
		} else {
			paths.remove(index);
			mPagerAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra(Constants.EXTRA_RESULT, paths);
		setResult(RESULT_OK, intent);
		finish();
		super.onBackPressed();
	}

}
