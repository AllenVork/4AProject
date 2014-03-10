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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.NewsLvAdpter;
import com.ms.learn.bean.News;
import com.ms.learn.bean.NewsItem;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.widgets.PullRefreshListView;
import com.ms.learn.widgets.RemoteImageView;
import com.ms.learn.widgets.PullRefreshListView.OnRefreshListener;
import com.ms.learn.xml.ParseXmlNewsLv;

public class NewsActivity extends Activity {
	private Context context=NewsActivity.this;
	private PullRefreshListView lv_News;
	private NewsLvAdpter newsLvAdpter;
	private News news;
	private List<NewsItem> newsPager;
	private List<NewsItem> newsLvList;
	private List<View> pager_Views = new ArrayList<View>();
	private boolean reflashFlag=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_activity);
		initUI();
	}
	
	private  void doInitPagerViewData(){
		pager_Views.clear();
	// �첽��������֮�����pagerVideoInfos�е��������
			for (int i = 0; i <newsPager.size(); i++) {
				RemoteImageView imgv = new RemoteImageView(NewsActivity.this);
				imgv.setDefaultImage(R.drawable.default_big);
				pager_Views.add(imgv);
			}
}

	private void initUI() {
	      findViewById(R.id.bt_newsBack).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NewsActivity.this.finish();
				
			}
		});
		  lv_News=(PullRefreshListView) findViewById(R.id.lv_newReflash);
		  lv_News.setonRefreshListener(new OnRefreshListener() {
				public void onRefresh() {
					reflashFlag=true;
					//���»�ȡ����
					getData();
				}
			});
		  lv_News.setOnItemClickListener(lvOnclick);
		  //��ȡ����
		 getData();
	}
	
	private void getData() {
		 if(CheckConnectNet.isNetworkConnected(context)){
			  //��ȡ���е���Ƶ����
				List<NameValuePair> params_GetNews = new ArrayList<NameValuePair>();
				params_GetNews.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
				params_GetNews.add(new BasicNameValuePair("function","GetAllNewsTopandList"));//��Ҫ�޸�
			    new GetNewsTask(context, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_GetNews, ConnectNetAsyncTask.GETNEWS).execute();
		  }else {
			  ShowToast.ShowTos(context, R.string.noNetwork);
		  }
	}

	private OnItemClickListener lvOnclick=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			System.out.println("++++arg2+++++"+arg2);
			Intent intent=new Intent(context,ShowNewsActivity.class);
			intent.putExtra("newsID", newsLvList.get(arg2-1).getNewsId());
			context.startActivity(intent);			
		}
	};
	
	class GetNewsTask extends ConnectNetAsyncTask{

		public GetNewsTask(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			//��������
			//��resultת��Ϊstream����������
			if(result!=null&&!reflashFlag){
				ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
				news=ParseXmlNewsLv.parseXmlNewsLv(stream);
				newsPager=news.getNewsPagers();
				newsLvList=news.getNewsLvItems();
				doInitPagerViewData();
				//��ViewPager��ͼ��list(pager_Views)����ȥ
				newsLvAdpter=new NewsLvAdpter(context, pager_Views, newsLvList, newsPager);
				lv_News.setAdapter(newsLvAdpter);
			}else{
				newsLvAdpter.notifyDataSetChanged();
				lv_News.onRefreshComplete();
			}
		}
	}
}
