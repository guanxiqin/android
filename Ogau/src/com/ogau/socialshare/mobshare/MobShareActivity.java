package com.ogau.socialshare.mobshare;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.ogau.R;
import com.ogau.base.BaseActivity;
import com.ogau.utils.PhoneUtils;

public class MobShareActivity extends BaseActivity implements OnClickListener {

	private String TAG = this.getClass().getSimpleName();
	private Context context = MobShareActivity.this;

	private Button bt_sina_shar_all, bt_wc_shar_all, bt_wechat_shar_all, bt_wechat_moment, bt_wechat_favorite;

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_social_share_mob);

		initViews();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub bt_wechat_favorite
		switch (v.getId()) {
		case R.id.bt_sina_shar_all:
			doSinaShareAll();
			break;
		case R.id.bt_wc_shar_all:
			doWxWiBoShareAll();
			break;
		case R.id.bt_wechat_shar_all:
			doWechatFirendShareAll();
			break;
		case R.id.bt_wechat_moment:
			doWeChatMoentShare();
			break;
		case R.id.bt_wechat_favorite:
			doWeChatFavoriteShare();
			break;
		}

	}

	/**
	 *新浪微博 
	 */
	private void doSinaShareAll() {
		ShareParams sp = new ShareParams();
		sp.setText("测试分享的文本  www.baidu.com");
		sp.setImagePath(PhoneUtils.getExternalPath() + "/MagazineUnlock/aa.jpg");
		// sp.setUrl("www.baidu.com");
		sp.setImageUrl("www.baidu.com");

		Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
		// weibo.setPlatformActionListener(paListener); // 设置分享事件回调
		// 执行图文分享
		weibo.share(sp);
	}

	/**
	 * 微信微博
	 */
	private void doWxWiBoShareAll() {
		Platform platform = ShareSDK.getPlatform(TencentWeibo.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText("测试分享的文本  www.baidu.com");
		shareParams.setImagePath(PhoneUtils.getExternalPath() + "/MagazineUnlock/aa.jpg");
		// shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setShareTencentWeibo(true);
		shareParams.setShareType(Platform.SHARE_IMAGE);
		// platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	/**
	 * 微信好友
	 */
	private void doWechatFirendShareAll() {
		Platform platform = ShareSDK.getPlatform(Wechat.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText("测试分享的文本  www.baidu.com");
		shareParams.setImagePath(PhoneUtils.getExternalPath() + "/MagazineUnlock/aa.jpg");
		// shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		// shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		// platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	/**
	 * 微信朋友圈
	 */
	private void doWeChatMoentShare() {
		Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText("测试分享的文本  www.baidu.com");
		shareParams.setImagePath(PhoneUtils.getExternalPath() + "/MagazineUnlock/aa.jpg");
		// shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		// shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setShareType(Platform.SHARE_IMAGE);
		// platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	/**
	 * 微信收藏
	 */
	private void doWeChatFavoriteShare() {
		Platform platform = ShareSDK.getPlatform(WechatFavorite.NAME);
		Platform.ShareParams shareParams = new Platform.ShareParams();
		shareParams.setText("测试分享的文本  www.baidu.com");
		shareParams.setImagePath(PhoneUtils.getExternalPath() + "/MagazineUnlock/aa.jpg");
//		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
//		shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
		shareParams.setShareType(Platform.SHARE_IMAGE);
//		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		bt_sina_shar_all = (Button) findViewById(R.id.bt_sina_shar_all);
		bt_wc_shar_all = (Button) findViewById(R.id.bt_wc_shar_all);
		bt_wechat_shar_all = (Button) findViewById(R.id.bt_wechat_shar_all);
		bt_wechat_moment = (Button) findViewById(R.id.bt_wechat_moment);
		bt_wechat_favorite = (Button) findViewById(R.id.bt_wechat_favorite);

		bt_sina_shar_all.setOnClickListener(this);
		bt_wc_shar_all.setOnClickListener(this);
		bt_wechat_shar_all.setOnClickListener(this);
		bt_wechat_moment.setOnClickListener(this);
		bt_wechat_favorite.setOnClickListener(this);

	}

	@Override
	protected void initActb() {
		// TODO Auto-generated method stub

	}

}
