package com.ogau.longscreenshoot;

import java.io.File;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnDrawListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

import com.ogau.R;
import com.ogau.base.BaseActivity;
import com.ogau.base.Constants4Paths;
import com.ogau.longscreenshoot.LongShoot1.OnSavePicFinishedListener;

@SuppressLint("NewApi")
public class LongScreenShootTestActivity extends BaseActivity implements OnClickListener, OnSavePicFinishedListener {
	private Context context = LongScreenShootTestActivity.this;
	private String TAG = this.getClass().getSimpleName();

	private LinearLayout ll_content;
	private WebView webview;
	private Button bt_test, bt_test2;
	private ListView listview;
	private ListAdapter myListView;

	LongShoot1 ls1;
	ScrollableViewRECUtil scrollableViewRECUtil;
	private String flag = "2";
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.long_screen_shoot_activity);
		initView();

		ls1 = new LongShoot1(this);
		myListView = new ListAdapter();
		listview.setAdapter(myListView);
		scrollableViewRECUtil = new ScrollableViewRECUtil(listview, ScrollableViewRECUtil.VERTICAL);

		initWebView(webview);
		testWebView();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_test:
			// start();
			saveWebviewScreen();
			break;
		case R.id.bt_test2:
			scrollableViewRECUtil.stop();
			break;
		}

	}

	private void testWebView() {
		listview.setVisibility(View.GONE);
		webview.setVisibility(View.VISIBLE);
		webview.loadUrl(Constants4Paths.BASE_PATH_4_WEB + "test/test.html");
	}

	private void saveWebviewScreen() {
		progressDialog.setMessage("正在截取屏幕内容，请稍后。。。");
		progressDialog.setCancelable(false);
		progressDialog.show();
		LayoutParams lp = (LayoutParams) webview.getLayoutParams();
		lp.height = 3000;
		lp.width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		webview.setLayoutParams(lp);
		webview.invalidate();
		flag = "1";
		// view重绘时回调
		webview.getViewTreeObserver().addOnDrawListener(new OnDrawListener() {

			@Override
			public void onDraw() {
				// TODO Auto-generated method stub
			}
		});

		// view加载完成时回调
		webview.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				Log.e(TAG, "OnGlobalLayoutListener-flag->" +flag);
				if (flag.equalsIgnoreCase("1")) {

					myHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Log.e(TAG, "myHandler-flag->" + flag);
							ls1.getViewBitmap(webview, System.currentTimeMillis() + ".jpg");
						}
					}, 500);
				}
				//addOnGlobalLayoutListener可能会被多次调用，需要移除*****
				webview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
	}

	Handler myHandler = new Handler();

	private void start() {
		// final View view = findViewById(R.id.view);
		// final ScrollableViewRECUtil scrollableViewRECUtil = new
		// ScrollableViewRECUtil(listview, ScrollableViewRECUtil.VERTICAL);
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
		// listview.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// scrollableViewRECUtil.stop();
		// }
		// }, 3000);
	}

	private class ListAdapter extends BaseAdapter {

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
			convertView = LayoutInflater.from(context).inflate(R.layout.long_screen_shoot_item, null);
			Button bt = (Button) convertView.findViewById(R.id.bt);
			bt.setText("第" + position + "位");

			return convertView;
		}

	}

	private void initWebView(WebView webView) {
		webView.getSettings().setJavaScriptEnabled(true);// 得到WebSetting对象，设置支持Javascript的参数
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		// webView.getSettings().setCacheMode(webView.getSettings().LOAD_NO_CACHE);//
		// 不使用缓存
		webView.getSettings().setDomStorageEnabled(true);// 使用localStorage则必须打开
		/**
		 * 设置自适应宽度 NORMAL：正常显示，没有渲染变化。 SINGLE_COLUMN：把所有内容放到WebView组件等宽的一列中。
		 * NARROW_COLUMNS：可能的话，使所有列的宽度不超过屏幕宽度。
		 */
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
		webView.getSettings().setBuiltInZoomControls(true);// 支持缩放功能
		webView.getSettings().setUseWideViewPort(true); // 让浏览器支持用户自定义view

		webView.getSettings().setLoadWithOverviewMode(true);// 概览模式打开网页
		webView.requestFocus();// 触摸焦点起作用
		webView.getSettings().setSavePassword(false);// 不保存密码
		webView.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);// 设置页面固定大小

		WebViewClient wvc = new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
			}

		};
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);

		// 设置WebViewClient对象
		webView.setWebViewClient(wvc);

		WebChromeClient wcc = new WebChromeClient() {

			@Override
			public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
				// TODO Auto-generated method stub
				return super.onJsAlert(view, url, message, result);
			}

			@Override
			public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
				// TODO Auto-generated method stub
				return super.onJsConfirm(view, url, message, result);
			}

		};
		webView.setWebChromeClient(wcc);
	}

	private void initView() {
		ll_content = (LinearLayout) findViewById(R.id.ll_content);
		listview = (ListView) findViewById(R.id.listview);
		bt_test = (Button) findViewById(R.id.bt_test);
		bt_test2 = (Button) findViewById(R.id.bt_test2);
		webview = (WebView) findViewById(R.id.webview);
		
		progressDialog=new ProgressDialog(context);

		bt_test.setOnClickListener(this);
		bt_test2.setOnClickListener(this);

	}

	@Override
	public void OnSavePicFinished() {
		// TODO Auto-generated method stub
		Log.d(TAG, "OnSavePicFinished-->"+"enter");
		flag = "2";
		LayoutParams lp = (LayoutParams) webview.getLayoutParams();
		lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		lp.width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		webview.setLayoutParams(lp);
		webview.invalidate();
		if(progressDialog!=null&&progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(progressDialog!=null&&progressDialog.isShowing()){
			progressDialog.dismiss();
		}
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initActb() {
		// TODO Auto-generated method stub
		
	}

}
