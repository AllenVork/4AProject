package com.ms.learn.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.ms.learn.R;
import com.ms.learn.fragment.TabFragmentExam;

public class ExamActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ÊúÆÁ
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.exam_activity);
		initUI();
	}

	private void initUI() {
		
		findViewById(R.id.bt_examBack).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ExamActivity.this.finish();
			}
		});
		
		FragmentManager fm = getSupportFragmentManager();
		TabFragmentExam tabFragment = (TabFragmentExam) fm.findFragmentById(R.id.examFragment_tab);
		tabFragment.gotoExamView();
        		
	}

	@Override
	protected void onResume() {
		
		super.onResume();
	}
   
	
	
}
