package com.ms.learn.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.Helper;
import com.ms.learn.util.ShowToast;

public class RegisterActivity extends Activity {

	private Context mContext = RegisterActivity.this;
	Button bt_ok, bt_registerBack;
	EditText ed_userName_rg, ed_pwd_rg, ed_phone_rg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_activity);

		// check netWork
		if (!CheckConnectNet.isNetworkConnected(mContext)) {
			ShowToast.ShowTos(mContext, R.string.noNetwork);
		}
		initUi();
	}

	private void initUi() {
		ed_userName_rg = (EditText) findViewById(R.id.ed_userName_rg);
		ed_pwd_rg = (EditText) findViewById(R.id.ed_userPwd_rg);
		ed_phone_rg = (EditText) findViewById(R.id.ed_phoneNo_rg);

		bt_ok = (Button) findViewById(R.id.bt_ok_rg);
		bt_ok.setOnClickListener(btOnclick);
		bt_registerBack = (Button) findViewById(R.id.bt_registerBack);
		bt_registerBack.setOnClickListener(btOnclick);
	}

	private OnClickListener btOnclick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == bt_ok) {
				// 注册
				if (CheckConnectNet.isNetworkConnected(mContext)) {
					// 稍后修改
					register();
				} else {
					ShowToast.ShowTos(mContext, R.string.requestTimeout);
				}

			} else if (v == bt_registerBack) {
				// 取消
				RegisterActivity.this.finish();
			}
		}
	};

	private void register() {
		String userName = ed_userName_rg.getText().toString().trim();
		String userPwd = ed_pwd_rg.getText().toString().trim();
		String phone = ed_phone_rg.getText().toString().trim();

		if (TextUtils.isEmpty(userName)) {
			ShowToast.ShowTos(mContext, R.string.pleaseEnterName);
		} else if (userName != null && userName.length() < 2) {
			ShowToast.ShowTos(mContext, R.string.EnterNameInfo);
		}
		if (!TextUtils.isEmpty(phone) && phone.length() < 11) {
			ShowToast.ShowTos(mContext, R.string.phoneNoInfo);
		}
		if (TextUtils.isEmpty(userPwd)) {
			ShowToast.ShowTos(mContext, R.string.pleaseEnterPwd);
		} else if (userPwd != null && userPwd.length() < 6) {
			ShowToast.ShowTos(mContext, R.string.EnterNameInfo);
		}

		if (userName.length() >= 2 && userPwd.length() >= 6) {
			// 设置HTTP POST请求参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("code", ShareData.REQUEST_CODE));
			params.add(new BasicNameValuePair("function", "RegUser"));
			params.add(new BasicNameValuePair("logname", userName));
			params.add(new BasicNameValuePair("logpwd", userPwd));
			params.add(new BasicNameValuePair("uname", ""));
			params.add(new BasicNameValuePair("udid", getUDID()));
			params.add(new BasicNameValuePair("mac", Helper
					.getLocalMacAddress(mContext)));
			// phone是可选的
			if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
				params.add(new BasicNameValuePair("phone", phone));
			} else {
				params.add(new BasicNameValuePair("phone", ""));
			}
			// 注册用户
			new RegisterTask(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST,
					params, ConnectNetAsyncTask.REGISTER).execute();
		}
	}

	private class RegisterTask extends ConnectNetAsyncTask {

		public RegisterTask(Context context, String url,
				List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			// 处理结果
			System.out.println("result" + result);
			// 处理结果
			dealResult(result);
		}
	}

	// 处理返回的结果
	private void dealResult(String msg) {

		int result = Integer.valueOf(msg);
		switch (result) {
		case 211:
			// 注册成功
			ShowToast.ShowTos(mContext, R.string.registeSuccess);

			Intent intent = new Intent(RegisterActivity.this,
					LoginActivity.class);
			startActivity(intent);
			RegisterActivity.this.finish();

			break;
		case 111:
			// 账号已存在
			ShowToast.ShowTos(mContext, R.string.userExist);
			break;
		case 112:
			// 注册失败
			ShowToast.ShowTos(mContext, R.string.registeFail);
			break;
		case 113:
			// 服务器异常

			break;
		default:
			break;
		}
	}

	// get UDID
	public String getUDID() {
		String id = android.provider.Settings.System.getString(
				super.getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);
		return id;
	}
}
