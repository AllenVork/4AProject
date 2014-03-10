package com.ms.learn.fragment;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.activity.PostsDetailActivity;
import com.ms.learn.adapter.MostNewsFragmentAdapter;
import com.ms.learn.bean.MostNewsPostsEntry;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.xml.ParseXmlMostNewsPosts;

public class SearchPostsFragment  extends Fragment{
	ListView listView;
	private Button bt_search;
	private EditText tv_search;
	private List<MostNewsPostsEntry>  mostNewsPostsEntrys;
	MostNewsFragmentAdapter mMostNewsFragmentAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view =getView();
		
		tv_search=(EditText) view.findViewById(R.id.tv_searchContent);
		listView=(ListView) view.findViewById(R.id.lv_searchList);
		bt_search=(Button) view.findViewById(R.id.bt_search);
		
		bt_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//获取搜索的内容
				if(tv_search.getText().toString().equals(null)||tv_search.getText().toString().equals("")){
					ShowToast.ShowTos(getActivity(), R.string.pleaseentercontent);
				}else {
				
				if(CheckConnectNet.isNetworkConnected(getActivity())){
				List<NameValuePair> params_GetThemeList = new ArrayList<NameValuePair>();
				params_GetThemeList.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
				params_GetThemeList.add(new BasicNameValuePair("function","GetThemeListByWhere"));
				try {
					params_GetThemeList.add(new BasicNameValuePair("WhereTxt",URLEncoder.encode(tv_search.getText().toString(), "utf-8")));
				
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				new GetThemeListByWhere(getActivity(), ShareData.LANSHAOQI_ADDRESS_DOPOST, params_GetThemeList,ConnectNetAsyncTask.GETTHEMELISTBYWHERE).execute();
				
				}else{
					ShowToast.ShowTos(getActivity(), R.string.noNetwork);
				}
				
			}
			}
			
		});
		
		

		listView.setOnItemClickListener(clickListener);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
       return inflater.inflate(R.layout.searchposts_fragment, container, false);
	}

	
	class GetThemeListByWhere extends ConnectNetAsyncTask {

		public GetThemeListByWhere(Context context, String url, List<NameValuePair> params,	int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			//处理结果
			if(result!=null&&!"113".equals(result)){
				System.out.println("+++++++++++++++"+result);
				ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
				mostNewsPostsEntrys=ParseXmlMostNewsPosts.parseXmlMostNewsPosts(stream);
				if(mostNewsPostsEntrys.size()!=0){
					mMostNewsFragmentAdapter =new MostNewsFragmentAdapter(mostNewsPostsEntrys, getActivity());
					listView.setAdapter(mMostNewsFragmentAdapter);
				}else {
					ShowToast.ShowTos(getActivity(), R.string.nosearchContent);
				}
				
			}
			
		}
		
	}
	
   private OnItemClickListener clickListener=new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		
		MostNewsPostsEntry mostNewsPostsEntry = mMostNewsFragmentAdapter.getItem(arg2);

		
		Intent intent = new Intent(getActivity(), PostsDetailActivity.class);
		intent.putExtra("mostNewsPostsEntry", mostNewsPostsEntry);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.animation_fadefromright,R.anim.animation_fadefromleft);  
        
	}
};
	
}
