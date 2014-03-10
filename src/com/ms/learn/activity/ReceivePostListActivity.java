package com.ms.learn.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;

import com.ms.learn.R;
import com.ms.learn.adapter.PostsDetailAdapter;

public class ReceivePostListActivity extends Activity {
	private ListView mListView;
	static PostsDetailAdapter mPostsDetailAdapter;
    
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ÊúÆÁ
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recevivepostslist_activity);
		initUI();
	}

	private void initUI() {
		 mListView=(ListView) findViewById(R.id.lv_postsDetail);
		 
         mPostsDetailAdapter=new PostsDetailAdapter(ReceivePostListActivity.this,PostsDetailActivity.postsReciveEnrtys);
         mListView.setAdapter(mPostsDetailAdapter);
         
         findViewById(R.id. bt_postsrecei).setOnClickListener(new OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				
 				ReceivePostListActivity.this.finish();
 				
 			}
 		});
		
	}
	
	 public static void notifiDatachange(int position){
		 PostsDetailActivity.postsReciveEnrtys.remove(position);
		 mPostsDetailAdapter.notifyDataSetChanged();
		 
	 } 
}
