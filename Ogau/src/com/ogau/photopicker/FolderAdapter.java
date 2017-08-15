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
import android.widget.ImageView;
import android.widget.TextView;

public class FolderAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<Folder> mFolders = new ArrayList<Folder>();
	int mImageSize;
	int lastSelected = 0;
	
	public FolderAdapter(Context context) {
		super();
		this.mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mImageSize = mContext.getResources().getDimensionPixelOffset(R.dimen.ogau_photopicker_folder_cover_size);
	}

	public void setData(List<Folder> folders) {
		if (folders != null && folders.size() > 0) {
			mFolders = folders;
		} else {
			mFolders.clear();
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFolders.size() + 1;
	}

	@Override
	public Folder getItem(int position) {
		// TODO Auto-generated method stub
		if (position == 0)
			return null;

		return mFolders.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.ogau_photopicker_item_folder, parent, false);
			holder = new ViewHolder(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (holder != null) {
			if (position == 0) {
				holder.name.setText("所有图片");
				holder.size.setText(getTotalImageSize() + "张");
				if (mFolders.size() > 0) {
					Folder f = mFolders.get(0);
					Glide.with(mContext).load(new File(f.path)).error(R.mipmap.default_error).override(mImageSize, mImageSize).centerCrop().into(holder.cover);
				}
			} else {
				holder.bindData(mFolders.get(position));
			}
			if (lastSelected == position) {
				holder.indicator.setVisibility(View.VISIBLE);
			} else {
				holder.indicator.setVisibility(View.INVISIBLE);
			}
		}
		return convertView;
	}

	private int getTotalImageSize() {
		int result = 0;
		if (mFolders != null && mFolders.size() > 0) {
			for (Folder f : mFolders) {
				result += f.images.size();
			}
		}
		return result;
	}

	public void setSelectIndex(int i) {
		if (lastSelected == i) {
			return;
		}
		lastSelected = i;
		notifyDataSetChanged();
	}

	public int getSelectIndex() {
		return lastSelected;
	}

	class ViewHolder {
		ImageView cover;
		TextView name;
		TextView size;
		ImageView indicator;

		ViewHolder(View view) {
			cover = (ImageView) view.findViewById(R.id.cover);
			name = (TextView) view.findViewById(R.id.name);
			size = (TextView) view.findViewById(R.id.size);
			indicator = (ImageView) view.findViewById(R.id.indicator);
			view.setTag(this);
		}

		void bindData(Folder data) {
			name.setText(data.name);
			size.setText(data.images.size() + "张");
			Glide.with(mContext).load(new File(data.cover.path)).placeholder(R.mipmap.default_error).error(R.mipmap.default_error).override(mImageSize, mImageSize).centerCrop()
					.into(cover);
		}

	}

}
