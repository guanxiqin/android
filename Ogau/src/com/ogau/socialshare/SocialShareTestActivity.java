package com.ogau.socialshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ogau.R;
import com.ogau.base.BaseActivity;
import com.ogau.socialshare.mobshare.MobShareActivity;

public class SocialShareTestActivity extends BaseActivity implements OnClickListener {

	private Button bt_share_mob;

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_social_share_test);

		initViews();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_share_mob:
			gotoMobShareActivity();
			break;
		}

	}
	private void gotoMobShareActivity(){
		Intent intent=new Intent(this, MobShareActivity.class);
		startActivity(intent);
		
	}

	private void initViews() {
		bt_share_mob = (Button) findViewById(R.id.bt_share_mob);

		bt_share_mob.setOnClickListener(this);
	}

}
