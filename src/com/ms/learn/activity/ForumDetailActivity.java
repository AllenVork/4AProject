package com.ms.learn.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.fragment.MostnewsFragment;
import com.ms.learn.fragment.TabFragmentExam;
import com.ms.learn.fragment.TabFragmentForumDetail;

public class ForumDetailActivity extends FragmentActivity {
    private TextView tv_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ÊúÆÁ
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forumdetail_activity);
		initUI();
	}

	private void initUI() {
		
		findViewById(R.id.bt_forumDetail).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ForumDetailActivity.this.finish();
			}
		});
		
	  findViewById(R.id.bt_sendPosts).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ALearnApplication.getInstance().getUserInfo().getTiezistate().equals("2")){
					Intent intent=new Intent(ForumDetailActivity.this,SendPostsActivity.class);
					intent.putExtra("id",getIntent().getExtras().getString("id") );
					startActivity(intent);
				}else{
					Toast.makeText(ForumDetailActivity.this, "±§Ç¸£¬²»ÔÊÐí·¢Ìû", 3000).show();
				}
				
			}
		});
		
		tv_title=(TextView) findViewById(R.id.tv_forumDetailTitle);
		String title=getIntent().getExtras().getString("title");
		tv_title.setText(title);
		
		FragmentManager fm = getSupportFragmentManager();
		TabFragmentForumDetail tabFragment = (TabFragmentForumDetail) fm.findFragmentById(R.id.forumDetailFragment_tab);
		tabFragment.gotoOtherView(new MostnewsFragment(),TabFragmentForumDetail.MOSTNEWS_STATE);
        		
	}

	
	
}
