package com.ms.learn.fragment;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.learn.R;
import com.ms.learn.net.ConnectNetAsyncTask;

public class SendPostsFragment  extends Fragment{
	
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view =getView();
		
		
		
		/*if(CheckConnectNet.isNetworkConnected(getActivity())){
		List<NameValuePair> params_GetThemeList = new ArrayList<NameValuePair>();
		params_GetThemeList.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
		params_GetThemeList.add(new BasicNameValuePair("function","GetThemeList"));
		params_GetThemeList.add(new BasicNameValuePair("tid",ALearnApplication.getInstance().getPostsTid()));
		params_GetThemeList.add(new BasicNameValuePair("ftype","2"));
		new GetThemeList(getActivity(), ShareData.LANSHAOQI_ADDRESS_DOPOST, params_GetThemeList,ConnectNetAsyncTask.GETTHEMELIST).execute();
		
		}else{
			ShowToast.ShowTos(getActivity(), R.string.noNetwork);
		}*/

		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
       return inflater.inflate(R.layout.sendposts_fragment, container, false);
	}

	class GetThemeList extends ConnectNetAsyncTask {

		public GetThemeList(Context context, String url, List<NameValuePair> params,	int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			//处理结果
			if(result!=null){
				
			}
			
		}
		
	}
	
  
}
