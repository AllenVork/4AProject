package com.ms.learn.activity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.SplashAdapter;
import com.ms.learn.bean.UserInfo;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.Helper;
import com.ms.learn.util.ShowToast;
import com.ms.learn.util.UpdateManager;
import com.ms.learn.widgets.SwitchButton;
import com.ms.learn.xml.ParseXmlLogin;

public class LoginActivity extends Activity implements OnPageChangeListener {
	private Context mContext = LoginActivity.this;
	private static final String SHAREPER_SAVEUSERNAME = "4ALearn_userName";
	private static final int CHANGEVIEW = 0x12;
	private static final String SHAREPER_HADLOAD = "4ALearn_loadSplash";
	private static final String SHAREPRE_SWITCHBUTTON = "4ALearn_switchButton";
	EditText ed_userName, ed_Pwd;
	Button bt_login, bt_register, bt_localLogin;
	SwitchButton bt_remenberPwd;// our button
	String userName, userPwd;
	SharedPreferences switchButton_preferences, loadSplash_preferences;
	LayoutInflater mInflater, mInflater2;
	View loginView, splashView;
	private ViewPager vp;
	private SplashAdapter splashAdapter;
	private List<View> views;
	// �ײ�С��ͼƬ
	private ImageView[] dots;
	// ��¼��ǰѡ��λ��
	private int currentIndex;
	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == CHANGEVIEW) {

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		mInflater = LayoutInflater.from(mContext);

		loadSplash_preferences = getSharedPreferences(SHAREPER_HADLOAD,
				Activity.MODE_PRIVATE);
		boolean hadLoad = loadSplash_preferences.getBoolean("hadLoad", false);
		if (!hadLoad) {
			// ������ع���״̬
			splashView = mInflater.inflate(R.layout.guide, null);
			loginView = mInflater.inflate(R.layout.login_activity, null);
			setContentView(splashView);
			SharedPreferences.Editor editor = loadSplash_preferences.edit();
			editor.putBoolean("hadLoad", true);
			editor.commit();
			// ��ʼ��ҳ��
			initViews();
			// ��ʼ���ײ�С��
			initDots();
		} else {
			loginView = mInflater.inflate(R.layout.login_activity, null);
			setContentView(loginView);
			initUI();
		}

		if (!CheckConnectNet.isNetworkConnected(mContext)) {
			ShowToast.ShowTos(mContext, R.string.noNetwork);
		}
	}

	private void initUI() {

		// wifi״̬�¼��汾����
		if (CheckConnectNet.isWifiConnect(LoginActivity.this)) {
			UpdateManager updateManager = new UpdateManager(LoginActivity.this);
			updateManager.checkUpdateInfo();
		}

		ed_userName = (EditText) loginView.findViewById(R.id.ed_userName);
		ed_Pwd = (EditText) loginView.findViewById(R.id.ed_userPwd);
		bt_remenberPwd = (SwitchButton) loginView
				.findViewById(R.id.bt_isRemPwd);
		// save password username
		SharedPreferences user_preferences = getSharedPreferences(
				SHAREPER_SAVEUSERNAME, Activity.MODE_PRIVATE);
		String userNam = user_preferences.getString("userName", "");
		String userPw = user_preferences.getString("userPwd", "");

		ed_userName.setText(userNam);
		// get state of save password
		switchButton_preferences = getSharedPreferences(SHAREPRE_SWITCHBUTTON,
				Activity.MODE_PRIVATE);
		boolean isCheck = switchButton_preferences.getBoolean("isCheck", false);
		bt_remenberPwd.setChecked(isCheck);
		if (isCheck) {
			ed_Pwd.setText(userPw);
		}

		bt_login = (Button) loginView.findViewById(R.id.bt_login);
		bt_login.setOnClickListener(btOnclick);

		bt_register = (Button) loginView.findViewById(R.id.bt_register);
		bt_register.setOnClickListener(btOnclick);

		// bt_localLogin = (Button)loginView. findViewById(R.id.bt_localLogin);
		// bt_localLogin.setOnClickListener(btOnclick);

		// bt_remenberPwd
		bt_remenberPwd
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						// ���滬����ť��״̬
						SharedPreferences.Editor editor = switchButton_preferences
								.edit();
						editor.putBoolean("isCheck", isChecked);
						editor.commit();

					}
				});
	}

	private OnClickListener btOnclick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == bt_login) {
				// login
				// check netWork
				if (CheckConnectNet.isNetworkConnected(mContext)) {
					// �Ժ��޸�
					loginMethod();
				} else {
					ShowToast.ShowTos(mContext, R.string.requestTimeout);
				}
			} else if (v == bt_register) {
				// register
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			} /*
			 * else if (v == bt_localLogin) { // local login }
			 */
		}
	};

	private void loginMethod() {
		userName = ed_userName.getText().toString().trim();
		userPwd = ed_Pwd.getText().toString().trim();
		if (TextUtils.isEmpty(userName)) {
			ShowToast.ShowTos(mContext, R.string.pleaseEnterName);
		} else if (userName != null && userName.length() < 2) {
			ShowToast.ShowTos(mContext, R.string.EnterNameInfo);
		}
		if (TextUtils.isEmpty(userPwd)) {
			ShowToast.ShowTos(mContext, R.string.pleaseEnterPwd);
		} else if (userPwd != null && userPwd.length() < 6) {
			ShowToast.ShowTos(mContext, R.string.EnterNameInfo);
		}

		if (userName.length() >= 2 && userPwd.length() >= 6) {

			// ����HTTP POST�������
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("code", "372102b2070309eb"));
			params.add(new BasicNameValuePair("function", "Login"));
			params.add(new BasicNameValuePair("logname", userName));
			params.add(new BasicNameValuePair("logpwd", userPwd));
			params.add(new BasicNameValuePair("mac", Helper
					.getLocalMacAddress(mContext)));

			// async request
			new LoginTask(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params,
					ConnectNetAsyncTask.LOGIN).execute();
		}
	}

	class LoginTask extends ConnectNetAsyncTask {

		public LoginTask(Context context, String url,
				List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		// do result
		@Override
		public void doResult(String result) {
			if (result != null) {
				// do result
				System.out.println("result==========" + result);
				dealResult(result);
			}
		}
	}

	// do result
	private void dealResult(String msg) {
		if (msg.length() == 3) {
			int result = Integer.valueOf(msg);
			switch (result) {
			case 402:
				// ���Ƶ�¼�����������������ϵ�¼
				ShowToast.ShowTos(mContext, R.string.loginPwdFail);
				break;
			case 113:// ��¼����
				ShowToast.ShowTos(mContext, R.string.loginFail);
				break;
			case 112:// �����������û�������
				ShowToast.ShowTos(mContext, R.string.userNotexist);
				break;
			case 401:// �˺�����˲������¼
				ShowToast.ShowTos(mContext, R.string.nologining);
				break;
			default:
				break;
			}

		} else if (msg.length() > 3) {
			// ��¼�ɹ�
			// ��resultת��Ϊstream����������
			ByteArrayInputStream stream = new ByteArrayInputStream(
					msg.getBytes());
			UserInfo mUserInfo = ParseXmlLogin.parseXml(stream);
			if (mUserInfo != null) {
				ALearnApplication.getInstance().setUserInfo(mUserInfo);
			}
			// ��������
			SharedPreferences ferences = getSharedPreferences(
					SHAREPER_SAVEUSERNAME, Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = ferences.edit();
			editor.putString("userName", userName);
			if (bt_remenberPwd.isChecked()) {
				editor.putString("userPwd", userPwd);
			} else {
				editor.putString("userPwd", "");
			}
			editor.commit();

			Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
		}

	}

	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		// ��ʼ������ͼƬ�б�
		views.add(inflater.inflate(R.layout.one, null));
		views.add(inflater.inflate(R.layout.two, null));
		views.add(inflater.inflate(R.layout.three, null));
		View view = inflater.inflate(R.layout.four, null);
		views.add(view);

		ImageView ivChange = (ImageView) view.findViewById(R.id.iv_splashFour);
		ivChange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �����Ѿ�����
				changeView();
			}

		});
		// ��ʼ��Adapter
		splashAdapter = new SplashAdapter(views, this.getApplicationContext());

		vp = (ViewPager) splashView.findViewById(R.id.viewpager);
		vp.setAdapter(splashAdapter);
		// �󶨻ص�
		vp.setOnPageChangeListener(this);
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) splashView.findViewById(R.id.ll);
		dots = new ImageView[views.size()];
		// ѭ��ȡ��С��ͼƬ
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// ����Ϊ��ɫ
		}
		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// ����Ϊ��ɫ����ѡ��״̬
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = position;
	}

	// ������״̬�ı�ʱ����
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	// ����ǰҳ�汻����ʱ����
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	// ���µ�ҳ�汻ѡ��ʱ����
	@Override
	public void onPageSelected(int arg0) {
		// ���õײ�С��ѡ��״̬
		setCurrentDot(arg0);
	}

	public void changeView() {
		setContentView(loginView);
		initUI();
	}
}
