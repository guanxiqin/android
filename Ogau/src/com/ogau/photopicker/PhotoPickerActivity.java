package com.ogau.photopicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ogau.R;
import com.ogau.base.BaseActivity;
import com.ogau.photopicker.intent.PhotoPreviewIntent;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoPickerActivity extends BaseActivity {

	public static final String TAG = PhotoPickerActivity.class.getName();
	private Context mCxt;

	private TextView tv_right, tv_title, tv_left;

	private GridView mGridView;
	private View mPopupAnchorView;
	private Button btnAlbum;
	private Button btnPreview;
	// 结果数据
	private ArrayList<String> resultList = new ArrayList<String>();
	// 文件夹数据
	private ArrayList<Folder> mResultFolder = new ArrayList<Folder>();

	private static final int LOADER_ALL = 0;
	private static final int LOADER_CATEGORY = 1;

	private ImageCaptureManager captureManager;
	private int mDesireImageCount;
	private ImageConfig imageConfig;

	private ImageGridAdapter mImageAdapter;
	private FolderAdapter mFolderAdapter;
	private ListPopupWindow mFolderPopupWindow;

	private boolean hasFolderGened = false;
	private boolean mIsShowCamera = false;
	private LoaderCallbacks<Cursor> mLoaderCallback = new LoaderCallbacks<Cursor>() {
		private final String[] IMAGE_PROJECTION = { MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATE_ADDED,
				MediaStore.Images.Media._ID };

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			// TODO Auto-generated method stub
			// 根据图片设置参数新增验证条件
			StringBuilder selectionArgs = new StringBuilder();

			if (imageConfig != null) {
				if (imageConfig.minWidth != 0) {
					selectionArgs.append(MediaStore.Images.Media.WIDTH + " >= " + imageConfig.minWidth);
				}

				if (imageConfig.minHeight != 0) {
					selectionArgs.append("".equals(selectionArgs.toString()) ? "" : " and ");
					selectionArgs.append(MediaStore.Images.Media.HEIGHT + " >= " + imageConfig.minHeight);
				}

				if (imageConfig.minSize != 0f) {
					selectionArgs.append("".equals(selectionArgs.toString()) ? "" : " and ");
					selectionArgs.append(MediaStore.Images.Media.SIZE + " >= " + imageConfig.minSize);
				}

				if (imageConfig.mimeType != null) {
					selectionArgs.append(" and (");
					for (int i = 0, len = imageConfig.mimeType.length; i < len; i++) {
						if (i != 0) {
							selectionArgs.append(" or ");
						}
						selectionArgs.append(MediaStore.Images.Media.MIME_TYPE + " = '" + imageConfig.mimeType[i] + "'");
					}
					selectionArgs.append(")");
				}
			}

			if (id == LOADER_ALL) {
				CursorLoader cursorLoader = new CursorLoader(mCxt, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, selectionArgs.toString(), null,
						IMAGE_PROJECTION[2] + " DESC");
				return cursorLoader;
			} else if (id == LOADER_CATEGORY) {
				String selectionStr = selectionArgs.toString();
				if (!"".equals(selectionStr)) {
					selectionStr += " and" + selectionStr;
				}
				CursorLoader cursorLoader = new CursorLoader(mCxt, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, IMAGE_PROJECTION[0] + " like '%"
						+ args.getString("path") + "%'" + selectionStr, null, IMAGE_PROJECTION[2] + " DESC");
				return cursorLoader;
			}

			return null;
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			// TODO Auto-generated method stub
			if (data != null) {
				List<Image> images = new ArrayList<Image>();
				int count = data.getCount();
				if (count > 0) {
					data.moveToFirst();
					do {
						String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
						String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
						long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));

						Image image = new Image(path, name, dateTime);
						images.add(image);
						if (!hasFolderGened) {
							// 获取文件夹名称
							File imageFile = new File(path);
							File folderFile = imageFile.getParentFile();
							Folder folder = new Folder();
							folder.name = folderFile.getName();
							folder.path = folderFile.getAbsolutePath();
							folder.cover = image;
							if (!mResultFolder.contains(folder)) {
								List<Image> imageList = new ArrayList<Image>();
								imageList.add(image);
								folder.images = imageList;
								mResultFolder.add(folder);
							} else {
								// 更新
								Folder f = mResultFolder.get(mResultFolder.indexOf(folder));
								f.images.add(image);
							}
						}

					} while (data.moveToNext());

					mImageAdapter.setData(images);

					// 设定默认选择
					if (resultList != null && resultList.size() > 0) {
						mImageAdapter.setDefaultSelected(resultList);
					}

					mFolderAdapter.setData(mResultFolder);
					Log.d("xiangce->",mResultFolder.size()+"");
					hasFolderGened = true;

				}
			}

		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ogau_photopicker_activity_photopicker);
		initViews();
		initOthers();
	}

	private void initOthers() {
		mIsShowCamera = getIntent().getBooleanExtra(Constants.EXTRA_SHOW_CAMERA, false);
		imageConfig = getIntent().getParcelableExtra(Constants.EXTRA_IMAGE_CONFIG);
		mDesireImageCount = getIntent().getIntExtra(Constants.EXTRA_SELECT_COUNT, Constants.DEFAULT_MAX_TOTAL);
		final int model = getIntent().getExtras().getInt(Constants.EXTRA_SELECT_MODE, Constants.MODE_SINGLE);

		getLoaderManager().initLoader(LOADER_ALL, null, mLoaderCallback);

		if (model == Constants.MODE_MULTI) {
			ArrayList<String> tmp = getIntent().getStringArrayListExtra(Constants.EXTRA_DEFAULT_SELECTED_LIST);
			if (tmp != null && tmp.size() > 0) {
				resultList.addAll(tmp);
			}
		}

		mImageAdapter = new ImageGridAdapter(mCxt, mIsShowCamera, getItemImageWidth());
		mImageAdapter.setShowSeclectIndicator(model == Constants.MODE_MULTI);
		mGridView.setAdapter(mImageAdapter);
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (mImageAdapter.isShowCamera()) {
					if (position == 0) {
						if (model == Constants.MODE_MULTI) {
							if (mDesireImageCount == resultList.size()) {
								Toast.makeText(mCxt, R.string.ogau_photopicker_msg_amount_limit, Toast.LENGTH_SHORT).show();
								return;
							}
						}
						showCameraAction();
					} else {
						// 正常操作
						Image image = (Image) parent.getAdapter().getItem(position);
						selectImageFromGrid(image, model);
					}

				} else {
					// 正常操作
					Image image = (Image) parent.getAdapter().getItem(position);
					selectImageFromGrid(image, model);
				}
			}
		});
		mFolderAdapter = new FolderAdapter(mCxt);
		// 打开相册列表
		btnAlbum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mFolderPopupWindow == null) {
					createPopupFolderList();
				}

				if (mFolderPopupWindow.isShowing()) {
					mFolderPopupWindow.dismiss();
				} else {
					mFolderPopupWindow.show();
					int index = mFolderAdapter.getSelectIndex();
					index = index == 0 ? index : index - 1;
					mFolderPopupWindow.getListView().setSelection(index);
				}
			}
		});
		// 预览
		btnPreview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PhotoPreviewIntent intent = new PhotoPreviewIntent(mCxt);
				intent.setCurrentItem(0);
				intent.setPhotoPaths(resultList);
				startActivityForResult(intent, Constants.REQUEST_PREVIEW);
			}
		});
	}

	private void initViews() {
		mCxt = this;
		captureManager = new ImageCaptureManager(mCxt);

		mGridView = (GridView) findViewById(R.id.grid);
		mGridView.setNumColumns(getNumColnums());

		mPopupAnchorView = findViewById(R.id.photo_picker_footer);
		btnAlbum = (Button) findViewById(R.id.btnAlbum);
		btnPreview = (Button) findViewById(R.id.btnPreview);

		tv_right = (TextView) findViewById(R.id.tv_right);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_left = (TextView) findViewById(R.id.tv_left);

		tv_right.setText("back");
		tv_title.setText("图片");
		tv_left.setText("完成");

		tv_right.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		tv_left.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				complete();
			}
		});
		refreshActionStatus();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			// 相机拍照完成后，返回图片路径
			case ImageCaptureManager.REQUEST_TAKE_PHOTO:
				if (captureManager.getCurrentPhotoPath() != null) {
					captureManager.galleryAddPic();
					resultList.add(captureManager.getCurrentPhotoPath());
				}
				complete();
				break;
			// 预览照片
			case Constants.REQUEST_PREVIEW:
				ArrayList<String> pathArr = data.getStringArrayListExtra(Constants.EXTRA_RESULT);
				// 刷新页面
				if (pathArr != null && pathArr.size() != resultList.size()) {
					resultList = pathArr;
					refreshActionStatus();
					mImageAdapter.setDefaultSelected(resultList);
				}
				break;
			}
		}
	}

	private void showCameraAction() {
		try {
			Intent intent = captureManager.dispatchTakePictureIntent();
			startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
		} catch (IOException e) {
			Toast.makeText(mCxt, R.string.ogau_photopicker_msg_no_camera, Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	private void createPopupFolderList() {
		mFolderPopupWindow = new ListPopupWindow(mCxt);
		mFolderPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mFolderPopupWindow.setAdapter(mFolderAdapter);
		mFolderPopupWindow.setContentWidth(ListPopupWindow.MATCH_PARENT);
		mFolderPopupWindow.setWidth(ListPopupWindow.MATCH_PARENT);

		// 计算ListPopupWindow内容的高度(忽略mPopupAnchorView.height)，R.layout.item_foloer
		int folderItemViewHeight =
		// 图片高度
		getResources().getDimensionPixelOffset(R.dimen.ogau_photopicker_folder_cover_size) +
		// Padding Top
				getResources().getDimensionPixelOffset(R.dimen.ogau_photopicker_folder_padding) +
				// Padding Bottom
				getResources().getDimensionPixelOffset(R.dimen.ogau_photopicker_folder_padding);
		int folderViewHeight = mFolderAdapter.getCount() * folderItemViewHeight;

		int screenHeigh = getResources().getDisplayMetrics().heightPixels;
		if (folderViewHeight >= screenHeigh) {
			mFolderPopupWindow.setHeight(Math.round(screenHeigh * 0.6f));
		} else {
			mFolderPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
		}

		mFolderPopupWindow.setAnchorView(mPopupAnchorView);
		mFolderPopupWindow.setModal(true);
		// mFolderPopupWindow.setAnimationStyle(R.style.Animation_AppCompat_DropDownUp);
		mFolderPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				mFolderAdapter.setSelectIndex(position);

				final int index = position;
				final AdapterView v = parent;

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mFolderPopupWindow.dismiss();

						if (index == 0) {
							getLoaderManager().restartLoader(LOADER_ALL, null, mLoaderCallback);
							btnAlbum.setText(R.string.ogau_photopicker_all_image);
							mImageAdapter.setShowCamera(mIsShowCamera);
						} else {
							Folder folder = (Folder) v.getAdapter().getItem(index-1);
							if (null != folder) {
								mImageAdapter.setData(folder.images);
								btnAlbum.setText(folder.name);
								// 设定默认选择
								if (resultList != null && resultList.size() > 0) {
									mImageAdapter.setDefaultSelected(resultList);
								}
							}
							mImageAdapter.setShowCamera(false);
						}

						// 滑动到最初始位置
						mGridView.smoothScrollToPosition(0);
					}
				}, 100);
			}
		});
	}

	// 返回已选择的图片数据
	private void complete() {
		Intent data = new Intent();
		data.putStringArrayListExtra(Constants.EXTRA_RESULT, resultList);
		setResult(RESULT_OK, data);
		finish();
	}

	/**
	 * 获取GridView Item宽度
	 * 
	 * @return
	 */
	private int getItemImageWidth() {
		int cols = getNumColnums();
		int screenWidth = getResources().getDisplayMetrics().widthPixels;
		int columnSpace = getResources().getDimensionPixelOffset(R.dimen.ogau_photopicker_space_size);
		return (screenWidth - columnSpace * (cols - 1)) / cols;
	}

	/**
	 * 根据屏幕宽度与密度计算GridView显示的列数， 最少为三列
	 * 
	 * @return
	 */
	private int getNumColnums() {
		int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
		return cols < 3 ? 3 : cols;
	}

	/**
	 * 选择图片操作
	 * 
	 * @param image
	 */
	private void selectImageFromGrid(Image image, int mode) {
		if (image != null) {
			// 多选模式
			if (mode == Constants.MODE_MULTI) {
				if (resultList.contains(image.path)) {
					resultList.remove(image.path);
					onImageUnselected(image.path);
				} else {
					// 判断选择数量问题
					if (mDesireImageCount == resultList.size()) {
						Toast.makeText(mCxt, R.string.ogau_photopicker_msg_amount_limit, Toast.LENGTH_SHORT).show();
						return;
					}
					resultList.add(image.path);
					onImageSelected(image.path);
				}
				mImageAdapter.select(image);
			} else if (mode == Constants.MODE_SINGLE) {
				// 单选模式
				onSingleImageSelected(image.path);
			}
		}
	}

	public void onImageSelected(String path) {
		if (!resultList.contains(path)) {
			resultList.add(path);
		}
		refreshActionStatus();
	}

	public void onImageUnselected(String path) {
		if (resultList.contains(path)) {
			resultList.remove(path);
		}
		refreshActionStatus();
	}

	public void onSingleImageSelected(String path) {
		Intent data = new Intent();
		resultList.add(path);
		data.putStringArrayListExtra(Constants.EXTRA_RESULT, resultList);
		setResult(RESULT_OK, data);
		finish();
	}

	/**
	 * 刷新操作按钮状态
	 */
	private void refreshActionStatus() {
		String text = getString(R.string.ogau_photopicker_done_with_count, resultList.size(), mDesireImageCount);
		tv_right.setText(text);
		boolean hasSelected = resultList.size() > 0;
		if (hasSelected) {
			tv_right.setVisibility(View.VISIBLE);
		} else {
			tv_right.setVisibility(View.INVISIBLE);
		}

		btnPreview.setEnabled(hasSelected);
		if (hasSelected) {
			btnPreview.setText(getResources().getString(R.string.ogau_photopicker_preview) + "(" + resultList.size() + ")");
		} else {
			btnPreview.setText(getResources().getString(R.string.ogau_photopicker_preview));
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.d(TAG, "on change");

		// 重置列数
		mGridView.setNumColumns(getNumColnums());
		// 重置Item宽度
		mImageAdapter.setItemSize(getItemImageWidth());

		if (mFolderPopupWindow != null) {
			if (mFolderPopupWindow.isShowing()) {
				mFolderPopupWindow.dismiss();
			}

			// 重置PopupWindow高度
			int screenHeigh = getResources().getDisplayMetrics().heightPixels;
			mFolderPopupWindow.setHeight(Math.round(screenHeigh * 0.6f));
		}

		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		captureManager.onSaveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		captureManager.onRestoreInstanceState(savedInstanceState);
		super.onRestoreInstanceState(savedInstanceState);
	}

}
