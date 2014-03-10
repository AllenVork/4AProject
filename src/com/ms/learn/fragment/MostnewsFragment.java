package com.ms.learn.fragment;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.activity.ExamInfoActivity;
import com.ms.learn.activity.PostsDetailActivity;
import com.ms.learn.adapter.ExamListAdapter;
import com.ms.learn.adapter.MostNewsFragmentAdapter;
import com.ms.learn.bean.ExamListEntry;
import com.ms.learn.bean.MostNewsPostsEntry;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.xml.ParseXmlExamList;
import com.ms.learn.xml.ParseXmlMostNewsPosts;

public class MostnewsFragment extends Fragment {
	ListView listView;
	private List<MostNewsPostsEntry> mostNewsPostsEntrys;
	MostNewsFragmentAdapter mMostNewsFragmentAdapter;
	public static boolean isSendPost=false;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view = getView();

		listView = (ListView) view.findViewById(R.id.lv_mostnewsList);
		getData();
		listView.setOnItemClickListener(clickListener);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.mostnews_fragment, container, false);
	}

	class GetThemeList extends ConnectNetAsyncTask {

		public GetThemeList(Context context, String url,
				List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			// 处理结果
			if (result != null) {
				System.out.println("+++++++++++++++" + result);
				ByteArrayInputStream stream = new ByteArrayInputStream(
						result.getBytes());
				mostNewsPostsEntrys = ParseXmlMostNewsPosts
						.parseXmlMostNewsPosts(stream);
				mMostNewsFragmentAdapter = new MostNewsFragmentAdapter(
						mostNewsPostsEntrys, getActivity());
				listView.setAdapter(mMostNewsFragmentAdapter);
			}

		}

	}

	private OnItemClickListener clickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			MostNewsPostsEntry mostNewsPostsEntry = mMostNewsFragmentAdapter.getItem(arg2);

			
			Intent intent = new Intent(getActivity(), PostsDetailActivity.class);
			intent.putExtra("mostNewsPostsEntry", mostNewsPostsEntry);
			startActivity(intent);

		}
	};

	@Override
	public void onResume() {
		if(isSendPost){
			getData();
			isSendPost=false;
		}
		super.onResume();
	}
	
	private void getData(){
		if (CheckConnectNet.isNetworkConnected(getActivity())) {
			List<NameValuePair> params_GetThemeList = new ArrayList<NameValuePair>();
			params_GetThemeList.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
			params_GetThemeList.add(new BasicNameValuePair("function","GetThemeList"));
			params_GetThemeList.add(new BasicNameValuePair("tid",ALearnApplication.getInstance().getPostsTid()));
			params_GetThemeList.add(new BasicNameValuePair("ftype", "1"));
			new GetThemeList(getActivity(), ShareData.LANSHAOQI_ADDRESS_DOPOST,
					params_GetThemeList, ConnectNetAsyncTask.GETTHEMELIST)
					.execute();

		} else {
			ShowToast.ShowTos(getActivity(), R.string.noNetwork);
		}
	}
	

}
