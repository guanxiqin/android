package com.ogau.widget.wheelselect;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ogau.R;
import com.ogau.base.BaseActivity;
import com.ogau.widget.wheelselect.kankan.wheel.widget.OnWheelChangedListener;
import com.ogau.widget.wheelselect.kankan.wheel.widget.OnWheelScrollListener;
import com.ogau.widget.wheelselect.kankan.wheel.widget.WheelView;
import com.ogau.widget.wheelselect.kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * @author guanxiqin 需要引入drawable中的两个文件：ogau_wheel_bg.xml和ogau_wheel_val.xml
 *         以及一个颜色 ogau_wheel_province_line_border
 * 
 */
public class WheelSecletcTestActivity extends BaseActivity implements OnClickListener, OnWheelChangedListener,OnWheelScrollListener {
	private String TAG = this.getClass().getSimpleName();
	private Context context = WheelSecletcTestActivity.this;

	private WheelView wsv_lv1;
	private WheelView wsv_lv2;
	private WheelView wsv_lv3;
	private Button bt_confirm;

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_wheel_select_test);
		initActb();
		initViews();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		wsv_lv1 = (WheelView) findViewById(R.id.wsv_lv1);
		wsv_lv2 = (WheelView) findViewById(R.id.wsv_lv2);
		wsv_lv3 = (WheelView) findViewById(R.id.wsv_lv3);
		bt_confirm = (Button) findViewById(R.id.bt_confirm);

		wsv_lv1.addChangingListener(this);
		wsv_lv3.addChangingListener(this);
		wsv_lv3.addChangingListener(this);
		bt_confirm.setOnClickListener(this);

	}

	@Override
	protected void initActb() {
		// TODO Auto-generated method stub

	}

	/**
	 * 获取一级菜单数据
	 */
	private void loadLv1Data() {

	}

	/**
	 * 获取二级菜单数据
	 */
	private void loadLv2Data() {

	}

	/**
	 * 获取三级菜单数据
	 */
	private void loadLv3Data() {

	}

	/**
	 * 更新一级菜单
	 */
	private void upLv1() {
//		initProvinceDatas();
		wsv_lv1.setViewAdapter(new ArrayWheelAdapter<String>(context, null));
		// 设置可见条目数量
		wsv_lv1.setVisibleItems(7);
		wsv_lv2.setVisibleItems(7);
		wsv_lv3.setVisibleItems(7);
		upLv2();
		upLv3();
	}

	/**
	 * 更新二级菜单
	 */
	private void upLv2() {

	}

	/**
	 * 更新三级菜单
	 */
	private void upLv3() {

	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == wsv_lv1) {
			upLv2();
		} else if (wheel == wsv_lv2) {
			upLv3();
		} else if (wheel == wsv_lv3) {

		}
	}

	@Override
	public void onScrollingStarted(WheelView wheel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollingFinished(WheelView wheel) {
		// TODO Auto-generated method stub
		wheel.getCurrentItem();
		
	}

}
