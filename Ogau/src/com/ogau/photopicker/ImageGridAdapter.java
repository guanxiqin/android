package com.ogau.photopicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.ogau.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageGridAdapter extends BaseAdapter {
	private static final int TYPE_CAMERA = 0;
	private static final int TYPE_NORMAL = 1;

	private Context mContext;
	private LayoutInflater mInflater;
	private boolean showCamera = true;
	private boolean showSeclectIndicator = true;

	private List<Image> mImages = new ArrayList<Image>();
	private List<Image> mSelectedImages = new ArrayList<Image>();

	private int mItemSize;
	private GridView.LayoutParams mItemLayoutParams;

	public ImageGridAdapter(Context mContext, boolean showCamera, int mItemSize) {
		super();
		this.mContext = mContext;
		this.showCamera = showCamera;
		this.mItemSize = mItemSize;

		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mItemLayoutParams = new GridView.LayoutParams(mItemSize, mItemSize);
	}

	public boolean isShowCamera() {
		return showCamera;
	}

	public void setShowCamera(boolean showCamera) {
		if (this.showCamera == showCamera) {
			return;
		}
		this.showCamera = showCamera;
		notifyDataSetChanged();

	}

	public void setShowSeclectIndicator(boolean showSeclectIndicator) {
		this.showSeclectIndicator = showSeclectIndicator;
	}

	/**
	 * 选择某个图片，改变选择状态
	 * 
	 * @param image
	 */
	public void select(Image image) {
		if (mSelectedImages.contains(image)) {
			mSelectedImages.remove(image);
		} else {
			mSelectedImages.add(image);
		}
		notifyDataSetChanged();
	}

	/**
	 * 通过图片路径设置默认选择
	 * 
	 * @param resultList
	 */
	public void setDefaultSelected(ArrayList<String> resultList) {
		mSelectedImages.clear();
		for (String path : resultList) {
			Image image = getImageByPath(path);
			if (image != null) {
				mSelectedImages.add(image);
			}
		}
		notifyDataSetChanged();
	}

	private Image getImageByPath(String path) {
		if (mImages != null && mImages.size() > 0) {
			for (Image image : mImages) {
				if (image.path.equalsIgnoreCase(path)) {
					return image;
				}
			}
		}
		return null;
	}

	/**
	 * 设置数据集
	 * 
	 * @param list
	 */
	public void setData(List<Image> list) {
		mSelectedImages.clear();
		if (list != null && list.size() > 0) {
			mImages = list;
		} else {
			mImages.clear();
		}
		notifyDataSetChanged();
	}

	/**
	 * 重置每个Column的Size
	 * 
	 * @param columnWidth
	 */
	public void setItemSize(int columnWidth) {
		if (mItemSize == columnWidth) {
			return;
		}
		mItemSize = columnWidth;
		mItemLayoutParams = new GridView.LayoutParams(mItemSize, mItemSize);
		notifyDataSetChanged();
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if (showCamera) {
			return position == 0 ? TYPE_CAMERA : TYPE_NORMAL;
		}
		return TYPE_NORMAL;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return showCamera ? mImages.size() + 1 : mImages.size();
	}

	@Override
	public Image getItem(int position) {
		// TODO Auto-generated method stub
		if (showCamera) {
			if (position == 0) {
				return null;
			} else {
				return mImages.get(position - 1);
			}
		} else {
			return mImages.get(position);
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		int type = getItemViewType(position);
		if (type == TYPE_CAMERA) {
			convertView = mInflater.inflate(R.layout.ogau_photopicker_item_camera, parent, false);
			convertView.setTag(null);
		} else if (type == TYPE_NORMAL) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.ogau_photopicker_item_select_image, parent, false);
				holder = new ViewHolder(convertView);
			} else {
				holder = (ViewHolder) convertView.getTag();
				if (holder == null) {
					convertView = mInflater.inflate(R.layout.ogau_photopicker_item_select_image, parent, false);
					holder = new ViewHolder(convertView);
				}
			}
			if (holder != null) {
				holder.bindData(getItem(position));
			}
		}
		/** Fixed View Size */
		GridView.LayoutParams lp = (GridView.LayoutParams) convertView.getLayoutParams();
		if (lp.height != mItemSize) {
			convertView.setLayoutParams(mItemLayoutParams);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView image;
		ImageView indictor;
		View mask;

		ViewHolder(View view) {
			image = (ImageView) view.findViewById(R.id.image);
			indictor = (ImageView) view.findViewById(R.id.checkmark);
			mask = view.findViewById(R.id.mask);
			view.setTag(this);
		}

		void bindData(final Image data) {
			if (data == null) {
				return;
			}
			// 处理单选和多选状态
			if (showSeclectIndicator) {
				indictor.setVisibility(View.VISIBLE);
				if (mSelectedImages.contains(data)) {
					// 设置选中状态
					indictor.setImageResource(R.mipmap.btn_selected);
					mask.setVisibility(View.VISIBLE);
				} else {
					// 未选择
					indictor.setImageResource(R.mipmap.btn_unselected);
					mask.setVisibility(View.GONE);
				}
			} else {
				indictor.setVisibility(View.GONE);
			}
			File imageFile = new File(data.path);
			if (mItemSize > 0) {
				// 显示图片
				Glide.with(mContext).load(imageFile).placeholder(R.mipmap.default_error).error(R.mipmap.default_error).override(mItemSize, mItemSize).centerCrop().into(image);
			}

		}

	}
}
