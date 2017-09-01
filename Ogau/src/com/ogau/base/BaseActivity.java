package com.ogau.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseActivity extends FragmentActivity {

	// *******************透明状态栏和浸入式导航栏1******************
	/*public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		View mDecorView = getWindow().getDecorView();
		if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
			// | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
			// | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
			window.setNavigationBarColor(Color.TRANSPARENT);
		}

	}*/

	// *******************透明状态栏和浸入式导航栏2******************
	private void initWindows() {
		Window window = getWindow();
		int color = getResources().getColor(android.R.color.holo_blue_light);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			// 设置状态栏颜色
			window.setStatusBarColor(color);
			// 设置导航栏颜色
			window.setNavigationBarColor(color);
			ViewGroup contentView = ((ViewGroup) findViewById(android.R.id.content));
			View childAt = contentView.getChildAt(0);
			if (childAt != null) {
				childAt.setFitsSystemWindows(true);
			}
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 透明状态栏
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			// 设置contentview为fitsSystemWindows
			ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
			View childAt = contentView.getChildAt(0);
			if (childAt != null) {
				childAt.setFitsSystemWindows(true);
			}
			// 给statusbar着色
			View view = new View(this);
			view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(this)));
			view.setBackgroundColor(color);
			contentView.addView(view);
		}
	}

	/**
	 * 获取导航栏高度
	 * 
	 * @param context
	 *            context
	 * @return 导航栏高度
	 */
	public static int getNavigationBarHeight(Context context) {
		int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}

	/**
	 * 获取状态栏高度
	 * 
	 * @param context
	 *            context
	 * @return 状态栏高度
	 */
	private static int getStatusBarHeight(Context context) {
		// 获得状态栏高度
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * 初始化界面
	 */
	protected abstract void initViews();
	/**
	 * 初始化状态栏
	 */
	protected abstract void initActb();
}
