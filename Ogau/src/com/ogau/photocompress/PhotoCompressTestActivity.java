package com.ogau.photocompress;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ogau.R;
import com.ogau.base.BaseActivity;
import com.ogau.photocompress.luban.Luban;
import com.ogau.photopicker.Constants;
import com.ogau.photopicker.SelectModel;
import com.ogau.photopicker.intent.PhotoPickerIntent;

public class PhotoCompressTestActivity extends BaseActivity implements OnClickListener {
	private String TAG = this.getClass().getSimpleName();
	private Context context = PhotoCompressTestActivity.this;

	private LinearLayout ll_act_m, ll_act_l, ll_act_r;
	private TextView tv_act_m, tv_act_l, tv_act_r;

	private TextView tv_pra_old, tv_pra_now,tv_path_now,tv_path_old;
	private ImageView iv_now;

	private static final int REQUEST_CAMERA_CODE = 11;

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_photo_compress_test);

		initAct();
		initViews();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private void gotoSelectPhoto() {
		PhotoPickerIntent intent = new PhotoPickerIntent(context);
		intent.setSelectModel(SelectModel.SINGLE);
		intent.setShowCamera(true);
		// intent.setImageConfig(null);
		startActivityForResult(intent, REQUEST_CAMERA_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			// 选择照片
			case REQUEST_CAMERA_CODE:
				compressPhoto(data);
				break;
			}
		}
	}

	private void compressPhoto(Intent data) {
		if (data != null) {
			ArrayList<String> photos = data.getStringArrayListExtra(Constants.EXTRA_RESULT);

			File imgFile = new File(photos.get(0));
			tv_pra_old.setText("压缩前图片参数："+imgFile.length() / 1024 + "k     " + computeSize(imgFile)[0] + "*" + computeSize(imgFile)[1]);

			for (String photo : photos) {
				compressWithRx(new File(photo));
			}
		}
	}

	private int[] computeSize(File srcImg) {
		int[] size = new int[2];

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inSampleSize = 1;

		BitmapFactory.decodeFile(srcImg.getAbsolutePath(), options);
		size[0] = options.outWidth;
		size[1] = options.outHeight;

		return size;
	}

	private void compressWithRx(File file) {
		tv_path_old.setText("压缩前图片路径："+file.getAbsolutePath());
		try {
			File fileNew=Luban.with(context).load(file).get();
			Glide.with(context).load(fileNew).into(iv_now);
		    tv_pra_now.setText("压缩后图片参数："+fileNew.length() / 1024 + "k     "+computeSize(fileNew)[0] + "*" + computeSize(fileNew)[1]);
		    tv_path_now.setText("压缩后图片路径："+fileNew.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		 Flowable.just(file)
//	        .observeOn(Schedulers.io())
//	        .map(new Function<File, File>() {
//	          @Override public File apply(@NonNull File file) throws Exception {
//	            return Luban.with(context).load(file).get();
//	          }
//	        })
//	        .observeOn(AndroidSchedulers.mainThread())
//	        .subscribe(new Consumer<File>() {
//	          @Override public void accept(@NonNull File file) throws Exception {
//	            Log.d(TAG, file.getAbsolutePath());
//
//	            Glide.with(context).load(file).into(iv_now);
//
//	            tv_pra_now.setText("压缩后图片参数："+file.length() / 1024 + "k     "+computeSize(file)[0] + "*" + computeSize(file)[1]);
//	            tv_path_now.setText("压缩后图片路径："+file.getAbsolutePath());
//	          }
//	        });
	}

	private void initViews() {
		tv_pra_old = (TextView) findViewById(R.id.tv_pra_old);
		tv_pra_now = (TextView) findViewById(R.id.tv_pra_now);
		tv_path_old = (TextView) findViewById(R.id.tv_path_old);
		tv_path_now = (TextView) findViewById(R.id.tv_path_now);
		iv_now = (ImageView) findViewById(R.id.iv_now);
	}

	private void initAct() {
		ll_act_m = (LinearLayout) findViewById(R.id.ll_act_m);
		ll_act_l = (LinearLayout) findViewById(R.id.ll_act_l);
		ll_act_r = (LinearLayout) findViewById(R.id.ll_act_r);

		tv_act_m = (TextView) findViewById(R.id.tv_act_m);
		tv_act_l = (TextView) findViewById(R.id.tv_act_l);
		tv_act_r = (TextView) findViewById(R.id.tv_act_r);

		tv_act_m.setText("图片压缩测试");
		tv_act_l.setText("返回");
		tv_act_r.setText("选择");
		// ll_act_r.setVisibility(View.GONE);
		ll_act_r.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoSelectPhoto();
			}
		});
		ll_act_l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
