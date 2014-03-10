package com.ms.learn.activity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.ListviewAdpter;
import com.ms.learn.bean.RecommendListItem;
import com.ms.learn.bean.VideoInfo;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.widgets.PullRefreshListView;
import com.ms.learn.widgets.PullRefreshListView.OnRefreshListener;
import com.ms.learn.widgets.RemoteImageView;
import com.ms.learn.xml.ParseXmlPagerInfo;
import com.ms.learn.xml.ParseXmlrecommendInfo;

public class RecommendActivity extends Activity {
	private Context mContext = RecommendActivity.this;
	boolean reflashFlag = false;

	private PullRefreshListView mPullRefreshListView;
	private List<View> pager_Views = new ArrayList<View>();
	private List<VideoInfo> pagerVideoInfos;// viewpager������
	private List<RecommendListItem> mRecommendListItems = new ArrayList<RecommendListItem>();// lv����������
	private Button bt_back, bt_search;
	private ListviewAdpter lvAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ����
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ��title
		setContentView(R.layout.recommend_activity);
		initUI();
		// ��ȡ����
		getData();
		mPullRefreshListView = (PullRefreshListView) findViewById(R.id.lv_reflash);
		mPullRefreshListView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				reflashFlag = true;
				// ���»�ȡ����
				getData();
			}
		});
	}

	private void getData() {
		// ��ȡ�ֻ���Ƶ��GetTopTitle
		List<NameValuePair> params_title = new ArrayList<NameValuePair>();
		params_title
				.add(new BasicNameValuePair("code", ShareData.REQUEST_CODE));
		params_title.add(new BasicNameValuePair("function", "GetTopTitle"));

		new RecommendGetTitleAsyTask(mContext,
				ShareData.LANSHAOQI_ADDRESS_DOPOST, params_title,
				ConnectNetAsyncTask.GETRECOMMEND_TITLE).execute();
	}

	private void initUI() {
		bt_back = (Button) findViewById(R.id.bt_recoBack);
		bt_back.setOnClickListener(btClickListener);

		bt_search = (Button) findViewById(R.id.bt_recoSearch);
		bt_search.setOnClickListener(btClickListener);
	}

	private OnClickListener btClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == bt_back) {
				RecommendActivity.this.finish();
			} else if (v == bt_search) {
				// ��������
				Intent intent = new Intent(RecommendActivity.this,
						SearchVideoActivity.class);
				startActivity(intent);
			}
		}
	};

	private void doInitPagerViewData() {
		pager_Views.clear();
		// �첽��������֮�����pagerVideoInfos�е��������
		for (int i = 0; i < pagerVideoInfos.size(); i++) {
			RemoteImageView imgv = new RemoteImageView(RecommendActivity.this);
			imgv.setDefaultImage(R.drawable.default_big);
			pager_Views.add(imgv);
		}
	}

	// ��ȡ�ֻ���ͼƬ���첽//GetTopTitle
	class RecommendGetTitleAsyTask extends ConnectNetAsyncTask {

		public RecommendGetTitleAsyTask(Context context, String url,
				List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			// ��resultת��Ϊstream����������
			ByteArrayInputStream stream = new ByteArrayInputStream(
					result.getBytes());
			pagerVideoInfos = ParseXmlPagerInfo.parseXmlPagerInfo(stream);
			doInitPagerViewData();
			// ���Ż�ȡ�б��е�����, ��ȡ��Ƶ�б��GetClassTop
			List<NameValuePair> params_listVideo = new ArrayList<NameValuePair>();
			params_listVideo.add(new BasicNameValuePair("code",
					ShareData.REQUEST_CODE));
			params_listVideo.add(new BasicNameValuePair("function",
					"GetRecommend"));
			new RecommendVideoListAsyTask(mContext,
					ShareData.LANSHAOQI_ADDRESS_DOPOST, params_listVideo,
					ConnectNetAsyncTask.GETRECOMMEND_VIDEOLIST).execute();
		}
	}

	// ��ȡ�Ƽ�����Ƶ�б���첽//GetRecommend
	class RecommendVideoListAsyTask extends ConnectNetAsyncTask {

		public RecommendVideoListAsyTask(Context context, String url,
				List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			if (!reflashFlag) {
				// ��������,��resultת��Ϊstream����������
				ByteArrayInputStream stream = new ByteArrayInputStream(
						result.getBytes());
				mRecommendListItems = ParseXmlrecommendInfo
						.parseXmlrecommendInfo(stream);
				// ������
				lvAdapter = new ListviewAdpter(RecommendActivity.this,
						pager_Views, mRecommendListItems, pagerVideoInfos,
						RecommendActivity.this.getWindowManager());
				mPullRefreshListView.setAdapter(lvAdapter);
			} else {
				lvAdapter.notifyDataSetChanged();
				mPullRefreshListView.onRefreshComplete();
			}

		}

	}

}