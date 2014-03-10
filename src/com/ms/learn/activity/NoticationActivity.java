package com.ms.learn.activity;

import com.ms.learn.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class NoticationActivity extends Activity {
	Button bt_enter,bt_cacel;
	TextView tv_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ÊúÆÁ
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.notification_layout);
		initUI();
	}

	private void initUI() {
		  bt_enter=(Button)findViewById(R.id.bt_notificationenter);
		  bt_enter.setOnClickListener(btClickListener);
	      bt_cacel=(Button)findViewById(R.id.bt_notificationCancel);
	      bt_cacel.setOnClickListener(btClickListener);
	      tv_content=(TextView) findViewById(R.id.tv_notificationContent);
	      tv_content.setMovementMethod(new ScrollingMovementMethod());
	      String msg= getIntent().getExtras().getString("msg");
	      tv_content.setText(msg);
		  
	}
	
	private OnClickListener btClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v==bt_enter){
				Intent intent=new Intent(NoticationActivity.this,LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				NoticationActivity.this.finish();
			}else if(v==bt_cacel){
				
				NoticationActivity.this.finish();
			}
			
			
		}
	};

}
