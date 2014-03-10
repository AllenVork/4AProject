package com.ms.learn.activity;

import com.ms.learn.R;
import com.ms.learn.bean.PushItem;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class ShowDetailPushMsgActivity extends Activity {
	
	private TextView tv_Title,tv_Time,tv_Content;
	PushItem pushItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ÊúÆÁ
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detailpushmsg_activity);
		initUI();
	}

	private void initUI() {
       findViewById(R.id.bt_showDetailBack).setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ShowDetailPushMsgActivity.this.finish();		
		}
	    });
       
       tv_Title=(TextView) findViewById(R.id.tv_showpushTitle);
       tv_Time=(TextView) findViewById(R.id.tv_pushtime);
       tv_Content=(TextView) findViewById(R.id.tv_showpushContent);
       tv_Content.setMovementMethod(new ScrollingMovementMethod());
       pushItem=(PushItem) getIntent().getExtras().getSerializable("pushItem");
       tv_Title.setText(pushItem.getTitle());
       String time =pushItem.getPushTime();
       tv_Time.setText(time.replace("T", " ").substring(0, time.lastIndexOf(":")+3));
       tv_Content.setText(pushItem.getDetails());
       
	}

	
	
}
