package com.ogau.longscreenshoot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.ogau.photopicker.Constants;
import com.ogau.utils.PhoneUtils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

public class LongShoot1 {
	
	
	public static String TAG = "LongShoot1";
	private OnSavePicFinishedListener onSavePicFinishedListener;
	
	

	public LongShoot1(OnSavePicFinishedListener onSavePicFinishedListener) {
		super();
		this.onSavePicFinishedListener = onSavePicFinishedListener;
	}
	public  void saveBitmap(Bitmap bm, String picName) {
		Log.e(TAG, "保存图片");
		File f = new File(PhoneUtils.getExternalPath(), picName);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
			onSavePicFinishedListener.OnSavePicFinished();
			Log.i(TAG, "已经保存");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 截图view
	 * **/
	public  Bitmap getViewBitmap(View view,  String picName) {
		int h = 0;
		Bitmap bitmap;
		Log.d(TAG, "实际高度:" + h);
		Log.d(TAG, "list 高度:" + view.getHeight());
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
		
		saveBitmap(bitmap, picName);
		return bitmap;
	}
	// 获取指定Activity的截屏，保存到png文件
		public  Bitmap takeScreenShotView(View view, String picName) {
			// View是你需要截图的View
			view.setDrawingCacheEnabled(true);
			view.buildDrawingCache();
			Bitmap b1 = view.getDrawingCache();

			// 获取状态栏高度
			Rect frame = new Rect();
			int statusBarHeight = frame.top;
			System.out.println(statusBarHeight);

			// 获取屏幕长和高
//			int width = activity.getWindowManager().getDefaultDisplay().getWidth();
			int width = view.getWidth();
//			int height = activity.getWindowManager().getDefaultDisplay().getHeight();
			int height = view.getHeight();
			// 去掉标题栏
			// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
			Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);
			view.destroyDrawingCache();
			saveBitmap(b, picName);
			return b;
		}
	// 获取指定Activity的截屏，保存到png文件
	public  Bitmap takeScreenShot(Activity activity, String picName) {
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		System.out.println(statusBarHeight);

		// 获取屏幕长和高
//		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int width = view.getWidth();
//		int height = activity.getWindowManager().getDefaultDisplay().getHeight();
		int height = view.getHeight();
		// 去掉标题栏
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);
		view.destroyDrawingCache();
		saveBitmap(b, picName);
		return b;
	}

	/**
	 * 截图listview
	 * **/
	public  Bitmap getListViewBitmap(ListView listView, String picpath) {
		int h = 0;
		Bitmap bitmap;
		// 获取listView实际高度
		for (int i = 0; i < listView.getChildCount(); i++) {
			h += listView.getChildAt(i).getHeight();
		}
		Log.d(TAG, "实际高度:" + h);
		Log.d(TAG, "list 高度:" + listView.getHeight());
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(listView.getWidth(), h, Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		listView.draw(canvas);
//		// 测试输出
//		FileOutputStream out = null;
//		try {
//			out = new FileOutputStream(picpath);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		try {
//			if (null != out) {
//				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//				out.flush();
//				out.close();
//			}
//		} catch (IOException e) {
//		}
		saveBitmap(bitmap, picpath);
		return bitmap;
	}
	 /**
     * 截取scrollview的屏幕
     * **/
    public  Bitmap getScrollViewBitmap(ScrollView scrollView,String picpath,String picName) {
        int h = 0;
        Bitmap bitmap;
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        Log.d(TAG, "实际高度:" + h);
        Log.d(TAG, " 高度:" + scrollView.getHeight());
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        // 测试输出
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(picpath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
        }
        saveBitmap(bitmap, picName);
        return bitmap;
    }

	
	
	// 获取指定Activity的截屏，保存到png文件
		public  Bitmap takeScreenShotByWindow(Activity activity, String picName) {
//			LayoutParams lp=new LayoutParams(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
//			View myView=activity.getWindow().getDecorView();
//			Window myWindow=activity.getWindow();
//			myWindow.addContentView(myView, lp);
//			
			//****************************************************************
			// View是你需要截图的View
			View view = activity.getWindow().getDecorView().getRootView();
//			View view = myWindow.getDecorView();
			view.setDrawingCacheEnabled(true);
			view.buildDrawingCache();
			Bitmap b1 = view.getDrawingCache();

			// 获取状态栏高度
			Rect frame = new Rect();
			activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
			int statusBarHeight = frame.top;
			System.out.println(statusBarHeight);

			// 获取屏幕长和高
//			int width = activity.getWindowManager().getDefaultDisplay().getWidth();
			int width = view.getWidth();
//			int height = activity.getWindowManager().getDefaultDisplay().getHeight();
			int height = view.getHeight();
			// 去掉标题栏
			// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
			Bitmap b = Bitmap.createBitmap(b1, 0, 0, width, height);
			view.destroyDrawingCache();
			saveBitmap(b, picName);
			return b;
		}
		
		public interface OnSavePicFinishedListener {
			void OnSavePicFinished();
		}
}
