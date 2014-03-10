package com.ms.learn.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.MenuVpAdapter;
import com.ms.learn.net.ConnectNetAsyncTask;

public class MenuActivity extends Activity {
	
	ImageView imagV_news,imagV_study,imgV_exam,imgV_forum;
	ImageView imagV_message,imagV_person,imgV_office,imgV_utility,imagV_shoudian,imagV_aboutus;
	MenuVpAdapter mMenuVpAdapter;
	ViewPager mViewPager;
	List<View> views=new ArrayList<View>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menupager);
		
		initUI();
	}

	private void initUI() {		
		mViewPager=(ViewPager) findViewById(R.id.vp_menu);
		LayoutInflater inflater = LayoutInflater.from(this);
		View viewFirst=inflater.inflate(R.layout.menufirst_activity, null);
		views.add(viewFirst);
		View viewSecond=inflater.inflate(R.layout.menusecond_activity, null);
		views.add(viewSecond);
		mMenuVpAdapter=new MenuVpAdapter(views, MenuActivity.this);
		mViewPager.setAdapter(mMenuVpAdapter);
		
		imagV_news=(ImageView) viewFirst.findViewById(R.id.imgBt_news);
		imagV_study=(ImageView) viewFirst.findViewById(R.id.imgBt_study);
		imgV_exam=(ImageView) viewFirst.findViewById(R.id.imgBt_exam);
		imgV_forum=(ImageView)viewFirst.findViewById(R.id.imgBt_forum);
		imagV_message=(ImageView)viewFirst.findViewById(R.id.imgBt_message);
		imagV_person=(ImageView)viewSecond.findViewById(R.id.imgBt_person);
		imgV_office=(ImageView) viewFirst.findViewById(R.id.imgBt_office);
		imgV_utility=(ImageView) viewSecond.findViewById(R.id.imgBt_erweima);
		imgV_utility.setVisibility(View.GONE);
		imagV_shoudian=(ImageView) viewSecond.findViewById(R.id.imgBt_shoudian);
		imagV_shoudian.setVisibility(View.GONE);
		imagV_aboutus=(ImageView) viewSecond.findViewById(R.id.imgBt_aboutus);
		
		imagV_news.setOnClickListener(btOnclick);
		imagV_study.setOnClickListener(btOnclick);
		imgV_exam.setOnClickListener(btOnclick);
		imgV_forum.setOnClickListener(btOnclick);
		imagV_message.setOnClickListener(btOnclick);
		imagV_person.setOnClickListener(btOnclick);
		imgV_office.setOnClickListener(btOnclick);
		imgV_utility.setOnClickListener(btOnclick);
		imagV_shoudian.setOnClickListener(btOnclick);
		imagV_aboutus.setOnClickListener(btOnclick);
		
		/*//设置推送栏的图标
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(MenuActivity.this);
		//builder.statusBarDrawable = R.drawable.jpush_notification_icon;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为自动消失
		JPushInterface.setPushNotificationBuilder(1, builder);*/
	}

private OnClickListener btOnclick=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v==imagV_news){
				startActivity(NewsActivity.class);
			}else if(v==imagV_study){
				startActivity(StudyVideoActivity.class);
			}else if(v==imgV_exam){
				startActivity(ExamActivity.class);
			}else if(v==imgV_forum){
				startActivity(ForumListActivity.class);
			}else if(v==imagV_person){
				startActivity(PersonActivity.class);
			}else if(v==imagV_message){
				startActivity(MessageActivity.class);
			}else if(v==imgV_office){
				startActivity(OfficeActivity.class);
			}else if(v==imgV_utility){
				startActivity(CaptureActivity.class);
			}else if(v==imagV_shoudian){
				startActivity(SdtActivity.class);
			}else if(v==imagV_aboutus){
				startActivity(AboutUsActivity.class);
			}
			
		}
	};  

	private void startActivity(Class toClass){
		Intent intent=new Intent(MenuActivity.this,toClass);
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			showExitDialog();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void showExitDialog(){
		new AlertDialog.Builder(MenuActivity.this).setTitle("确定退出").setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
				
				List<NameValuePair> params_exit = new ArrayList<NameValuePair>();
				params_exit.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
				params_exit.add(new BasicNameValuePair("function","Exit"));
				params_exit.add(new BasicNameValuePair("uid",ALearnApplication.getInstance().getUserInfo().getUserId()));
				new Exit(MenuActivity.this,ShareData.LANSHAOQI_ADDRESS_DOPOST,params_exit, ConnectNetAsyncTask.EXIT).execute();
				MenuActivity.this.finish();
			
			}
		}).create().show();
	}
	private class Exit extends ConnectNetAsyncTask{

		public Exit(Context context, String url, List<NameValuePair> params,	int flag) {
			super(context, url, params, flag);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void doResult(String result) {
			
		}
		
	}
}
