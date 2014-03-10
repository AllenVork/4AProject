package com.ms.learn.activity;

import com.ms.learn.R;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class AboutUsActivity extends Activity {
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ÊúÆÁ
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.aboutus_activity);
		initUI();
	}

	private void initUI() {
       findViewById(R.id.bt_aboutus).setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			AboutUsActivity.this.finish();		
		}
	});
       tv= (TextView) findViewById(R.id.tv_showContent);
       tv.setMovementMethod(new ScrollingMovementMethod());
	}
	
	 
	
	
}
