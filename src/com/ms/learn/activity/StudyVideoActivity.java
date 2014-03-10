package com.ms.learn.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;

import com.ms.learn.R;

public class StudyVideoActivity extends TabActivity {

	private TabHost mTabHost;
	private Button[] btTab = new Button[4];

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ÎÞtitle
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ÊúÆÁ
		setContentView(R.layout.studyvideo_activity);
		initUI();
	}

	private void initUI() {

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		// mTabHost=getTabHost();
		mTabHost.addTab(mTabHost.newTabSpec("Recommend").setIndicator("", null)
				.setContent(new Intent(this, RecommendActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec(" Course").setIndicator("", null)
				.setContent(new Intent(this, CourseActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("Download").setIndicator("", null)
				.setContent(new Intent(this, DownloadActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("History").setIndicator("", null)
				.setContent(new Intent(this, HistoryActivity.class)));

		btTab[0] = (Button) findViewById(R.id.bt_recommend);
		btTab[1] = (Button) findViewById(R.id.bt_choiceCourse);
		btTab[2] = (Button) findViewById(R.id.bt_download);
		btTab[3] = (Button) findViewById(R.id.bt_history);
		for (int i = 0; i < btTab.length; i++) {
			btTab[i].setOnClickListener(viewListener);
		}
		mTabHost.setCurrentTab(0);
		btTab[0].setSelected(true);
	}

	private OnClickListener viewListener = new View.OnClickListener() {
		int i = -1;

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.bt_recommend) {
				mTabHost.setCurrentTab(0);
				i = 0;
			} else if (v.getId() == R.id.bt_choiceCourse) {
				mTabHost.setCurrentTab(1);
				i = 1;
			} else if (v.getId() == R.id.bt_download) {
				mTabHost.setCurrentTab(2);
				i = 2;
			} else if (v.getId() == R.id.bt_history) {
				mTabHost.setCurrentTab(3);
				i = 3;
			}
			for (int j = 0; j < 4; j++) {
				if (i == j) {
					btTab[j].setSelected(true);
				} else {
					btTab[j].setSelected(false);
				}

			}

		}
	};

}
