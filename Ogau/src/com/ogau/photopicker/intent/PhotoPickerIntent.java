package com.ogau.photopicker.intent;

import java.util.ArrayList;

import com.ogau.photopicker.Constants;
import com.ogau.photopicker.ImageConfig;
import com.ogau.photopicker.PhotoPickerActivity;
import com.ogau.photopicker.SelectModel;

import android.content.Context;
import android.content.Intent;

public class PhotoPickerIntent extends Intent {

	public PhotoPickerIntent(Context packageContext) {
		super(packageContext, PhotoPickerActivity.class);
	}

	public void setShowCamera(boolean b) {
		this.putExtra(Constants.EXTRA_SHOW_CAMERA, b);
	}

	public void setMaxTotal(int total) {
		this.putExtra(Constants.EXTRA_SELECT_COUNT, total);
	}

	/**
	 * 选择
	 * 
	 * @param model
	 */
	public void setSelectModel(SelectModel model) {
		this.putExtra(Constants.EXTRA_SELECT_MODE, Integer.parseInt(model.toString()));
	}

	/**
	 * 已选择的照片地址
	 * 
	 * @param paths
	 */
	public void setSelectedPaths(ArrayList<String> paths) {
		this.putStringArrayListExtra(Constants.EXTRA_DEFAULT_SELECTED_LIST, paths);
	}

	/**
	 * 显示相册图片的属性
	 * 
	 * @param imageConfig
	 */
	public void setImageConfig(ImageConfig imageConfig) {
		this.putExtra(Constants.EXTRA_IMAGE_CONFIG, imageConfig);
	}
}
