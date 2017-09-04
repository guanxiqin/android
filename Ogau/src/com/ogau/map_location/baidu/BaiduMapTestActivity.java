package com.ogau.map_location.baidu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.ogau.R;
import com.ogau.base.BaseActivity;

public class BaiduMapTestActivity extends BaseActivity implements OnClickListener {

	private String TAG = this.getClass().getSimpleName();
	private Context context = BaiduMapTestActivity.this;

	private MapView bmapView;

	private BaiduMap mBaiduMap;
	private LocationClient bLocationClient;
	private MyBaiDuLocationListener bLocationListener;
	private LocationClientOption locationOption;
	
	private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float direction;

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_baidu_map_test);
		initViews();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		bmapView = (MapView) findViewById(R.id.bmapView);

		mBaiduMap = bmapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);

		bLocationClient = new LocationClient(context);
		bLocationListener = new MyBaiDuLocationListener();
		locationOption = new LocationClientOption();

		initLocationOption(locationOption);
		bLocationClient.registerLocationListener(bLocationListener);

		bLocationClient.start();

	}

	/**
	 * @param option
	 * 定位参数
	 */
	private void initLocationOption(LocationClientOption option) {
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
		bLocationClient.setLocOption(option);
	}

	@Override
	protected void initActb() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		bmapView.onDestroy();
		mBaiduMap.setMyLocationEnabled(false);
		bmapView.onDestroy();
		bmapView = null;
		super.onDestroy();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		bmapView.onPause();
		super.onPause();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		bmapView.onResume();
		super.onResume();

	}

	private class MyBaiDuLocationListener extends BDAbstractLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null || bmapView == null) {
				return;
			}
			mCurrentLat = location.getLatitude();
			mCurrentLon = location.getLongitude();
			mCurrentAccracy = location.getRadius();
			locData = new MyLocationData.Builder().accuracy(location.getRadius())
			// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(mCurrentDirection).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatus.Builder builder = new MapStatus.Builder();
				builder.target(ll).zoom(18.0f);
				mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
			}

			// 获取定位结果
			location.getTime(); // 获取定位时间
			location.getLocationID(); // 获取定位唯一ID，v7.2版本新增，用于排查定位问题
			location.getLocType(); // 获取定位类型
			location.getLatitude(); // 获取纬度信息
			location.getLongitude(); // 获取经度信息
			location.getRadius(); // 获取定位精准度
			location.getAddrStr(); // 获取地址信息
			location.getCountry(); // 获取国家信息
			location.getCountryCode(); // 获取国家码
			location.getCity(); // 获取城市信息
			location.getCityCode(); // 获取城市码
			location.getDistrict(); // 获取区县信息
			location.getStreet(); // 获取街道信息
			location.getStreetNumber(); // 获取街道码
			location.getLocationDescribe(); // 获取当前位置描述信息
			location.getPoiList(); // 获取当前位置周边POI信息

			location.getBuildingID(); // 室内精准定位下，获取楼宇ID
			location.getBuildingName(); // 室内精准定位下，获取楼宇名称
			location.getFloor(); // 室内精准定位下，获取当前位置所处的楼层信息

			if (location.getLocType() == BDLocation.TypeGpsLocation) {

				// 当前为GPS定位结果，可获取以下信息
				location.getSpeed(); // 获取当前速度，单位：公里每小时
				location.getSatelliteNumber(); // 获取当前卫星数
				location.getAltitude(); // 获取海拔高度信息，单位米
				location.getDirection(); // 获取方向信息，单位度

			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {

				// 当前为网络定位结果，可获取以下信息
				location.getOperators(); // 获取运营商信息

			} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

				// 当前为网络定位结果

			} else if (location.getLocType() == BDLocation.TypeServerError) {

				// 当前网络定位失败
				// 可将定位唯一ID、IMEI、定位失败时间反馈至loc-bugs@baidu.com

			} else if (location.getLocType() == BDLocation.TypeNetWorkException) {

				// 当前网络不通

			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {

				// 当前缺少定位依据，可能是用户没有授权，建议弹出提示框让用户开启权限
				// 可进一步参考onLocDiagnosticMessage中的错误返回码

			}
		}

	}

}
