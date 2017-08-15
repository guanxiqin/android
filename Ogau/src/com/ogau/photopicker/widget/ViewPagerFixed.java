package com.ogau.photopicker.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerFixed extends ViewPager {

	public ViewPagerFixed(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ViewPagerFixed(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			return super.onInterceptTouchEvent(arg0);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		try {
			return super.onTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return false;
	}

}
