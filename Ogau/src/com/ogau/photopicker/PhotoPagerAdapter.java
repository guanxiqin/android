package com.ogau.photopicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import com.bumptech.glide.Glide;
import com.ogau.R;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PhotoPagerAdapter extends PagerAdapter {

	public interface PhotoViewClickLinstener {
		void OnPhotoTapLinstener(View view, float v, float v1);
	}

	public PhotoViewClickLinstener linstener;
	private Context context;
	private List<String> paths = new ArrayList<String>();
	private LayoutInflater mInflater;

	public PhotoPagerAdapter(Context context, List<String> paths) {
		super();
		this.context = context;
		this.paths = paths;
		mInflater = LayoutInflater.from(context);
	}
	public void setPhotoViewClickListener(PhotoViewClickLinstener linstener) {
		this.linstener = linstener;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return paths.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		View itemView = mInflater.inflate(R.layout.ogau_photopicker_item_preview, container, false);
		PhotoView imageView = (PhotoView) itemView.findViewById(R.id.iv_pager);

		String path = paths.get(position);
		Uri uri;
		if (path.startsWith("http")) {
			uri = Uri.parse(path);
		} else {
			uri = Uri.fromFile(new File(path));
		}
		Glide.with(context).load(uri).error(R.mipmap.default_error).centerCrop().into(imageView);
		imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				if (linstener != null) {
					linstener.OnPhotoTapLinstener(arg0, arg1, arg2);
				}
			}

		});
		container.addView(itemView);
		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View) object);

	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == object;
	}

}
