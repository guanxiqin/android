package com.ogau.longscreenshoot;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ogau.R;
import com.ogau.base.BaseActivity;

public class LongScreenShootTestActivity extends BaseActivity {
	private Context context=LongScreenShootTestActivity.this;
	private String TAG=this.getClass().getSimpleName();
	private ListView listview;
	private ListAdapter myListView;

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.long_screen_shoot_activity);
		listview = (ListView) findViewById(R.id.listview);
		myListView=new ListAdapter();

		listview.setAdapter(myListView);

		File file = new File(Environment.getExternalStorageDirectory(), "aaa");
		file.mkdirs();
		for (File f : file.listFiles()) {
			f.delete();
		}
		listview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				listview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				start();
			}
		});
	}

	private void start() {
		final View view = findViewById(R.id.view);
		final ScrollableViewRECUtil scrollableViewRECUtil = new ScrollableViewRECUtil(view, ScrollableViewRECUtil.VERTICAL);
		scrollableViewRECUtil.start(new ScrollableViewRECUtil.OnRecFinishedListener() {
			@Override
			public void onRecFinish(Bitmap bitmap) {
				File f = Environment.getExternalStorageDirectory();
				System.out.print(f.getAbsoluteFile().toString());
				Toast.makeText(getApplicationContext(), f.getAbsolutePath(), Toast.LENGTH_LONG).show();
				try {
					bitmap.compress(Bitmap.CompressFormat.JPEG, 60, new FileOutputStream(new File(f, "rec" + System.currentTimeMillis() + ".jpg")));
					Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		// scrollableViewRECUtil
		view.postDelayed(new Runnable() {
			@Override
			public void run() {
				scrollableViewRECUtil.stop();
			}
		}, 90 * 1000);
	}
	
	
	private class ListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 100;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView=LayoutInflater.from(context).inflate(R.layout.long_screen_shoot_item, null);
			Button bt=(Button) convertView.findViewById(R.id.bt);
			bt.setText("第"+position+"位");
					
			return convertView;
		}
		
	}

}
