package com.ogau.photopicker.intent;

import java.util.ArrayList;

import com.ogau.photopicker.Constants;
import com.ogau.photopicker.PhotoPreviewActivity;


import android.content.Context;
import android.content.Intent;

public class PhotoPreviewIntent extends Intent {
	public PhotoPreviewIntent(Context packageContext) {
		super(packageContext, PhotoPreviewActivity.class);
	}
	
	/**
	 * 照片地址
	 * @param paths
	 */
	public void setPhotoPaths(ArrayList<String> paths) {
		this.putStringArrayListExtra(Constants.EXTRA_PHOTOS, paths);
	}

	/**
	 * 当前照片的下标
	 * @param currentItem
	 */
	public void setCurrentItem(int currentItem) {
		this.putExtra(Constants.EXTRA_CURRENT_ITEM, currentItem);
	}
}
