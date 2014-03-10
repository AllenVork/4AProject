package com.ms.learn.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;

public class PersonActivity extends Activity {
	private Context mContext=PersonActivity.this;
	private EditText ed_oldPwd,ed_newPwd,ed_userName,ed_newPwdAgin;
	private Button btChangePwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.person_activity);
		initUI();
		checkChange();
	}



	private void initUI() {
		ed_userName=(EditText) findViewById(R.id.ed_userNamePerson);
		
		ed_oldPwd=(EditText) findViewById(R.id.ed_userPwdPerson);
		ed_newPwd=(EditText) findViewById(R.id.ed_userNewPwdPerson);
		ed_newPwdAgin=(EditText) findViewById(R.id.ed_userNewPwdPersonSec);
		btChangePwd=(Button) findViewById(R.id.bt_changePwd);
		btChangePwd.setOnClickListener(btChangeOnclick);
		
		findViewById(R.id.bt_personBack).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PersonActivity.this.finish();
			}
		});
	}
   
	private void checkChange() {
		 String userNam=ALearnApplication.getInstance().getUserInfo().getUserName();
		if(CheckConnectNet.isNetworkConnected(mContext)){
	        if(userNam!=null){
	        	ed_userName.setText(userNam);
	        }
		}else {
			ShowToast.ShowTos(mContext, R.string.noNetwork);
		}
		
	}
	
	private void changePwd(){
		String newPwd=ed_newPwd.getText().toString();
		String newPwdSec=ed_newPwdAgin.getText().toString();
		String oldPwd=ed_oldPwd.getText().toString();
		if(newPwd==null||newPwdSec==null||oldPwd==null){
			ShowToast.ShowTos(mContext, R.string.pleaseEnter);
		}else if(!newPwd.equals(newPwdSec)){
			ShowToast.ShowTos(mContext, R.string.errorPwd);
		}else if(newPwd.length()<6){
			ShowToast.ShowTos(mContext, R.string.EnterPwdInfo);
		}
		String id=ALearnApplication.getInstance().getUserInfo().getUserId();
		if(id!=null){
			
			List<NameValuePair> params_ChangePwd = new ArrayList<NameValuePair>();
			params_ChangePwd.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
			params_ChangePwd.add(new BasicNameValuePair("function","UpdatePwd"));
			params_ChangePwd.add(new BasicNameValuePair("uid", id));
			params_ChangePwd.add(new BasicNameValuePair("historyPwd",oldPwd));
			params_ChangePwd.add(new BasicNameValuePair("newPwd", newPwd));
			new ChangePwdTask(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_ChangePwd, ConnectNetAsyncTask.CHANGEPWD).execute();
			
			
		}else{
			ShowToast.ShowTos(mContext, R.string.loginOnline);
		}
		
		
		
	}
	private OnClickListener btChangeOnclick=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(CheckConnectNet.isNetworkConnected(mContext)){
				  changePwd();
			}else {
				ShowToast.ShowTos(mContext, R.string.noNetwork);
			}
			  
		}
	};
	private class ChangePwdTask  extends ConnectNetAsyncTask{

		public ChangePwdTask(Context context, String url,	List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			System.out.println("+++++result+++++++++"+result);
			/*211（修改成功）
			112（修改失败）
			114（原密码错误）
			111（用户不存在或用户被删除）
			113（服务器异常）*/
			if("211".equals(result)){
				ShowToast.ShowTos(mContext, R.string.changePwdSuccess);
			}else if("112".equals(result)||"113".equals(result)){
				ShowToast.ShowTos(mContext, R.string.changePwdfail);
			}else if("114".equals(result)){
				ShowToast.ShowTos(mContext, R.string.oldPwderro);
			}else if("111".equals(result)){
				ShowToast.ShowTos(mContext, R.string.userNotexist);
			}
			
		}
		
	} 
}
